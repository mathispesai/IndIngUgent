package be.ugent.tiwi.datastructures.lab4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Calculates the Jaccard similarity between documents. 
 * Returns pairs where the similarity is larger than the threshold. 
 * Instead of comparing sets of Strings, this class first transforms them into sets
 * of ints where each string is replaced with an unique ID.
 * @author sleroux
 */
public class IntegerJaccardDuplicateFinder extends DuplicateFinder {

    private final List<Set<Integer>> codes;
    
    public IntegerJaccardDuplicateFinder(List<Set<String>> tokens) {
        super(tokens);
        
        // We transform the Set<String> into Set<Integer>
        codes = new ArrayList<>(tokens.size());
        // Each String is replaced with a unique ID, this map stores those IDs
        HashMap<String, Integer> mapping = new HashMap<>();

        for (Set<String> doc : tokens) {
            Set<Integer> code = new HashSet<>();
            for (String s : doc) {
                if (!mapping.containsKey(s)) {
                    mapping.put(s, mapping.size());
                }
                code.add(mapping.get(s));
            }
            codes.add(code);
        }
    }



    @Override
    public List<Pair> findDuplicates(double threshold) {
        List<Pair> output = new ArrayList<>();

        for (int i = 0; i < codes.size(); i++) {
            for (int j = i + 1; j < codes.size(); j++) {
                int intersection = 0;
                int union = 0;

                Set<Integer> a = codes.get(i);
                Set<Integer> b = codes.get(j);

                for (int s : a) {
                    if (b.contains(s)) {
                        intersection++;
                    }
                }

                union = a.size() + b.size() - intersection;
                double score = ((double) intersection) / union;
                if (score > threshold){
                    output.add(new Pair(i, j, score));
                }
            }
        }
        return output;
    }

}
