package edu.emory.cs.sort.divide_conquer;

import edu.emory.cs.sort.AbstractSort;

import java.util.Comparator;

public class QuickSort<T extends Comparable<T>> extends AbstractSort<T> {

    public QuickSort() {
        this(Comparator.naturalOrder());
    }

    public QuickSort(Comparator<T> comparator) {
        super(comparator);
    }

    public void sort(T[] array, int left, int right){
        if (left >= right)
        {
            return;
        }

        int pivotIndex = partition(array,left,right);

        sort(array, left, pivotIndex);
        sort(array, pivotIndex + 1, right);

    }

    protected int partition(T[] array, int beginIndex, int endIndex) {
        int fst = beginIndex, snd = endIndex;

        while (true) {
            while (++fst < endIndex && compareTo(array, beginIndex, fst) >= 0);
            while (--snd > beginIndex && compareTo(array, beginIndex, snd) <= 0);
            if (fst >= snd) break;
            swap(array, fst, snd);
        }

        swap(array, beginIndex, snd);
        return snd;
    }


}
