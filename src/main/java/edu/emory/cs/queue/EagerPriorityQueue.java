package edu.emory.cs.queue;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EagerPriorityQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public EagerPriorityQueue() {
        this(Comparator.naturalOrder());
    }

    public EagerPriorityQueue(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
    }

    @Override
    public int size() {
        return keys.size();
    }

    /**
     * Adds a key to {@link #keys} by the priority.
     * @param key the key to be added.
     */
    @Override
    public void add(T key) {
        // binary search (if not found, index < 0)
        int index = Collections.binarySearch(keys, key, priority);
        // if not found, the appropriate index is {@code -(index +1)}
        if (index < 0) index = -(index + 1);
        keys.add(index, key);
    }

    /**
     * Remove the last key in the list.
     * @return the key with the highest priority if exists; otherwise, {@code null}.
     */
    @Override
    public T remove() {
        return isEmpty() ? null : keys.remove(keys.size() - 1);
    }

    /*
    L6-12: inserts a key to the list in O(n) .
    L8: finds the index of the key to be inserted in the list using binary search in O(logn).
    L10: reverse engineers the return value of Collections.binarySearch() .
    L11: inserts the key at the index position in O(n).
    L19-21: removes a key from the list in O(1).
     */
}
