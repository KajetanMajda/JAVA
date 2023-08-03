document.addEventListener('DOMContentLoaded', function() {
    const addProjectButton = document.getElementById('addProjectButton');
    const newProjectNameInput = document.getElementById('newProject');

    const deleteProjectButton = document.getElementById('deleteProjectButton');
    const deleteProjectSelect = document.getElementById('deleteProjectSelect');

    const updateProjectButton = document.getElementById('updateProjectButton');
    const updateProjectSelect = document.getElementById('updateProjectSelect');
    const updatedProjectNameInput = document.getElementById('updateProjectName');

    // Pobierz i wypełnij listę projektów
    fetch('/projects/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(project => {
                const deleteOption = document.createElement('option');
                deleteOption.value = project.name;
                deleteOption.textContent = project.name;
                deleteProjectSelect.appendChild(deleteOption);

                const updateOption = document.createElement('option');
                updateOption.value = project.id;
                updateOption.textContent = project.name;
                updateProjectSelect.appendChild(updateOption);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania projektów:', error);
        });

    // Obsługa dodawania projektu
    addProjectButton.addEventListener('click', function() {
        const projectName = newProjectNameInput.value;
        if (projectName) {
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
                    // Odśwież listę projektów
                    location.reload();
                })
                .catch(error => {
                    console.error('Błąd dodawania projektu:', error);
                });
        }
    });

    // Obsługa usuwania projektu
    deleteProjectButton.addEventListener('click', function() {
        const selectedProject = deleteProjectSelect.value;
        if (selectedProject) {
            fetch(`/projects/delete?name=${selectedProject}`, {
                method: 'DELETE',
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    // Odśwież listę projektów
                    location.reload();
                })
                .catch(error => {
                    console.error('Błąd usuwania projektu:', error);
                });
        }
    });

    // Obsługa aktualizacji projektu
    updateProjectButton.addEventListener('click', function() {
        const selectedProjectId = updateProjectSelect.value;
        const updatedProjectName = updatedProjectNameInput.value;
        if (selectedProjectId && updatedProjectName) {
            fetch(`/projects/update?id=${selectedProjectId}&name=${updatedProjectName}`, {
                method: 'PUT',
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    // Odśwież listę projektów
                    location.reload();
                })
                .catch(error => {
                    console.error('Błąd aktualizacji projektu:', error);
                });
        }
    });
});
