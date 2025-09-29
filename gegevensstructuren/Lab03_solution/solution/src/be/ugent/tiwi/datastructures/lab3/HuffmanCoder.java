package be.ugent.tiwi.datastructures.lab3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author sleroux
 */
public class HuffmanCoder {
    private BinaryTree tree;

    public HuffmanCoder(String text) {
        Map<Character, Double> frequencies = getFrequencies(text);
        this.tree = buildTree(frequencies);
        
    }
    
    private Map<Character, Double> getFrequencies(String text){
        Map<Character, Double> frequencies = new HashMap<>();
        for (char c: text.toCharArray()){
            frequencies.put(c, frequencies.getOrDefault(c, 0.0)+1);
        }
        return frequencies;
    }
    
    private BinaryTree buildTree(Map<Character, Double> frequencies){
        PriorityQueue<BinaryTree> trees = new PriorityQueue<>(new Comparator<BinaryTree>() {
            @Override
            public int compare(BinaryTree arg0, BinaryTree arg1) {
                return Double.compare(arg0.getWeight(), arg1.getWeight());
            }
        });
        
        for (char c: frequencies.keySet()){
            BinaryTree t = new BinaryTree(Character.toString(c), frequencies.get(c));
            trees.add(t);
        }
        
        while (trees.size() > 1){
            BinaryTree tree1 = trees.poll();
            BinaryTree tree2 = trees.poll();
            
            BinaryTree largerTree = new BinaryTree(tree1, tree2);
            
            trees.add(largerTree);
        }
        return trees.poll();
    }
    
    public String encode(String text){
        StringBuilder output =  new StringBuilder();
        for (int i=0; i<text.length(); i++){
            char c = text.charAt(i);
            BinaryTree t = this.tree;
            while (!t.isLeaf()){
                if (t.getLeft().getCharacter().indexOf(c) >= 0){
                    t = t.getLeft();
                    output.append("0");
                }
                else{
                    t = t.getRight();
                    output.append("1");
                }
            }
            output.append(" ");
        }
        
        return output.toString();
    }
    
    public String decode(String code){
        StringBuilder output =  new StringBuilder();
        BinaryTree t = this.tree;
        for (char c: code.toCharArray()){
            if (c == '0'){
                t = t.getLeft();
            }
            else if (c == '1'){
                t = t.getRight();
            }
            else{
                output.append(t.getCharacter());
                t = this.tree;
            }
        }
        
        
        return output.toString();
    }

    public BinaryTree getTree() {
        return tree;
    }
    
    
    
}
