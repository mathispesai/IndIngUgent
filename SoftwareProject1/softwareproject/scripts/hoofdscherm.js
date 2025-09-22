import {
    browserSessionPersistence,
    onAuthStateChanged,
    setPersistence
} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js";
import {ref, getDownloadURL} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-storage.js";
import {collection, getDocs, getDoc, doc} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import {auth, db, storage} from "./firebaseConfig.js";
import fetchProfileData from "./FetchProfileData.js";
import {findBestMatches, calculateDistance, deg2rad} from "./graaf.js";
import {SporterData} from "./GraafConfig.js";

const img = document.getElementById('profiel_img');

let textremoved = false;
//persistence zorgt ervoor dat je ingelogd blijft tussen paginas
let email = null;
setPersistence(auth, browserSessionPersistence)
    .then(() => {
        console.log("Local persistence enabled");
        // Start your application or authentication process here
    })
    .catch((error) => {
        console.error("Error setting persistence: ", error);
    });
let beginzoekertjes = 0;
let eindzoekertjes = 2;
let zoekertjes = [];
let zoekertjesElementen = [];
let index = -1;

async function showNextZoekertjes(richting) {
    if (richting === "vooruit" && eindzoekertjes <= zoekertjes.length - 3) {
        beginzoekertjes += 3;
        eindzoekertjes += 3;
    }
    if (richting === "achteruit" && beginzoekertjes >= 3) {
        beginzoekertjes -= 3;
        eindzoekertjes -= 3;
    }
    index = -1;
    const zoekertjesShowing = document.getElementById("showzoekertjes");
    zoekertjesShowing.textContent = beginzoekertjes + 1 + " - " + (eindzoekertjes + 1);
    document.querySelector(".feedInner").innerHTML = "";
    await maakZoekertje();
}

const loadMoreButton1 = document.createElement("button");
loadMoreButton1.textContent = "<";
loadMoreButton1.addEventListener("click", function () {
    showNextZoekertjes("achteruit");
});

const loadMoreButton2 = document.createElement("button");
loadMoreButton2.textContent = ">";
loadMoreButton2.addEventListener("click", function () {
    showNextZoekertjes("vooruit");
});

const zoekertjesShowing = document.createElement("p");
zoekertjesShowing.setAttribute("id", "showzoekertjes");
zoekertjesShowing.textContent = beginzoekertjes + 1 + " - " + (eindzoekertjes + 1);

document.querySelector(".load-more-container").appendChild(loadMoreButton1);
document.querySelector(".load-more-container").appendChild(loadMoreButton2);
document.querySelector(".load-more-container").appendChild(zoekertjesShowing);
let docRef = null

document.getElementById("herladen").addEventListener('click', function () {
    //zorgt ervoor dat alle zoekertjes opnieuw opgehaald worden en gesorteerd worden
    sessionStorage.removeItem('zoekertjesLoaded');
    sessionStorage.removeItem('zoekertjes');
    location.reload();
});

// Functie om de zoekertjes te tonen en te sorteren
const toonzoekertje = async (user) => {
    if (!sessionStorage.getItem('zoekertjesLoaded')) { // Controleer of de zoekertjes nog niet zijn geladen
        sessionStorage.setItem('zoekertjesLoaded', true); // Markeer dat de zoekertjes nu zijn geladen

        const {userData, profilePicUrl} = await fetchProfileData(email);
        let besteMatches;
        if (email !== null) {
            besteMatches = findBestMatches(SporterData.graph, email, 20);
        } else {
            console.log("email is null", email);
            return; // Stop de functie als er geen e-mail is
        }
        for (let match of besteMatches) {
            const {userData, profilePicUrl} = await fetchProfileData(match);
            if (userData.aantalzoekertjes !== 0) {
                for (let z of userData.zoekertjeslinks) {
                    // Fetch de zoekertje details from Firestore
                    docRef = doc(db, "Zoekertjes", z);
                    const docSnap = await getDoc(docRef);
                    if (docSnap.exists()) {
                        let data = docSnap.data();
                        data["id"] = z;
                        zoekertjes.push(data);
                    } else {
                        console.log("Zoekertje not found");
                    }
                }
            }

        }
        console.log(zoekertjes)
        if(zoekertjes.length === 0) {
            document.getElementById("geenGevonden").hidden = false;
            if (textremoved === false) {
                document.getElementById("loadingtext").remove();
                textremoved = true
            }
        }
        zoekertjes.sort((a, b) => {
            // Bereken de afstand tussen het huidige gebruiker locatie en de locatie van zoekertje 'a'
            const distanceToA = calculateDistance(userData.lat, userData.lng, a.lat, a.lng);
            // Bereken de afstand tussen het huidige gebruiker locatie en de locatie van zoekertje 'b'
            const distanceToB = calculateDistance(userData.lat, userData.lng, b.lat, b.lng);
            // Sorteer op basis van de afstand, van dichtbij naar ver weg
            return distanceToA - distanceToB;
        });
        // Sla de zoekertjes op in sessionStorage
        sessionStorage.setItem('zoekertjes', JSON.stringify(zoekertjes));
    }
    await maakZoekertje();
}
if(JSON.parse(sessionStorage.getItem("zoekertjes"))!=null){
    console.log( JSON.parse(sessionStorage.getItem("zoekertjes")))
    if(JSON.parse(sessionStorage.getItem("zoekertjes")).length==0){
        document.getElementById("geenGevonden").hidden = false;
        if (textremoved === false) {
            document.getElementById("loadingtext").remove();
            textremoved = true
        }
    }
}



const maakZoekertje = async () => {
    const {userData, profilePicUrl} = await fetchProfileData(email);
    zoekertjes = JSON.parse(sessionStorage.getItem('zoekertjes')) || []; // Haal zoekertjes op uit sessionStorage of gebruik een lege array als er geen zijn
    zoekertjes.forEach((doc) => {
        if (doc.aantalgeaccepteerd < doc.aantalSporters && !userData.geaccepteerdezoekertjes.includes(doc.id) && userData.age >= doc.minimumage && userData.age <= doc.maximumage && userData.sport.includes(doc.sport[0]) && !userData.zoekertjeslinks.includes(doc.id)) {
            index += 1;
            if (beginzoekertjes <= index && eindzoekertjes >= index) {
                const anchor = document.createElement("a");
                anchor.setAttribute("href", `zoekertje.html?zoekertje=${doc.id}`);
                anchor.setAttribute("class", "zoekertje-link"); // Optional: Add a class for styling

                // Prevent default link styling
                anchor.style.textDecoration = "none";
                anchor.style.color = "inherit";

                // Create a new div element for the zoekertje
                const div = document.createElement("div");
                div.setAttribute("class", "zoekertje");

                // Create div elements for the zoekertje title and body
                const titleDiv = document.createElement("div");
                titleDiv.setAttribute("class", "zoekertje-titel");
                titleDiv.textContent = doc.titel; // Assuming title is a property of zoekertje

                const DescriptionDiv = document.createElement("div");
                DescriptionDiv.setAttribute("class", "zoekertje-body");
                DescriptionDiv.textContent = "beschrijving: " + doc.description;

                const AmountOfPeopleDiv = document.createElement("div");
                AmountOfPeopleDiv.setAttribute("class", "zoekertje-body");
                AmountOfPeopleDiv.textContent = "Aantal mensen: " + doc.aantalSporters;

                const sportDiv = document.createElement("div");
                sportDiv.setAttribute("class", "zoekertje-body");
                sportDiv.textContent = "Sport: " + doc.sport[0];

                const AgeDiv = document.createElement("div");
                AgeDiv.setAttribute("class", "zoekertje-body");
                AgeDiv.textContent = "beschrijving: " + doc.minimumage + " - " + doc.maximumage;

                // Append title and body divs to the zoekertje div
                div.appendChild(titleDiv);
                div.appendChild(DescriptionDiv);
                div.appendChild(AmountOfPeopleDiv);
                div.appendChild(sportDiv);
                div.appendChild(AgeDiv);

                // Append the zoekertje div to the anchor
                anchor.appendChild(div);
                zoekertjesElementen.push(anchor.outerHTML);

                // Append the anchor to the container (assuming "container" is the parent element)
                document.querySelector(".feedInner").appendChild(anchor);
                if (textremoved === false) {
                    document.getElementById("loadingtext").remove();
                    textremoved = true
                }
            }
        }
    });
}
// checkt of je ingelogd bent of niet en zet dan profielfoto.
onAuthStateChanged(auth, (user)  => {
    if (user) {
        email = user.email;
        // User is signed in.
        getDownloadURL(ref(storage, 'ProfilePictures/' + user.email))
            .then((url) => {
                img.src = url;
            })
            .catch((error) => {
                // Handle any errors
                console.error("Error getting profile picture URL:", error);
            });
         toonzoekertje(user);

    } else {
        // No user is signed in.
        window.location.href = "login.html";
    }
});