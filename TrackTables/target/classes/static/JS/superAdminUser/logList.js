document.addEventListener('DOMContentLoaded', function () {
    const auditLogTable = document.createElement('table');
    auditLogTable.classList.add('auditLogTable');
    const auditLogDetailsDiv = document.querySelector('.auditLogDetails');

    fetch('/logs/all')
        .then(response => response.json())
        .then(data => {
            const headerRow = auditLogTable.insertRow();
            const headerColumns = ['ID', 'Email', 'Akcja', 'Data i czas'];

            headerColumns.forEach(column => {
                const th = document.createElement('th');
                th.textContent = column;
                headerRow.appendChild(th);
            });

            data.forEach((log) => {
                const row = auditLogTable.insertRow();
                const idCell = row.insertCell();
                const emailCell = row.insertCell();
                const actionCell = row.insertCell();
                const dateAndTimeCell = row.insertCell();

                idCell.textContent = log.id;
                emailCell.textContent = log.email;
                actionCell.textContent = log.action;
                dateAndTimeCell.textContent = log.dateAndTime;

                row.classList.add('auditLogRow');
                auditLogTable.appendChild(row);
            });

            const auditLogDiv = document.querySelector('.auditLog');
            auditLogDiv.appendChild(auditLogTable);
        })
        .catch(error => {
            console.error('Błąd pobierania danych audytu:', error);
        });

    auditLogTable.addEventListener('click', function (event) {
        if (event.target.parentElement.classList.contains('auditLogRow')) {
            const selectedId = event.target.parentElement.cells[0].textContent;

            fetch(`/log/details?id=${selectedId}`)
                .then(response => response.json())
                .then(log => {
                    auditLogDetailsDiv.textContent = `ID: ${log.id}, Email: ${log.email}, Akcja: ${log.action}, Data i czas: ${log.dateAndTime}`;
                })
                .catch(error => {
                    console.error('Błąd pobierania szczegółów audytu:', error);
                });
        }
    });
});
