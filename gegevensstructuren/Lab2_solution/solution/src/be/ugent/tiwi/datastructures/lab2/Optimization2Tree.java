package be.ugent.tiwi.datastructures.lab2;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sleroux
 */
public class Optimization2Tree extends DecisionTree{

    public boolean build(List<String> questions, List<String> animals, List<Map<String, Boolean>> answers) {
        if (animals.size() == 1) {
            // We are done, only one remaining animal, add a leaf
            value = animals.get(0);
            return true;
        } else if (!questions.isEmpty()) {
            // More than one animal remaining but luckily we have questions left.
            int selectedQuestion = -1;
            double score = 0;

            for (int i = 0; i < questions.size(); i++) {
                int yes = 0;
                int no = 0;
                for (String animal : animals) {
                    if (answers.get(i).get(animal)) {
                        yes++;
                    } else {
                        no++;
                    }
                }
                double question_score = Math.abs(yes - no);
                if (selectedQuestion < 0 || question_score < score) {
                    score = question_score;
                    selectedQuestion = i;
                }
            }

            // Split the remaining animals based on this question
            List<String> l = new LinkedList<>();
            List<String> r = new LinkedList<>();

            for (String animal : animals) {
                if (answers.get(selectedQuestion).get(animal)) {
                    l.add(animal);
                } else {
                    r.add(animal);
                }
            }
            value = questions.get(selectedQuestion);

            if (l.isEmpty() || r.isEmpty()) {
                System.out.print("Can not distinghuish between ");
                for (String s : animals) {
                    System.out.print(s + " ");
                }
                System.out.println("");
                return false;
            } else {

                boolean result = true;
                if (!l.isEmpty()) {
                    left = new Optimization2Tree();
                    result &= left.build(questions, l, answers);
                }
                if (!r.isEmpty()) {
                    right = new Optimization2Tree();
                    result &= right.build(questions, r, answers);
                }

                return result;
            }
        }
        return false;
    }
}
