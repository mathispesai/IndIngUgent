import { auth } from "./firebaseConfig.js";
import fetchProfileData from "./FetchProfileData.js";
const img = document.getElementById('profile-pic');
const urlParams = new URLSearchParams(window.location.search);
const user2 = urlParams.get("profile");
//gebruikt de fetchdata functie om data op te halen over de gebruiker en weer te geven op het profiel.
const fetchData = async () => {
    try {
        auth.onAuthStateChanged(async (user) => {
            if (user) {
                const { userData, profilePicUrl } = await fetchProfileData(user2);
                // Populate HTML elements with retrieved data
                document.getElementById('firstName').textContent = userData.first;
                document.getElementById('lastName').textContent = userData.last;
                document.getElementById('sport').textContent = userData.sport;
                document.getElementById('age').textContent = userData.age;
                // Set profile picture
                img.src = profilePicUrl;
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
fetchData();