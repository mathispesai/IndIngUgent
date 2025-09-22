export {map,marker}
let map;
let marker;
//initialiseren van map en marker met nodige eventListener zodat hij kan verplaatst worden
async function initMap() {
    // The location of Brussel
    const position = { lat: 50.8465573, lng: 4.351697};
    // Request needed libraries.
    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
    //map gecentreerd op brussel
    map = new Map(document.getElementById("map"), {
        zoom: 10,
        center: position,
        mapId: "LocatieZoekertje",
    });
    // The marker, positioned at Brussel
    marker = new AdvancedMarkerElement({
        map: map,
        position: position
    });
    //om marker te verplaatsen naar de plaats waar je klikt
    map.addListener('click', (event) => {
        placeMarkerAndPanTo(event.latLng,map,marker);
    });
    // Create the search box and link it to the UI element.
    const input = document.getElementById("pac-input");
    const searchBox = new google.maps.places.SearchBox(input);

    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
    // Bias the SearchBox results towards current map's viewport.
    map.addListener("bounds_changed", () => {
        searchBox.setBounds(map.getBounds());
    });

    // Create the autocomplete object
    const autocomplete = new google.maps.places.Autocomplete(input);

    // Set the fields to return when the user selects a place
    autocomplete.setFields(["address_components", "geometry", "icon", "name"]);

    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    autocomplete.addListener("place_changed", () => {
        const place = autocomplete.getPlace();
        if (!place.geometry) {
            console.log("No details available for input: '" + place.name + "'");
            return;
        }
        // If the place has a geometry, center the map on it
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);  // Default zoom level when the place has no viewport
        }
        // Move the marker to the selected location
        placeMarkerAndPanTo(place.geometry.location,map,marker);
    });
}
function placeMarkerAndPanTo(latLng, map,marker) {
    marker.position = latLng;
    map.panTo(latLng);
}
initMap();
