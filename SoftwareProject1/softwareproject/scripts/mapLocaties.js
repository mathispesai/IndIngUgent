import {db} from "./firebaseConfig.js";
import {getDocs, collection} from "https://www.gstatic.com/firebasejs/10.9.0/firebase-firestore.js";

//haalt alle zoekertjes op van de firebase en geeft ze terug
let zoekertjesCollection = "Zoekertjes";
async function ophalenZoekertjes() {
    let zoekertjesDict = {}
    const querySnapshot = await getDocs(collection(db, zoekertjesCollection));
    querySnapshot.forEach((doc) => {
        zoekertjesDict[doc.id] = doc.data();
    });
    return zoekertjesDict;
}
let zoekertjesDict = await ophalenZoekertjes();
let map;

async function initMap() {
    // The location of Brussel
    const brussel = {lat: 50.8465573, lng: 4.351697};
    // Request needed libraries.
    //@ts-ignore
    const {Map} = await google.maps.importLibrary("maps");
    const {AdvancedMarkerElement} = await google.maps.importLibrary("marker");

    // The map, centered at Brussel
    map = new Map(document.getElementById("map"), {
        zoom: 9,
        center: brussel,
        mapId: "LocatieZoekertje",
    });
    const infowindow = new google.maps.InfoWindow();
    //overloopt alle zoekertjes en plaatst een nieuwe marker op de juiste locatie met omschrijving
    for (let zoekertje in zoekertjesDict) {
        let data = zoekertjesDict[zoekertje];
        const position = {
            lat: data.lat,
            lng: data.lng
        };
        const marker = new AdvancedMarkerElement({
            map: map,
            position: position
        });
        const anchor = document.createElement("a");
        anchor.setAttribute("href", `zoekertje.html?zoekertje=${encodeURIComponent(zoekertje)}`);
        anchor.textContent = "Link naar het zoekertje"; // Tekst die wordt weergegeven in de link

        //string met omschrijving van zoekertje
        const contentString = data.sport[0] + ", " + data.description+ ", " +  " aantal spelers:" + data.aantalSporters
            + "leeftijd: " + data.minimumage + "-"+ data.maximumage + anchor.outerHTML;

        marker.addListener('click', () => {
            infowindow.setContent(contentString);
            infowindow.open({
                anchor: marker,
                map,
            });
            map.addListener('click', function () {
                infowindow.close();
            });
        });
    }
}
initMap();