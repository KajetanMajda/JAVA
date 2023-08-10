document.addEventListener('DOMContentLoaded', function () {
    const addDivisionButton = document.getElementById('addDivisionButton');
    const newDivisionNameInput = document.getElementById('newDivisionName');
    const addDivisionProjectSelect = document.getElementById('addDivisionProjectSelect');

    fetch('/projects/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(project => {
                const addDivisionOption = document.createElement('option');
                addDivisionOption.value = project.id;
                addDivisionOption.textContent = project.name;
                addDivisionProjectSelect.appendChild(addDivisionOption);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania projektów:', error);
        });

    addDivisionButton.addEventListener('click', function () {
        const divisionName = newDivisionNameInput.value;
        const selectedProjectId = addDivisionProjectSelect.value;
        if (divisionName && selectedProjectId) {
            if (window.confirm('Czy na pewno chcesz dodać tę dywizję?')) {
                fetch('/division/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `name=${divisionName}&projectsId=${selectedProjectId}`,
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        // Odśwież listę dywizji
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd dodawania dywizji:', error);
                    });
            }
        }
    });
});


document.addEventListener('DOMContentLoaded', function () {
    const deleteDivisionButton = document.getElementById('deleteDivisionButton');
    const deleteDivisionSelect = document.getElementById('deleteDivisionSelect');

    const updateDivisionButton = document.getElementById('updateDivisionButton');
    const updateDivisionSelect = document.getElementById('updateDivisionSelect');
    const updatedDivisionNameInput = document.getElementById('updateDivisionName');

    fetch('/division/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(division => {
                const deleteDivisionOption = document.createElement('option');
                deleteDivisionOption.value = division.name;
                deleteDivisionOption.textContent = division.name;
                deleteDivisionSelect.appendChild(deleteDivisionOption);

                const updateDivisionOption = document.createElement('option');
                updateDivisionOption.value = division.id;
                updateDivisionOption.textContent = division.name;
                updateDivisionSelect.appendChild(updateDivisionOption);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania dywizji:', error);
        });

    deleteDivisionButton.addEventListener('click', function () {
        const selectedDivision = deleteDivisionSelect.value;
        if (selectedDivision) {
            if (window.confirm('Czy na pewno chcesz usunąć tę dywizję?')) {
                fetch(`/division/delete?name=${selectedDivision}`, {
                    method: 'DELETE',
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd usuwania dywizji:', error);
                    });
            }
        }
    });

    updateDivisionButton.addEventListener('click', function () {
        const selectedDivisionId = updateDivisionSelect.value;
        const updatedDivisionName = updatedDivisionNameInput.value;
        if (selectedDivisionId && updatedDivisionName) {
            if (window.confirm('Czy na pewno chcesz zaktualizować tę dywizję?')) {
                fetch(`/division/update?id=${selectedDivisionId}&name=${updatedDivisionName}`, {
                    method: 'PUT',
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        // Odśwież listę dywizji
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd aktualizacji dywizji:', error);
                    });
            }
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const changeDivisionProjectButton = document.getElementById('changeDivisionProjectButton');
    const changeDivisionSelect = document.getElementById('changeDivisionSelect');
    const changeProjectSelect = document.getElementById('changeProjectSelect');

    fetch('/division/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(division => {
                const changeDivisionOption = document.createElement('option');
                changeDivisionOption.value = division.name;
                changeDivisionOption.textContent = division.name;
                changeDivisionSelect.appendChild(changeDivisionOption);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania dywizji:', error);
        });

    fetch('/projects/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(project => {
                const changeProjectOption = document.createElement('option');
                changeProjectOption.value = project.id;
                changeProjectOption.textContent = project.name;
                changeProjectSelect.appendChild(changeProjectOption);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania projektów:', error);
        });

    changeDivisionProjectButton.addEventListener('click', function () {
        const selectedDivision = changeDivisionSelect.value;
        const selectedProjectId = changeProjectSelect.value;
        if (selectedDivision && selectedProjectId) {
            if (window.confirm('Czy na pewno chcesz zmieć projekt tej dywizji?')) {
                fetch(`/division/changeProjects?name=${selectedDivision}&projectsId=${selectedProjectId}`, {
                    method: 'PUT',
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        // Odśwież listę dywizji
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd zmiany projektu dywizji:', error);
                    });
            }
        }
    });
});