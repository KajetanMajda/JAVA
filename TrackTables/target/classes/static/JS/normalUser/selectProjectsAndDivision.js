document.addEventListener("DOMContentLoaded", function () {
    var projectSelect = document.getElementById("project");
    var dzialSelect = document.getElementById("dzial");

    fetch("/projects/all")
        .then(response => response.json())
        .then(projects => {
            projects.forEach(project => {
                addOption(projectSelect, project.id, project.name);
            });
        });

    projectSelect.addEventListener("change", function () {
        var selectedProjectId = projectSelect.value;

        dzialSelect.innerHTML = "";
        addOption(dzialSelect, "", "Wybierz wszystko", "all");

        if (selectedProjectId !== "") {
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


    function addOption(select, value, text, id) {
        var option = document.createElement("option");
        option.value = value;
        option.text = text;
        option.dataset.id = id;
        select.appendChild(option);
    }

});