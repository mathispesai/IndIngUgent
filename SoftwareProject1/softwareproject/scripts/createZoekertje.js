import { doc, setDoc, updateDoc } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import { db, auth } from "./firebaseConfig.js";
import { marker } from "./mapLocatieKiezen.js";
import fetchProfileData from "./FetchProfileData.js";

const submit = document.getElementById('submit');
const cancel = document.getElementById('cancelbtn');
const searchInput = document.getElementById('searchInput');

let email = null;
let user = null;
let selectedSports = []; // Array om geselecteerde sporten bij te houden

cancel.addEventListener('click', async function(event) {
    event.preventDefault();
    console.log("cancel clicked");
    window.location.href = "hoofdscherm.html";
});

auth.onAuthStateChanged((authUser) => {
    user = authUser;
    if (user) {
        email = user.email;
    } else {
        window.location.href = "login.html";
    }
});

submit.addEventListener("click", async function(event) {
    event.preventDefault();
    if (!user) {
        console.error("User not authenticated");
        return;
    }
    try {
        const { userData,profilePicUrl } = await fetchProfileData(user.email);
        let aantalzoekertjes = userData.aantalzoekertjes;

        const titel = document.getElementById('title').value;
        const description = document.getElementById('Description').value;
        const minimumage = document.getElementById("fromSlider").value;
        const maximumage = document.getElementById("toSlider").value;
        const aantalSporters = document.getElementById("AmountOfSporters").value;

        // Geselecteerde sporten ophalen uit de checkboxes
        const sportCheckboxes = document.querySelectorAll('input[name="sport"]:checked');
        const selectedSports = Array.from(sportCheckboxes).map(checkbox => checkbox.value);

        // Controleren of alle verplichte velden zijn ingevuld
        if (!titel || !description || selectedSports.length === 0) {
            alert("Please fill in all required fields.");
            return;
        }
        if (aantalSporters > 99 || aantalSporters < 0) {
            alert("please enter a correct number of people");
            return;
        }

        let zoekertjeslinks = userData.zoekertjeslinks || [];
        const docRef = await setDoc(doc(db, "Zoekertjes", email + "("+(aantalzoekertjes+1)+")"), {
            titel: titel,
            description: description,
            minimumage: minimumage,
            maximumage: maximumage,
            aantalSporters: aantalSporters,
            sport: selectedSports, // Geselecteerde sporten toevoegen aan het object
            lat: marker.position.lat,
            lng: marker.position.lng,
            aantalgeaccepteerd: 0,
            geaccepteerdesporters: null,
        });

        // De zoekertjes halen uit de Firebase om bij het profiel te zetten
        const userDocRef = doc(db, "users", email);
        let aantal = aantalzoekertjes+1;
        const newZoekertjeLink = email + "(" + (aantalzoekertjes + 1) + ")";
        zoekertjeslinks.push(newZoekertjeLink);
        await updateDoc(userDocRef, {
            aantalzoekertjes: aantal,
            zoekertjeslinks: zoekertjeslinks
        });
        window.location.href = "hoofdscherm.html";
    } catch (error) {
        console.error(error);
        const errorMessage = error.message;
        alert(errorMessage);
    }
});

const form = document.querySelector('form');
form.addEventListener('submit', function(event) {
    // Validatie code hier...
});

// Zoekfunctionaliteit implementeren
const allSports = ["Voetbal","Basketbal","Tennis","Golf","Rugby","Cricket","Hockey","Atletiek","Zwemmen","Volleybal","Badminton","Handbal","Honkbal","American football","Wielrennen","Boksen","Schaatsen","Judo","Paardensport","Karate","Surfen","Snowboarden","Skiën","Waterskiën","Turnen","Worstelen","Boogschieten","Gewichtheffen","Roeien","Kanoën","Triatlon","Bergbeklimmen","Boulderen","Squash","Racketsport","Ski-jöring","Paddleboarding","Rugby sevens","Beachvolleybal","Freerunning","Slacklining","Polo","Ultimate frisbee","Padel","Muurklimmen","Kajakpolo","BMX","Skateboarden","Breakdancing"];

const renderSportsCheckboxes = (sports) => {
    const sportCheckboxContainer = document.getElementById('sport-checkboxes');
    sportCheckboxContainer.innerHTML = ''; // Vorige checkboxes verwijderen

    sports.forEach(sport => {
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.name = 'sport';
        checkbox.value = sport;

        // Controleren of deze sport al geselecteerd is en deze zo nodig selecteren
        checkbox.checked = selectedSports.includes(sport);

        const label = document.createElement('label');
        label.textContent = sport;

        const div = document.createElement('div');
        div.appendChild(checkbox);
        div.appendChild(label);
        sportCheckboxContainer.appendChild(div);

        // Luisteren naar wijzigingen in de selectiestatus van het selectievakje
        checkbox.addEventListener('change', function() {
            if (this.checked) {
                selectedSports.push(sport);
                searchInput.value= ""
            } else {
                const index = selectedSports.indexOf(sport);
                if (index !== -1) {
                    selectedSports.splice(index, 1);
                }
            }
            updateDisplayedSports();
        });
    });
};

const updateDisplayedSports = () => {
    const searchTerm = searchInput.value.toLowerCase();
    let filteredSports = allSports;

    if (searchTerm.trim() !== '') {
        filteredSports = allSports.filter(sport => sport.toLowerCase().includes(searchTerm));
    } else {
        filteredSports = selectedSports;
    }

    renderSportsCheckboxes(filteredSports);
};

searchInput.addEventListener('input', function() {
    updateDisplayedSports();
});

window.addEventListener('load', function() {
    updateDisplayedSports();
});