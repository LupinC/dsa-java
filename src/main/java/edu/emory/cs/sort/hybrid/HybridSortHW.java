package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.divide_conquer.QuickSort;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class HybridSortHW <T extends Comparable<T>> implements HybridSort<T>{

    private final int threads = Runtime.getRuntime().availableProcessors();

    public T[] sort(T[][] input){
        if(input[0].length<230){
            return sort2(input);
        }
        else{
            return sort1(input);
        }
    }

    public T[] sort2(T[][] input) {
        List<T[]> rows = new ArrayList<T[]>();

        for(int i = 0; i < input.length; i++){

            if (isAscending(input[i]))
            {

            }
            else if(isDescending(input[i]))
            {
                reverse(input[i]);
            }
            else
            {
                AbstractSort<T> engine = new QuickSort<>();
                engine.sort(input[i], 0,input[i].length);
            }
            rows.add(input[i]);
        }
        return merge2(rows);
    }

    public T[] sort1(T[][] input) {
        List<T[]> rows = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        List<Future<T[]>> futures = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            int finalI = i;
            Future<T[]> future = executor.submit(() -> {
                if (isAscending(input[finalI])) {

                } else if (isDescending(input[finalI])) {
                    reverse(input[finalI]);
                } else {
                    AbstractSort<T> engine = new QuickSort<>();
                    engine.sort(input[finalI], 0, input[finalI].length);
                }
                return input[finalI];
            });

            futures.add(future);
        }

        executor.shutdown();

        for (Future<T[]> future : futures) {
            try {
                rows.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return merge2(rows);
    }

    public boolean isAscending(T[] row){
        for(int i = 0; i< row.length-1;i++){
            if(row[i].compareTo(row[i+1])>0){
                return false;
            }
        }
        return true;
    }

    public boolean isDescending(T[] row){
        for(int i = 0; i< row.length-1;i++){
            if(row[i].compareTo(row[i+1])<0){
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public T[] merge(T[] one, T[] two) {
        T[] three = (T[]) Array.newInstance(one[0].getClass(),one.length+two.length);
        int len = three.length;
        int indexone = 0;
        int indextwo = 0;
        for(int i = 0; i < len; i++)
        {
            if(indexone<one.length&&indextwo<two.length)
            {
                if(one[indexone].compareTo(two[indextwo])<=0)
                {   three[i]=one[indexone];
                    indexone++;}
                else
                {   three[i]=two[indextwo];
                    indextwo++;}
            }
            else
            {
                if(indextwo<two.length){
                    three[i]=two[indextwo];
                    indextwo++;
                }
                if(indexone<one.length){
                    three[i]=one[indexone];
                    indexone++;
                }
            }
        }
        return three;
    }

    public T[] merge2(List<T[]> rows)
    {
        while (rows.size() > 1) {
            List<T[]> newRows = new ArrayList<>();
            for (int i = 0; i < rows.size(); i += 2) {
                if (i + 1 >= rows.size()) {
                    newRows.add(rows.get(i));
                } else {
                    T[] merged = merge(rows.get(i), rows.get(i + 1));
                    newRows.add(merged);
                }
            }
            rows = newRows;
        }
        return rows.get(0);
    }

    public void reverse(T[] row){
        int left = 0;
        int right = row.length-1;
        while(left<=right){
            T temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }
}
