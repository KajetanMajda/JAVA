document.addEventListener('DOMContentLoaded', function() {
    const deleteElementButton = document.getElementById('deleteElementButton');
    const deleteElementsSelect = document.getElementById('deleteElementsSelect');

    // Pobierz elementy
    fetch('/elements/all')
        .then(response => response.json())
        .then(data => {
            // Przetwarzaj dane
            data.forEach(element => {
                const deleteOption = document.createElement('option');
                deleteOption.value = element.lp;
                deleteOption.textContent = `Element ${element.lp}`;
                deleteElementsSelect.appendChild(deleteOption);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania elementów:', error);
        });

// Obsługa usuwania elementu
    deleteElementButton.addEventListener('click', function() {
        const selectedElementLp = deleteElementsSelect.value;
        if (selectedElementLp) {
            fetch(`/elements/delete?lp=${selectedElementLp}`, {
                method: 'DELETE',
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    // Odśwież listę elementów
                    location.reload();
                })
                .catch(error => {
                    console.error('Błąd usuwania elementu:', error);
                });
        }
    });

});