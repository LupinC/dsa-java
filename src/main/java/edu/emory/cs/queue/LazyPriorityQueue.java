package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LazyPriorityQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T>{
    private final List<T> keys;

    public LazyPriorityQueue(){
        this(Comparator.naturalOrder());
    }

    /** @see AbstractPriorityQueue#AbstractPriorityQueue(Comparator). */
    public LazyPriorityQueue(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
    }

    public int size(){
        return keys.size();
    }

    public void add(T key){
        keys.add(key);

    }

    public T remove(){
        if(isEmpty()) return null;
        T max = Collections.max(keys, priority);
        keys.remove(max);
        return max;

    }
    /*L6-8: appends a key to the list in .
    L29-33: removes a key to the list in .
    L30: edge case handling.
    L31: finds the max-key in the list using Collections.max() in .
    L32: removes a key from the list in . */
}
