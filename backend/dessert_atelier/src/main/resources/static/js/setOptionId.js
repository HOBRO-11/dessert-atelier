function setOptionId(id) {
    // 현재 활성화된 input 필드에 product.id를 설정
    const activeInput = document.querySelector('input[name="optionCreateForms[0].description"]');
    if (activeInput) {
        activeInput.value = id; // 예시로 description 필드에 id를 설정
    }
}