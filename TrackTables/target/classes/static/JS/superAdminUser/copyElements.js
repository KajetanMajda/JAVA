document.addEventListener('DOMContentLoaded', function () {
    async function fetchAndFillSelectOptions() {
        try {
            const response = await fetch('/projects/all'); // Zmień na odpowiedni URL endpointa
            const projects = await response.json();

            const projectFromSelect = document.getElementById('projectFrom');
            const projectToSelect = document.getElementById('projectTo');

            projects.forEach(project => {
                const option = document.createElement('option');
                option.value = project.id;
                option.textContent = `${project.id} - ${project.name}`;
                projectFromSelect.appendChild(option);
            });

            projects.forEach(project => {
                const option = document.createElement('option');
                option.value = project.id;
                option.textContent = `${project.id} - ${project.name}`;
                projectToSelect.appendChild(option);
            });

        } catch (error) {
            console.error('Błąd podczas pobierania danych:', error);
        }
    }

    fetchAndFillSelectOptions()


    const copyButton = document.getElementById('copyButton');

    let sourceProjectId = null;
    let targetProjectId = null;

    copyButton.addEventListener('click', function () {
        const projectFromSelect = document.getElementById('projectFrom');
        const projectToSelect = document.getElementById('projectTo');

        sourceProjectId = projectFromSelect.value;
        targetProjectId = projectToSelect.value;

        if (!sourceProjectId || !targetProjectId) {
            alert('Wybierz projekt źródłowy i docelowy.');
            return;
        }

        const confirmMessage = `Czy na pewno chcesz skopiować projekt ${sourceProjectId} do projektu ${targetProjectId}?`;
        const isConfirmed = window.confirm(confirmMessage);

        if (isConfirmed) {
            copyProject(sourceProjectId, targetProjectId);
        }
    });

    async function copyProject(sourceProjectId, targetProjectId) {
        try {
            const response = await fetch(`/projects/copy?sourceProjectId=${sourceProjectId}&targetProjectId=${targetProjectId}`, {
                method: 'POST'
            });

            if (!response.ok) {
                throw new Error('Błąd kopiowania projektu.');
            }

            alert('Projekt został skopiowany wraz z dywizjami i elementami.');
            location.reload();

        } catch (error) {
            console.error('Błąd kopiowania projektu:', error);
        }
    }

});
