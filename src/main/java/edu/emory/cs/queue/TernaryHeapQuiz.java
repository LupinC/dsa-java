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

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        // TODO: to be updated
        keys.add(null);

    }

    @Override
    public void add(T key) {
        // TODO: to be updated
    }

    @Override
    public T remove() {
        // TODO: to be updated
        return null;
    }

    @Override
    public int size() {
        // TODO: to be updated
        return 0;
    }
}

