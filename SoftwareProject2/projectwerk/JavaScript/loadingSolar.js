import { importFunction, importSpaceObjects} from './solarSystem.js';

document.addEventListener("DOMContentLoaded", () => {
    if (window.location.pathname.includes("solarSystem.html")) {
        const urlParams = new URLSearchParams(window.location.search);
        const systemName = urlParams.get("system");

        if (systemName) {
            const jsonUrl = `../JavaScript/Data/${systemName}.json`;

            fetch(jsonUrl)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Kan bestand niet laden: ${jsonUrl}`);
                    }
                    return response.json();
                })
                .then(data => {
                    importSpaceObjects(data);
                })
                .catch(error => {
                    console.error("Fout bij laden zonnestelsel:", error);
                });
        }
    }
});
