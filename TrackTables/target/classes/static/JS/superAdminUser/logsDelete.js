document.addEventListener('DOMContentLoaded', function() {
    const deleteButton = document.getElementById('deleteButton');

    deleteButton.addEventListener('click', function() {
        if (confirm("Czy na pewno chcesz usunąć zawartość tabeli?")) {
            fetch('/logs/clearLogs', {
                method: 'DELETE'
            })
                .then(response => response.json())
                .then(data => {
                    location.reload();
                })
                .catch(error => {
                    console.error('Błąd usuwania zawartości tabeli:', error);
                });
        }
    });
});