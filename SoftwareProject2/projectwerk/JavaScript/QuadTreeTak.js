import Punt2D from "./Punt2D.js";

export default class QuadtreeTak {
    constructor(midden, grootte, diepte = 0, maxDiepte = 5) {
        this.midden = midden;     // Middelpunt van deze tak
        this.grootte = grootte;
        this.diepte = diepte;
        this.maxDiepte = maxDiepte; // om eventueel later maxdiepte aan te passen

        this.kinderen = [];     // onderliggende takken [linksboven, rechtsboven, linksonder, rechtsonder]
        this.objecten = []; // lijst van ruimteobjecten in deze tak
        this.massamiddelpunt = null;
        this.totaleMassa = 0;
    }

    bevat(punt) { //checkt of een punt in de tak ligt
        const h = this.grootte;
        return (
            punt.x >= this.midden.x - h &&
            punt.x <  this.midden.x + h &&
            punt.y >= this.midden.y - h &&
            punt.y <  this.midden.y + h
        );
    }

    insert(obj) {
        if (this.bevat(obj.positie)) {
            this.objecten.push(obj);
            return true;
        }
        return false;
    }

    verdeelAlsNodig() { //zorgt voor het recursief onderverdelen van de takken
        if (this.objecten.length <= 1 || this.diepte >= this.maxDiepte) return;

        this.subdivide();

        for (const obj of this.objecten) { //voeg de ruimteobjecten van deze tak toe aan de kinderen
            for (const kind of this.kinderen) {
                if (kind.insert(obj)) break;
            }
        }

        // Recurseer verder
        for (const kind of this.kinderen) {
            kind.verdeelAlsNodig();
        }
    }

    subdivide() { //splitst de tak in 4 kinderen
        const h = this.grootte / 2;
        const x = this.midden.x;
        const y = this.midden.y;

        this.kinderen = [
            new QuadtreeTak(new Punt2D(x - h, y - h), h, this.diepte + 1, this.maxDiepte), // linksboven
            new QuadtreeTak(new Punt2D(x + h, y - h), h, this.diepte + 1, this.maxDiepte), // rechtsboven
            new QuadtreeTak(new Punt2D(x - h, y + h), h, this.diepte + 1, this.maxDiepte), // linksonder
            new QuadtreeTak(new Punt2D(x + h, y + h), h, this.diepte + 1, this.maxDiepte), // rechtsonder
        ];
    }


    berekenMassa(excludeObj = null) {
        if (this.objecten.length === 0) {
            this.totaleMassa = 0;
            this.massamiddelpunt = null;
            return;
        }

        let massa = 0;
        let somX = 0;
        let somY = 0;

        for (const obj of this.objecten) {
            if (obj === excludeObj) continue;
            massa += obj.gewicht;
            somX += obj.positie.x * obj.gewicht;
            somY += obj.positie.y * obj.gewicht;
        }

        this.totaleMassa = massa;
        if (massa > 0) {
            this.massamiddelpunt = new Punt2D(somX / massa, somY / massa);
        } else {
            this.massamiddelpunt = null;
        }
    }

    isBlad() {
        return this.kinderen.length === 0;
    }
}