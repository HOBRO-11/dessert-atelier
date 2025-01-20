let optionCount = 1;

function addOptionForm() {
    const optionFormsDiv = document.getElementById('optionCreateForms');
    const newOptionForm = document.createElement('div');
    newOptionForm.classList.add('optionCreateForm');
    newOptionForm.innerHTML = `
                <h4>옵션 ${optionCount + 1}</h4>
                <label for="optionDescription">옵션 설명:</label>
                <input type="text" name="optionCreateForms[${optionCount}].description" required><br><br>

                <label for="optionPrice">옵션 가격:</label>
                <input type="number" name="optionCreateForms[${optionCount}].price" required><br><br>

                <label for="optionLayer">옵션 레이어:</label>
                <input type="number" name="optionCreateForms[${optionCount}].optionLayer" required><br><br>
            `;
    optionFormsDiv.appendChild(newOptionForm);
    optionCount++;
}