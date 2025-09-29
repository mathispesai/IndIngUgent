package be.ugent.tiwi.datastructures.lab1;

/**
 *
 * @author sleroux
 */
public class SortedLinkedList implements SortedList {

    private class Node {

        int key;
        Node next;
    }

    private Node start;
    int size = 0;

    @Override
    public void clear() {
        start = null;
        size = 0;
    }

    @Override
    public void print() {
        for (Node n = start; n != null; n = n.next) {
            System.out.print(n.key + " ");
        }
        System.out.println("");
    }

    @Override
    public void insert(int key) {
        Node n = new Node();
        n.key = key;

        if (start == null || start.key > key) {
            n.next = start;
            start = n;
        } else {
            Node m = start;
            while (m.next != null && m.next.key < key) {
                m = m.next;
            }
            n.next = m.next;
            m.next = n;

        }
        size++;
    }

    @Override
    public boolean contains(int key) {
        Node n = start;
        while (n != null && n.key < key) {
            n = n.next;
        }
        return n != null && n.key == key;
    }

    @Override
    public void remove(int key) {
        if (start.key == key) {
            start = start.next;
            size--;
        } else {
            Node n = start;
            while (n.next != null && n.next.key != key) {
                n = n.next;
            }
            if (n.next != null) {
                n.next = n.next.next;
                size--;
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
        int i = 0;
        Node n = start;
        while (n != null) {
            output[i] = n.key;
            n = n.next;
            i++;
        }
        return output;
    }

}
