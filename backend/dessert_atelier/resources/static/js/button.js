const uploadForm = document.getElementById('uploadForm');
const imagesContainer = document.getElementById('imagesContainer');
const addImageButton = document.getElementById('addImageButton');
const metadataField = document.getElementById('metadataField');
const dpIdField = document.getElementById('dpId');
const rateField = document.getElementById('rate');
const commentField = document.getElementById('comment');
const originField = document.getElementById('origin');

let imageCount = 0;

// 이미지 추가 버튼 이벤트
addImageButton.addEventListener('click', () => {
    const imageContainer = document.createElement('div');
    imageContainer.className = 'image-container';

    const inputFile = document.createElement('input');
    inputFile.type = 'file';
    inputFile.name = `images`;
    inputFile.accept = 'image/*';

    const deleteButton = document.createElement('button');
    deleteButton.type = 'button';
    deleteButton.innerText = 'Delete';
    deleteButton.addEventListener('click', () => {
        imagesContainer.removeChild(imageContainer);
    });

    imageContainer.appendChild(inputFile);
    imageContainer.appendChild(deleteButton);
    imagesContainer.appendChild(imageContainer);

    imageCount++;
});

// 메타데이터 생성 함수
function generateMetadata() {
    const images = document.querySelectorAll('input[type="file"]');
    const imageMetadata = [];

    images.forEach((image, index) => {
        if (image.files[0]) {
            imageMetadata.push({
                name: image.files[0].name,
                size: image.files[0].size,
                type: image.files[0].type // 파일의 MIME 타입 추가
            });
        }
    });

    return JSON.stringify(imageMetadata);
}

// 폼 제출 이벤트 처리
uploadForm.addEventListener('submit', (event) => {
    event.preventDefault(); // 기본 제출 동작 방지

    // 메타데이터 생성 및 숨겨진 필드에 삽입
    metadataField.value = generateMetadata();

    // FormData로 모든 데이터를 수집
    const formData = new FormData();

    // 메타데이터를 첫 번째로 추가
    formData.append('metadata', metadataField.value);

    // 텍스트 필드 및 기타 정보 추가
    formData.append('dpId', dpIdField.value);
    formData.append('rate', rateField.value);
    formData.append('comment', commentField.value);
    formData.append('origin', originField.value);

    // 이미지 추가
    const images = document.querySelectorAll('input[type="file"]');
    images.forEach((image, index) => {
        if (image.files[0]) {
            formData.append(`images`, image.files[0]);
        }
    });

    // 폼 데이터를 서버로 전송
    fetch(uploadForm.action, {
        method: 'POST',
        body: formData
    }).then(response => {
        if (response.ok) {
            alert('Upload successful!');
        } else {
            alert('Upload failed!');
        }
    }).catch(error => {
        console.error('Error:', error);
    });
});