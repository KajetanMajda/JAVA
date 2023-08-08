document.addEventListener("DOMContentLoaded", function () {
    const loggedInUserEmail = document.getElementById("loggedInUserEmail").textContent;
    console.log("Zalogowany użytkownik:", loggedInUserEmail);

    const logoutButton = document.getElementById("logoutButton");
    logoutButton.addEventListener("click", function () {
        const confirmation = window.confirm("Czy na pewno chcesz się wylogować?");

        if (confirmation) {
            fetch("/logout")
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    window.location.href = "/user/login";
                })
                .catch(error => {
                    console.error("Błąd wylogowania:", error);
                });
        }
    });
});

