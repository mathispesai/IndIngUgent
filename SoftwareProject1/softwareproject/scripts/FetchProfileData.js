import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js";
import { doc, getDoc    } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import {ref, getDownloadURL } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-storage.js";
import { auth, db, storage } from "./firebaseConfig.js";

// roep deze functie op om profiel informatie en profielfoto op te halen.
const fetchProfileData = (email) => {
    return new Promise((resolve, reject) => {
        onAuthStateChanged(auth, async (user) => {
            if (user) {
                try {
                    const docRef = doc(db, "users", email);
                    const docSnap = await getDoc(docRef);

                    if (docSnap.exists()) {
                        const userData = docSnap.data();
                        // Fetch profile picture URL
                        const profilePicUrl = await getDownloadURL(ref(storage, 'ProfilePictures/' + email));
                        resolve({ userData, profilePicUrl });
                    } else {
                        reject("No such document!");
                    }
                } catch (error) {
                    reject(error);
                }
            } else {
                reject("User not authenticated");
            }
        });
    });
};
export default fetchProfileData;