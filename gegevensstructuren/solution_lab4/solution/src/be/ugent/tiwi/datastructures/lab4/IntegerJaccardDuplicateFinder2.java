package be.ugent.tiwi.datastructures.lab4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Calculates the Jaccard similarity between documents. 
 * Returns pairs where the similarity is larger than the threshold. 
 * Instead of comparing sets of Strings, this class first transforms them into sets
 * of ints where each string is replaced with its hashcode.
 * @author sleroux
 */
public class IntegerJaccardDuplicateFinder2 extends IntegerJaccardDuplicateFinder {

    private final List<Set<Integer>> codes;
    
    public IntegerJaccardDuplicateFinder2(List<Set<String>> tokens) {
        super(tokens);
        
        // Transform the Set<String> into Set<Integer>
        codes = new ArrayList<>(tokens.size());

        for (Set<String> doc : tokens) {
            Set<Integer> code = new HashSet<>();
            for (String s : doc) {
                // Every word is replaced by its hashcode
                code.add(s.hashCode());
            }
            codes.add(code);
        }
    }
}
