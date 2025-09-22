import { auth } from "./firebaseConfig.js";
import fetchProfileData from "./FetchProfileData.js";
import {map, marker} from "./mapToonLocatie.js";
const img = document.getElementById('profile-pic');

//gebruikt de fetchdata functie om data op te halen over de gebruiker en weer te geven op het profiel.
const fetchData = async () => {
    try {
        auth.onAuthStateChanged(async (user) => {
            if (user) {
                const { userData, profilePicUrl } = await fetchProfileData(user.email);
                // Populate HTML elements with retrieved data
                document.getElementById('firstName').textContent = userData.first;
                document.getElementById('lastName').textContent = userData.last;
                document.getElementById('sport').textContent = userData.sport;
                document.getElementById('age').textContent = userData.age;
                document.getElementById('niveau').textContent = userData.niveau;

                //juist zetten van marker en map
                marker.position = {lat:userData.lat,lng:userData.lng};
                map.panTo(marker.position);

                // Set profile picture
                img.src = profilePicUrl;
            } else {
                window.location.href = "login.html";
                console.error("User not authenticated");
                // Handle authentication errors
            }
        });
    } catch (error) {
        console.error(error);
        // Handle other errors
    }
};
fetchData();