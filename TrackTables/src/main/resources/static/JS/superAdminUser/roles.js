document.addEventListener('DOMContentLoaded', function () {
    const addRoleButton = document.getElementById('addRoleButton');
    const newRoleNameInput = document.getElementById('newRoleName');

    const deleteRoleButton = document.getElementById('deleteRoleButton');
    const deleteRoleSelect = document.getElementById('deleteRoleSelect');


    fetch('/userRole/allRoles')
        .then(response => response.json())
        .then(data => {
            data.forEach(role => {
                if (role !== 'Super_Admin_User') {
                    const deleteOption = document.createElement('option');
                    deleteOption.value = role;
                    deleteOption.textContent = role;
                    deleteRoleSelect.appendChild(deleteOption);

                }
            });
        })
        .catch(error => {
            console.error('Błąd pobierania ról:', error);
        });


    addRoleButton.addEventListener('click', function () {
        const roleName = newRoleNameInput.value;
        console.log('Próbuję dodać rolę:', roleName);
        if (roleName) {
            if (window.confirm(`Czy na pewno chcesz dodać nową rolę: ${roleName}?`)) {
                fetch('/userRole/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `name=${roleName}`,
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        // Odśwież listę ról
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd dodawania roli:', error);
                    });
            }
        }
    });

    deleteRoleButton.addEventListener('click', function () {
        const selectedRole = deleteRoleSelect.value;
        if (selectedRole) {
            if (window.confirm(`Czy na pewno chcesz usunąć rolę: ${selectedRole}?`)) {
                fetch(`/userRole/delete?name=${selectedRole}`, {
                    method: 'DELETE',
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        // Odśwież listę ról
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd usuwania roli:', error);
                    });
            }
        }
    });

});

document.addEventListener('DOMContentLoaded', function () {
    const updateRoleButton = document.getElementById('updateRoleButton');
    const updateRoleSelect = document.getElementById('updateRoleSelect');
    const updatedRoleNameInput = document.getElementById('updatedRoleName');

    fetch('/userRole/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;
                option.textContent = role.name;
                updateRoleSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Błąd pobierania ról:', error);
        });

    updateRoleButton.addEventListener('click', function () {
        const selectedRoleId = updateRoleSelect.value;
        const updatedRoleName = updatedRoleNameInput.value;
        if (selectedRoleId && updatedRoleName) {
            if (window.confirm(`Czy na pewno chcesz zaktualizować rolę o ID ${selectedRoleId} na: ${updatedRoleName}?`)) {
                fetch(`/userRole/update?id=${selectedRoleId}&name=${updatedRoleName}`, {
                    method: 'PUT',
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Błąd aktualizacji roli:', error);
                    });
            }
        }
    });
});
