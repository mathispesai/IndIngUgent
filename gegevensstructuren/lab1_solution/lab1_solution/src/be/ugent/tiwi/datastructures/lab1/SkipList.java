package be.ugent.tiwi.datastructures.lab1;

import java.util.Random;

public class SkipList implements SortedList{

    // The number of elements in the list
    private int size;

    // References to the start element at each level
    private final Node[] levels;

    private static final Random rnd = new Random();

    /**
     * Represents a single node in the SkipList
     */
    class Node {
        // References to the successor node at each level where this node is present
        private Node[] pointers;
        // The data to store
        private int key;
    }

    /**
     * Create a new SkiplistSkiplist
     * @param l The Maximum number of levels
     */
    public SkipList(int l) {
        levels = new Node[l];
        size = 0;
    }

    /**
     * Clear the list
     */
    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < levels.length; i++) {
            levels[i] = null;
        }
    }

    /**
     * Insert a new element in the list
     * @param key
     */
    @Override
    public void insert(int key) {
        // Create a new node and set the key
        Node n = new Node();
        n.key = key;

        // Determine in how many levels this node should be present.
        int nLevels = 1;
        while (nLevels < levels.length && rnd.nextBoolean()) {
            nLevels++;
        }

        // The node should store references to the successor for each level where this node is present
        n.pointers = new Node[nLevels];

        // Add the element. We descend from top to bottom
        Node[] pointers = levels;
        int level = pointers.length - 1; // Start at the top level

        while (level >= 0) { // While we are not at the bottom level
            Node m = pointers[level]; // Follow the current pointer
            if (m == null || m.key > key) {
                if (level < nLevels) {
                    // If we are at the end of the list or if the next element is greater
                    // than the element we have to add, we should insert the element here
                    // but only if the node should be present at this level
                    Node temp = pointers[level];
                    pointers[level] = n; //
                    n.pointers[level] = temp;
                }
                level--; // descend one level
            } else {
                // The new element is smaller than the current element, move right
                pointers = m.pointers;
            }
        }
        size++;

    }

    /**
     * Checks if a key is present in the SkipList
     * @param key
     * @return
     */
    @Override
    public boolean contains(int key) {
        Node[] pointers = levels;
        int level = pointers.length - 1; // Start at the top level

        Node n = null;
        while (level >= 0) { // Descend from top the bottom
            n = pointers[level]; // The next element at this level
            if (n == null || n.key >= key) {
                // If the next element is null or its key is larger than the key we are looking for
                // we will not find it here in this list, descend one level.
                level--;
            } else {
                // Move right
                pointers = n.pointers;
            }
        }

        // We found the spot where the key should be stored, check if it matches the
        // key we are looking for.
        return n != null && n.key == key;
    }

    /**
     * Remove a key from the skiplist. Similar approach as add and contains but now we
     * have to make sure we remove the element from each level.
     * @param key
     */
    @Override
    public void remove(int key) {
        Node[] pointers = levels;
        int level = pointers.length - 1;
        Node n;

        // Start at the top level and descend down
        while (level >= 0) {
            n = pointers[level]; // The next node at this level
            if (n == null || n.key > key) {
                // If the element can not be found at this level, drop down one level
                level--;
            } else if (n.key == key) {
                // The node is present at this level. Bypass it
                pointers[level] = n.pointers[level];
                if (level == 0) {
                    size--;
                }
            } else {
                // Element not found yet, move right
                pointers = n.pointers;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] toArray() {
        int[] output = new int[size];
        Node n = levels[0];
        for (int i = 0; i < size; i++) {
            output[i] = n.key;
            n = n.pointers[0];
        }
        return output;
    }

    @Override
    public void print() {
        System.out.print("Level 0\t");

        for (Node m = levels[0]; m != null; m = m.pointers[0]) {
            System.out.print(m.key + "\t");
        }
        System.out.println("");

        for (int level = 1; level < levels.length; level++) {
            System.out.print("Level " + level + "\t");
            Node m = levels[0];
            Node n = levels[level];

            while (m != null && n != null && level < n.pointers.length) {
                if (n.key == m.key) {
                    System.out.print(m.key + "\t");

                    n = n.pointers[level];

                } else {
                    System.out.print(" - \t");
                }
                m = m.pointers[0];
            }

            System.out.println("");
        }

    }
}