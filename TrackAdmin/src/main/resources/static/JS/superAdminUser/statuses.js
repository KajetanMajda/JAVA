document.addEventListener('DOMContentLoaded', function() {
    const addStatusButton = document.getElementById('addStatusButton');
    const newStatusNameInput = document.getElementById('newStatusName');

    const deleteStatusButton = document.getElementById('deleteStatusButton');
    const deleteStatusSelect = document.getElementById('deleteStatusSelect');

    // Pobierz i wypełnij listę statusów
    fetch('/status/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(status => {
                const deleteOption = document.createElement('option');
                deleteOption.value = status.name;
                deleteOption.textContent = status.name;
                deleteStatusSelect.appendChild(deleteOption);

            });
        })
        .catch(error => {
            console.error('Błąd pobierania statusów:', error);
        });

    // Obsługa dodawania statusu
    addStatusButton.addEventListener('click', function() {
        const statusName = newStatusNameInput.value;
        if (statusName) {
            fetch('/status/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `name=${statusName}`,
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    // Odśwież listę statusów
                    location.reload();
                })
                .catch(error => {
                    console.error('Błąd dodawania statusu:', error);
                });
        }
    });

    // Obsługa usuwania statusu
    deleteStatusButton.addEventListener('click', function() {
        const selectedStatus = deleteStatusSelect.value;
        if (selectedStatus) {
            fetch(`/status/delete?name=${selectedStatus}`, {
                method: 'DELETE',
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    // Odśwież listę statusów
                    location.reload();
                })
                .catch(error => {
                    console.error('Błąd usuwania statusu:', error);
                });
        }
    });
});

// obsluga aktualizaji statusu
document.addEventListener('DOMContentLoaded', function() {
    const updateStatusButton = document.getElementById('updateStatusButton');
    const updateStatusSelect = document.getElementById('updateStatusSelect');
    const updatedStatusNameInput = document.getElementById('updatedStatusName');

    fetch('/status/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(status => {
                const option = document.createElement('option');
                option.value = status.id;
                option.textContent = status.name;
                updateStatusSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania statusów:', error);
        });

    updateStatusButton.addEventListener('click', function() {
        const selectedStatusId = updateStatusSelect.value;
        const updatedStatusName = updatedStatusNameInput.value;
        if (selectedStatusId && updatedStatusName) {
            fetch(`/status/update?id=${selectedStatusId}&name=${updatedStatusName}`, {
                method: 'PUT',
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    // Odśwież listę statusów
                    location.reload();
                })
                .catch(error => {
                    console.error('Błąd aktualizacji statusu:', error);
                });
        }
    });
});