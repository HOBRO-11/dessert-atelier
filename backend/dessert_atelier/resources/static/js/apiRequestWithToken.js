async function apiRequestWithToken(url, options, refreshToken) {
    try {
        // 기존 Access Token으로 요청
        const response = await fetch(url, options);

        if (response.status === 401) { // Access Token이 만료된 경우
            console.warn('Access token expired. Attempting to refresh...');

            // Refresh Token으로 Access Token 재발급
            const data = await refreshAccessToken(refreshToken);
            const newAccessToken = data.accessToken;

            // 로컬 저장소나 메모리에서 토큰 업데이트
            localStorage.setItem('accessToken', newAccessToken);

            // 헤더에 새로운 Access Token 설정 후 요청 재시도
            options.headers['Authorization'] = `Bearer ${newAccessToken}`;
            return await fetch(url, options);
        }

        return response; // 정상 응답
    } catch (error) {
        console.error('Error during API request:', error);
        throw error;
    }
}