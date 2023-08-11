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
                const headerColumns = ['Ikona', 'Lp', 'Dział', 'Operacja', 'Opis', 'Uwagi', 'Status','Uwagi z realizacji', 'Zrealizowane przez',
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
                        const uwagiRealizacjiCell = row.insertCell();
                        const accomplishCell = row.insertCell();
                        const accomplishDateCell = row.insertCell();
                        const confirm_nameCell = row.insertCell();
                        const confirmDateCell = row.insertCell();
                        const actionsCell = row.insertCell();

                        idCell.textContent = element.id;
                        imageCell.innerHTML = `<img src="/ICONS/${element.status.id}.png" alt="x" width="50" height="50">`;
                        transactionCell.innerHTML = `<input class="input-transaction" type="text" value="${element.transaction || ''}">`;
                        descriptionCell.innerHTML = `<textarea id="text" class="textarea-description">${element.description || ''}</textarea>`;
                        commentCell.innerHTML = `<textarea id="text" class="textarea-comment">${element.comment || ''}</textarea>`;
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

                        uwagiRealizacjiCell.innerHTML = `<textarea id="text" class="textarea-uwagi-realizacja">${element.uwagi_z_realizacji || ''}</textarea>`;
                        accomplishCell.innerHTML = `<input class="input-accomplish" type="checkbox"> <input class="input-accomplish" type="text" value="${element.accomplish || ''}" readonly>`;
                        accomplishDateCell.innerHTML = `<input class="input-accomplish-date" type="date"  readonly><input class="input-accomplish-date" type="date" value="${element.accomplish_date || ''}"readonly>`;
                        confirm_nameCell.innerHTML = `<input class="input-confirm-name" type="checkbox"><input class="input-confirm-name" type="text" value="${element.confirm_name || ''}"readonly>`;
                        confirmDateCell.innerHTML = `<input class="input-confirm-date" type="date" readonly><input class="input-confirm-date" type="date" value="${element.confirm_date || ''}"readonly>`;

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
                    <td><textarea id=text" class="textarea-uwagi-realizacja"></textarea></td>
                    <td><input class="input-accomplish" type="checkbox"></td>
                    <td><input class="input-accomplish-date" type="date" readonly></td>
                    <td><input class="input-confirm-name" type="checkbox"></td>
                    <td><input class="input-confirm-date" type="date" readonly></td>
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

        const divisionSelect = document.querySelector('.input-division');

        divisionSelect.innerHTML = '';

        const defaultOption = document.getElementById("all");
        divisionSelect.appendChild(defaultOption);

        if (selectedDzialId !== "") {
            fetch(`/division/byDzial/${selectedDzialId}`)
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
            const textareaUwagiRealizacji = row.querySelector('.textarea-uwagi-realizacja');
            const inputAccomplish = row.querySelector('.input-accomplish');
            const inputConfirmName = row.querySelector('.input-confirm-name');
            const formData = new URLSearchParams();

            formData.append('id', id);
            formData.append('transaction', inputTransaction.value);
            formData.append('description', textareaDescription.value);
            formData.append('comment', textareaComment.value);
            formData.append('statusId', inputStatus.value);
            formData.append('uwagi_z_realizacji', textareaUwagiRealizacji.value);

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

            if (inputAccomplish.checked) {
                const loggedInUserEmailSpan = document.getElementById('loggedInUserEmail');
                const loggedInUserEmail = loggedInUserEmailSpan.textContent;

                let currentDate = new Date().toJSON().slice(0, 10);
                const formData = new URLSearchParams();

                const username = loggedInUserEmail.split('@')[0];
                const words = username.split('.');
                const formattedNameArray = words.map(word => {
                    const firstLetter = word.charAt(0).toUpperCase();
                    const restOfWord = word.slice(1).toLowerCase();
                    return firstLetter + restOfWord;
                });
                const formattedName = formattedNameArray.join('. ');

                formData.append('id', id);
                formData.append('accomplish', formattedName);
                formData.append('accomplish_date', currentDate);

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
                    })
                    .catch(error => {
                        console.error('Błąd aktualizacji elementu:', error);
                    });
            } else if (inputConfirmName.checked) {
                const loggedInUserEmailSpan = document.getElementById('loggedInUserEmail');
                const loggedInUserEmail = loggedInUserEmailSpan.textContent;

                let currentDate = new Date().toJSON().slice(0, 10);
                const formData = new URLSearchParams();

                const username = loggedInUserEmail.split('@')[0];
                const words = username.split('.');
                const formattedNameArray = words.map(word => {
                    const firstLetter = word.charAt(0).toUpperCase();
                    const restOfWord = word.slice(1).toLowerCase();
                    return firstLetter + restOfWord;
                });
                const formattedName = formattedNameArray.join('. ');

                formData.append('id', id);
                formData.append('confirm_name', formattedName);
                formData.append('confirm_date', currentDate);

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
                    })
                    .catch(error => {
                        console.error('Błąd aktualizacji elementu:', error);
                    });
            } else if(inputAccomplish.checked && inputConfirmName.checked){
                const loggedInUserEmailSpan = document.getElementById('loggedInUserEmail');
                const loggedInUserEmail = loggedInUserEmailSpan.textContent;

                let currentDate = new Date().toJSON().slice(0, 10);
                const formData = new URLSearchParams();

                const username = loggedInUserEmail.split('@')[0];
                const words = username.split('.');
                const formattedNameArray = words.map(word => {
                    const firstLetter = word.charAt(0).toUpperCase();
                    const restOfWord = word.slice(1).toLowerCase();
                    return firstLetter + restOfWord;
                });
                const formattedName = formattedNameArray.join('. ');

                formData.append('id', id);
                formData.append('accomplish', formattedName);
                formData.append('accomplish_date', currentDate);
                formData.append('confirm_name', formattedName);
                formData.append('confirm_date', currentDate);

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
                    })
                    .catch(error => {
                        console.error('Błąd aktualizacji elementu:', error);
                    });
            }

                // }
        }
    });

    elementsListDiv.addEventListener('click', function (event) {
        if (event.target.classList.contains('add-button')) {
            const newRow = event.target.parentElement.parentElement;

            const confirmMessages = "Czy na pewno chcesz dodać element? PAMIETAJ to odświerzy stronę wiec stracisz wszystkie NIEZAPISANE dane!";
            const isConfirmedAdd = window.confirm(confirmMessages);
            if (isConfirmedAdd) {
                const inputDivision = newRow.querySelector('.input-division');
                const inputTransaction = newRow.querySelector('.input-transaction');
                const textareaDescription = newRow.querySelector('.textarea-description');
                const textareaComment = newRow.querySelector('.textarea-comment');
                const inputStatus = newRow.querySelector('.input-status');
                const textareaUwagiRealizacji = newRow.querySelector('.textarea-uwagi-realizacja');
                const inputAccomplish = newRow.querySelector('.input-accomplish');
                const inputConfirmName = newRow.querySelector('.input-confirm-name');



                const formData = new URLSearchParams();
                formData.append('divisionId', inputDivision.value);
                formData.append('transaction', inputTransaction.value);
                formData.append('description', textareaDescription.value);
                formData.append('comment', textareaComment.value);
                formData.append('statusId', inputStatus.value);
                formData.append('uwagi_z_realizacji', textareaUwagiRealizacji.value);

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

                if (inputAccomplish.checked) {
                    const loggedInUserEmailSpan = document.getElementById('loggedInUserEmail');
                    const loggedInUserEmail = loggedInUserEmailSpan.textContent;

                    let currentDate = new Date().toJSON().slice(0, 10);
                    const formData = new URLSearchParams();

                    const username = loggedInUserEmail.split('@')[0];
                    const words = username.split('.');
                    const formattedNameArray = words.map(word => {
                        const firstLetter = word.charAt(0).toUpperCase();
                        const restOfWord = word.slice(1).toLowerCase();
                        return firstLetter + restOfWord;
                    });
                    const formattedName = formattedNameArray.join('. ');

                    formData.append('id', id);
                    formData.append('accomplish', formattedName);
                    formData.append('accomplish_date', currentDate);

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
                            location.reload();
                        })
                        .catch(error => {
                            console.error('Błąd aktualizacji elementu:', error);
                        });
                } else if (inputConfirmName.checked) {
                    const loggedInUserEmailSpan = document.getElementById('loggedInUserEmail');
                    const loggedInUserEmail = loggedInUserEmailSpan.textContent;

                    let currentDate = new Date().toJSON().slice(0, 10);
                    const formData = new URLSearchParams();

                    const username = loggedInUserEmail.split('@')[0];
                    const words = username.split('.');
                    const formattedNameArray = words.map(word => {
                        const firstLetter = word.charAt(0).toUpperCase();
                        const restOfWord = word.slice(1).toLowerCase();
                        return firstLetter + restOfWord;
                    });
                    const formattedName = formattedNameArray.join('. ');

                    formData.append('id', id);
                    formData.append('confirm_name', formattedName);
                    formData.append('confirm_date', currentDate);

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
                            location.reload();
                        })
                        .catch(error => {
                            console.error('Błąd aktualizacji elementu:', error);
                        });
                } else if(inputAccomplish.checked && inputConfirmName.checked){
                    const loggedInUserEmailSpan = document.getElementById('loggedInUserEmail');
                    const loggedInUserEmail = loggedInUserEmailSpan.textContent;

                    let currentDate = new Date().toJSON().slice(0, 10);
                    const formData = new URLSearchParams();

                    const username = loggedInUserEmail.split('@')[0];
                    const words = username.split('.');
                    const formattedNameArray = words.map(word => {
                        const firstLetter = word.charAt(0).toUpperCase();
                        const restOfWord = word.slice(1).toLowerCase();
                        return firstLetter + restOfWord;
                    });
                    const formattedName = formattedNameArray.join('. ');

                    formData.append('id', id);
                    formData.append('accomplish', formattedName);
                    formData.append('accomplish_date', currentDate);
                    formData.append('confirm_name', formattedName);
                    formData.append('confirm_date', currentDate);

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
                            location.reload();
                        })
                        .catch(error => {
                            console.error('Błąd aktualizacji elementu:', error);
                        });
                }
            }
        }
    });

});