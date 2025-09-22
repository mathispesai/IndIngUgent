export class RuimteObject{

    constructor(snelheid, positie, afbeelding, gewicht, naam) {
        this.snelheid = snelheid; // in km/s
        this.positie = positie; // Punt2D
        this.afbeeldingsrc = afbeelding;
        this.image = new Image();
        this.image.src = this.afbeeldingsrc;
        this.image.onload = () => {
            console.log("afbeelding geladen");
        }
        this.gewicht = gewicht * 1e21; //gewicht wordt geschaald van de input, kg
        this.naam = naam;
        //this.straal = 20; //voorlopig vast, maar kan later nog aangepast worden
        this.straal = Math.max(20,Math.min(50,5*Math.log10(this.gewicht/(6000*1e21))+20)); //Werken met logaritme om straal te berekenen, zodat het logische grote heeft voor simulatie + min en max straal
        
    }

    afstandKwadraatTot(obj) {
        const dx = this.positie.x - obj.positie.x;
        const dy = this.positie.y - obj.positie.y;
        return dx * dx + dy * dy;
    }

    teken(canvas){
        const ctx = canvas.getContext("2d");

        ctx.drawImage(this.image, this.positie.x + canvas.width/2 - this.straal/2, this.positie.y + canvas.height/2 - this.straal/2, this.straal, this.straal);
    }

    update(krachtvector, tijdsinterval ){
        //tijdsinterval is in maanden, 2630000 seconden in een maand

        //update snelheid
        this.snelheid[0] += (krachtvector[0] * tijdsinterval * 2630000/this.gewicht) / 1000;
        this.snelheid[1] += (krachtvector[1] * tijdsinterval * 2630000/this.gewicht) / 1000;

        //update positie
        const canvasSchaal = 3000000000;  // meter per pixel
        this.positie.x += this.snelheid[0] * 1000 * tijdsinterval * 2630000 / canvasSchaal; //vermenigvuldigen met 1000, want snelheid in km/s
        this.positie.y += this.snelheid[1] * 1000 * tijdsinterval* 2630000 / canvasSchaal;
        console.log("planteet getekend!", this.naam + "positie: " +this.positie.x + "," + this.positie.y + "Snelheid: " + this.snelheid[0] + "," + this.snelheid[1] + "massa : " + this.gewicht);
    }
}


export class Planeet extends RuimteObject{
    constructor(snelheid, positie, afbeelding, gewicht, naam) {
        super(snelheid, positie, afbeelding, gewicht, naam);
        this.toestand = null;
        this.leven = false;
    }

    //later te implementeren
    levenscheck(){
        return null;
    }

    //later te implementeren
    toestandscheck(){
        return null;
    }

}

export class Komeet extends Planeet{
    constructor(snelheid, positie, gewicht, naam) {
        super(snelheid, positie, "Media/", gewicht, naam); //foto nog toevoegen
    }
}

export class Ster extends RuimteObject{
    constructor(positie,afbeelding, gewicht){
        super([0,0],positie,"../Media/Zon.png",gewicht, "Zon");
        this.straal = 30;
    }
}