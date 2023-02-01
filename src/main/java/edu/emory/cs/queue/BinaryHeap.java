package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BinaryHeap <T extends Comparable<T>> extends AbstractPriorityQueue<T>{
    private final List<T> keys;

    public BinaryHeap(){
        this(Comparator.naturalOrder());
    }

    public BinaryHeap(Comparator<T> priority){
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);

    }

    public int size() {
        return keys.size() - 1;
    }

    /**
     * @param k1 the index of the first key in {@link #keys}.
     * @param k2 the index of the second key in {@link #keys}.
     * @return a negative integer, zero, or a positive integer as the first key is
     * less than, equal to, or greater than the second key.
     */
    private int compare(int k1, int k2) {
        return priority.compare(keys.get(k1), keys.get(k2));
    }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    private void swim(int k) {
        for (; 1 < k && compare(k / 2, k) < 0; k /= 2)
            Collections.swap(keys, k / 2, k);
    }

    /*L3: appends the new key to the list.
    L7-10: keeps swimming until the list becomes a heap:
    L8: compares the new key to its parent if exists.
    L9: if the new key has a higher priority than its parent, Collections.swap() them.*/

    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    private void sink() {
        for (int k = 1, i = 2; i <= size(); k = i, i *= 2) {
            if (i < size() && compare(i, i + 1) < 0) i++;
            if (compare(k, i) >= 0) break;
            Collections.swap(keys, k, i);
        }
    }
    /*L3: checks if the heap is empty.
    L4: swaps the root and the last key in the list.
    L3: removes the (swapped) last key with the highest priority in this heap.
            L10-16: keeps sinking until the list becomes a heap:
    L12: finds the child with a higher priority.
            L13: breaks if the parent has a higher priority than the child.
            L14: swaps if the child has a higher priority than the parent.*/
}