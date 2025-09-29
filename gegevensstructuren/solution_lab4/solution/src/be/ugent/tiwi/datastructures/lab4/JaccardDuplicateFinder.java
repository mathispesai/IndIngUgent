package be.ugent.tiwi.datastructures.lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Calculates the Jaccard similarity between documents. 
 * Returns pairs where the similarity is larger than the threshold. 
 * @author sleroux
 */
public class JaccardDuplicateFinder extends DuplicateFinder {

    public JaccardDuplicateFinder(List<Set<String>> tokens) {
        super(tokens);
    }

    @Override
    public List<Pair> findDuplicates(double threshold) {
        // List to store the found pairs
        List<Pair> output = new ArrayList<>();

        // Check every document
        for (int i = 0; i < tokens.size(); i++) {
            // Compare it with every other document
            // sim(a, b) = sim(b, a) so avoid checking the same pair twice
            for (int j = i + 1; j < tokens.size(); j++) {
                int intersection = 0;
                int union = 0;

                // These sets contain the words of both documents
                Set<String> a = tokens.get(i);
                Set<String> b = tokens.get(j);

                // Calculate the intersection: words that are present in both sets
                for (String s : a) {
                    if (b.contains(s)) {
                        intersection++;
                    }
                }

                // denominator for the Jaccard similarity
                union = a.size() + b.size() - intersection;
                
                // Jaccard similarity
                double score = ((double) intersection) / union;
                if (score > threshold){
                    output.add(new Pair(i, j, score));
                }
            }
        }

        return output;
    }

}
