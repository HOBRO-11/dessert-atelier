document.getElementById('submitBtn').addEventListener('click', async () => {
    const form = document.getElementById('productForm');
    const formData = new FormData(form);

    const imageInput = document.getElementById('image');
    const file = imageInput.files[0];

    if (!file) {
        alert('Please select an image!');
        return;
    }

    formData.append('image', file);

    try {
        const response = await fetch('/product', {
            method: 'POST',
            body: formData,
        });

        if (response.ok) {
            const result = await response.json();
            alert(`Product uploaded successfully! ID: ${result.id}`);
        } else {
            alert('Failed to upload product.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred during the upload.');
    }
});