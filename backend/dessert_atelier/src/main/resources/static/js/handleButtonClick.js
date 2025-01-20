async function fetchCSRFToken() {
    try {
        const response = await fetch('/csrf-token', {
            method: 'GET',
            credentials: 'include' // 쿠키 포함
        });

        if (!response.ok) {
            throw new Error('CSRF 토큰 요청 실패');
        }

        return response.headers.get('X-CSRF-TOKEN');
    } catch (error) {
        console.error('CSRF 토큰 가져오기 실패:', error);
        throw error;
    }
}

async function handleButtonClick(method, uri) {
    if (method.toUpperCase() === 'GET') {
        window.location.href = uri;
        return;
    }

    try {
        // CSRF 토큰 가져오기
        const csrfToken = await fetchCSRFToken();

        const response = await fetch(uri, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            credentials: 'include', // 쿠키 포함
            // 필요한 경우 body 추가 가능
            // body: JSON.stringify(data)
        });

        if (!response.ok) {
            // 401 에러 처리
            if (response.status === 401) {
                // 현재 경로를 리다이렉트 파라미터로 전달
                const currentPath = window.location.pathname + window.location.search;
                window.location.href = `/auth/login?redirect=${encodeURIComponent(currentPath)}`;
                return;
            }

            throw new Error('요청 실패');
        }

        const data = await response.json();
        alert('요청 성공');
        window.location.href = '/'; // root 경로로 이동
    } catch (error) {
        console.error('요청 중 오류:', error);
        alert('요청 실패: ' + error.message);
    }
}



{/* <form th:action="@{/login}" method="post">
    <!-- 로그인 폼 -->
    <input type="hidden" th:value="${redirectUrl}" name="redirectUrl">
</form> */}