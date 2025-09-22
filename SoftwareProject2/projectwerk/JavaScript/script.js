document.addEventListener("DOMContentLoaded", function () {
    initializeApp();

    const colorBlindSelect = document.getElementById("colorBlindSelect");
    if (colorBlindSelect) {
        colorBlindSelect.addEventListener("change", function () {
            const selectedMode = this.value;
            setColorBlindMode(selectedMode);
        });
    }

    const savedColorBlindMode = localStorage.getItem('colorBlindMode');
    if (savedColorBlindMode) {
        setColorBlindMode(savedColorBlindMode);
    }
});

function initializeApp() {
    const zonnestelsels = document.querySelectorAll(".zonnestelsel");

    zonnestelsels.forEach((zonnestelsel) => {
        zonnestelsel.addEventListener("mouseenter", function () {
            zonnestelsel.style.transform = "scale(1.1)";
            zonnestelsel.style.transition = "transform 0.3s ease";
        });

        zonnestelsel.addEventListener("mouseleave", function () {
            zonnestelsel.style.transform = "scale(1)";
        });
    });

    setupNavigation();
    initializeSettings();
}

function setColorBlindMode(modus) {
    localStorage.setItem('colorBlindMode', modus);
    document.body.classList.remove('kleurenblind-protanopia', 'kleurenblind-deuteranopia', 'kleurenblind-tritanopia', 'kleurenblind-achromatopsia');
    if (modus !== 'normaal') {
        document.body.classList.add(`kleurenblind-${modus}`);
    }

    const links = document.querySelectorAll('a');
    links.forEach(link => {
        link.classList.remove('link-protanopia', 'link-deuteranopia', 'link-tritanopia', 'link-achromatopsia');
        if (modus !== 'normaal') {
            link.classList.add(`link-${modus}`);
        }
    });

    const sliders = document.querySelectorAll('input[type="range"]');
    sliders.forEach(slider => {
        slider.classList.remove('kleurenblind-protanopia', 'kleurenblind-deuteranopia', 'kleurenblind-tritanopia', 'kleurenblind-achromatopsia');
        if (modus !== 'normaal') {
            slider.classList.add(`kleurenblind-${modus}`);
        }
    });

    veranderAfbeeldingKleurenblind(modus);
}

function resetKleurenblindheid() {
    localStorage.removeItem('colorBlindMode');

    document.body.classList.remove('kleurenblind-protanopia', 'kleurenblind-deuteranopia', 'kleurenblind-tritanopia', 'kleurenblind-achromatopsia');
    const links = document.querySelectorAll('a');
    links.forEach(link => {
        link.classList.remove('link-protanopia', 'link-deuteranopia', 'link-tritanopia', 'link-achromatopsia');
    });
    const sliders = document.querySelectorAll('input[type="range"]');
    sliders.forEach(slider => {
        slider.classList.remove('kleurenblind-protanopia', 'kleurenblind-deuteranopia', 'kleurenblind-tritanopia', 'kleurenblind-achromatopsia');
    });

    veranderAfbeeldingKleurenblind("normaal");
}

function setupNavigation() {
    const navLinks = {
        "creeer-pagina": "creeer.html",
        "mijndesigns": "design.html",
        "overdeapp": "info.html",
        "nbody": "algoritme.html"
    };

    for (const [id, url] of Object.entries(navLinks)) {
        const element = document.getElementById(id);
        if (element) {
            element.addEventListener("click", function (event) {
                navigeerNaar(url);
            });
        }
    }

    // Voeg taalkliklisteners toe (voor veiligheid, als fallback)
    document.querySelectorAll('.dropdown-menu a').forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            const onclickAttr = this.getAttribute('onclick');
            if (onclickAttr && onclickAttr.includes('i18n.changeLanguage')) {
                const langMatch = onclickAttr.match(/i18n\.changeLanguage\('([^']+)'\)/);
                if (langMatch && langMatch[1]) {
                    i18n.changeLanguage(langMatch[1]);
                }
            }
        });
    });
}

function veranderAfbeeldingKleurenblind(type) {
    const AlleAfbeeldingen = document.querySelectorAll('.kleurenblind');

    AlleAfbeeldingen.forEach(afbeelding => {
        let oorspronkelijk = afbeelding.getAttribute('src');
        const extensie = oorspronkelijk.slice(oorspronkelijk.lastIndexOf('.'));
        const basis = oorspronkelijk.slice(0, oorspronkelijk.lastIndexOf('.'));
        const basisZonderSuffix = basis.replace(/_[a-z]+$/, '');

        let nieuwAfbeelding = type === 'normaal'
            ? `${basisZonderSuffix}${extensie}`
            : `${basisZonderSuffix}_${type}${extensie}`;

        afbeelding.setAttribute('src', nieuwAfbeelding);
    });
}

function navigeerNaar(url) {
    window.location.href = url;
}

function initializeSettings() {
    const savedMode = localStorage.getItem('colorBlindMode') || 'normaal';
    const select = document.getElementById("colorBlindSelect");
    if (select) {
        select.value = savedMode;
        setColorBlindMode(savedMode);
    }
}

const button = document.getElementById('toggleButton');
const content = document.getElementById('dropdownContent');

/*button.addEventListener('click', () => {
    // Als de dropdown verborgen is, eerst zichtbaar maken
    if (content.classList.contains('hidden')) {
        content.classList.remove('hidden');

        // kleine vertraging om de animatie te laten werken
        setTimeout(() => content.classList.add('show'), 10);
    } else {
        content.classList.remove('show');

        // wacht tot de animatie klaar is, dan verbergen
        content.addEventListener('transitionend', function handleTransition() {
            content.classList.add('hidden');
            content.removeEventListener('transitionend', handleTransition);
        });
    }
});




function updatePlanetImage() {
    const selectElement = document.getElementById("planet-select");
    const imageElement = document.getElementById("planet-image");
    const selectedPlanet = selectElement.value;

    if (selectedPlanet) {
    // Stel de src van het img-element in op het pad naar de afbeelding in de Media-map
        imageElement.src = `../Media/${selectedPlanet}.png`;
        imageElement.alt = `Afbeelding van ${selectElement.options[selectElement.selectedIndex].text}`;
    } else {
    // Als er geen planeet is geselecteerd, leeg de afbeelding
        imageElement.src = "";
        imageElement.alt = "Planeet afbeelding";
    }
}



window.onload = function() {
    updatePlanetImage();
};

*/

function showPage(pageId, event) {
    event.preventDefault(); // Voorkom standaardgedrag
    // Verberg alle content-secties
    const contents = document.getElementsByClassName('content-section');
    for (let content of contents) {
        content.classList.add('hidden');
    }
    // Toon de geselecteerde content-sectie
    document.getElementById(pageId).classList.remove('hidden');

    // Verwijder active-klasse van alle nav-links
    const navLinks = document.getElementsByClassName('nav-link');
    for (let link of navLinks) {
        link.classList.remove('active');
    }
    // Voeg active-klasse toe aan de geklikte link
    event.target.classList.add('active');
}

// Code om juiste pdf file (qua taal) te openen bij klikken gebruiksvoorwaarden/privacybeleid.
function updateFooterLinksBasedOnLanguage(lang) {
    const selectedLang = ['nl', 'fr', 'en', 'de'].includes(lang) ? lang : 'nl';

    const privacyLink = document.getElementById('privacy-link');
    const termsLink = document.getElementById('terms-link');

    if (privacyLink) {
        privacyLink.setAttribute('href', `../PDFFiles/SolarsimPrivacybeleid_${selectedLang}.pdf`);
        privacyLink.setAttribute('target', '_blank');
        privacyLink.setAttribute('rel', 'noopener noreferrer');
    }

    if (termsLink) {
        termsLink.setAttribute('href', `../PDFFiles/SolarsimGebruiksvoorwaarden_${selectedLang}.pdf`);
        termsLink.setAttribute('target', '_blank');
        termsLink.setAttribute('rel', 'noopener noreferrer');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const lang = localStorage.getItem('preferredLang') || 'nl';
    updateFooterLinksBasedOnLanguage(lang);

    document.addEventListener('languageChanged', (event) => {
        updateFooterLinksBasedOnLanguage(event.detail);
    });
});






