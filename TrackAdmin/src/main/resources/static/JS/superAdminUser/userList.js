document.addEventListener('DOMContentLoaded', function() {
    const userListTable = document.createElement('table');
    userListTable.classList.add('userListTable');
    const userDetailsDiv = document.querySelector('.userDetails');

    fetch('/user/all')
        .then(response => response.json())
        .then(data => {
            const headerRow = userListTable.insertRow();
            const headerColumns = ['lp', 'Imię', 'Nazwisko', 'Email', 'Rola'];

            headerColumns.forEach(column => {
                const th = document.createElement('th');
                th.textContent = column;
                headerRow.appendChild(th);
            });

            data.forEach((user, index) => {
                const row = userListTable.insertRow();
                const lpCell = row.insertCell();
                const firstNameCell = row.insertCell();
                const lastNameCell = row.insertCell();
                const emailCell = row.insertCell();
                const roleCell = row.insertCell();

                lpCell.textContent = index + 1;
                firstNameCell.textContent = user.name;
                lastNameCell.textContent = user.surname;
                emailCell.textContent = user.email;
                roleCell.textContent = user.role.name;

                row.classList.add('userRow');
                userListTable.appendChild(row);
            });

            const userListDiv = document.querySelector('.userList');
            userListDiv.appendChild(userListTable);
        })
        .catch(error => {
            console.error('Błąd pobierania użytkowników:', error);
        });

    userListTable.addEventListener('click', function(event) {
        if (event.target.parentElement.classList.contains('userRow')) {
            const selectedEmail = event.target.parentElement.cells[3].textContent;

            fetch(`/user/details?email=${selectedEmail}`)
                .then(response => response.json())
                .then(user => {
                    userDetailsDiv.textContent = `Email: ${user.email}, Imię: ${user.firstName}, Nazwisko: ${user.lastName}, Rola: ${user.role.name}`;
                })
                .catch(error => {
                    console.error('Błąd pobierania szczegółów użytkownika:', error);
                });
        }
    });
});