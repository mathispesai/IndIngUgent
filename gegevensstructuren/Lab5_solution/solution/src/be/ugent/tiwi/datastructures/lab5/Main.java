package be.ugent.tiwi.datastructures.lab5;

public class Main {

    public static void main(String[] args) {
        Treap t = new Treap();

        for (int i = 0; i < 10; i++) {
            t.add(i);
        }

        System.out.println("Height: " + t.height());
        System.out.println(t);

        boolean valid = true;
        // Check if all elements are present
        for (int i = 0; i < 10; i++) {
            if (!t.contains(i)) {
                valid = false;
                break;
            }

        }
        System.out.println("Treap contains all elements: "+  (valid ? " OK ": " FAIL "));
        System.out.println("Valid tree (parent and child pointers match): "+  (t.isValidTree() ? " OK ": " FAIL "));
        System.out.println("Valid BST (BST property): "+  (t.isValidBST()? " OK ": " FAIL "));
        System.out.println("Valid heap (heap property on priorities): "+  (t.isValidHeap()? " OK ": " FAIL "));

    }
}
