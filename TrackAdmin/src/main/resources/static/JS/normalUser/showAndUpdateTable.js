document.addEventListener("DOMContentLoaded", function() {
    const elementsListDiv = document.querySelector('.elementsList');
    const elementDetailsDiv = document.querySelector('.elementDetails');

    // Funkcja do pobierania i wyświetlania wszystkich elementów lub elementów z wybranej dywizji
    function fetchAndDisplayElements(divisionId = null) {
        fetch('/elements/all')
            .then(response => response.json())
            .then(data => {
                const elementsTable = document.createElement('table');
                elementsTable.classList.add('elementsTable');

                const headerRow = elementsTable.insertRow();
                const headerColumns = ['Lp', 'Dział', 'Operacja', 'Opis', 'Uwagi', 'Status', 'Zrealizowane przez',
                    'Data realizacji', 'Zatwierdzone przez ', 'Data zatwierdzenia','Zaktualizuj'];
                headerColumns.forEach(column => {
                    const th = document.createElement('th');
                    th.textContent = column;
                    headerRow.appendChild(th);
                });

                data.forEach((element) => {
                    if (!divisionId || element.division.id == divisionId) {
                        const row = elementsTable.insertRow();
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

                        accomplishCell.innerHTML = `<input class="input-accomplish" type="text" value="${element.accomplish || ''}">`;
                        accomplishDateCell.innerHTML = `<input class="input-accomplish-date" type="date" value="${element.accomplish_date || ''}">`;
                        confirm_nameCell.innerHTML = `<input class="input-confirm-name" type="text" value="${element.confirm_name || ''}">`;
                        confirmDateCell.innerHTML = `<input class="input-confirm-date" type="date" value="${element.confirm_date || ''}">`;

                        const saveButton = document.createElement('button');
                        saveButton.textContent = 'Zapisz';
                        saveButton.classList.add('save-button');
                        actionsCell.appendChild(saveButton);

                        row.classList.add('elementRow');
                        elementsTable.appendChild(row);
                    }
                });

                elementsListDiv.innerHTML = ''; // Wyczyść zawartość przed dodaniem nowej tabeli
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
    dzialSelect.addEventListener("change", function() {
        var selectedOption = dzialSelect.options[dzialSelect.selectedIndex];
        var selectedDzialId = selectedOption.value;

        if (selectedDzialId === "") {
            // Jeśli wybrano opcję "Wybierz wszystko", wyświetl wszystkie elementy
            fetchAndDisplayElements();
        } else {
            // W przeciwnym razie, wyświetl tylko elementy z wybranej dywizji
            fetchAndDisplayElements(selectedDzialId);
        }
    });

    // Funkcja dodawania opcji do selecta
    function addOption(select, value, text, id) {
        var option = document.createElement("option");
        option.value = value;
        option.text = text;
        option.dataset.id = id; // Dodaj atrybut data z id działu
        select.appendChild(option);
    }

    function fetchStatusName(statusId) {
        return fetch("/status/all")
            .then(response => response.json())
            .then(statuses => {
                const foundStatus = statuses.find(status => status.id === statusId);
                return foundStatus ? foundStatus.name : "";
            });
    }

    elementsListDiv.addEventListener('click', function(event) {
        if (event.target.parentElement.classList.contains('elementRow')) {
            const selectedId = event.target.parentElement.cells[0].textContent;

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

    elementsListDiv.addEventListener('click', function(event) {
        if (event.target.classList.contains('save-button')) {
            const row = event.target.parentElement.parentElement;
            const id = row.cells[0].textContent;
            const inputAccomplish = row.querySelector('.input-accomplish');
            const inputAccomplishDate = row.querySelector('.input-accomplish-date');
            const inputConfirmName = row.querySelector('.input-confirm-name'); // Poprawiona nazwa zmiennej
            const inputConfirmDate = row.querySelector('.input-confirm-date');
            const formData = new URLSearchParams();

            formData.append('id', id);
            formData.append('accomplish', inputAccomplish.value);
            formData.append('accomplish_date', inputAccomplishDate.value);
            formData.append('confirm_name', inputConfirmName.value);
            formData.append('confirm_date', inputConfirmDate.value);



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
    });
});