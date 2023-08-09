
document.addEventListener('DOMContentLoaded', function () {
async function fetchAndFillSelectOptions() {
    try {
        const response = await fetch('/division/all'); // Zmień na odpowiedni URL endpointa
        const data = await response.json();

        const dzialFromSelect = document.getElementById('dzialFrom');
        const dzialToSelect = document.getElementById('dzialTo');

        // Wypełnianie opcji wyboru dla dzialFrom
        data.forEach(item => {
            const option = document.createElement('option');
            option.value = item.id;
            option.textContent = `${item.id} - ${item.name}`;
            dzialFromSelect.appendChild(option);
        });

        // Wypełnianie opcji wyboru dla dzialTo (możesz użyć tego samego zestawu danych)
        data.forEach(item => {
            const option = document.createElement('option');
            option.value = item.id;
            option.textContent = `${item.id} - ${item.name}`;
            dzialToSelect.appendChild(option);
        });

    } catch (error) {
        console.error('Błąd podczas pobierania danych:', error);
    }
}

fetchAndFillSelectOptions();

    const copyButton = document.getElementById('copyButton');
    copyButton.addEventListener('click', async function () {
        const dzialFromSelect = document.getElementById('dzialFrom');
        const dzialToSelect = document.getElementById('dzialTo');

        const selectedDzialFromId = dzialFromSelect.value;
        const selectedDzialToId = dzialToSelect.value;

        if (selectedDzialFromId && selectedDzialToId) {
            const confirmMessage = "Czy na pewno chcesz skopiować dane?";
            const isConfirmed = window.confirm(confirmMessage);

            if (isConfirmed) {
                try {
                    const response = await fetch(`/elements/getToCopy/${selectedDzialFromId}`);
                    if (!response.ok) {
                        throw new Error('Błąd pobierania danych');
                    }

                    const transactions = await response.json();

                    const copyResponse = await fetch(`/elements/copy?divisionIdTo=${selectedDzialToId}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(transactions)
                    });

                    if (!copyResponse.ok) {
                        throw new Error('Błąd kopiowania danych');
                    }

                    // Aktualizuj widok po udanej operacji
                    fetchAndDisplayElements();
                } catch (error) {
                    console.error('Błąd kopiowania danych:', error);
                }
            }
        } else {
            alert('Wybierz obie dywizje.');
        }
    });
    // ... (inne fragmenty kodu)
});