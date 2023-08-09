document.addEventListener('DOMContentLoaded', function () {
    const deleteElementButton = document.getElementById('deleteElementButton');
    const deleteElementsSelect = document.getElementById('deleteElementsSelect');

    fetch('/elements/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(element => {
                const deleteOption = document.createElement('option');
                deleteOption.value = element.id;
                deleteOption.textContent = `Element ${element.id}`;
                deleteElementsSelect.appendChild(deleteOption);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania elementów:', error);
        });

    deleteElementButton.addEventListener('click', function () {
        const selectedElementId = deleteElementsSelect.value;
        if (selectedElementId) {
            if (window.confirm('Czy na pewno chcesz usunąć ten element?')) {
                fetch(`/elements/delete?id=${selectedElementId}`, {
                    method: 'DELETE',
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd usuwania elementu:', error);
                    });
            }
        }
    });

});
