import Punt2D from './Punt2D.js';
import QuadtreeTak from './QuadTreeTak.js';

const canvas = document.getElementById('VisualisatieCanvas');
const ctx = canvas.getContext('2d');

let pauze = false;
let snelheid = 1.5;
function PauzeHervat() {
    pauze = !pauze;
    document.getElementById('pauzeSim').innerText = pauze ? 'Hervat' : 'Pauze';
}

class Cirkel{
    constructor(x,y, snelheidx, snelheidy){
        this.x = x;
        this.y = y;
        this.snelheidx = snelheidx;
        this.snelheidy = snelheidy;
    }

    update(){
        this.x += this.snelheidx;
        this.y += this.snelheidy;

        if(this.x <= 0 || this.x > canvas.width){
            this.snelheidx *= -1;
        }
        if(this.y <= 0 || this.y > canvas.height){
            this.snelheidy *= -1;
        }
    }

    draw(){
        ctx.beginPath();
        ctx.arc(this.x, this.y, 3, 0, Math.PI * 2);
        ctx.fillStyle = 'black';
        ctx.fill();
        ctx.closePath();
    }
        
}

function genereerCirkels(aantal){
    const arrayVanCirkels = [];
    for(let i = 0; i < aantal; i++){
        const hoek = Math.random() * Math.PI * 2;
        const snelheidx = Math.cos(hoek) * snelheid;
        const snelheidy = Math.sin(hoek) * snelheid;
        arrayVanCirkels.push(new Cirkel(Math.random() * canvas.width, Math.random() * canvas.height, snelheidx, snelheidy));
    }
    return arrayVanCirkels;
}

let cirkels = [];

function tekenCirkels(){
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    const quadtree = new QuadtreeTak(new Punt2D(canvas.width/2,canvas.height/2), canvas.width/2, 0, 100);
    for(let cirkel of cirkels){
        const cirkelObject = {
            positie: new Punt2D(cirkel.x, cirkel.y),
        }
        quadtree.insert(cirkelObject);
    }
    quadtree.verdeelAlsNodig();
    tekenTree(quadtree);

    for(let cirkel of cirkels){
        if(!pauze){
            cirkel.update();
        }
        cirkel.draw();
    }
}

function tekenTree(tak){
    ctx.strokeStyle = 'black';
    ctx.strokeRect(tak.midden.x - tak.grootte, tak.midden.y - tak.grootte, tak.grootte * 2, tak.grootte * 2);
    for (const kind of tak.kinderen) {
        tekenTree(kind);
    }
}

function animeer(){
    requestAnimationFrame(animeer);
    tekenCirkels();
}

function startVisualisatie(aantal){
    let aantalMax = Math.min(aantal, 50); //max aantal cirkels tot max 50 om crashes te voorkomen (1000+ cirkels kan site niet aan)
    if(aantal> 50){
        alert("Te veel cirkels, 50 cirkels worden gegenereerd.");
    }
    cirkels = genereerCirkels(aantalMax);
}

startVisualisatie(2);
animeer();



document.getElementById('pauzeSim').addEventListener('click', PauzeHervat);
document.getElementById('2planetenknop').addEventListener('click', () => startVisualisatie(2));
document.getElementById('5planetenknop').addEventListener('click', () => startVisualisatie(5));
document.getElementById('10planetenknop').addEventListener('click', () => startVisualisatie(10));
document.getElementById('customaantalplaneten').addEventListener('click', () => {
    const aantal = parseInt(document.getElementById('aantalPlaneten').value);
    startVisualisatie(aantal);
});
document.getElementById('SliderSNelheid').addEventListener('input', (event) => {
    snelheid = parseFloat(event.target.value);

    for (let cirkel of cirkels) {
        const hoek = Math.atan2(cirkel.snelheidy, cirkel.snelheidx);;
        cirkel.snelheidx = Math.cos(hoek) * snelheid;
        cirkel.snelheidy = Math.sin(hoek) * snelheid;
    }
});
document.getElementById('aantalPlaneten').addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
        const aantal = parseInt(event.target.value);
        startVisualisatie(aantal);
    }
});