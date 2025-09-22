/**
 * @jest-environment jsdom
 */

import QuadtreeTak from '../QuadTreeTak.js';
import Punt2D from '../Punt2D.js';

describe('QuadtreeTak', () => {
  let quadtree;
  const midden = new Punt2D(0, 0);
  const grootte = 100;

  // Dummy object voor testen
  const maakObject = (x, y, gewicht = 1) => ({
    positie: new Punt2D(x, y),
    gewicht: gewicht
  });

  beforeEach(() => {
    quadtree = new QuadtreeTak(midden, grootte);
  });

  test('bevat() retourneert true als punt in tak ligt', () => {
    const punt = new Punt2D(10, 10);
    expect(quadtree.bevat(punt)).toBe(true);
  });

  test('bevat() retourneert false als punt buiten tak ligt', () => {
    const punt = new Punt2D(200, 200);
    expect(quadtree.bevat(punt)).toBe(false);
  });

  test('insert() voegt object toe als het binnen grenzen ligt', () => {
    const obj = maakObject(10, 10);
    expect(quadtree.insert(obj)).toBe(true);
    expect(quadtree.objecten).toContain(obj);
  });

  test('insert() faalt als object buiten grenzen ligt', () => {
    const obj = maakObject(200, 200);
    expect(quadtree.insert(obj)).toBe(false);
    expect(quadtree.objecten).not.toContain(obj);
  });

  test('subdivide() creëert vier kinderen', () => {
    quadtree.subdivide();
    expect(quadtree.kinderen.length).toBe(4);
    for (const kind of quadtree.kinderen) {
      expect(kind).toBeInstanceOf(QuadtreeTak);
    }
  });

  test('verdeelAlsNodig() verdeelt de tak als er meer dan één object is', () => {
    quadtree.insert(maakObject(10, 10));
    quadtree.insert(maakObject(-10, -10));
    quadtree.verdeelAlsNodig();
    expect(quadtree.kinderen.length).toBe(4);
  });

  test('berekenMassa() berekent alles coorect', () => {
    const obj1 = maakObject(10, 0, 2);
    const obj2 = maakObject(0, 10, 2);
    quadtree.insert(obj1);
    quadtree.insert(obj2);
    quadtree.berekenMassa();

    expect(quadtree.totaleMassa).toBe(4);
    expect(quadtree.massamiddelpunt.x).toBeCloseTo(5);
    expect(quadtree.massamiddelpunt.y).toBeCloseTo(5);
  });

  test('isBlad() retourneert true als geen knoop geen kinderen heeft', () => {
    expect(quadtree.isBlad()).toBe(true);
  });

  test('isBlad() retourneert false na een onderverdeling', () => {
    quadtree.subdivide();
    expect(quadtree.isBlad()).toBe(false);
  });
});