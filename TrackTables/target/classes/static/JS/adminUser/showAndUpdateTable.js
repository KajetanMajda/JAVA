document.addEventListener("DOMContentLoaded", function () {
    const elementsListDiv = document.querySelector('.elementsList');
    const elementDetailsDiv = document.querySelector('.elementDetails');


    const saveAllButton = document.querySelector('#saveAll');

    // Nasłuchuj na zdarzenie "click" na przycisku "Zapisz wszytsko"
    saveAllButton.addEventListener('click', function () {

        const confirmMessages = "Czy napewno chcesz zapisać wszystkie zmiany?";
        const isConfirmed = window.confirm(confirmMessages);

        if (isConfirmed) {
            const saveButtons = document.querySelectorAll('.save-button');

            saveButtons.forEach(saveButton => {
                saveButton.click(); // Symuluj kliknięcie przycisku "Zapisz" dla każdego rzędu
            });

        }
    });


    function fetchAndDisplayElements(divisionId = null) {
        fetch('/elements/all')
            .then(response => response.json())
            .then(data => {
                const elementsTable = document.createElement('table');
                elementsTable.classList.add('elementsTable');

                const headerRow = elementsTable.insertRow();
                const headerColumns = ['Ikona', 'Lp', 'Dział', 'Operacja', 'Opis', 'Uwagi', 'Status', 'Zrealizowane przez',
                    'Data realizacji', 'Zatwierdzone przez', 'Data zatwierdzenia', 'Zaktualizuj','Usuń'];
                headerColumns.forEach(column => {
                    const th = document.createElement('th');
                    th.textContent = column;
                    headerRow.appendChild(th);
                });



                document.addEventListener("input", function(event) {
                    if (event.target && event.target.id === "text") {
                        autoExpandTextarea(event.target);
                    }
                });

                function autoExpandTextarea(textarea) {
                    textarea.style.width = "150px";
                    textarea.style.height = "auto";
                    textarea.style.height = (textarea.scrollHeight) + "px";
                }

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
                        imageCell.innerHTML = `<img src="/ICONS/${element.status.id}.png" alt="x" width="50" height="50">`;
                        transactionCell.innerHTML = `<input class="input-transaction" type="text" value="${element.transaction || ''}">`
                        descriptionCell.innerHTML = `<textarea id="text" class="textarea-description">${element.description || ''}</textarea>`
                        commentCell.innerHTML = `<textarea id="text" class="textarea-comment">${element.comment || ''}</textarea>`
                        divisionCell.textContent = element.division.name;

                        const statusSelect = document.createElement("select");
                        statusSelect.classList.add("input-status");
                        statusCell.appendChild(statusSelect);


                        fetch("/status/all")
                            .then(response => response.json())
                            .then(statuses => {
                                statuses.forEach(status => {
                                    const option = document.createElement("option");
                                    option.value = status.id;
                                    option.textContent = status.name;
                                    statusSelect.appendChild(option);
                                });

                                statusSelect.value = element.status.id;

                            })
                            .catch(error => {
                                console.error("Błąd pobierania statusów:", error);
                            });
                        statusCell.appendChild(statusSelect);

                        accomplishCell.innerHTML = `<input class="input-accomplish" type="text" value="${element.accomplish || ''}">`;
                        accomplishDateCell.innerHTML = `<input class="input-accomplish-date" type="date" value="${element.accomplish_date || ''}">`;
                        confirm_nameCell.innerHTML = `<input class="input-confirm-name" type="text" value="${element.confirm_name || ''}">`;
                        confirmDateCell.innerHTML = `<input class="input-confirm-date" type="date" value="${element.confirm_date || ''}">`;

                        const saveButton = document.createElement('button');
                        saveButton.textContent = 'Zapisz';
                        saveButton.classList.add('save-button');
                        actionsCell.appendChild(saveButton);


                        const deleteButtonCell = row.insertCell();
                        const deleteButton = document.createElement('button');
                        deleteButton.textContent = 'Usuń';
                        deleteButton.classList.add('delete-button');
                        deleteButtonCell.appendChild(deleteButton);

                        deleteButton.addEventListener('click', function () {
                            const isConfirmedDelete = window.confirm('Czy na pewno chcesz usunąć ten element?');
                            if (isConfirmedDelete) {
                                const row = deleteButton.parentElement.parentElement;
                                const id = row.cells[1].textContent;

                                fetch(`/elements/adminDelete/${id}`, {
                                    method: 'DELETE'
                                })
                                    .then(response => response.text())
                                    .then(data => {
                                        console.log(data);
                                        location.reload();
                                    })
                                    .catch(error => {
                                        console.error('Błąd usuwania elementu:', error);
                                    });
                            }
                        });

                        row.classList.add('elementRow');
                        elementsTable.appendChild(row);
                    }
                });

                const newRow = elementsTable.insertRow();
                newRow.innerHTML = `

                    <td><img src="/ICONS/5.png" alt="x" width="50" height="50"></td>
                    <td></td>

                    <td>
                        <select class="input-division">
                            <option value="">Wybierz dywizję</option>
                            <!--            JavaScript          -->
                        </select>
                    </td>
                    <td><input class="input-transaction" type="text"></td>
                    <td><textarea id="text" class="textarea-description"></textarea></td>
                    <td><textarea id="text" class="textarea-comment"></textarea></td>
                    <td>
                        <select class="input-status">
                            <!--            JavaScript          -->
                        </select>
                    </td>
                    <td><input class="input-accomplish" type="text"></td>
                    <td><input class="input-accomplish-date" type="date"></td>
                    <td><input class="input-confirm-name" type="text"></td>
                    <td><input class="input-confirm-date" type="date"></td>
                    <td><button class="add-button">Dodaj</button></td>`;
                newRow.classList.add('elementRow', 'new-row');
                elementsTable.appendChild(newRow);

                elementsListDiv.innerHTML = '';
                elementsListDiv.appendChild(elementsTable);

                const divisionSelect = newRow.querySelector('.input-division');
                fetch("/division/all")
                    .then(response => response.json())
                    .then(divisions => {
                        divisions.forEach(division => {
                            const option = document.createElement("option");
                            option.value = division.id;
                            option.textContent = division.name;
                            divisionSelect.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error("Błąd pobierania dywizji:", error);
                    });

                const statusSelect = newRow.querySelector('.input-status');
                fetch("/status/all")
                    .then(response => response.json())
                    .then(statuses => {
                        statuses.forEach(status => {
                            const option = document.createElement("option");
                            option.value = status.id;
                            option.textContent = status.name;

                            if (status.id === 5) {
                                option.selected = true;
                            }


                            statusSelect.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error("Błąd pobierania statusów:", error);
                    });
            })
            .catch(error => {
                console.error('Błąd pobierania elementów:', error);
            });
    }


    fetchAndDisplayElements();
    const dzialSelect = document.getElementById("dzial");


    dzialSelect.addEventListener("change", function () {
        var selectedOption = dzialSelect.options[dzialSelect.selectedIndex];
        var selectedDzialId = selectedOption.value;

        if (selectedDzialId === "") {
            fetchAndDisplayElements();
        } else {
            fetchAndDisplayElements(selectedDzialId);
        }
    });

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

            // const confirmMessages = "Czy napewno chcesz zapisać zmiany?";
            // const isConfirmed = window.confirm(confirmMessages);

            // if (isConfirmed) {

            const inputTransaction = row.querySelector('.input-transaction');
            const textareaDescription = row.querySelector('.textarea-description');
            const textareaComment = row.querySelector('.textarea-comment');
            const inputStatus = row.querySelector('.input-status');
            const inputAccomplish = row.querySelector('.input-accomplish');
            const inputAccomplishDate = row.querySelector('.input-accomplish-date');
            const inputConfirmName = row.querySelector('.input-confirm-name');
            const inputConfirmDate = row.querySelector('.input-confirm-date');
            const formData = new URLSearchParams();

            formData.append('id', id);
            formData.append('transaction', inputTransaction.value);
            formData.append('description', textareaDescription.value);
            formData.append('comment', textareaComment.value);
            formData.append('statusId', inputStatus.value);
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

                    for (const field in inputFields) {
                        const columnIndex = Array.from(row.cells).findIndex(cell => cell.classList.contains(field));
                        if (columnIndex !== -1) {
                            row.cells[columnIndex].textContent = inputFields[field];
                        }
                    }
                })
                .catch(error => {
                    console.error('Błąd aktualizacji elementu:', error);
                });
            // }
        }
    });

    elementsListDiv.addEventListener('click', function (event) {
        if (event.target.classList.contains('add-button')) {
            const newRow = event.target.parentElement.parentElement;

            const confirmMessages = "Czy na pewno chcesz dodać element?";
            const isConfirmedAdd = window.confirm(confirmMessages);
            if (isConfirmedAdd) {
                const inputDivision = newRow.querySelector('.input-division');
                const inputTransaction = newRow.querySelector('.input-transaction');
                const textareaDescription = newRow.querySelector('.textarea-description');
                const textareaComment = newRow.querySelector('.textarea-comment');
                const inputStatus = newRow.querySelector('.input-status');
                const inputAccomplish = newRow.querySelector('.input-accomplish');
                const inputAccomplishDate = newRow.querySelector('.input-accomplish-date');
                const inputConfirmName = newRow.querySelector('.input-confirm-name');
                const inputConfirmDate = newRow.querySelector('.input-confirm-date');

                const formData = new URLSearchParams();
                formData.append('divisionId', inputDivision.value);
                formData.append('transaction', inputTransaction.value);
                formData.append('description', textareaDescription.value);
                formData.append('comment', textareaComment.value);
                formData.append('statusId', inputStatus.value);
                formData.append('accomplish', inputAccomplish.value);
                formData.append('accomplish_date', inputAccomplishDate.value);
                formData.append('confirm_name', inputConfirmName.value);
                formData.append('confirm_date', inputConfirmDate.value);

                fetch(`/elements/add`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: formData.toString()
                })
                    .then(response => response.json())
                    .then(data => {
                        fetchAndDisplayElements();
                    })
                    .catch(error => {
                        console.error('Błąd dodawania elementu:', error);
                        location.reload();
                    });
            }
        }
    });

});