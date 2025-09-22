import { initializeApp } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-app.js";
import { getAuth } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js";
import { getFirestore } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import { getStorage } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-storage.js";

// zorgt ervoor dat de firebase geconfigured wordt
const firebaseConfig = {
    apiKey: "AIzaSyADswFCinx3VyMPwhPt93_d63AP-aWBD7g",
    authDomain: "softwareproject-7b076.firebaseapp.com",
    databaseURL: "https://softwareproject-7b076-default-rtdb.europe-west1.firebasedatabase.app",
    projectId: "softwareproject-7b076",
    storageBucket: "softwareproject-7b076.appspot.com",
    messagingSenderId: "756178662133",
    appId: "1:756178662133:web:60b53e8e454bab51760bbc",
    measurementId: "G-FLTVHZ223P"
};
// Initialiseer de verschillende delen van de firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const db = getFirestore(app);
const storage = getStorage(app);

export { app, auth, db, storage };