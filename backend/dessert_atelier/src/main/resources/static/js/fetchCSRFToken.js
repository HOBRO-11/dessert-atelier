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