document.addEventListener('DOMContentLoaded', function() {
    fetch('/userRole/all')
        .then(response => response.json())
        .then(data => {
            const selectElement = document.getElementById('role');
            data.forEach(role => {
                const option = document.createElement('option');
                option.value = role.name;
                option.textContent = role.name;
                selectElement.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania ról:', error);
        });
});