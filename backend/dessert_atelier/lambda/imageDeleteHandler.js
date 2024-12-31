const { S3Client, DeleteObjectCommand } = require('@aws-sdk/client-s3');

// S3 클라이언트 생성
const s3 = new S3Client({
    region: 'ap-northeast-2', // 버킷 리전
});

// S3에 저장된 크기 별 경로
const RESIZE_SIZES = ['s', 'm', 'l'];

exports.handler = async (event) => {
    try {
        // SQS 메시지 처리
        const records = event.Records;
        for (const record of records) {
            const message = JSON.parse(record.body); // SQS 메시지의 body 파싱
            const { operator, path, filenames, bucketname } = message;

            if (operator !== 'delete') {
                console.warn(`Unsupported operator: ${operator}`);
                continue;
            }

            if (!bucketname || !path || !filenames) {
                console.warn('Invalid message format:', message);
                continue;
            }

            // 파일 삭제 처리
            await deleteResizedFiles(bucketname, path, filenames);
        }

        return {
            statusCode: 200,
            body: 'File deletion completed successfully',
        };
    } catch (error) {
        console.error('Error processing SQS message:', error);
        return {
            statusCode: 500,
            body: 'Failed to process SQS message',
        };
    }
};

async function deleteResizedFiles(bucketName, path, filenames) {
    const deletePromises = [];

    for (const basename of filenames) {
        for (const size of RESIZE_SIZES) {
            const objectKey = `resize/${path}${size}/${basename}.avif`;
            console.log(`Attempting to delete: ${objectKey}`);

            const deleteCommand = new DeleteObjectCommand({
                Bucket: bucketName,
                Key: objectKey,
            });

            deletePromises.push(
                s3
                    .send(deleteCommand)
                    .then(() => console.log(`Deleted: ${objectKey}`))
                    .catch((error) => {
                        if (error.name === 'NoSuchKey') {
                            console.log(`File not found: ${objectKey}`);
                        } else {
                            console.error(`Failed to delete ${objectKey}:`, error);
                        }
                    })
            );
        }
    }

    // 모든 삭제 요청 병렬 처리
    await Promise.all(deletePromises);
    console.log('Successfully deleted all specified files');
}