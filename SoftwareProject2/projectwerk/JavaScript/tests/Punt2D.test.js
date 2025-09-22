/**
 * @jest-environment jsdom
 */

import Punt2D from "../Punt2D";

describe("Punt2D", () => {
    test("connstructor slaat x en y correct op", () => {
        const punt = new Punt2D(3, 4);
        expect(punt.x).toBe(3);
        expect(punt.y).toBe(4);
    });
    test("afstandTot berekent de afstand correct", () => {
        const punt1 = new Punt2D(3, 4);
        const punt2 = new Punt2D(0, 0);
        expect(punt1.afstandTot(punt2)).toBe(25); // 5^2 = 25
        expect(punt2.afstandTot(punt1)).toBe(25); // 5^2 = 25, dubbel controleren voor zekerheid
    });
    test("afstandTot met negatieve coördinaten", () => {
        const punt1 = new Punt2D(-3, -4);
        const punt2 = new Punt2D(0, 0);
        expect(punt1.afstandTot(punt2)).toBe(25); // 5^2 = 25
        expect(punt2.afstandTot(punt1)).toBe(25); // 5^2 = 25, dubbel controleren voor zekerheid
    });
    test("afstandTot met zelfde coördinaten", () => {
        const punt1 = new Punt2D(0, 0);
        const punt2 = new Punt2D(0, 0);
        expect(punt1.afstandTot(punt2)).toBe(0); // afstand tussen zelfde punten is 0
    });
    test("afstandTot met negatieve en positieve coördinaten", () => {
        const punt1 = new Punt2D(-3, 4);
        const punt2 = new Punt2D(0, 0);
        expect(punt1.afstandTot(punt2)).toBe(25); // 5^2 = 25
        expect(punt2.afstandTot(punt1)).toBe(25); // 5^2 = 25, dubbel controleren voor zekerheid
    });
});