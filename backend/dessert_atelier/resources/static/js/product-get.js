document.addEventListener('DOMContentLoaded', () => {
    // 기본 이미지 경로 설정
    const BASE_IMAGE_PATH = '/img/product/';

    // URL에서 productId 추출
    const url = new URL(window.location.href);
    const pathParts = url.pathname.split('/');
    let productId = pathParts[pathParts.indexOf('products') + 1];

    // productId가 없으면 0으로 설정
    productId = productId ? productId : 0;

    // 서버에 요청하여 상품 목록(List) 가져오기
    fetch(`/api/products/${productId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 응답 오류: ${response.status}`);
            }
            return response.json(); // List 형식의 데이터 반환
        })
        .then(products => {
            // 상품 목록을 화면에 표시
            const productListContainer = document.getElementById('product-list');
            productListContainer.innerHTML = ''; // 기존 목록 초기화

            if (Array.isArray(products) && products.length > 0) {
                products.forEach(product => {
                    const thumbPath = product.thumb
                        ? `${BASE_IMAGE_PATH}${product.thumb}`
                        : `${BASE_IMAGE_PATH}default.jpg`;

                    // 각 상품을 나타내는 HTML 구조 생성
                    const productItem = document.createElement('div');
                    productItem.classList.add('product-item');

                    productItem.innerHTML = `
                        <img src="${thumbPath}" alt="${product.name}">
                        <h2>${product.name}</h2>
                        <p>가격: ${product.price}원</p>
                        <p>수량: ${product.quantity}</p>
                    `;

                    // 생성된 상품 항목을 목록에 추가
                    productListContainer.appendChild(productItem);
                });
            } else {
                productListContainer.innerHTML = `<p>상품 목록을 불러오는 데 실패했습니다.</p>`;
            }
        })
        .catch(error => {
            console.error('상품 목록을 불러오는 중 오류 발생:', error);
            document.getElementById('product-list').innerHTML = `<p>상품 목록을 불러오지 못했습니다.</p>`;
        });
});