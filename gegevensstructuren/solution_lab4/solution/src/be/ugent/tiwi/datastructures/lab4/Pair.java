package be.ugent.tiwi.datastructures.lab4;

/**
 *
 * @author sleroux
 */
public class Pair {

    public final int docId1;
    public final int docId2;
    public final double similarity;

    public Pair(int docId1, int docId2, double similarity) {
        this.docId1 = docId1;
        this.docId2 = docId2;
        this.similarity = similarity;
    }
}
