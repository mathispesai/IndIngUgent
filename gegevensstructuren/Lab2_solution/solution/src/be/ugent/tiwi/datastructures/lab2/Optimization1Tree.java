package be.ugent.tiwi.datastructures.lab2;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sleroux
 */
public class Optimization1Tree extends DecisionTree {

    public boolean build(List<String> questions, List<String> animals, List<Map<String, Boolean>> answers) {
        return build(0, questions, animals, answers);
    }
    
    private boolean build(int i, List<String> questions, List<String> animals, List<Map<String, Boolean>> answers) {
        if (animals.size() == 1){
            // We are done, only one remaining animal, add a leaf
            value = animals.get(0);
            return true;
        }
        else if (i < questions.size()){
            // More than one animal remaining but luckily we have questions left.
            value = questions.get(i);
            
            // Split the remaining animals based on this question
            List<String> l = new LinkedList<>();
            List<String> r = new LinkedList<>();
            
            for (String animal: animals){
                if (answers.get(i).get(animal)){
                    l.add(animal);
                }
                else{
                    r.add(animal);
                }
            }
            
            boolean result = true;
            
            if (l.isEmpty() || r.isEmpty()){
                result = build(i+1, questions, animals, answers);
            }
            else{
                if (!l.isEmpty()){
                    Optimization1Tree o = new Optimization1Tree();
                    result &= o.build(i+1, questions, l, answers);
                    left = o;
                    
                }
                if (!r.isEmpty()){
                    Optimization1Tree o = new Optimization1Tree();
                    result &= o.build(i+1, questions, r, answers);
                    right = o;
                }
            }
            
            return result;
        }
        else{
            System.out.print("Can not distinghuish between ");
            for (String s: animals){
                System.out.print(s+" ");
            }
            System.out.println("");
            return false;
        }
    }
}
