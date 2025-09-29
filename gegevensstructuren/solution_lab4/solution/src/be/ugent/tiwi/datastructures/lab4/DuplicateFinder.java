/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.tiwi.datastructures.lab4;

import java.util.List;
import java.util.Set;

/**
 *
 * @author sleroux
 */
public abstract class DuplicateFinder {
    protected List<Set<String>> tokens;

    public DuplicateFinder(List<Set<String>> tokens) {
        this.tokens = tokens;
    }
    
    public abstract List<Pair> findDuplicates(double threshold);
    
    
}
