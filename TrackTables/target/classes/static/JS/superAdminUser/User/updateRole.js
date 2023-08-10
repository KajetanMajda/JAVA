document.addEventListener('DOMContentLoaded', function () {
    fetch('/user/all')
        .then(response => response.json())
        .then(data => {
            const selectElement = document.getElementById('emailRole');
            data.forEach(user => {
                if (user.email !== 'admin@proxmus.com') {
                    const option = document.createElement('option');
                    option.value = user.email;
                    option.textContent = user.email;
                    selectElement.appendChild(option);
                }
            });
        })
        .catch(error => {
            console.error('Błąd pobierania użytkowników:', error);
        });

    const emailRoleSelect = document.getElementById('emailRole');
    const currentRoleInput = document.getElementById('currentRole');
    const newRoleSelect = document.getElementById('newRole');

    emailRoleSelect.addEventListener('change', function () {
        const selectedEmail = emailRoleSelect.value;
        currentRoleInput.value = '';

        if (selectedEmail) {
            fetch('/user/all')
                .then(response => response.json())
                .then(data => {
                    const user = data.find(user => user.email === selectedEmail);
                    if (user) {
                        currentRoleInput.value = user.role ? user.role.name : 'Użytkownik nie ma przypisanej roli.';
                    } else {
                        currentRoleInput.value = 'Użytkownik nie został znaleziony.';
                    }
                })
                .catch(error => {
                    console.error('Błąd pobierania użytkowników:', error);
                });
        }
    });


    fetch('/userRole/all')
        .then(response => response.json())
        .then(data => {
            const selectElement = document.getElementById('newRole');

            data.forEach(role => {
                if (role.name !== 'Super_Admin_User' && role.name !== currentRoleInput.value) {
                    const option = document.createElement('option');
                    option.value = role.name;
                    option.textContent = role.name;
                    selectElement.appendChild(option);
                }
            });

            newRoleSelect.addEventListener('change', function () {
                if (currentRoleInput.value === newRoleSelect.value) {
                    alert('Nie możesz wybrać tej samej roli, co obecna rola.');
                    newRoleSelect.value = '';
                }
            });
        })
        .catch(error => {
            console.error('Błąd pobierania ról:', error);
        });
});

document.addEventListener("DOMContentLoaded", function () {
    const changeRoleButton = document.getElementById("changeRoleButton");

    changeRoleButton.addEventListener("click", function (event) {
        const confirmation = window.confirm("Czy na pewno chcesz zmienić użytkownikowi rolę?");

        if (!confirmation) {
            event.preventDefault();
        }
    });

});