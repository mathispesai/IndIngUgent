import QuadtreeTak from "./QuadTreeTak.js";
import Punt2D from "./Punt2D.js";
import {Planeet, RuimteObject, Ster} from "./RuimteObject.js";
import { berekenKrachtVanTak } from "./krachtberekening.js";

window.isPaused = false;

function getQueryParam(name) {
    const params = new URLSearchParams(window.location.search);
    return params.get(name);
}

const urlSystem = getQueryParam('system');
if (urlSystem) {
    localStorage.setItem('selectedDesignId', urlSystem);
}

export class Starfield {
    constructor(canvas) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.stars = [];
        this.resizeCanvas();
        window.addEventListener('resize', () => {
            this.resizeCanvas();
            this.createStars();
        });
        this.createStars();

        this.ruimteobjecten = [];
        this.lastTimestamp = null;
        this.explosies = [];
    }

    resizeCanvas() {
        this.canvas.width = window.innerWidth;
        this.canvas.height = window.innerHeight;
    }

    createStars() {
        const starCount = Math.floor((this.canvas.width * this.canvas.height) / 1500);
        this.stars = [];

        for (let i = 0; i < starCount; i++) {
            this.stars.push({
                x: Math.random() * this.canvas.width,
                y: Math.random() * this.canvas.height,
                size: Math.random() * 1.5 + 0.5,
                baseOpacity: Math.random() * 0.5 + 0.5,
                opacity: 0,
                twinkleSpeed: Math.random() * 0.02 + 0.01,
                twinkleOffset: Math.random() * Math.PI * 2
            });
        }
    }

    update(tijdsinterval) {
        const time = Date.now() * 0.001;
        this.stars.forEach(star => {
            star.opacity = star.baseOpacity * (0.5 + Math.sin(time * star.twinkleSpeed + star.twinkleOffset) * 0.5);
        });

        if (!isPaused){
            let quadtree = new QuadtreeTak(new Punt2D(this.canvas.width/2, this.canvas.height/2), Math.max(this.canvas.width, this.canvas.height) / 2, 0, 5);
            for (const obj of this.ruimteobjecten) {
                quadtree.insert(obj);
            }

            quadtree.verdeelAlsNodig();

            for (const obj of this.ruimteobjecten) {
                obj.update(berekenKrachtVanTak(obj, quadtree, 0.5), tijdsinterval);
            }
        }

        //toevoegen botsingen / explosies aan update cycles
        this.detecteerBotsing();
        this.explosies.forEach(explosie => explosie.update());
        this.explosies = this.explosies.filter(explosie => !explosie.Gedaan());
    }

    draw() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

        this.ctx.fillStyle = 'black';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        //eerst achtergrond tekenen
        this.stars.forEach(star => {
            this.ctx.beginPath();
            this.ctx.arc(star.x, star.y, star.size, 0, Math.PI * 2);

            const gradient = this.ctx.createRadialGradient(
                star.x, star.y, 0,
                star.x, star.y, star.size * 2
            );
            gradient.addColorStop(0, `rgba(255, 255, 255, ${star.opacity})`);
            gradient.addColorStop(1, 'rgba(255, 255, 255, 0)');

            this.ctx.fillStyle = gradient;
            this.ctx.fill();
        });

        this.ctx.fillStyle = 'rgba(255, 255, 255, 0.1)';
        for (let i = 0; i < this.stars.length / 4; i++) {
            const x = Math.random() * this.canvas.width;
            const y = Math.random() * this.canvas.height;
            const size = Math.random() * 0.5;
            this.ctx.beginPath();
            this.ctx.arc(x, y, size, 0, Math.PI * 2);
            this.ctx.fill();
        }

        //planeten tekenen
        for (const obj of this.ruimteobjecten) {
            obj.teken(this.canvas);
        }

        //verwijzing naar tekenen van explosies vanuit draw
        this.explosies.forEach(explosie => explosie.tekenExplosie(this.ctx));

        //kruis in midden, wit, 80% opacity
        const midX = this.canvas.width / 2;
        const midY = this.canvas.height / 2;

        this.ctx.strokeStyle = 'rgba(255, 255, 255, 0.8)';
        this.ctx.lineWidth = 2;

        this.ctx.beginPath();
        this.ctx.moveTo(midX - 10, midY);
        this.ctx.lineTo(midX + 10, midY);
        this.ctx.stroke();

        this.ctx.beginPath();
        this.ctx.moveTo(midX, midY - 10);
        this.ctx.lineTo(midX, midY + 10);
        this.ctx.stroke();

    }

    animate(timestamp) {
        //tijdsverschil tussen frames berekenen zodat verschillende framerates dezelfde snelheid van planeten zien
        if (this.lastTimestamp === null) {
            this.lastTimestamp = timestamp;
        }

        const deltaSeconden = (timestamp - this.lastTimestamp) / 1000;
        const tijdsinterval = deltaSeconden * timeScale;

        if (!window.isPaused) {
            this.update(tijdsinterval);
        }

        const infoPanel = document.getElementById('planetInfoList');
        if (infoPanel && infoPanel.style.display !== 'none') {
            updatePlanetInfoData();
        }

        this.draw();
        this.lastTimestamp = timestamp;
        requestAnimationFrame((ts) => this.animate(ts));
    }

    clearObjects() {
        // Clear all objects
        this.ruimteobjecten = [];
        console.log("All space objects cleared");
    }

    
//functie die botsingen detecteert,afhandelt en explosies aanmaakt
    detecteerBotsing(){
        //Set om de te verwijderen objecten te verwjideren
        const verwijder = new Set();
        //Alle planeten checken op botsingen
        for(let i = 0;i<this.ruimteobjecten.length;i++){
            for(let j = i+1;j<this.ruimteobjecten.length;j++){
                const planeet1 = this.ruimteobjecten[i];
                const planeet2 = this.ruimteobjecten[j];
                const dx = planeet1.positie.x - planeet2.positie.x;
                const dy = planeet1.positie.y - planeet2.positie.y;
                const afstand = Math.sqrt(dx * dx + dy * dy);
                //botsing detecteren + afhandelen
                if(afstand < Math.max(planeet1.straal/2, planeet2.straal/2)){
                    const [lichtste,zwaarste] = planeet1.gewicht < planeet2.gewicht ? [planeet1,planeet2] : [planeet2,planeet1];
                    const totGewicht = lichtste.gewicht + zwaarste.gewicht;
                    zwaarste.snelheid[0] = (lichtste.snelheid[0] * lichtste.gewicht * 0.25 + zwaarste.snelheid[0] * zwaarste.gewicht) / totGewicht;
                    zwaarste.snelheid[1] = (lichtste.snelheid[1] * lichtste.gewicht * 0.25+ zwaarste.snelheid[1] * zwaarste.gewicht) / totGewicht;
                    zwaarste.gewicht += lichtste.gewicht *0.25;
                    
                    verwijder.add(lichtste);
                    
                    this.explosies.push(new Explosie(lichtste.positie.x +this.canvas.width/2, lichtste.positie.y+this.canvas.height/2 , lichtste.straal));
                }
            }
        }
        this.ruimteobjecten = this.ruimteobjecten.filter(obj => !verwijder.has(obj));

    }
}

class Explosie{
    constructor(x,y,straal){
        this.explosieDeeltjes = [];
        for(let i=0;i<30;i++){
            const hoek = Math.random() * Math.PI * 2;
            const snelheid = Math.random() * 1.3 + 0.2;
            this.explosieDeeltjes.push({
                x:x,
                y:y,
                snelheidx:Math.cos(hoek) * snelheid,
                snelheidy:Math.sin(hoek) * snelheid,
                straal: straal *0.2,
                transparantie: 1
            });

        }
    }

    update(){
        this.explosieDeeltjes.forEach(deeltje => {
            deeltje.x += deeltje.snelheidx;
            deeltje.y += deeltje.snelheidy;
            deeltje.transparantie -= 1/(deeltje.straal/0.75);
        });
        this.explosieDeeltjes = this.explosieDeeltjes.filter(deeltje => deeltje.transparantie > 0);
    }

    tekenExplosie(ctx){
        this.explosieDeeltjes.forEach(deeltje => {
            ctx.beginPath();
            ctx.arc(deeltje.x, deeltje.y, deeltje.straal, 0, Math.PI * 2);
            ctx.fillStyle = `rgba(255, 100, 0, ${deeltje.transparantie})`;
            ctx.fill();
        });
    }

    Gedaan(){
        return this.explosieDeeltjes.length === 0;
    }
}

export function importFunction() {
    const importButton = document.getElementById("import");
    const fileInput = document.getElementById("fileInput");

    // Alleen json bestanden accepteren
    fileInput.setAttribute("accept", ".json,application/json");

    importButton.addEventListener("click", () => {
        fileInput.click(); // Open file explorer
    });

    fileInput.addEventListener("change", (event) => {
        const file = event.target.files[0];
        if (!file) return;

        // Checken of de file de extensie json heeft
        if (!file.name.endsWith('.json') && file.type !== 'application/json') {
            alert("Please select a valid JSON file.");
            return;
        }

        const reader = new FileReader();

        reader.onload = function(e) {
            try {
                const importedData = JSON.parse(e.target.result);

                if (!Array.isArray(importedData)) {
                    throw new Error("Invalid data format: Expected an array of space objects");
                }

                importSpaceObjects(importedData).then(() => {
                    initPlanetInfoList();
                    starfield.draw();
                });
            } catch (error) {
                console.error("Error importing file:", error);
                alert("Error importing file: " + error.message);
            }
        };

        reader.onerror = function() {
            console.error("Error reading file");
            alert("Error reading file");
        };

        reader.readAsText(file);
    });
}


export function importSpaceObjects(objectsData) {
    return new Promise((resolve, reject) => {
        try {
            starfield.clearObjects();
            let importedCount = 0;

            for (const objData of objectsData) {
                try {
                    if (!objData.hasOwnProperty('positie') ||
                        !objData.hasOwnProperty('snelheid') ||
                        !objData.hasOwnProperty('afbeeldingsrc') ||
                        !objData.hasOwnProperty('gewicht')) {
                        console.warn("Skipping invalid object data:", objData);
                        continue;
                    }

                    const positie = new Punt2D(objData.positie.x, objData.positie.y);
                    const ruimteObject = new Planeet(
                        objData.snelheid,
                        positie,
                        objData.afbeeldingsrc,
                        objData.gewicht,
                        objData.naam || "Onbekende planeet"
                    );

                    starfield.ruimteobjecten.push(ruimteObject);
                    importedCount++;
                } catch (error) {
                    console.error(`Error creating space object from data:`, objData, error);
                }
            }

            console.log(`Successfully imported ${importedCount} space objects`);
            resolve(); // ⬅ belangrijk
        } catch (error) {
            console.error("Error during import:", error);
            alert("Er is een fout opgetreden bij het importeren: " + error.message);
            reject(error);
        }
    });

}



const starfieldCanvas = document.getElementById('starfield');
const starfield = new Starfield(starfieldCanvas);

let selectedImagePath = "../Media/Aarde.png"; // standaard afbeelding

const selectedImageDiv = document.getElementById('selectedImage');
const imageOptions = document.getElementById('imageOptions');

selectedImageDiv.addEventListener('click', () => {
    imageOptions.style.display = imageOptions.style.display === 'block' ? 'none' : 'block';
});

imageOptions.querySelectorAll('img').forEach(img => {
    img.addEventListener('click', () => {
        selectedImagePath = img.getAttribute('src');
        selectedImageDiv.style.backgroundImage = `url('${selectedImagePath}')`;
        imageOptions.style.display = 'none';
    });
});

document.getElementById('planetForm').addEventListener('submit', (e) => {
    e.preventDefault();

    const name = document.getElementById('planetName').value;
    const positionX = parseFloat(document.getElementById('positionX').value);
    const positionY = parseFloat(document.getElementById('positionY').value);
    const velocityX = parseFloat(document.getElementById('velocityX').value);
    const velocityY = parseFloat(document.getElementById('velocityY').value);
    const mass = parseFloat(document.getElementById('mass').value);

    const velocity = [velocityX, velocityY];
    const position = new Punt2D(positionX / 3, positionY / 3); //position /3 omdat canvas op schaar 3miljoen werkt en input 1miljoen

    const nieuwePlaneet = new Planeet(velocity, position, selectedImagePath, mass, name);
    starfield.ruimteobjecten.push(nieuwePlaneet);

    initPlanetInfoList();

    console.log(`Planeet toegevoegd: ${name}`);
});

function saveKnop() {
    const saveButton = document.getElementById("save");

    saveButton.addEventListener("click", () => {
        const selectedDesignId = localStorage.getItem('selectedDesignId');
        const designs = JSON.parse(localStorage.getItem('designs')) || [];
        const existingDesign = selectedDesignId ? designs.find(d => d.id === selectedDesignId) : null;

        let name = prompt("Geef een naam aan je planetenstelsel:", existingDesign ? existingDesign.name : "");
        if (!name || name.trim() === "") {
            name = existingDesign ? existingDesign.name : `Zonnestelsel ${new Date().toLocaleDateString()}`;
        }

        const canvas = document.getElementById("starfield");
        const wasPaused = window.isPaused;
        window.isPaused = true;
        starfield.draw();

        requestAnimationFrame(() => {
            const screenshot = canvas.toDataURL('image/png');
            window.isPaused = wasPaused;
            starfield.lastTimestamp = null;

            const designId = existingDesign ? existingDesign.id : Date.now().toString();

            // Create serializable versions of the space objects
            const serializableObjects = starfield.ruimteobjecten.map(obj => {
                return {
                    naam: obj.naam,
                    positie: {
                        "x": obj.positie.x,
                        "y": obj.positie.y
                    },
                    snelheid: [...obj.snelheid],
                    gewicht: obj.gewicht/(1e21),
                    afbeeldingsrc: obj.afbeeldingsrc,
                    straal: obj.straal || undefined
                };
            });

            const design = {
                id: designId,
                name: name.trim(),
                screenshot: screenshot,
                configuration: serializableObjects
            };

            if (existingDesign) {
                const updatedDesigns = designs.map(d => d.id === designId ? design : d);
                localStorage.setItem('designs', JSON.stringify(updatedDesigns));
            } else {
                designs.push(design);
                localStorage.setItem('designs', JSON.stringify(designs));
            }

            // Download the space objects as a JSON file
            downloadRuimteobjectenAsJson(design.name, starfield.ruimteobjecten);

            alert(`Design opgeslagen als "${name}"! Het JSON-bestand wordt gedownload om te kunnen importeren.`);
        });
    });
}

// Functie om alleen de ruimteobjecten als JSON-bestand te downloaden
function downloadRuimteobjectenAsJson(designName, ruimteobjecten) {
    // Create a simplified copy of ruimteobjecten for serialization
    const serializableObjects = ruimteobjecten.map(obj => {
        return {
            naam: obj.naam,
            positie: { x: obj.positie.x, y: obj.positie.y },
            snelheid: [...obj.snelheid],
            gewicht: obj.gewicht / 1e21, // maar wijzig dit NIET op obj zelf
            afbeeldingsrc: obj.afbeeldingsrc,
            straal: obj.straal || undefined
        };
    });

    const jsonString = JSON.stringify(serializableObjects, null, 2);
    const blob = new Blob([jsonString], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const downloadLink = document.createElement('a');

    downloadLink.href = url;
    downloadLink.download = `${designName.replace(/\s+/g, '_')}.json`;
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
    URL.revokeObjectURL(url);
}

function loadSelectedDesign() {
    const selectedDesignId = localStorage.getItem('selectedDesignId');
    if (selectedDesignId) {
        const designs = JSON.parse(localStorage.getItem('designs')) || [];
        const design = designs.find(d => d.id === selectedDesignId);
        if (design) {
            starfield.clearObjects();
            importSpaceObjects(design.configuration).then(() => {
                initPlanetInfoList();
                updatePlanetDropdown?.();

                starfield.createStars();
                starfield.update(0);
                starfield.draw();

                window.isPaused = true;
                updatePauseButton();
                requestAnimationFrame((ts) => starfield.animate(ts));
            });
        }
    }
    setTimeout(() => {
        console.log("Force initPlanetInfoList en achtergrond sterren");
        initPlanetInfoList();
    }, 500);
}



// Vul de dropdown met huidige planeten
function updatePlanetDropdown() {
    const dropdown = document.getElementById('planetDropdown');

    if (!dropdown) {
        console.error("Planet dropdown element not found!");
        return;
    }

    dropdown.innerHTML = ''; // Reset options

    starfield.ruimteobjecten.forEach((planeet, index) => {
        const option = document.createElement('option');
        option.value = index;
        option.textContent = planeet.naam || `Planeet ${index + 1}`;
        dropdown.appendChild(option);
    });
}
// Luister naar verwijderknop
document.getElementById('removePlanet').addEventListener('click', () => {
    const dropdown = document.getElementById('planetDropdown');
    const selectedIndex = parseInt(dropdown.value, 10);

    if (!isNaN(selectedIndex) && starfield.ruimteobjecten[selectedIndex]) {
        const verwijderde = starfield.ruimteobjecten.splice(selectedIndex, 1)[0];
        console.log("Verwijderde planeet:", verwijderde);
        updatePlanetDropdown();

    } else {
        console.log("Geen geldige planeet geselecteerd");
    }
});



// Pauzeren van de simulatie
document.getElementById('pauseSimulation').addEventListener('click', () => {
    window.isPaused = !window.isPaused;
    const pauseButton = document.getElementById('pauseSimulation');
    pauseButton.innerHTML = window.isPaused ?
        '<span class="icon">▶</span><span>Resume</span>' :
        '<span class="icon">⏸</span><span>Pause</span>';
});

document.addEventListener("DOMContentLoaded", () => {
    importFunction();
    saveKnop();

    requestAnimationFrame(() => {
        if (localStorage.getItem('selectedDesignId')) {
            loadSelectedDesign();
        }

        const tijdelijkeData = localStorage.getItem("temporaryPlanet");
        if (tijdelijkeData) {
            importSpaceObjects(JSON.parse(tijdelijkeData)).then(() => {
                initPlanetInfoList();
                starfield.draw();
            });
            localStorage.removeItem("temporaryPlanet");
        }
    });
});

/* Zon wordt getekend maar de andere planeten niet ?
function startAnimatie() {
    requestAnimationFrame(starfield.draw);
}

window.addEventListener('load', () => {
    const tijd = Date.now();
    starfield.ruimteobjecten.push(
        new Planeet(
            [0, 0],
            new Punt2D(starfieldCanvas.width / 2, starfieldCanvas.height / 2),
            "/../Media/Zon.png",
            3000000000000000000000,
            "zon" + tijd
        )
    );
    startAnimatie();
    console.log("Zon toegevoegd bij het opstarten");
});

 */



const timeSlider = document.getElementById('timeSpeed');
const timeDisplay = document.createElement('div');
timeDisplay.className = 'time-display';
timeSlider.parentNode.insertBefore(timeDisplay, timeSlider.nextSibling);

let timeScale = 0;

timeSlider.addEventListener('input', (e) => {
    const value = parseFloat(e.target.value);
    timeScale = value;


    const slider = e.target;
    const percentage = ((value + 6) / 12) * 100;


    slider.style.background = `linear-gradient(to right, 
        var(--accent-color) 0%, 
        var(--accent-color) ${percentage}%, 
        var(--secondary-color) ${percentage}%, 
        var(--secondary-color) 100%)`;


    if (value === 0) {
        timeDisplay.textContent = 'Stopped';
    } else {
        const months = Math.abs(value);
        const direction = value < 0 ? 'backward' : 'forward';
        timeDisplay.textContent = `${months} month${months !== 1 ? 's' : ''} per second ${direction}`;
    }
});

updatePlanetInfoData();
requestAnimationFrame((ts) => starfield.animate(ts));

document.addEventListener('DOMContentLoaded', () => {
    const mijndesignknop = document.getElementById('designsknop');
    if (mijndesignknop) {
        mijndesignknop.addEventListener('click', () => {
            window.location.href = '../HTML/design.html';
        });
    } else {
        console.warn("Designknop niet gevonden");
    }

});

document.addEventListener('DOMContentLoaded', () => {
    const toggleButton = document.getElementById('toggle-btn');
    const controlsPanel = document.querySelector('.controls-panel');

    toggleButton.addEventListener('mousedown', () => {
        controlsPanel.classList.add('hidden');
    });
    document.addEventListener('mouseup', () => {
        controlsPanel.classList.remove('hidden');
    });
});

// Laatst toegevoegde planeet wordt verwijderd
// Als die is verwijderd kan je de planeet ervoor verwijderen enz...
document.getElementById('removePlanet').addEventListener('click', () => {
    if (starfield.ruimteobjecten.length > 0) {
        const verwijderde = starfield.ruimteobjecten.pop();
        console.log(`Laatste planeet verwijderd: ${verwijderde.naam}`);
        initPlanetInfoList();
        updatePlanetDropdown?.();
        starfield.draw();
    } else {
        console.log("Geen planeten om te verwijderen.");
    }
});

function initPlanetInfoList() {
    const container = document.getElementById('planetListContent');
    container.innerHTML = ''; // Reset

    starfield.ruimteobjecten.forEach((planeet, index) => {
        const item = document.createElement('div');
        item.className = 'planet-info-item';
        item.dataset.index = index;

        const img = document.createElement('img');
        img.src = planeet.afbeeldingsrc;
        img.alt = planeet.naam;

        const info = document.createElement('div');
        info.className = 'planet-info-text';

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'X';
        deleteButton.className = 'delete-planet-button';
        deleteButton.title = `Verwijder ${planeet.naam}`;
        deleteButton.addEventListener('click', () => {
            starfield.ruimteobjecten.splice(index, 1);
            initPlanetInfoList();
            updatePlanetDropdown?.();
            starfield.draw();
        });

        item.appendChild(img);
        item.appendChild(info);
        item.appendChild(deleteButton);
        container.appendChild(item);
    });

    updatePlanetInfoData(); // Initieer data
}
function updatePlanetInfoData() {
    const items = document.querySelectorAll('.planet-info-item');
    items.forEach((item, index) => {
        const infoBox = item.querySelector('.planet-info-text');
        const planeet = starfield.ruimteobjecten[index];

        if (planeet && infoBox) {
            infoBox.innerHTML = generatePlaneetStatsHTML(planeet);
        }
    });
}


function generatePlaneetStatsHTML(planeet) {
    return `
        <strong>${planeet.naam}</strong><br>
        📍 ${i18n.t('planet.position')}: (${planeet.positie.x.toFixed(2)}, ${planeet.positie.y.toFixed(2)})<br>
        💨 ${i18n.t('planet.velocity')}: (${planeet.snelheid[0].toFixed(2)}, ${planeet.snelheid[1].toFixed(2)})<br>
        ⚖️ ${i18n.t('planet.mass')}: ${planeet.gewicht.toExponential(2)} kg
    `;
}

document.getElementById('toggleInfoPanel').addEventListener('click', () => {
    const panel = document.getElementById('planetInfoList');
    const knop = document.getElementById('toggleInfoPanel');

    const isHidden = panel.style.display === 'none' || panel.style.display === '';

    panel.style.display = isHidden ? 'block' : 'none';
    knop.innerHTML = isHidden
        ? `🔽 ${i18n.t('planetList.hideButton')}`
        : `🔼 ${i18n.t('planetList.showButton')}`;
});

document.addEventListener('languageChanged', () => {
    updatePlanetInfoData();
});

// errors fixen omtrent pause button
function updatePauseButton() {
    const pauseButton = document.getElementById('pauseSimulation');
    if (!pauseButton) return;

    pauseButton.innerHTML = window.isPaused
        ? '<span class="icon">▶</span><span>Resume</span>'
        : '<span class="icon">⏸</span><span>Pause</span>';
}