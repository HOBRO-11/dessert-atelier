async function refreshAccessToken(refreshToken) {
    const response = await fetch('/auth/refresh', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ refreshToken }),
    });

    if (!response.ok) {
        throw new Error('Failed to refresh access token');
    }

    // 응답 헤더에서 Access Token 읽기
    const newAccessToken = response.headers.get('Access-Token');
    if (!newAccessToken) {
        throw new Error('Access token not found in response headers');
    }

    return newAccessToken;
}

// 사용 예시
refreshAccessToken('your_refresh_token_here')
    .then((accessToken) => {
        console.log('New Access Token:', accessToken);
    })
    .catch((error) => {
        console.error('Error refreshing token:', error);
    });