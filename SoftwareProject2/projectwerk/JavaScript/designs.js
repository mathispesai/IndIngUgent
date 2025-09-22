function renderDesigns() {
    const designsGrid = document.getElementById('designsGrid');
    const designs = JSON.parse(localStorage.getItem('designs')) || [];

    // Maak de grid leeg
    designsGrid.innerHTML = '';

    // Voor elk design een grid-element maken
    designs.forEach(design => {
        const designElement = document.createElement('div');
        designElement.className = 'design';
        designElement.dataset.id = design.id;

        // Afbeelding (screenshot)
        const img = document.createElement('img');
        img.src = design.screenshot;
        img.alt = design.name;

        // Naam
        const name = document.createElement('p');
        name.textContent = design.name;

        // Verwijderknop
        const deleteButton = document.createElement('button');
        deleteButton.textContent = i18n.translate('simulation.controls.planetManagement.remove');
        deleteButton.setAttribute('data-i18n', 'simulation.controls.planetManagement.remove');
        deleteButton.addEventListener('click', (e) => {
            e.stopPropagation(); // Voorkom dat de click op het design wordt geactiveerd
            const updatedDesigns = designs.filter(d => d.id !== design.id);
            localStorage.setItem('designs', JSON.stringify(updatedDesigns));
            renderDesigns(); // Herlaad designs inclusief "nieuw" tegel
        });

        // Click-event om design te laden
        designElement.addEventListener('click', () => {
            localStorage.setItem('selectedDesignId', design.id);
            window.location.href = 'solarSystem.html';
        });

        designElement.appendChild(img);
        designElement.appendChild(name);
        designElement.appendChild(deleteButton);
        designsGrid.appendChild(designElement);
    });

    // Voeg de vaste "+ Nieuw zonnestelsel" tegel toe
    voegNieuwDesignTegelToe(designsGrid);
}

function voegNieuwDesignTegelToe(grid) {
    const nieuwDesignElement = document.createElement('div');
    nieuwDesignElement.className = 'design new-design';

    const plus = document.createElement('div');
    plus.className = 'plus-icon';
    plus.textContent = '+';

    const label = document.createElement('p');
    label.textContent = i18n.translate('simulation.controls.planetManagement.solarsystem');
    label.setAttribute('data-i18n', 'simulation.controls.planetManagement.solarsystem');

    nieuwDesignElement.appendChild(plus);
    nieuwDesignElement.appendChild(label);

    nieuwDesignElement.addEventListener('click', () => {
        localStorage.removeItem('selectedDesignId');
        window.location.href = 'solarSystem.html';
    });

    grid.appendChild(nieuwDesignElement);
}

document.addEventListener('DOMContentLoaded', () => {
    // Ensure i18n is initialized first
    i18n.init();

    // Then render designs
    renderDesigns();

});