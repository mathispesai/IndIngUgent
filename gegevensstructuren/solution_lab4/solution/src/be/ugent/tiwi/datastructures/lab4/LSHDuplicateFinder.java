package be.ugent.tiwi.datastructures.lab4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sleroux
 */
public class LSHDuplicateFinder extends MinHashingDuplicateFinder {

    private final int r;
    private final int buckets;

    public LSHDuplicateFinder(List<Set<String>> tokens, int k, int r, int buckets) {
        super(tokens, k);
        this.r = r;
        this.buckets = buckets;
    }

    @Override
    public List<Pair> findDuplicates(double threshold) {
        // Stores a set of document IDs that have to be checked for similarities
        HashMap<Integer, Set<Integer>> potentialPairs = new HashMap<>();

        // Divide the k minhashes into bands of r minhashes each
        for (int band = 0; band < k; band += r) {

            // Define a set of buckets
            List<List<Integer>> buckets = new ArrayList<>(this.buckets);
            for (int i = 0; i < this.buckets; i++) {
                buckets.add(new ArrayList<>());
            }


            // Loop over all documents
            for (int i = 0; i < minhashes.length; i++) {
                int[] h = minhashes[i];
                // Hash the hashes in this band
                int rowhash = 0;
                for (int j = band; j < band + r; j++) {
                    rowhash = 31*rowhash + h[j];
                }
                rowhash = (rowhash & 0x7FFFFFFF) % this.buckets; // absolute value
                buckets.get(rowhash).add(i); // add this document to the bucket
            }

            // Loop over all buckets
            for (List<Integer> bucket : buckets) {
                // All documents in this bucket are a potential match and have to be checked
                for (int i = 0; i < bucket.size(); i++) {
                    for (int j = i + 1; j < bucket.size(); j++) {
                        if (!potentialPairs.containsKey(bucket.get(i))) {
                            potentialPairs.put(bucket.get(i), new HashSet<>());
                        }
                        // Add them to potentialPairs to check them in the next step
                        potentialPairs.get(bucket.get(i)).add(bucket.get(j));
                    }
                }
            }
        }

        // Instead of comparing each document with all other documents
        // only documents that were hashed into the same bucket for at least
        // one band should be checked
        List<Pair> output = new ArrayList<>();
        for (int i : potentialPairs.keySet()) {
            int[] h1 = minhashes[i];
            for (int j : potentialPairs.get(i)) {
                // This just uses minhashing to estimate the Jaccard similarity
                int equal = 0;
                int[] h2 = minhashes[j];
                for (int l = 0; l < k; l++) {
                    if (h1[l] == h2[l]) {
                        equal++;
                    }
                }

                double score = ((double) equal) / k;
                if (score > threshold) {
                    output.add(new Pair(i, j, score));
                }
            }

        }

        return output;

    }

}
