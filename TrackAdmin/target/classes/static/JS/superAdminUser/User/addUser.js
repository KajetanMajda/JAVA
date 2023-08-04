var roleSelect = document.getElementById("role");

function loadRoles() {
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

document.addEventListener("DOMContentLoaded", loadRoles);

document.getElementById("userForm").addEventListener("submit", function (event) {
    event.preventDefault();

    var nameInput = document.getElementById("name");
    var surnameInput = document.getElementById("surname");
    var emailInput = document.getElementById("email");
    var passwordInput = document.getElementById("password");

    console.log("Imię:", nameInput.value);
    console.log("Nazwisko:", surnameInput.value);
    console.log("Email:", emailInput.value);
    console.log("Hasło:", passwordInput.value);

    var formData = new FormData(this);
    var name = nameInput.value;
    var surname = surnameInput.value;
    var email = emailInput.value;
    var password = passwordInput.value;
    var selectedRole = roleSelect.value;

    formData.append("name", name);
    formData.append("surname", surname);
    formData.append("email", email);
    formData.append("password", password);
    formData.append("role", selectedRole);
    console.log(selectedRole);

    fetch('/user/add', {
        method: 'POST',
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString()
    })
        .then(response => response.text())
        .then(result => {
            if (result === "success") {
                window.location.href = "/user/superAdminUser";
            } else {
                console.error('Błąd przy dodawaniu użytkownika');
            }
        })
        .catch(error => console.error('Błąd:', error));
});
