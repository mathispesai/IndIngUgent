import fetchProfileData from "./FetchProfileData.js";
import { auth, db } from "./firebaseConfig.js";
import { doc, getDoc } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js";

let user = null;
let email = null;
let zoekertje = null;
const img = document.getElementById('profiel_img');

onAuthStateChanged(auth, async (authUser) => {
    user = authUser;
    if (user) {
        email = user.email;
        const { userData, profilePicUrl } = await fetchProfileData(email);
        img.src = profilePicUrl;
        let zoekertjes = userData.zoekertjeslinks;
        for (const zoekertjeslink of zoekertjes) {
            // Create a new anchor element for the zoekertje
            const anchor = document.createElement("a");
            anchor.setAttribute("href", `zoekertje.html?zoekertje=${encodeURIComponent(zoekertjeslink)}`);
            anchor.setAttribute("class", "zoekertje-link"); // Optional: Add a class for styling

            // Prevent default link styling
            anchor.style.textDecoration = "none";
            anchor.style.color = "inherit";

            // Create a new div element for the zoekertje
            const div = document.createElement("div");
            div.setAttribute("class", "zoekertje");

            // Create div elements for the zoekertje title and body
            const titleDiv = document.createElement("div");
            const docRef = doc(db, "Zoekertjes", zoekertjeslink);
            const docSnap = await getDoc(docRef);
            if (docSnap.exists()) {
                zoekertje = docSnap.data();
                console.log("hooray");
                titleDiv.setAttribute("class", "zoekertje-titel");
                titleDiv.textContent = zoekertje.titel; // Assuming title is a property of zoekertje

                const bodyDiv = document.createElement("div");
                bodyDiv.setAttribute("class", "zoekertje-body");
                bodyDiv.textContent = zoekertje.description; // Assuming description is a property of zoekertje

                // Append title and body divs to the zoekertje div
                div.appendChild(titleDiv);
                div.appendChild(bodyDiv);

                // Append the zoekertje div to the anchor
                anchor.appendChild(div);

                // Append the anchor to the container (assuming "container" is the parent element)
                document.querySelector(".feed").appendChild(anchor);

            } else {
                console.log(zoekertjeslink);
            }
        }
    } else {
        window.location.href = "login.html";
    }
});