function handleRegistrationRadio(qualifiedName) {
    var registerRadio = document.getElementById("register");
    var loginRadio = document.getElementById("login");
    var usernameInput = document.getElementById("username");
    var passwordInput = document.getElementById("password");
    var placeholderLogin = "Imie Nazwisko";
    var placeholderPassword = "!AlaMaKota17";

    if (registerRadio.checked) {

        usernameInput.setAttribute("required", "");
        usernameInput.setAttribute("placeholder", placeholderLogin);
        usernameInput.setAttribute("pattern", "^[A-Z][a-z]+ [A-Z][a-z]+$");
        usernameInput.setAttribute("title", "Imie i nazwisko ma się zaczynać wielką literą. Wyrazy mają być oddzielone znakiem spacji");
        usernameInput.value = "";

        passwordInput.setAttribute("required", "");
        passwordInput.setAttribute("placeholder", placeholderPassword);
        passwordInput.setAttribute("pattern", "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");
        passwordInput.setAttribute("title", "Musi zawierać małe i duże litery, cyfrę, znak specjalny oraz musi mieć 8 lub więcej znaków");
        passwordInput.value = "";

    } else if (loginRadio.checked) {

        usernameInput.value = "";
        usernameInput.removeAttribute("required");
        usernameInput.removeAttribute("placeholder");
        usernameInput.removeAttribute("pattern");
        usernameInput.removeAttribute("title");

        passwordInput.value = "";
        passwordInput.removeAttribute("class");
        passwordInput.removeAttribute("required");
        passwordInput.removeAttribute("placeholder");
        passwordInput.removeAttribute("pattern");
        passwordInput.removeAttribute("title");
    }
}