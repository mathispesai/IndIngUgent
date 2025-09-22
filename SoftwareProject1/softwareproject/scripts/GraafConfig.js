import {db} from "./firebaseConfig.js";
import {getDocs, collection } from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
export { SporterData, generateRandomLocation, ophalenUsers, userCollection}
// collectie in firebase die het algoritme standaard moet gebruiken
// en waarin nieuwe gebruikers worden opgeslagen
// ook aan te passen als je wilt testen.
let userCollection = "users";
//een eenvoudige graafklasse die een web bijhoudt van alle sporters en relatieve afstand tov van elkaar, zie ook graaf.js
class Graph {
    constructor() {
        this.nodes = {};
    }
    addNode(id, location) {
        this.nodes[id] = { location: location, edges: {} };
    }
    addEdge(source, destination, weight) {
        this.nodes[source].edges[destination] = weight;
        this.nodes[destination].edges[source] = weight;
    }
}
//bijhouden van alle sporters voor algo in static vars
class SporterData {
    static sporterInterests = {};
    static sporterLevels = {};
    static sporterAges = {};

    //statische graaf aanmaken zodat alle sporters bewaard blijven
    static graph = new Graph();
}
// Functie om een willekeurige locatie in Vlaanderen te genereren
function generateRandomLocation() {
    // Breedte- en lengtegraden voor Vlaanderen
    const minLatitude = 50.6; // Ondergrens van Vlaanderen
    const maxLatitude = 51.5; // Bovengrens van Vlaanderen
    const minLongitude = 2.5; // Westelijke grens van Vlaanderen
    const maxLongitude = 5.5; // Oostelijke grens van Vlaanderen

    // Genereer willekeurige breedte- en lengtegraden binnen het bereik van Vlaanderen
    const latitude = Math.random() * (maxLatitude - minLatitude) + minLatitude;
    const longitude = Math.random() * (maxLongitude - minLongitude) + minLongitude;

    return { latitude, longitude };
}
//ophalen van collectie met gebruikers in de firebase
async function ophalenUsers(){
    let usersDict = {}
    const querySnapshot = await getDocs(collection(db, userCollection));
    querySnapshot.forEach((doc) => {
        // doc.data() is never undefined for query doc snapshots
        //console.log(doc.id, " => ", doc.data());
        usersDict[doc.id] = doc.data();
    });
    return usersDict;
}
//klaarzetten van de bestaande gebruikers zodat algoritme niet enkel nieuw aangemaakte gebruikers bezit
let usersDict = await ophalenUsers();
for (let id in usersDict){
    let data = usersDict[id]

    //toevoegen bestaande gebruiker aan de graaf adhv opgeslagen locatie in profiel
    SporterData.graph.addNode(id, { latitude: data.lat, longitude: data.lng });

    //toevoegen aan SporterData
    SporterData.sporterAges[id] = data.age;
    SporterData.sporterInterests[id] = data.sport;
    SporterData.sporterLevels[id] = data.niveau;
}