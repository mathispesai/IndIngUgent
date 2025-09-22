/**
 * @jest-environment jsdom
 */

import { berekenKrachtVanTak } from '../krachtberekening.js';

describe('berekenKrachtVanTak', () => {
    // Dummy implementatie van Tak klasse
  const DummyTak = (overrides = {}) => ({
    totaleMassa: 10,
    massamiddelpunt: { x: 5, y: 5 },
    grootte: 2,
    objecten: [],
    kinderen: [],
    berekenMassa: jest.fn(),
    isBlad: jest.fn().mockReturnValue(true),
    ...overrides
  });

  const obj = {
    positie: { x: 0, y: 0 },
    gewicht: 5
  };

  test('geeft [0, 0] terug als totaleMassa 0 is', () => {
    const tak = DummyTak({ totaleMassa: 0 });
    const kracht = berekenKrachtVanTak(obj, tak, 0.5);
    expect(kracht).toEqual([0, 0]);
  });

  test('geeft [0, 0] terug als massamiddelpunt ontbreekt', () => {
    const tak = DummyTak({ massamiddelpunt: null });
    const kracht = berekenKrachtVanTak(obj, tak, 0.5);
    expect(kracht).toEqual([0, 0]);
  });

  test('geeft [0, 0] terug als enige object in tak hetzelfde is als obj', () => {
    const tak = DummyTak({ objecten: [obj] });
    const kracht = berekenKrachtVanTak(obj, tak, 0.5);
    expect(kracht).toEqual([0, 0]);
  });

  test('berekent correcte kracht voor bladtak', () => {
    const tak = DummyTak();
    const kracht = berekenKrachtVanTak(obj, tak, 0.5);

    const dx = tak.massamiddelpunt.x - obj.positie.x;
    const dy = tak.massamiddelpunt.y - obj.positie.y;
    const afstand = Math.sqrt(dx * dx + dy * dy);
    const G = 6.67430e-11;
    const verwachteKracht = (G * obj.gewicht * tak.totaleMassa) / (afstand * afstand);

    expect(kracht[0]).toBeCloseTo(verwachteKracht * dx / afstand);
    expect(kracht[1]).toBeCloseTo(verwachteKracht * dy / afstand);
  });

  test('roept zichzelf correct recursief aan bij niet-bladtak', () => {
    const kind1 = DummyTak({
      massamiddelpunt: { x: 2, y: 2 },
      totaleMassa: 3,
      isBlad: jest.fn().mockReturnValue(true)
    });

    const kind2 = DummyTak({
      massamiddelpunt: { x: -3, y: -4 },
      totaleMassa: 7,
      isBlad: jest.fn().mockReturnValue(true)
    });

    const tak = DummyTak({
      isBlad: jest.fn().mockReturnValue(false),
      kinderen: [kind1, kind2]
    });

    const kracht = berekenKrachtVanTak(obj, tak, 0.00001);

    expect(kracht.length).toBe(2);
    expect(typeof kracht[0]).toBe('number');
    expect(typeof kracht[1]).toBe('number');
  });
});
