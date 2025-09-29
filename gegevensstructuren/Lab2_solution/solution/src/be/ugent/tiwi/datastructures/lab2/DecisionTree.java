/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.tiwi.datastructures.lab2;

import java.util.List;
import java.util.Map;

/**
 *
 * @author sleroux
 */
public abstract class  DecisionTree {
    protected DecisionTree left;
    protected DecisionTree right;
    protected String value;
    
    public abstract boolean build(List<String> questions, List<String> animals, List<Map<String, Boolean>> answers);
    
    public int height() {
        int height = 0;
        if (left != null) {
            height = left.height();
        }
        if (right != null) {
            height = Math.max(height, right.height());
        }
        return height + 1;
    }
    
    public int numberOfLeaves(){
        if (left == null && right == null){
            return 1;
        }
        else{
            int leaves = 0;
            if (left != null){
                leaves += left.numberOfLeaves();
            }
            if (right != null){
                leaves += right.numberOfLeaves();
            }
            return leaves;
        }
    }
    
    public int numberOfSplits(){
        int splits = 0;
        if (left != null || right != null){
            splits = 1;
            if (left != null){
                splits += left.numberOfSplits();
            }
            if (right != null){
                splits += right.numberOfSplits();
            }
        }
        return splits;
        
    }

    public double averageDepth() {
        double depth = 0.0;
        if (left != null){
            depth += (left.averageDepth()+1)*left.numberOfLeaves();
        }
        if (right != null){
            depth += (right.averageDepth()+1)*right.numberOfLeaves();
        }
        return depth/numberOfLeaves();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString("", sb);
        return sb.toString();
    }

    private void buildString(String prefix, StringBuilder sb) {
            sb.append(value);
            sb.append("\n");
            if (left != null) {
                sb.append(prefix).append("--Y--> ");
                left.buildString(prefix+ "    ", sb);
            }
            if (right != null) {
                sb.append(prefix).append("--N--> ");
                right.buildString(prefix + "    ", sb);
            }
        }
    
}
