package be.ugent.tiwi.datastructures.lab3;

/**
 *
 * @author sleroux
 */
public class BinaryTree {

    private BinaryTree left;
    private BinaryTree right;
    private String character;
    private double weight;

    public BinaryTree(String character, double weight) {
        this.character = character;
        this.weight = weight;
    }

    public BinaryTree(BinaryTree left, BinaryTree right) {
        this(left.character + right.character, left.weight + right.weight);
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    public BinaryTree getLeft() {
        return left;
    }

    public void setLeft(BinaryTree left) {
        this.left = left;
    }

    public BinaryTree getRight() {
        return right;
    }

    public void setRight(BinaryTree right) {
        this.right = right;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString("", sb);
        return sb.toString();
    }

    private void buildString(String prefix, StringBuilder sb) {
        sb.append(character).append("(").append(weight).append(")");
        sb.append("\n");
        if (left != null) {
            sb.append(prefix).append("--0--> ");
            left.buildString(prefix + "    ", sb);
        }
        if (right != null) {
            sb.append(prefix).append("--1--> ");
            right.buildString(prefix + "    ", sb);
        }

    }

}
