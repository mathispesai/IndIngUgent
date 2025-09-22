export function berekenKrachtVanTak(obj, tak, theta) {
    tak.berekenMassa(obj);

    if (tak.totaleMassa === 0 || !tak.massamiddelpunt) return [0, 0];
    if (tak.objecten.length === 1 && tak.objecten[0] === obj) return [0, 0];

    const canvasSchaal = 3000000000; // 3 000 000 000 m/pixel

    const dx = (tak.massamiddelpunt.x - obj.positie.x) * canvasSchaal;
    const dy = (tak.massamiddelpunt.y - obj.positie.y) * canvasSchaal;

    const afstand2 = dx * dx + dy * dy;
    let afstand = Math.sqrt(afstand2);
    if (afstand === 0) afstand = 1; //mag weg na implementatie botsingen

    const s = tak.grootte * 2 * canvasSchaal;

    if (tak.isBlad() || (s / afstand) < theta) {
        const G = 6.67430e-11;
        const kracht = (G * obj.gewicht * tak.totaleMassa) / afstand2;

        return [
            kracht * dx / afstand,
            kracht * dy / afstand
        ];
    } else {
        let totaal = [0, 0];
        for (const kind of tak.kinderen) {
            const f = berekenKrachtVanTak(obj, kind, theta);
            totaal[0] += f[0];
            totaal[1] += f[1];
        }
        return totaal;
    }
}