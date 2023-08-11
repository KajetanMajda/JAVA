const loggedInUserEmailSpan = document.getElementById('loggedInUserEmail');
const loggedInUserEmail = loggedInUserEmailSpan.textContent;

const userFullNameSpan = document.getElementById('userFullName');

fetch('/user/all')
    .then(response => response.json())
    .then(users => {
        const matchingUser = users.find(user => user.email === loggedInUserEmail);
        if (matchingUser) {
            const fullName = matchingUser.name + ' ' + matchingUser.surname;
            userFullNameSpan.textContent = fullName;
        } else {
            console.log('Nie znaleziono użytkownika o pasującym adresie email.');
        }
    })
    .catch(error => {
        console.error('Błąd podczas pobierania użytkowników:', error);
    });