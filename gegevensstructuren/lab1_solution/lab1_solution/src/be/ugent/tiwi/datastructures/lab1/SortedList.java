package be.ugent.tiwi.datastructures.lab1;
/**
 *
 * @author sleroux
 */
public interface SortedList {
    
    void insert(int key);
    boolean contains(int key);
    void remove(int key);
    int size();
    int[] toArray();
    void clear();
    void print();
    
}
