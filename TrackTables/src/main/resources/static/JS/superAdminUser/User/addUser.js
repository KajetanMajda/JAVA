document.addEventListener("DOMContentLoaded", function () {
    const userForm = document.getElementById("userForm");
    const roleSelect = document.getElementById("role");

    // Funkcja do ładowania ról do listy wyboru
    function loadRolesToSelect() {
        fetch('/userRole/allRoles')
            .then(response => response.json())
            .then(data => {
                data.forEach(roleName => {
                    if (roleName !== 'Super_Admin_User') {
                        var option = document.createElement("option");
                        option.value = roleName;
                        option.text = roleName;
                        roleSelect.appendChild(option);
                    }
                });
            })
            .catch(error => console.error('Błąd:', error));
    }

    loadRolesToSelect();

    userForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const nameInput = document.getElementById("name");
        const surnameInput = document.getElementById("surname");
        const emailInput = document.getElementById("email");
        const passwordInput = document.getElementById("password");

        const name = nameInput.value;
        const surname = surnameInput.value;
        const email = emailInput.value;
        const password = passwordInput.value;
        const role = roleSelect.value;

        const formData = new URLSearchParams({
            name: name,
            surname: surname,
            email: email,
            password: password,
            role: role
        });
        if (window.confirm('Czy na pewno chcesz dodać tego użytkownika?')) {
            fetch('/user/add', {
                method: 'POST',
                body: formData
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    window.location.href = '/user/superAdminUser';
                })
                .catch(error => {
                    console.error('Błąd:', error);
                });
        }
    });
});
