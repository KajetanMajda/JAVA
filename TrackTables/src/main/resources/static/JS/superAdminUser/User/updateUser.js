document.addEventListener('DOMContentLoaded', function() {
        fetch('/user/all')
            .then(response => response.json())
            .then(data => {
                const selectElement = document.getElementById('userId');
                data.forEach(user => {
                    const option = document.createElement('option');
                    option.value = user.id;
                    option.textContent = user.email;
                    selectElement.appendChild(option);
                });

                const userIdSelect = document.getElementById('userId');
                userIdSelect.addEventListener('change', function () {
                    const selectedUserId = userIdSelect.value;
                    const nameInput = document.getElementById('nameUpdate');
                    const surnameInput = document.getElementById('surnameUpdate');
                    const emailInput = document.getElementById('emailUpdate');
                    const passwordInput = document.getElementById('passwordUpdate');

                    if (selectedUserId) {
                        const user = data.find(user => user.id === parseInt(selectedUserId));
                        if (user) {
                            nameInput.value = user.name;
                            surnameInput.value = user.surname;
                            emailInput.value = user.email;
                        } else {
                            console.log('Użytkownik nie został znaleziony.');
                        }
                    }
                });

                const form = document.querySelector('.userFromUpdate');
                form.addEventListener('submit', function (event) {
                    event.preventDefault();

                    const formData = new FormData(form);
                    const selectedUserId = userIdSelect.value;

                    if (selectedUserId) {
                        if (window.confirm('Czy na pewno chcesz zaaktualizować tego użytkownika?')) {
                            fetch(`/user/update?id=${selectedUserId}`, {
                                method: 'PUT',
                                body: formData,
                            })
                                .then(response => response.text())
                                .then(data => {
                                    // Możesz tutaj obsłużyć odpowiedź z serwera po udanym zaktualizowaniu
                                    console.log(data);
                                    // np. przekierować użytkownika na inną stronę
                                    window.location.href = '/user/superAdminUser';
                                })
                                .catch(error => {
                                    console.error('Wystąpił błąd:', error);
                                });
                        } else {
                            console.log('Proszę wybrać użytkownika.');
                        }
                    }
                });
            })
            .catch(error => {
                console.error('Błąd pobierania użytkowników:', error);
            });

});
