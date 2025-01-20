document.addEventListener('DOMContentLoaded', function() {
    const button = document.getElementById('dynamicButton');
    const method = button.getAttribute('data-method');
    const uri = button.getAttribute('data-uri');

    // 버튼 텍스트 설정
    button.textContent = getButtonText(method);

    // 버튼 클릭 이벤트 리스너 추가
    button.addEventListener('click', function() {
        handleButtonClick(method, uri);
    });
});

function getButtonText(method) {
    switch(method.toUpperCase()) {
        case 'GET':
            return '이동';
        case 'POST':
            return '등록';
        case 'PUT':
            return '수정';
        case 'DELETE':
            return '삭제';
        default:
            return '확인';
    }
}

function handleButtonClick(method, uri) {
    if (method.toUpperCase() === 'GET') {
        // GET 메서드인 경우 해당 URI로 이동
        window.location.href = uri;
        return;
    }

    // GET 이외의 메서드 처리
    fetch(uri, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            // CSRF 토큰 필요한 경우 추가
            // 'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
        },
        // 필요한 경우 body 추가 가능
        // body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('요청 실패');
        }
        return response.json();
    })
    .then(data => {
        alert('요청 성공');
        window.location.href = '/'; // root 경로로 이동
    })
    .catch(error => {
        alert('요청 실패: ' + error.message);
    });
}