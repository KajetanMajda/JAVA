function removeUser(username) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/removeUser/' + username, true);
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = '/Szlachta';
        }
    };
}

function updateIsAdmin(newValue) {
    var isAdminValue = document.getElementById('isAdminValue');
    isAdminValue.innerText = newValue;
}

document.getElementById('logiButton').addEventListener('click', function () {
    window.location.href = '/logi';
});

document.getElementById('logoutButton').addEventListener('click', function () {
    window.location.href = '/auth';
});