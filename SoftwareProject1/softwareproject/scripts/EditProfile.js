import {doc, updateDoc} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import {auth, db, storage} from "./firebaseConfig.js";
import fetchProfileData from "./FetchProfileData.js";
import {ref, uploadBytes} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-storage.js";
import {map,marker} from "./mapLocatieKiezen.js";

const img = document.getElementById('profile-pic');
let searchTerm ="";
const allSports = ["Voetbal","Basketbal","Tennis","Golf","Rugby","Cricket","Hockey","Atletiek","Zwemmen","Volleybal","Badminton","Handbal","Honkbal","American football","Wielrennen","Boksen","Schaatsen","Judo","Paardensport","Karate","Surfen","Snowboarden","Skiën","Waterskiën","Turnen","Worstelen","Boogschieten","Gewichtheffen","Roeien","Kanoën","Triatlon","Bergbeklimmen","Boulderen","Squash","Racketsport","Ski-jöring","Paddleboarding","Rugby sevens","Beachvolleybal","Freerunning","Slacklining","Polo","Ultimate frisbee","Padel","Muurklimmen","Kajakpolo","BMX","Skateboarden","Breakdancing"];
// Voeg een object toe om de selectiestatus van elke checkbox bij te houden
const checkboxStatus = {};
// Zoekbalk element
const searchInput = document.getElementById('searchInput');
// Haalt eerst data op met de fetchData.
const setData = async () => {
    try {
        auth.onAuthStateChanged(async (user) => {
            if (user) {
                const { userData, profilePicUrl } = await fetchProfileData(user.email);
                document.getElementById('email').textContent = user.email;
                document.getElementById('firstName').value = userData.first;
                document.getElementById('lastName').value = userData.last;
                document.getElementById('age').value = userData.age;
                document.getElementById('niveau').value = userData.niveau;
                // Set profile picture
                img.src = profilePicUrl;

                //marker positie juist zetten
                marker.position = {lat: userData.lat, lng: userData.lng};
                map.panTo(marker.position);

                // Vervang drop-down lijst door checkboxes voor sporten
                const sportCheckboxContainer = document.getElementById('sport-checkboxes');
                sportCheckboxContainer.innerHTML = '';

                allSports.forEach(sportName => {
                    const isChecked = userData.sport.includes(sportName);
                    checkboxStatus[sportName] = isChecked; // Sla de selectiestatus op voor elke sport
                    if (isChecked) {
                        const sportCheckbox = createSportCheckbox(sportName, true);
                        sportCheckboxContainer.appendChild(sportCheckbox);

                    }
                });
            } else {
                console.error("User not authenticated");
                // Handle authentication errors
            }
        });
    } catch (error) {
        console.error(error);
        // Handle other errors
    }
};
setData();

// Luister naar wijzigingen in de zoekterm
searchInput.addEventListener('input', function() {
     searchTerm = searchInput.value.toLowerCase();
    if (searchTerm.trim() === '') {
        // Als het zoekvak leeg is, toon alleen de aangevinkte sporten
        renderSportsCheckboxes(getCheckedSports());
    } else {
        // Als er tekst in het zoekvak staat, filter de sporten op basis van de zoekterm
        const filteredSports = allSports.filter(sport => sport.toLowerCase().includes(searchTerm));
        renderSportsCheckboxes(filteredSports);
    }
});
// Functie om de aangevinkte sporten op te halen
function getCheckedSports() {
    return allSports.filter(sportName => checkboxStatus[sportName]);
}
// Functie om de checkboxes dynamisch weer te geven op basis van de gefilterde sporten
function renderSportsCheckboxes(sports) {
    const sportCheckboxContainer = document.getElementById('sport-checkboxes');
    sportCheckboxContainer.innerHTML = ''; // Wis eerst alle vorige checkboxes

    sports.forEach(sportName => {
        const isChecked = checkboxStatus[sportName] || false;
        const sportCheckbox = createSportCheckbox(sportName, isChecked);
        sportCheckboxContainer.appendChild(sportCheckbox);
    });
}
// Functie om checkboxen te maken voor elke sport
function createSportCheckbox(sportName, isChecked) {
    const checkbox = document.createElement('input');
    checkbox.type = 'checkbox';
    checkbox.value = sportName;
    checkbox.id = `sport-${sportName}`;
    checkbox.checked = isChecked;

    const label = document.createElement('label');
    label.textContent = sportName;
    label.htmlFor = `sport-${sportName}`;

    const checkboxDiv = document.createElement('div');
    checkboxDiv.appendChild(checkbox);
    checkboxDiv.appendChild(label);

    // Luister naar wijzigingen in de selectiestatus van de checkbox
    checkbox.addEventListener('change', function() {
        checkboxStatus[sportName] = this.checked;// Sla de selectiestatus op
        searchTerm = ""; // Clear the search term
        searchInput.value = "";
        renderSportsCheckboxes(getCheckedSports());
    });
    return checkboxDiv;
}
const submit = document.getElementById('opslaan');

// Indien op de submit knop wordt gedrukt, worden de wijzigingen opgeslagen
submit.addEventListener("click", async function(event) {
    event.preventDefault();
    const email = document.getElementById('email').textContent;
    const fname = document.getElementById('firstName').value;
    const lname = document.getElementById('lastName').value;
    const age = document.getElementById('age').value;
    const niveau = document.getElementById('niveau').value;
    const lat = marker.position.lat;
    const lng = marker.position.lng;

    if (age < 18 || age > 99) {
        alert("Age must be between 18 and 99.");
        return;
    }
    if (niveau < 1 || niveau > 5) {
        alert("Niveau must be between 1 and 5.");
        return;
    }
    // Bouw de lijst met geselecteerde sporten opnieuw op op basis van de status van elke checkbox
    const selectedSports = Object.keys(checkboxStatus).filter(sport => checkboxStatus[sport]);
    try {
        const dataToUpdate = {
            first: fname,
            last: lname,
            age: age,
            niveau: niveau,
            sport: selectedSports, // Geef array van geselecteerde sporten door
            lat:lat,
            lng:lng
        };
        const docRef = doc(db, "users", email);
        await updateDoc(docRef, dataToUpdate);

        const profilePictureInput = document.getElementById('profile-picture-input');
        const profilePictureFile = profilePictureInput.files[0];
        if (profilePictureFile) {
            const storageRef = ref(storage, 'ProfilePictures/' + String(email));
            uploadBytes(storageRef, profilePictureFile).then((snapshot) => {
                console.log('Uploaded a blob or file!');
            });
        }
        window.location.href = "profiel.html";
    } catch (error) {
        const errorCode = error.code;
        const errorMessage = error.message;
        alert(errorMessage);
    }
});