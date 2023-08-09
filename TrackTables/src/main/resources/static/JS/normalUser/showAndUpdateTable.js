document.addEventListener("DOMContentLoaded", function () {
    const elementsListDiv = document.querySelector('.elementsList');
    const elementDetailsDiv = document.querySelector('.elementDetails');

    function fetchAndDisplayElements(divisionId = null) {
        fetch('/elements/all')
            .then(response => response.json())
            .then(data => {
                const elementsTable = document.createElement('table');
                elementsTable.classList.add('elementsTable');

                const headerRow = elementsTable.insertRow();
                const headerColumns = ['Ikona','Lp', 'Dział', 'Operacja', 'Opis', 'Uwagi', 'Status', 'Zrealizowane przez',
                    'Data realizacji', 'Zatwierdzone przez ', 'Data zatwierdzenia', 'Zaktualizuj'];
                headerColumns.forEach(column => {
                    const th = document.createElement('th');
                    th.textContent = column;
                    headerRow.appendChild(th);
                });

                data.forEach((element) => {
                    if (!divisionId || element.division.id == divisionId) {
                        const row = elementsTable.insertRow();
                        const imageCell = row.insertCell()
                        const idCell = row.insertCell();
                        const divisionCell = row.insertCell();
                        const transactionCell = row.insertCell();
                        const descriptionCell = row.insertCell();
                        const commentCell = row.insertCell();
                        const statusCell = row.insertCell();
                        const accomplishCell = row.insertCell();
                        const accomplishDateCell = row.insertCell();
                        const confirm_nameCell = row.insertCell();
                        const confirmDateCell = row.insertCell();
                        const actionsCell = row.insertCell();

                        idCell.textContent = element.id;


                        idCell.textContent = element.id;
                        imageCell.innerHTML = `<img src="/ICONS/${element.status.id}.png" alt="x" width="50" height="50">`;
                        transactionCell.textContent = element.transaction;
                        descriptionCell.textContent = element.description;
                        commentCell.textContent = element.comment;

                        divisionCell.textContent = element.division.name;

                        fetchStatusName(element.status.id)
                            .then(statusName => {
                                statusCell.textContent = statusName;
                            })
                            .catch(error => {
                                console.error("Błąd pobierania nazwy statusu:", error);
                            });
                        if (element.accomplish === null && element.accomplish_date === null) {
                            accomplishCell.innerHTML = `<input class="input-accomplish" type="text" value="${element.accomplish || ''}">`;
                            accomplishDateCell.innerHTML = `<input class="input-accomplish-date" type="date" value="${element.accomplish_date || ''}">`;
                        } else {
                            accomplishCell.innerHTML = element.accomplish;
                            accomplishDateCell.innerHTML = element.accomplish_date;
                        }

                        confirm_nameCell.innerHTML = element.confirm_name;
                        confirmDateCell.innerHTML = element.confirm_date;

                        const saveButton = document.createElement('button');
                        saveButton.textContent = 'Zapisz';
                        saveButton.classList.add('save-button');
                        actionsCell.appendChild(saveButton);

                        row.classList.add('elementRow');
                        elementsTable.appendChild(row);
                    }
                });

                elementsListDiv.innerHTML = '';
                elementsListDiv.appendChild(elementsTable);
            })
            .catch(error => {
                console.error('Błąd pobierania elementów:', error);
            });
    }

    // Wywołanie funkcji przy starcie strony
    fetchAndDisplayElements();
    const dzialSelect = document.getElementById("dzial");

    // Reakcja na zmianę wybranego działu
    dzialSelect.addEventListener("change", function () {
        var selectedOption = dzialSelect.options[dzialSelect.selectedIndex];
        var selectedDzialId = selectedOption.value;

        if (selectedDzialId === "") {
            fetchAndDisplayElements();
        } else {
            fetchAndDisplayElements(selectedDzialId);
        }
    });


    function fetchStatusName(statusId) {
        return fetch("/status/all")
            .then(response => response.json())
            .then(statuses => {
                const foundStatus = statuses.find(status => status.id === statusId);
                return foundStatus ? foundStatus.name : "";
            });
    }

    elementsListDiv.addEventListener('click', function (event) {
        if (event.target.parentElement.classList.contains('elementRow')) {
            const selectedId = event.target.parentElement.cells[1].textContent;

            fetch(`/element/details?id=${selectedId}`)
                .then(response => response.json())
                .then(element => {
                    elementDetailsDiv.textContent = `ID: ${element.id}, Transaction: ${element.transaction}, Description: ${element.description}, Comment: ${element.comment}, Accomplish: ${element.accomplish}, Accomplish Date: ${element.accomplish_date}, Confirm_name: ${element.confirm_name}, Confirm Date: ${element.confirm_date}, Division: ${element.division.name}, Status: ${element.status.name}`;
                })
                .catch(error => {
                    console.error('Błąd pobierania szczegółów elementu:', error);
                });
        }
    });

    elementsListDiv.addEventListener('click', function (event) {
        if (event.target.classList.contains('save-button')) {
            const row = event.target.parentElement.parentElement;
            const id = row.cells[1].textContent;

            const confirmMessages = "Czy napewno chcesz zapisać zmiany? PAMIETAJ! ze musisz miec wypełnione 2 pole bo już potem nie mozesz ich edytować";
            const isConfirmed = window.confirm(confirmMessages);

            if (isConfirmed) {
                const inputAccomplish = row.querySelector('.input-accomplish');
                const inputAccomplishDate = row.querySelector('.input-accomplish-date');

                const formData = new URLSearchParams();

                formData.append('id', id);
                formData.append('accomplish', inputAccomplish.value);
                formData.append('accomplish_date', inputAccomplishDate.value);



                fetch(`/elements/update`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: formData.toString()
                })
                    .then(response => response.text())
                    .then(data => {
                        console.log(data);
                        location.reload(); // Odśwież tabelę elementów
                    })
                    .catch(error => {
                        console.error('Błąd aktualizacji elementu:', error);
                    });
            }
        }
    });
});