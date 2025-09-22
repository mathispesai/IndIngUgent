import { auth, db } from "./firebaseConfig.js";
import { doc, getDoc, deleteDoc, updateDoc } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js";
import fetchProfileData from "./FetchProfileData.js";
import {map,marker} from "./mapToonLocatie.js"

let user = null;
let email = null;
// Get the zoekertje ID from the URL query parameter
const urlParams = new URLSearchParams(window.location.search);
const zoekertjeId = urlParams.get('zoekertje');
let docRef = null;
const img = document.getElementById('profiel_img');

onAuthStateChanged(auth, async (authUser) => {
    user = authUser;
    if (user) {
        email = user.email;

        // Do something with userData and profilePicUrl
    } else {
        window.location.href = "login.html";
    }
});
// Fetch the zoekertje details from Firestore
const fetchZoekertjeDetails = async (zoekertjeId) => {
    docRef = doc(db, "Zoekertjes", zoekertjeId);
    const docSnap = await getDoc(docRef);
    if (docSnap.exists()) {
        return docSnap.data();
    } else {
        throw new Error("Zoekertje not found");
    }
};

const zoekertjeDetails = await fetchZoekertjeDetails(zoekertjeId);
// Display the zoekertje details on the page
const zoekertjeDetailsContainer = document.getElementById('zoekertje-details');
const displayZoekertjeDetails = async () => {
    try {
        zoekertjeDetailsContainer.innerHTML = '';

        let titel = document.getElementById("zoekertje-titel");
        titel.innerText = zoekertjeDetails.titel;
        document.getElementById("title").innerText = zoekertjeDetails.titel

        const descriptionElement = document.createElement('p');
        descriptionElement.textContent = "beschrijving: " + zoekertjeDetails.description;

        const sport = document.createElement('p');
        sport.textContent = "sport: " + zoekertjeDetails.sport;

        const leeftijd = document.createElement('p');
        leeftijd.textContent = "leeftijd: " + zoekertjeDetails.minimumage + " - " + zoekertjeDetails.maximumage;

        const gezochtaantalsporters = document.createElement('p');
        gezochtaantalsporters.textContent = "gezocht aantal sporters: " + zoekertjeDetails.aantalSporters;

        const detailsmaker = document.createElement('p');
        detailsmaker.textContent = "account maker: ";

        const geaccepteerdesporters=document.createElement('p');
        if(zoekertjeDetails.geaccepteerdesporters!= null){
            geaccepteerdesporters.textContent = "geaccepteerde sporters: " + zoekertjeDetails.geaccepteerdesporters.split(",").map(str => str.trim()).filter(Boolean).length + "/" + zoekertjeDetails.aantalSporters + ":";
        }
        else{
            geaccepteerdesporters.textContent = "geaccepteerde sporters: geen";
        }
        
        //map en marker juist zetten
        marker.position = {lat:zoekertjeDetails.lat,lng:zoekertjeDetails.lng};
        map.panTo(marker.position);

        zoekertjeDetailsContainer.appendChild(titel)
        zoekertjeDetailsContainer.appendChild(descriptionElement);
        zoekertjeDetailsContainer.appendChild(sport);
        zoekertjeDetailsContainer.appendChild(leeftijd);
        zoekertjeDetailsContainer.appendChild(gezochtaantalsporters);
        zoekertjeDetailsContainer.appendChild(detailsmaker);
        await persoonkaart(((zoekertjeId).split('('))[0]);

        zoekertjeDetailsContainer.appendChild(geaccepteerdesporters);

        if(zoekertjeDetails.geaccepteerdesporters != null){
            let sportersArray = zoekertjeDetails.geaccepteerdesporters.split(",");
            console.log(sportersArray)
            for (let persoon of sportersArray) {
                if (persoon.trim() !== '') {
                    console.log(persoon);
                   await persoonkaart(persoon);

                }
            }
        }
        // Append elements to the container
        const { userData,profilePicUrl } = await fetchProfileData(email);
        img.src = profilePicUrl;

        if (zoekertjeId.includes(email)) {
            let deleteknop = document.createElement("button");
            deleteknop.innerText = "delete";
            deleteknop.addEventListener("click", deleteknophandler)
            const mailtoButton = document.createElement("button");
            mailtoButton.addEventListener("click", function() {
                let emaillijst = [];
                let string = zoekertjeDetails.geaccepteerdesporters.toString();
                let stringlijst = string.split((","));
                stringlijst.forEach(item => {
                    if(! item.includes(email)){
                        emaillijst.push(item);
                    }
                });
                window.location.href = `mailto:${emaillijst}`;
            });
            mailtoButton.textContent = "Send Email";
            zoekertjeDetailsContainer.appendChild(mailtoButton);
            zoekertjeDetailsContainer.appendChild(deleteknop);
        } else if ( userData.geaccepteerdezoekertjes.includes(zoekertjeId)) {
            const mailtoButton = document.createElement("button");
            mailtoButton.addEventListener("click", function() {
                let emaillijst = [];
                    let string = zoekertjeDetails.geaccepteerdesporters.toString();
                    let stringlijst = string.split((","));
                    stringlijst.forEach(item => {
                        if(! item.includes(email)){
                            emaillijst.push(item);
                        }
                    });
                emaillijst.push(((zoekertjeId).split('('))[0])
                window.location.href = `mailto:${emaillijst}`;
            });
            mailtoButton.textContent = "Send Email";
            zoekertjeDetailsContainer.appendChild(mailtoButton);
        } else {
            let accepteerknop = document.createElement("button");
            accepteerknop.innerText = "accepteer";
            accepteerknop.addEventListener("click", accepteerknophandler);
            zoekertjeDetailsContainer.appendChild(accepteerknop);
        }
    } catch (error) {
        console.error(error);
    }
};
async function persoonkaart(persoon){
    const { userData , profilePicUrl } = await fetchProfileData(persoon.trim());

    const anchor = document.createElement("a");
    anchor.setAttribute("href", `profielAnderepersoon.html?profile=${persoon}`);
    anchor.setAttribute("class", "zoekertje-link");
    const div = document.createElement("div");
    div.setAttribute("class", "zoekertje");

    const titleDiv = document.createElement("div");
    titleDiv.setAttribute("class", "zoekertje-titel");
    titleDiv.textContent = userData.first +" " +userData.last;

    const content = document.createElement("div");
    content.style.display = "flex";
    content.style.flexDirection = "row";
    content.style.margin= "20";

    const textbox = document.createElement("div");
    textbox.style.marginLeft= "20px";
    const profielfoto =  document.createElement("img");
    profielfoto.width = 100; // Set the width to your desired value
    profielfoto.height = 100;
    profielfoto.src = profilePicUrl;

    const leeftijd= document.createElement("p");
    leeftijd.textContent = "leeftijd: " + userData.age;

    const niveau = document.createElement("p");
    niveau.textContent = "niveau: "+ userData.niveau;

    content.appendChild(profielfoto);
    textbox.appendChild(leeftijd);
    textbox.appendChild(niveau);
    content.appendChild(textbox);
    anchor.appendChild(titleDiv);
    // Append the zoekertje div to the anchor
    div.appendChild(anchor);
    div.appendChild( content);
    zoekertjeDetailsContainer.appendChild(div);
}
async function deleteknophandler() {
    await deleteDoc(docRef);
    const { userData } = await fetchProfileData(email);
    let nieuwzoekertjeslinks = userData.zoekertjeslinks.filter(item=>item !== zoekertjeId);
    alert(nieuwzoekertjeslinks);
    let docRefAcc = doc(db, "users", email);
    let aantalzoekertjes = userData.aantalzoekertjes-1;
    const dataToUpdateAccount = {
        aantalzoekertjes: aantalzoekertjes,
        zoekertjeslinks : nieuwzoekertjeslinks
    }
    await updateDoc(docRefAcc, dataToUpdateAccount);
    window.location.href = "MijnZoekertjes.html";
    //remove zoekertje from database and return to mijnzoekertjesoverzicht
}

async function accepteerknophandler() {
    const { userData } = await fetchProfileData(email);
    let naamsporters=null;
    if(zoekertjeDetails.geaccepteerdesporters == null){
        naamsporters = email +",";
    }
    else {
        naamsporters = zoekertjeDetails.geaccepteerdesporters + email +",";
    }
    let aantalgeaccepteerdeerst = (parseInt(zoekertjeDetails.aantalgeaccepteerd) + 1).toString();

    const dataToUpdateZoekertje = {
        aantalgeaccepteerd: aantalgeaccepteerdeerst,
        geaccepteerdesporters: naamsporters,
    };
    await updateDoc(docRef, dataToUpdateZoekertje);
    if (userData && userData.geaccepteerdezoekertjes) {
        userData.geaccepteerdezoekertjes.push(zoekertjeId);
        let acceptedEerst = userData.geaccepteerdezoekertjes;

        const dataToUpdateAccount = {
            geaccepteerdezoekertjes: acceptedEerst
        }
        let docRefAcc = doc(db, "users", email);
        await updateDoc(docRefAcc, dataToUpdateAccount);
        window.location.href = "hoofdscherm.html"
    } else {
        console.error("userData or geaccepteerdezoekertjes is null");
    }
}
// Call the function to display zoekertje details
displayZoekertjeDetails();