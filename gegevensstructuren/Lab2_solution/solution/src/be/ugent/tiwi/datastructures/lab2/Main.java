package be.ugent.tiwi.datastructures.lab2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author sleroux
 */
public class Main {

    private static void readQuestions(String filename, List<String> questions, List<String> animals, List<Map<String, Boolean>> answers) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream(filename))) {
            int nAnimals = sc.nextInt();
            int nQuestions = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < nQuestions; i++) {
                String question = sc.nextLine();
                questions.add(question);
                answers.add(new HashMap<>());
            }

            for (int i = 0; i < nAnimals; i++) {
                String animal = sc.next();
                animals.add(animal);
                for (int j = 0; j < nQuestions; j++) {
                    boolean answer = sc.next().equals("y");
                    answers.get(j).put(animal, answer);
                }
                sc.nextLine();
            }
        }
    }

    /**
     * @param args the command line arguments
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        List<String> questions = new LinkedList<>();
        List<String>animals = new LinkedList<>();
        List<Map<String, Boolean>> answers = new LinkedList<>();
        
        readQuestions("animals_large.txt", questions, animals, answers);
        
        //DecisionTree tree = new BasicTree();
        //DecisionTree tree = new Optimization1Tree();
        DecisionTree tree = new Optimization2Tree();
        boolean result = tree.build(questions, animals, answers);
        System.out.println(result);
        
        if (result){
            System.out.println(tree);
            System.out.println("height: "+ tree.height());
            System.out.println(tree.numberOfLeaves()+" leaves");
            System.out.println(tree.numberOfSplits()+" splits");
            System.out.println("average depth: "+tree.averageDepth());
        }
        else{
            System.out.println("Not enough information to build a tree");
        }
        
    }

}
