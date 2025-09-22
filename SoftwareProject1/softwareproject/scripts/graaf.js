//firebase initialiseren
import {doc, setDoc, collection, getDocs} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";
import {db} from "./firebaseConfig.js";
import {SporterData, generateRandomLocation, ophalenUsers, userCollection} from "./GraafConfig.js";
import {generateRandomProfile, generateUniqueRandomProfile} from "./RandomProfileGenerator.js";

export {findBestMatches, calculateDistance, deg2rad}

//collectie in firebase waarvan het testsysteem gebruik maakt
let testUserCollecie = "users";
//aantal aangemaakt testprofielen
let aantalProfielen = 10;

// Matching algoritme: Weighted Matching met afstand, sporten (interests), niveau en leeftijd
function findBestMatches(graaf, startNodeId, numMatches) {
    const startNode = graaf.nodes[startNodeId];
    const interests = SporterData.sporterInterests[startNodeId];
    const level = SporterData.sporterLevels[startNodeId];
    const age = SporterData.sporterAges[startNodeId];
    const matches = [];

    // Bepaal maximale afstand om normalisatie toe te passen
    let maxDistance = 0;
    for (const nodeId in graaf.nodes) {
        if (nodeId !== startNodeId) {
            const node = graaf.nodes[nodeId];
            const distance = calculateDistance(startNode.location.latitude, startNode.location.longitude, node.location.latitude, node.location.longitude);
            maxDistance = Math.max(maxDistance, distance);
        }
    }

    // Loop door alle knooppunten in de graaf
    for (const nodeId in graaf.nodes) {
        if (nodeId !== startNodeId) {
            const node = graaf.nodes[nodeId];
            const distance = calculateDistance(startNode.location.latitude, startNode.location.longitude, node.location.latitude, node.location.longitude);

            // Controleer of er minstens één overeenkomstige sport is
            const nodeInterests = SporterData.sporterInterests[nodeId];
            if (Array.isArray(interests)) {
                const commonInterests = interests.filter(interest => nodeInterests.includes(interest));
                if (commonInterests.length === 0) {
                    continue; // Ga naar de volgende node als er geen overeenkomstige sporten zijn
                }
            } else {
                // Handle het geval waarin interests geen array is
                console.log("probleem met interests")
                continue; // Ga naar de volgende node als interests geen array is
            }

            // Normaliseer en keer de afstandsscore om
            const normalizedDistance = 1 - (distance / maxDistance);
            // hoe dichter twee profielen bij elkaar liggen hoe dichter bij 1 de normalized distance ligt
            // hoe verder twee profielen van elkaar liggen hoe dichter bij 0 de normalized distance ligt

            const interestMatchScore = calculateInterestMatchScore(nodeId, interests); // Bereken match score op basis van interesses
            const levelDiff = Math.abs(level - SporterData.sporterLevels[nodeId]); // Bereken het verschil in niveau
            const ageDiff = Math.abs(age - SporterData.sporterAges[nodeId]); // Bereken het verschil in leeftijd

            /*
            console.log("normalized distance: " + normalizedDistance)
            console.log("interestmatchscore: " + interestMatchScore)
            console.log("leveldiff: " + levelDiff)
            console.log("agediff: " + ageDiff)
            */

            // Gewichten voor afstand, niveau en leeftijd kunnen worden aangepast naar eigen inzicht
            // score rond de 10 is perfecte match
            // score rond -10 is slechtste match
            const totalScore = (normalizedDistance*10) + (interestMatchScore/2) - (levelDiff/4) - (ageDiff/6);
            //console.log(totalScore)

            matches.push({nodeId, totalScore});
        }
    }
    // Sorteer de matches op basis van de totale score (van hoog naar laag)
    matches.sort((a, b) => b.totalScore - a.totalScore);

    // Selecteer alleen de top 'numMatches' matches
    const topMatches = matches.slice(0, numMatches).map(match => match.nodeId);

    return topMatches;
}
// functie om match score op basis van interesses te berekenen (hoe hoger, hoe beter)
function calculateInterestMatchScore(nodeId, interests) {
    // Simpel voorbeeld: aantal overeenkomende interesses
    const nodeInterests = SporterData.sporterInterests[nodeId];
    let matchScore = 0;
    for (const interest of interests) {
        if (nodeInterests.includes(interest)) {
            matchScore++;
        }
    }
    return matchScore;
}

// Implementeer een functie om de afstand tussen twee punten te berekenen met de haversine-formule
function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371; // Radius van de aarde in kilometers
    const dLat = deg2rad(lat2 - lat1);
    const dLon = deg2rad(lon2 - lon1);
    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c; // Afstand in kilometers
    return distance;
}

function deg2rad(deg) {
    return deg * (Math.PI / 180);
}
const currentPage = window.location.pathname;
const isGraafPage = currentPage.includes('/graaf.html');
if (isGraafPage) {

/* code om random profielen aan te maken
//Maken van x aantal random profielen die in firebase, graph en SportersData worden bijgehouden
    document.getElementById("testknop").addEventListener("click", async function () {
        console.log("nieuwe profielen aanmaken")
        // Voorbeeldgebruik met aantalProfielen sporters en random profielen

        // Voeg knooppunten toe (sporters) met willekeurige locaties in Vlaanderen
        // Definieer interesses, niveaus en leeftijden van elke sporter met behulp van de random profiel generator
        for (let i = 1; i <= aantalProfielen; i++) {

            const profile = generateUniqueRandomProfile();

            //toevoegen profiel aan dictionary die gebruikt worden voor matching
            SporterData.sporterInterests[profile.email] = profile.sport;
            SporterData.sporterLevels[profile.email] = profile.level;
            SporterData.sporterAges[profile.email] = profile.age;

            //toevoegen gebruiker aan de graaf
            const location = generateRandomLocation();
            SporterData.graph.addNode(profile.email, {latitude: location.latitude, longitude: location.longitude});

            //toevoegen gegevens aan firebase
            const docRef = await setDoc(doc(db, testUserCollecie, profile.email), {
                first: profile.firstName,
                last: profile.lastName,
                age: profile.age,
                sport: profile.sport,
                niveau: profile.level,
                lat: location.latitude,
                lng: location.longitude
            });
        }

    })
*/

//testen van algoritme
    document.getElementById("testUsers").addEventListener('click', async function (event) {
        console.log('matching uitgevoerd')
        event.preventDefault();
        const numMatchesToShow = 5; // Aantal beste matches om weer te geven

        // Zoek voor elke sporter de beste matches op basis van locatie, interesses, niveau en leeftijd
        for (const nodeId in SporterData.graph.nodes) {
            const bestMatches = findBestMatches(SporterData.graph, nodeId, numMatchesToShow);
            console.log('Beste matches voor', nodeId, 'zijn:', bestMatches);

            document.getElementById("matchingTextarea").textContent += "Beste matches voor " + nodeId + " ("+
                SporterData.sporterInterests[nodeId] + " " +SporterData.sporterAges[nodeId]+ " "+ SporterData.sporterLevels[nodeId] +") zijn: \n";
            for (let match of bestMatches) {
                //document.getElementById("matchingTextarea").textContent += bestMatches[match]+" "+ match.totalScore + "\n";
                document.getElementById("matchingTextarea").textContent += match + "\t"
                let loc = SporterData.graph.nodes[match].location;
                let node = SporterData.graph.nodes[nodeId];
                let distance = calculateDistance(loc.latitude, loc.longitude, node.location.latitude, node.location.longitude)
                //console.log(distance);
                document.getElementById("matchingTextarea").textContent += distance + "\t"
                document.getElementById("matchingTextarea").textContent += SporterData.sporterInterests[match] + "\t"
                document.getElementById("matchingTextarea").textContent += SporterData.sporterAges[match] + "\t"
                document.getElementById("matchingTextarea").textContent += SporterData.sporterLevels[match] + "\n"
            }
            document.getElementById("matchingTextarea").textContent += "\n"
        }
    })
//statische variabelen uit GraafConfig uittesten
    document.getElementById("teststatic").addEventListener('click', async function () {
        console.log('statische vars:')
        console.log(SporterData.graph)
        console.log(SporterData.sporterAges) // aanpasbaar naar sporterLevels en sporterInterests
    })

//ophalen van TESTusers
    async function ophalenTestUsers() {
        let usersDict = {}
        const querySnapshot = await getDocs(collection(db, testUserCollecie));
        querySnapshot.forEach((doc) => {
            // doc.data() is never undefined for query doc snapshots
            console.log(doc.id, " => ", doc.data());
            usersDict[doc.id] = doc.data();
        });
        return usersDict;
    }

//opgehalen users uit firebase en displayen in textarea
    document.getElementById("ophalenusers").addEventListener('click', async function () {
        console.log("ophalen users")
        let usersDict = await ophalenTestUsers();
        for (let id in usersDict) {
            document.getElementById("userListTextarea").textContent += id + "\n";
        }

    })
}