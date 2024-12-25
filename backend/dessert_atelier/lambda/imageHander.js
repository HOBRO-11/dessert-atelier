const { S3Client, GetObjectCommand, DeleteObjectCommand, PutObjectCommand } = require('@aws-sdk/client-s3');
const sharp = require('sharp');
const stream = require('stream');
const util = require('util');

// S3 클라이언트 생성 (리전 추가)
const s3 = new S3Client({
    region: 'ap-northeast-2', // 버킷이 위치한 리전
});

// 스트림을 버퍼로 변환하는 유틸리티 함수
const streamToBuffer = util.promisify((stream, callback) => {
    const chunks = [];
    stream.on('data', chunk => chunks.push(chunk));
    stream.on('end', () => callback(null, Buffer.concat(chunks)));
    stream.on('error', callback);
});

const SUPPORTED_FORMATS = ['jpeg', 'png', 'jpg'];
const RESIZE_SIZES = [
    { suffix: 'small', width: 100 },
    { suffix: 'middle', width: 250 },
    { suffix: 'large', width: 500 },
];

exports.handler = async (event) => {
    try {
        const records = event.Records;

        for (const record of records) {
            const message = JSON.parse(record.body);
            const { operator, filename, path, bucketname } = message;

            if (operator === 'create') {
                await handleCreate(bucketname, path, filename);
            } else if (operator === 'delete') {
                await handleDelete(bucketname, path, filename);
            } else {
                console.warn(`Unknown operator: ${operator}`);
            }
        }

        return { statusCode: 200, body: 'Operation completed successfully' };
    } catch (error) {
        console.error('Error processing message:', error);
        return { statusCode: 500, body: 'Failed to process message' };
    }
};

async function handleCreate(bucketName, path, filenames) {
    const resizePromises = [];

    for (const file of filenames) {
        const objectKey = `${path}${file}`;
        try {
            console.log(`Fetching file ${file} from S3`);
            

            // S3에서 객체 가져오기
            const getObjectCommand = new GetObjectCommand({
                Bucket: bucketName,
                Key: objectKey,
            });

            const originalImage = await s3.send(getObjectCommand);

            // originalImage.Body는 스트림이므로 이를 버퍼로 변환
            const buffer = await streamToBuffer(originalImage.Body);

            const format = (await sharp(buffer).metadata()).format;

            if (!SUPPORTED_FORMATS.includes(format)) {
                console.warn(`Unsupported format (${format}) for file: ${file}`);
                const deleteObjectCommand = new DeleteObjectCommand({
                    Bucket: bucketName,
                    Key: objectKey,
                });
                await s3.send(deleteObjectCommand);
                continue;
            }

            const baseFileName = file.split('/').pop().replace(/\.[^/.]+$/, ''); // 파일 이름 추출 및 확장자 제거
            RESIZE_SIZES.forEach(({ suffix, width }) => {
                const resizePromise = sharp(buffer)
                    .resize({ width })
                    .toFormat('avif')
                    .toBuffer()
                    .then((resizedBuffer) => {
                        const resizedKey = `${path}${suffix}/${baseFileName}.avif`;
                        const putObjectCommand = new PutObjectCommand({
                            Bucket: bucketName,
                            Key: resizedKey,
                            Body: resizedBuffer,
                            ContentType: 'image/avif',
                        });
                        return s3.send(putObjectCommand);
                    });

                resizePromises.push(resizePromise);
            });

            console.log(`Resizing ` + objectKey + ` for different sizes`);

            // 원본 파일 삭제
            const deleteObjectCommand = new DeleteObjectCommand({
                Bucket: bucketName,
                Key: objectKey,
            });
            resizePromises.push(s3.send(deleteObjectCommand));

        } catch (error) {
            console.error(`Failed to process file`+  objectKey + `:`, error);
        }
    }

    // 모든 리사이즈 및 삭제 작업 병렬 처리
    await Promise.all(resizePromises);
    console.log(`Successfully processed and resized all files`);
}

async function handleDelete(bucketName, path, filenames) {
    for (const file of filenames) {
        try {
            const baseFileName = file.split('/').pop().replace(/\.[^/.]+$/, ''); // 파일 이름 추출 및 확장자 제거
            const deletePromises = RESIZE_SIZES.map(({ suffix }) => {
                const resizedKey = `${path}${suffix}/${baseFileName}.avif`;
                const deleteObjectCommand = new DeleteObjectCommand({
                    Bucket: bucketName,
                    Key: resizedKey,
                });
                return s3.send(deleteObjectCommand);
            });

            await Promise.all(deletePromises);
            console.log(`Successfully deleted resized images for ${file}`);
        } catch (error) {
            console.error(`Failed to delete resized images for ${file}:`, error);
        }
    }
}