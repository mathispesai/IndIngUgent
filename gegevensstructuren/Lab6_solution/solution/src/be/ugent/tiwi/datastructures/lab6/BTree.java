package be.ugent.tiwi.datastructures.lab6;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author sleroux
 */
public class BTree {

    private int m; // Degree
    private Node root;

    public BTree(int m) {
        this.m = m;
        this.root = new Node();
    }

    public class Node {

        public boolean isLeaf;
        public int k;    // The number of keys stored in this node
        public int[] keys; // All the keys stored in this node (at most m-1)
        public long[] values; // The corresponding values
        public Node[] children; // The children of this node (at most m, always k+1)
        public Node parent; // The parent of this node

        public Node() {
            isLeaf = true;
            k = 0;
            keys = new int[m - 1];
            values = new long[m - 1];
            children = new Node[m];
            parent = null;
        }
    }

    private void splitNode(Node n) {
        Node sibling = new Node();
        sibling.isLeaf = n.isLeaf;

        int middle = (m-1) / 2;

        for (int i = middle + 1; i < m - 1; i++) {
            sibling.keys[i - (middle + 1)] = n.keys[i];
            sibling.values[i - (middle + 1)] = n.values[i];
            sibling.children[i - (middle + 1)] = n.children[i];
            if (sibling.children[i - (middle + 1)] != null)
                sibling.children[i - (middle + 1)].parent = sibling;
            sibling.k++;
        }
        sibling.children[sibling.k] = n.children[m - 1];
        if (sibling.children[sibling.k] != null)
            sibling.children[sibling.k].parent = sibling;

        n.k = middle;

        Node parent = n.parent;

        if (parent == null) {
            parent = new Node();
            parent.keys[0] = n.keys[middle];
            parent.values[0] = n.values[middle];
            parent.children[0] = n;
            parent.children[1] = sibling;
            parent.k = 1;
            parent.isLeaf = false;

            n.parent = parent;
            sibling.parent = parent;

            root = parent;
        } else {
            sibling.parent = parent;

            // move all the keys larger than the new key to the right
            int i = parent.k;
            while (i > 0 && parent.keys[i - 1] > n.keys[middle]) {
                parent.keys[i] = parent.keys[i - 1];
                parent.values[i] = parent.values[i - 1];
                parent.children[i+1] = parent.children[i];
                i--;
            }

            parent.keys[i] = n.keys[middle];
            parent.values[i] = n.values[middle];
            parent.children[i + 1] = sibling;

            parent.k++;
            if (parent.k == m - 1) {
                splitNode(parent);
            }
        }
    }

    public void add(int key, long value) {
        Node n = root;

        // Descend until you end up in a leaf
        while (!n.isLeaf) {
            int i = 0;
            while (i < n.k && n.keys[i] < key) {
                i++;
            }
            n = n.children[i];
        }

        // move all the keys larger than the new key to the right
        int i = n.k;
        while (i > 0 && n.keys[i - 1] > key) {
            n.keys[i] = n.keys[i - 1];
            n.values[i] = n.values[i - 1];
            i--;
        }

        // insert the new key here
        n.keys[i] = key;
        n.values[i] = value;
        n.k++;

        // Check if we need to split the node
        if (n.k == m - 1) {
            splitNode(n);
        }
    }

    public long get(int key) {
        Node n = root;

        long result = -1;
        boolean done = false;

        while (!done) {
            int i = 0;
            while (i < n.k && n.keys[i] < key) {
                i++;
            }
            if (i < n.k && n.keys[i] == key) {
                result = n.values[i];
                done = true;
            } else if (n.isLeaf) {
                done = true;
            } else {
                n = n.children[i];
            }
        }
        return result;

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<Node> toDo = new LinkedList<>();
        toDo.add(root);
        Node last = root.children[root.k];

        while (!toDo.isEmpty()) {
            Node element = toDo.poll();

            sb.append(" |");
            for (int i = 0; i < element.k; i++) {
                sb.append(element.keys[i] + " ");
            }
            if (!element.isLeaf) {
                for (int i = 0; i <= element.k; i++) {
                    toDo.add(element.children[i]);
                }
            }
            sb.append("|\t");

            if (element == root || element == last) {
                last = element.children[element.k];
                sb.append("\n");
            }

        }
        return sb.toString();
    }
}


