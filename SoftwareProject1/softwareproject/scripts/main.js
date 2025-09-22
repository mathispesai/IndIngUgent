import {
    browserSessionPersistence,
    onAuthStateChanged,
    setPersistence
} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js";
// firebase wordt geconfigured en autentication opgestart in firebaseconfig.js
import {auth} from "./firebaseConfig.js";

var user = auth.currentUser;
// persistence zorgt ervoor dat je tussen paginas blijft ingelogd.
setPersistence(auth, browserSessionPersistence)
    .then(() => {
        console.log("Local persistence enabled");
        // Start your application or authentication process here
    })
    .catch((error) => {
        console.error("Error setting persistence: ", error);
    });
//inlogstatus evalueren
onAuthStateChanged(auth, (user) => {
    if (user) {
        // User is signed in, see docs for a list of available properties
        // https://firebase.google.com/docs/reference/js/auth.user
        const uid = user.uid;
    }
});
