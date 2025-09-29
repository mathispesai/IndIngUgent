package be.ugent.tiwi.datastructures.lab4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author sleroux
 */
public class MinHashingDuplicateFinder extends DuplicateFinder {

    private final int c = 186581; // A large prime number
    protected final int[][] minhashes; // Stores the minhashes for each document
    protected final int k; // Number of minhashes

    public MinHashingDuplicateFinder(List<Set<String>> tokens, int k) {
        super(tokens);
        this.k = k;

        // To calculate the minhashes we first have to transform the Set<String>
        // into Set<Integer>. We do this in the same way as in the previous version
        List<Set<Integer>> codes = new ArrayList<>(tokens.size());
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

        Random rnd = new Random();
        // For each document we store k minhashes
        minhashes = new int[tokens.size()][k];

        // K minhashes
        for (int i = 0; i < k; i++) {
            // Random hash function
            int a = rnd.nextInt(mapping.size());
            int b = rnd.nextInt(mapping.size());

            // Loop over all documents
            for (int doc = 0; doc < codes.size(); doc++) {
                Set<Integer> document = codes.get(doc);
                int minHash = Integer.MAX_VALUE;
                int minIndex = 0;

                // Hash all the words in this document and store the word
                // that results in the smallest hash value
                for (int code : document) {
                    int h = (a * code + b) % c; // hashing
                    if (h < minHash) {
                        minHash = h;
                        minIndex = code;
                    }
                }
                minhashes[doc][i] = minIndex;
            }
        }
    }

    @Override
    public List<Pair> findDuplicates(double threshold) {
        List<Pair> output = new ArrayList<>();

        // Compare all documents with all other documents
        for (int i = 0; i < minhashes.length; i++) {
            int[] h1 = minhashes[i];
            for (int j = i + 1; j < minhashes.length; j++) {
                int equal = 0;
                int[] h2 = minhashes[j];

                // Check for each hash function if they resulted in the same element
                // for both documents
                for (int l = 0; l < k; l++) {
                    if (h1[l] == h2[l]) {
                        equal++;
                    }
                }

                // Estimate the jaccard similarity
                double score = ((double) equal) / k;
                if (score > threshold) {
                    output.add(new Pair(i, j, score));
                }

            }
        }
        return output;

    }

}
