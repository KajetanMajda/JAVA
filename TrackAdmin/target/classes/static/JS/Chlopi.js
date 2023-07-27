document.getElementById('logoutButton').addEventListener('click', function () {
    window.location.href = '/auth';
});

document.addEventListener('DOMContentLoaded', function () {
    var loggedInUser = getCookieValue('loggedInUser');
    document.getElementById('loggedInUser').innerText = loggedInUser || '';
});

function getCookieValue(name) {
    var value = '; ' + document.cookie;
    var parts = value.split('; ' + name + '=');
    if (parts.length === 2) return parts.pop().split(';').shift();
}

document.addEventListener('DOMContentLoaded', () => {
    const markaSelect = document.getElementById('marka');
    const modelSelect = document.getElementById('model');


    markaSelect.addEventListener('change', () => {
        const selectedMarka = markaSelect.value;
        updateModelOptions(selectedMarka);
    });

    function updateModelOptions(selectedMarka) {
        modelSelect.innerHTML = '<option value="" hidden>Wybierz Dział</option>';

        if (selectedMarka === 'audi') {
            addOption(modelSelect, 'A3');
            addOption(modelSelect, 'A4');
            addOption(modelSelect, 'A6');
        } else if (selectedMarka === 'bmw') {
            addOption(modelSelect, 'Seria 1');
            addOption(modelSelect, 'Seria 3');
            addOption(modelSelect, 'Seria 5');
        }
    }

    function addOption(selectElement, text) {
        const option = document.createElement('option');
        option.textContent = text;
        option.value = text.toLowerCase().replace(/\s/g, '');
        selectElement.appendChild(option);
    }
});