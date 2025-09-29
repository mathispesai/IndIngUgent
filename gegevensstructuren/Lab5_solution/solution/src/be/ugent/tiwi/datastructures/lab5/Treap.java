package be.ugent.tiwi.datastructures.lab5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * @author sleroux
 */
public class Treap {

    private static final Random rnd = new Random();
    private Node root;

    private class Node {

        private final int value;
        private final int priority;

        private Node left;
        private Node right;
        private Node parent;

        public Node(int value) {
            this.value = value;
            this.priority = rnd.nextInt(100);
        }

    }

    public void add(int key) {
        // Create new node
        Node n = new Node(key);

        // If the tree is empty, use it as root
        if (root == null) {
            root = n;
            return;
        }

        // Else, find the location where the element needs to be placed
        Node parent = null;
        Node current = root;
        while (current != null) {
            parent = current;
            if (key < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (key < parent.value) {
            parent.left = n;
        } else {
            parent.right = n;
        }
        n.parent = parent;

        while (n.parent != null && n.parent.priority < n.priority) {
            if (n.parent.left == n) {
                // right rotation
                n.parent.left = n.right;
                if (n.right != null) {
                    n.right.parent = n.parent;
                }
                n.right = n.parent;
            } else {
                // left rotation
                n.parent.right = n.left;
                if (n.left != null) {
                    n.left.parent = n.parent;
                }
                n.left = n.parent;
            }
            if (n.parent.parent == null) {
                root = n;
                n.parent.parent = n;
                n.parent = null;

            } else {
                if (n.parent.parent.left == n.parent) {
                    n.parent.parent.left = n;
                } else {
                    n.parent.parent.right = n;
                }
                Node temp = n.parent;
                n.parent = n.parent.parent;
                temp.parent = n;
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // Provided methods, no changes are needed here
    ///////////////////////////////////////////////////////////////////////////
    public int height() {
        List<Node> currentLevel = new ArrayList();
        currentLevel.add(root);
        int height = -1;

        while (!currentLevel.isEmpty()) {
            height++;
            List<Node> nextLevel = new ArrayList<>();

            for (Node n : currentLevel) {
                if (n != null){
                    nextLevel.add(n.left);
                    nextLevel.add(n.right);
                }
            }
            currentLevel = nextLevel;
        }

        return height;

    }

    public boolean contains(int key) {
        Node n = root;

        while (n != null && n.value != key) {
            if (key < n.value) {
                n = n.left;
            } else {
                n = n.right;
            }
        }

        return n != null;
    }

    public boolean isValidBST() {
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        boolean valid = true;

        while (valid && !nodes.isEmpty()) {
            Node n = nodes.poll();
            if (n != null) {
                if (n.left != null && n.left.value > n.value) {
                    valid = false;
                }
                if (n.right != null && n.right.value < n.value) {

                    valid = false;
                }
                nodes.add(n.left);
                nodes.add(n.right);
            }
        }

        return valid;
    }

    public boolean isValidHeap() {
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        boolean valid = true;

        while (valid && !nodes.isEmpty()) {
            Node n = nodes.poll();
            if (n != null) {
                if (n.left != null && n.left.priority > n.priority) {
                    valid = false;
                }
                if (n.right != null && n.right.priority > n.priority) {
                    valid = false;
                }
                nodes.add(n.left);
                nodes.add(n.right);
            }
        }

        return valid;
    }

    public boolean isValidTree() {
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        boolean valid = true;

        while (valid && !nodes.isEmpty()) {
            Node n = nodes.poll();
            if (n != null) {
                if (n.left != null && n.left.parent != n) {
                    valid = false;
                }
                if (n.right != null && n.right.parent != n) {
                    valid = false;
                }
                nodes.add(n.left);
                nodes.add(n.right);
            }
        }

        return valid;
    }

    @Override
    public String toString() {
        final int height = 10, width = 120;

        int len = width * height * 2 + 2;
        StringBuilder sb = new StringBuilder(len);
        for (int i = 1; i <= len; i++) {
            sb.append(i < len - 2 && i % width == 0 ? "\n" : ' ');
        }

        displayR(sb, width / 2, 1, width / 4, width, root, " ");
        return sb.toString();
    }

    private void displayR(StringBuilder sb, int c, int r, int d, int w, Node n, String edge) {
        if (n != null) {
            displayR(sb, c - d, r + 2, d / 2, w, n.left, " /");

            String s = n.value + " (" + n.priority + ")";
            int idx1 = r * w + c - (s.length() + 1) / 2;
            int idx2 = idx1 + s.length();
            int idx3 = idx1 - w;
            if (idx2 < sb.length()) {
                sb.replace(idx1, idx2, s).replace(idx3, idx3 + 2, edge);
            }

            displayR(sb, c + d, r + 2, d / 2, w, n.right, "\\ ");
        }
    }

}
