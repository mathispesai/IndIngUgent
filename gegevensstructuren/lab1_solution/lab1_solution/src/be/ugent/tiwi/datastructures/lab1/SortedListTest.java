package be.ugent.tiwi.datastructures.lab1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SortedListTest {
    private final int[] values;
    private final SortedList d;
    private static final Random rnd = new Random();

    public SortedListTest() {
        int n = 100;
        values = new int[n];

        d = new SkipList(10);
        //d = new SortedLinkedList();

        for (int i = 0; i < n; i++) {
            values[i] = i;
        }
        shuffleArray(values);

        for (int v: values){
            d.insert(v);
        }
    }

    @Test
    public void sizeTest(){
        assertEquals(values.length, d.size());
    }

    @Test
    public void contentTest() {
        int[] sorted = values.clone();
        Arrays.sort(sorted);

        assertArrayEquals(d.toArray(), sorted);

    }

    @Test
    public void containTest() {
        for (int v : values) {
            assertTrue(d.contains(v));
        }
        for (int v: values) {
            assertFalse(d.contains(100+v));
        }

    }

    @Test
    public void removeTest() {
        for (int v : values) {
            d.remove(v);
            assertFalse(d.contains(v));
        }
        assertEquals(0, d.size());
    }

    private void shuffleArray(int[] ar) {
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

}