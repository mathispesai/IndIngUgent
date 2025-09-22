import {auth} from "./firebaseConfig.js";
import {signOut} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js"

const logtbutton = document.getElementById('logoutbutton');

logtbutton.addEventListener('click', function (event) {
    event.preventDefault()
    signOut(auth).then(function () {
        // Sign-out successful.
        // Redirecten naar main page
        window.location.href = "index.html";
    }).catch(function (error) {
        // error tijdens signout
        console.error("An error occurred during sign-out:", error);
    });
})