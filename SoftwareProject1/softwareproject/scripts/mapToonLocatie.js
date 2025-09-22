export {map,marker}
let marker;
let map;

async function initMap() {

    // The location of Brussel
    const locatie = {lat: 50.8465573, lng: 4.351697};
    // Request needed libraries
    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");

    // The map, centered at Brussel
    map = new Map(document.getElementById("map"), {
        zoom: 12,
        center: locatie,
        mapId: "LocatieZoekertje",
    });

    marker = new AdvancedMarkerElement({
        position:locatie,
        map: map
    });
}
initMap();