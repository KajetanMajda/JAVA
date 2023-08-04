function displayLoggedInUser() {
    var loggedInUserElement = document.getElementById("loggedInUser");
    if (loggedInUserElement) {
        var loggedInUser = getSessionUser();
        if (loggedInUser) {
            loggedInUserElement.innerText = loggedInUser.name + " " + loggedInUser.surname;
        }
    }
}

function getSessionUser() {
    return {
        name: "Imię",
        surname: "Nazwisko"
    };
}