package be.ugent.tiwi.datastructures.lab4;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sleroux
 */
public class Main {

    public static void readData(String filename, List<Set<String>> tokens, List<String> ids) throws FileNotFoundException {
        try (Scanner s = new Scanner(new FileReader(filename))) {
            String line;
            while (s.hasNextLine()) {
                Set<String> docTokens = new HashSet<>();
                line = s.nextLine();
                line = line.toLowerCase();
                line = line.replaceAll("[^a-zA-Z0-9 ]", "");
                String[] words = line.split(" ", 2);
                String id = words[0];
                words = words[1].split(" ");
                docTokens.addAll(Arrays.asList(words));
                ids.add(id);
                tokens.add(docTokens);
            }
        }
    }

    public static Map<Integer, Integer> readGroundTruth(String filename, List<String> ids) throws FileNotFoundException {
        Map<String, Integer> indices = new HashMap<>(ids.size());
        for (int i = 0; i < ids.size(); i++) {
            indices.put(ids.get(i), i);
        }
        Map<Integer, Integer> matches = new HashMap<>();
        try (Scanner s = new Scanner(new FileReader(filename))) {
            String line;
            while (s.hasNextLine()) {
                line = s.nextLine();
                String[] words = line.split(" ");
                matches.put(indices.get(words[0]), indices.get(words[1]));
            }
        }
        return matches;
    }

    public static void accuracy(Map<Integer, Integer> truth, List<Pair> predictions) {
        int tp = 0;
        int fp = 0;

        for (Pair p : predictions) {
            if (truth.containsKey(p.docId1) && truth.get(p.docId1).equals(p.docId2)) {
                tp++;
            } else if (truth.containsKey(p.docId2) && truth.get(p.docId2).equals(p.docId1)) {
                tp++;
            } else {
                fp++;
            }
        }

        int fn = truth.size() - tp;

        System.out.println("False postives: " + fp);
        System.out.println("True positives: " + tp);
        System.out.println("False negatives: " + fn);
    }

    public static void main(String[] args) {
        try {
            List<Set<String>> tokens = new ArrayList<>();
            List<String> ids = new ArrayList<>();

            readData("articles_10000.train", tokens, ids);
            Map<Integer, Integer> matches = readGroundTruth("articles_10000.truth", ids);

            long start = System.currentTimeMillis();
            //DuplicateFinder df = new JaccardDuplicateFinder(tokens);
            //DuplicateFinder df = new IntegerJaccardDuplicateFinder(tokens);
            //DuplicateFinder df = new IntegerJaccardDuplicateFinder2(tokens);

            //DuplicateFinder df = new MinHashingDuplicateFinder(tokens, 10);
            DuplicateFinder df = new LSHDuplicateFinder(tokens, 10, 5, 1000);
            System.out.println("Initialization took " + (System.currentTimeMillis() - start) + " ms");

            start = System.currentTimeMillis();

            List<Pair> results = df.findDuplicates(0.9);
            System.out.println("Finding duplicates took " + (System.currentTimeMillis() - start) + " ms");
            System.out.println(results.size());


            for (Pair p : results) {
                System.out.println(String.format("%.2f", p.similarity) + "\t" + ids.get(p.docId1) + " <----> " + ids.get(p.docId2));
            }

            accuracy(matches, results);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
