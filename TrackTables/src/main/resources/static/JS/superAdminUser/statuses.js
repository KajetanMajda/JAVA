document.addEventListener('DOMContentLoaded', function () {
    const addStatusButton = document.getElementById('addStatusButton');
    const newStatusNameInput = document.getElementById('newStatusName');

    const deleteStatusButton = document.getElementById('deleteStatusButton');
    const deleteStatusSelect = document.getElementById('deleteStatusSelect');

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

    addStatusButton.addEventListener('click', function () {
        const statusName = newStatusNameInput.value;
        if (statusName) {
            if (window.confirm(`Czy na pewno chcesz dodać nowy status: ${statusName}?`)) {
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
        }
    });

    deleteStatusButton.addEventListener('click', function () {
        const selectedStatus = deleteStatusSelect.value;
        if (selectedStatus) {
            if (window.confirm('Czy na pewno chcesz usunąć ten status?')) {
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
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
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

    updateStatusButton.addEventListener('click', function () {
        const selectedStatusId = updateStatusSelect.value;
        const updatedStatusName = updatedStatusNameInput.value;
        if (selectedStatusId && updatedStatusName) {
            if (window.confirm(`Czy na pewno chcesz zaktualizować status o ID ${selectedStatusId} na: ${updatedStatusName}?`)) {
                fetch(`/status/update?id=${selectedStatusId}&name=${updatedStatusName}`, {
                    method: 'PUT',
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd aktualizacji statusu:', error);
                    });
            }
        }
    });
});
