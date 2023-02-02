package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }
    /**
     * starts at index 0
     * an index k -> its parent at (k-1)/3
     * its children at 3k+1; 3k+2; 3k+3
     * */
    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();

    }

    private int compare(int k1, int k2) {
        return priority.compare(keys.get(k1), keys.get(k2));
    }


    @Override
    public void add(T key) {
        keys.add(key);
        swim(size()-1);// the last index is at size()-1 since I start at 0
    }

    private void swim(int k) {
        for (; 0 < k && compare((k-1)/3, k) < 0; k = (k-1)/3)
            Collections.swap(keys, (k-1)/3, k);
        //compare the index's value with its parent's,
        // if the index's value has a higher priority
        // than the parents, swap them
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 0, size()-1);//swap the max priority with the last one
        T max = keys.remove(size()-1);
        sink();
        return max;
    }

    @Override
    public int size() {
        return keys.size();
    }

    private void sink(){
        for(int k = 0, i = 1; i<size(); k = i, i = 3*i+1){
            //if the number of children of this parent is 3
            //see if any of the second two is larger than the first one
            if(i+2< size())
            {
                if(compare(i,i+1)<0||compare(i,i+2)<0)
                {
                    i++;
                }
            }
            if (i+1 < size() && compare(i, i + 1) < 0) i++;

            if (compare(k,i)>=0) break;
            Collections.swap(keys,k,i);
        }
    }
}

