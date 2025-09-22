import {signInWithEmailAndPassword} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-auth.js";
import {auth} from "./firebaseConfig.js";

const submit = document.getElementById('loginbtn');
const cancel = document.getElementById('cancelbtn');

//cancel button stuurt je terug naar het main scherm
cancel.addEventListener('click', terugNrMain);
function terugNrMain(){
    window.location.href = "index.html";
}
auth.onAuthStateChanged(async (user) => {
    if (user) {
        window.location.href= "hoofdscherm.html";
    }
});
//submit button linkt je aan je account en stuurt je door naar hoofdscherm
submit.addEventListener("click", function (event) {
    event.preventDefault()
    const email = document.getElementById('email').value;
    const passwd = document.getElementById('psw').value;
    signInWithEmailAndPassword(auth, email, passwd)
        .then((userCredential) => {
            const user = userCredential.user;
            window.location.href = "hoofdscherm.html";
        })
        .catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            alert(Error);
        });
})
document.getElementById("signupbtn").addEventListener("click", function(){
    window.location.href ="signup.html"
})