document.addEventListener('DOMContentLoaded', function() {
    const addProjectButton = document.getElementById('addProjectButton');
    const newProjectNameInput = document.getElementById('newProject');

    addProjectButton.addEventListener('click', function() {
        const projectName = newProjectNameInput.value;
        if (projectName) {
            if (window.confirm('Czy na pewno chcesz dodać nowy projekt?')) {
                fetch('/projects/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `name=${projectName}`,
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd dodawania projektu:', error);
                    });
            }
        }
    });
});