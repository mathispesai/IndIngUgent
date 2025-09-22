import { doc, setDoc } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import {createUserWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js";
import { uploadBytes } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-storage.js";
import {auth,db,storage} from "./firebaseConfig.js";
import {SporterData, generateRandomLocation,userCollection} from "./GraafConfig.js";
import {marker} from "./mapLocatieKiezen.js";
import {ref, getDownloadURL } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-storage.js";

const submit = document.getElementById('signupbtn');
const cancel = document.getElementById('cancelbtn');

auth.onAuthStateChanged(async (user) => {
    if (user) {
        window.location.href= "hoofdscherm.html";
    }

});

//cancel-button stuurt je terug naar main scherm
    cancel.addEventListener('click', async function(event) {
        event.preventDefault()
        console.log("cancel clicked")
        window.location.href = "index.html";
    })
let sporten = []

//submit button maakt profiel en stuurt je door naar hoofdscherm
submit.addEventListener("click", async function(event) {
    event.preventDefault();
    console.log("signup clicked")
    const email = document.getElementById('email').value;
    const passwd = document.getElementById('psw').value;
    const repeat_pswd = document.getElementById('psw-repeat').value;
    const fname = document.getElementById('fname').value;
    const lname = document.getElementById('lname').value;
    const age = document.getElementById('age').value;
    const niveau = document.getElementById("niveau").value;
    const aantalzoekertjes = 0;
    const zoekertjeslinks = []

    if (age < 18 || age > 99) {
        alert("Age must be between 18 and 99.");
        return;
    }

    if (niveau < 1 || niveau > 5) {
        alert("Niveau must be between 1 and 5.");
        return;
    }

    if (passwd !== repeat_pswd) {
        alert("Passwords do not match.");
        return;
    }

    const profilePictureInput = document.getElementById('profile-picture');
    try {
        let profilePictureFile = profilePictureInput.files[0];
        if (profilePictureFile === undefined) {

            await fetch('./images/profiel.png')
                .then(response => response.blob()) // Convert response to blob
                .then(blob => {
                    profilePictureFile = blob;
                    // Do something with profilePictureFile, such as displaying it
                })
                .catch(error => console.error('Error fetching profile picture:', error));
        }

        const storageRef = ref(storage, 'ProfilePictures/'+String(email));
        uploadBytes(storageRef, profilePictureFile).then((snapshot) => {
            console.log('Uploaded a blob or file!');
        });
        //voor graaf algoritme
        SporterData.sporterLevels[email] = niveau;
        SporterData.sporterAges[email] = age;
        SporterData.sporterInterests[email] = sporten;

        //SporterData.graph.addNode(email, { latitude: marker.position.lat, longitude: marker.position.lng });
        const docRef = await setDoc(doc(db, "users", email), {
            first: fname,
            last: lname,
            age: age,
            sport: sporten,
            niveau: niveau,
            aantalzoekertjes: aantalzoekertjes,
            zoekertjeslinks: zoekertjeslinks,
            geaccepteerdezoekertjes: [],
            lat:marker.position.lat,
            lng:marker.position.lng
        });
        const userCredential = await createUserWithEmailAndPassword(auth, email, passwd);
        const user = userCredential.user;
        window.location.href = "hoofdscherm.html";
    } catch (error) {
        const errorCode = error.code;
        const errorMessage = error.message;
        alert(errorMessage);
    }
})

//code voor zoeksporten ding
const allSports = ["Voetbal","Basketbal","Tennis","Golf","Rugby","Cricket","Hockey","Atletiek","Zwemmen","Volleybal","Badminton","Handbal","Honkbal","American football","Wielrennen","Boksen","Schaatsen","Judo","Paardensport","Karate","Surfen","Snowboarden","Skiën","Waterskiën","Turnen","Worstelen","Boogschieten","Gewichtheffen","Roeien","Kanoën","Triatlon","Bergbeklimmen","Boulderen","Squash","Racketsport","Ski-jöring","Paddleboarding","Rugby sevens","Beachvolleybal","Freerunning","Slacklining","Polo","Ultimate frisbee","Padel","Muurklimmen","Kajakpolo","BMX","Skateboarden","Breakdancing"];

const searchInput = document.getElementById('searchInput');
let filteredSports = []; // Een lijst om de gefilterde sporten bij te houden

// Luister naar wijzigingen in de zoekterm
searchInput.addEventListener('input', function() {
    const searchTerm = searchInput.value.toLowerCase();
    if (searchTerm.trim() === '') {
        // Als het zoekvak leeg is, toon alleen de aangevinkte sporten
        renderSportsCheckboxes(getCheckedSports());
    } else {
        // Als er tekst in het zoekvak staat, filter de sporten op basis van de zoekterm
        filteredSports = allSports.filter(sport => sport.toLowerCase().includes(searchTerm));
        renderSportsCheckboxes(filteredSports);
    }
});

// Functie om de aangevinkte sporten op te halen
function getCheckedSports() {
    return allSports.filter(sportName => sporten.includes(sportName));
}

// Functie om de checkboxes dynamisch weer te geven op basis van de gefilterde sporten
function renderSportsCheckboxes(sports) {
    console.log("test");
    const sportCheckboxContainer = document.getElementById('sportCheckboxContainer');
    sportCheckboxContainer.innerHTML = ''; // Wis eerst alle vorige checkboxes

    sports.forEach(sport => {
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.name = 'sport';
        checkbox.value = sport;

        // Controleer of deze sport al is geselecteerd en vink het aan indien nodig
        checkbox.checked = sporten.includes(sport);

        const label = document.createElement('label');
        label.textContent = sport;

        const div = document.createElement('div');
        div.appendChild(checkbox);
        div.appendChild(label);
        sportCheckboxContainer.appendChild(div);

        // Luister naar wijzigingen in de selectiestatus van de checkbox
        checkbox.addEventListener('change', function() {
            const isChecked = this.checked;
            const sport = this.value;
            if (isChecked) {
                sporten.push(sport); // Voeg sport toe aan de lijst
            } else {
                const index = sporten.indexOf(sport);
                if (index !== -1) {
                    sporten.splice(index, 1); // Verwijder sport uit de lijst als checkbox wordt uitgevinkt
                }
            }
        });
    });
    const checkboxes = document.querySelectorAll('input[name=sport]');

    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
        console.log("test");            const sport = this.value;
            if (this.checked) {
                // Controleer of de sport al aanwezig is in de lijst
                if (!sporten.includes(sport)) {
                    sporten.push(sport);
                    // Voeg sport toe aan de lijst
                }
            } else {
                const index = sporten.indexOf(sport);
                if (index !== -1) {
                    sporten.splice(index, 1);
                    // Verwijder sport uit de lijst als checkbox wordt uitgevinkt
                }
            }
            searchInput.value ="";
            renderSportsCheckboxes(sporten);
        });
    });
}


document.getElementById("niveau").addEventListener("change",function(){if((document.getElementById("niveau").value)>5){document.getElementById("niveau").value =5}else if((document.getElementById("niveau").value)<1){document.getElementById("niveau").value =1} })
document.getElementById("age").addEventListener("change", function(){if(document.getElementById("age").value<=0){document.getElementById("age").value= 1}})