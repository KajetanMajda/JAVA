document.addEventListener("DOMContentLoaded", function() {
    // Tworzenie referencji do elementów select
    var projectSelect = document.getElementById("project");
    var dzialSelect = document.getElementById("dzial");

    // Pobierz projekty z endpointu /projects/all i wypełnij select
    fetch("/projects/all")
        .then(response => response.json())
        .then(projects => {
            projects.forEach(project => {
                addOption(projectSelect, project.id, project.name);
            });
        });

    // Pobierz działy powiązane z wybranym projektem i wypełnij select
    projectSelect.addEventListener("change", function() {
        var selectedProjectId = projectSelect.value;

        // Wyczyść poprzednią listę działań
        dzialSelect.innerHTML = "";
        addOption(dzialSelect, "", "Wybierz wszystko");

        if (selectedProjectId !== "") {
            // Pobierz działy powiązane z wybranym projektem
            fetch("/division/all")
                .then(response => response.json())
                .then(divisions => {
                    divisions.forEach(division => {
                        if (division.projects && division.projects.id == selectedProjectId) {
                            addOption(dzialSelect, division.id, division.name, division.name);
                        }
                    });
                });
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

});