/**
 * @jest-environment jsdom
 */
import { RuimteObject, Planeet, Komeet, Ster } from '../RuimteObject.js';
import Punt2D from '../Punt2D.js';

describe('RuimteObject', () => {
  const positie = new Punt2D(50, 100);
  const snelheid = [2, 3];
  const afbeelding = '/Media/Aarde.png';
  const gewicht = 10;
  const naam = 'TestPlaneet';

  let object;

  beforeEach(() => {
    object = new RuimteObject(snelheid, positie, afbeelding, gewicht, naam);
  });

  test('constructor zet de juiste waarden', () => {
    expect(object.naam).toBe(naam);
    expect(object.positie).toEqual(positie);
    expect(object.snelheid).toEqual(snelheid);
    expect(object.gewicht).toBe(gewicht);
    expect(object.image).toBeInstanceOf(Image);
  });

  test('updatePositie is correct', () => {
    object.updatePositie();
    expect(object.positie.x).toBe(52);
    expect(object.positie.y).toBe(103);
  });

  test('afstandKwadraatTot berekent afstand correct', () => {
    const anderObject = new RuimteObject([0, 0], new Punt2D(53, 104), afbeelding, 1, 'andere');
    const afstand = object.afstandKwadraatTot(anderObject);
    expect(afstand).toBe((52 - 53) ** 2 + (103 - 104) ** 2);
  });
});

describe('Planeet', () => {
  const planeet = new Planeet([1, 2], new Punt2D(0, 0), '/Media/p.png', 5, 'planeet');

  test('erft van ruimteobject', () => {
    expect(planeet).toBeInstanceOf(RuimteObject);
  });

  test('updatesnelheid werkt correct', () => {
    const kracht = [10, 5];
    planeet.updatesnelheid(kracht, 2);
    expect(planeet.snelheid[0]).toBeCloseTo(1 + 10 / 5 * 2);
    expect(planeet.snelheid[1]).toBeCloseTo(2 + 5 / 5 * 2); 
  });
});

//uiteindelijk niet meer geimplementeerd in code, maar test kan geruikt worden voor wanneer het wel geimplementeerd is
describe('Komeet', () => {
  const komeet = new Komeet([1, 1], new Punt2D(0, 0), 3, 'Halley');

  test('erft van Planeet', () => {
    expect(komeet).toBeInstanceOf(Planeet);
    expect(komeet.naam).toBe('Halley');
  });
});
//uiteindelijk niet meer geimplementeerd in code, maar test kan geruikt worden voor wanneer het wel geimplementeerd is
describe('Ster', () => {
  const ster = new Ster(new Punt2D(100, 100), '/Media/Zon.png', 1000);

  test('erft van RuimteObject', () => {
    expect(ster).toBeInstanceOf(RuimteObject);
  });

  test('heeft straal 30', () => {
    expect(ster.straal).toBe(30);
  });
});
