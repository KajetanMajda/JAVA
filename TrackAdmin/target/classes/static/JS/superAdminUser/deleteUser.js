document.addEventListener('DOMContentLoaded', function() {
    const emailDeleteSelect = document.getElementById('emailDelete');
    const formDelete = document.querySelector('.userFromDelete');

    fetch('/user/all')
        .then(response => response.json())
        .then(data => {
            data.forEach(user => {
                if (user.email !== 'admin@proxmus.com') {
                    const option = document.createElement('option');
                    option.value = user.email;
                    option.textContent = user.email;
                    emailDeleteSelect.appendChild(option);
                }
            });
        })
        .catch(error => {
            console.error('Błąd pobierania użytkowników:', error);
        });

    formDelete.addEventListener('submit', function(event) {
        event.preventDefault();

        const selectedEmail = emailDeleteSelect.value;

        if (selectedEmail) {
            fetch(`/user/delete?email=${selectedEmail}`, {
                method: 'DELETE',
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    window.location.href = '/user/superAdminUser';
                })
                .catch(error => {
                    console.error('Wystąpił błąd:', error);
                });
        } else {
            console.log('Proszę wybrać użytkownika do usunięcia.');
        }
    });
});
