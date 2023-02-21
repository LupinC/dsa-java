package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.divide_conquer.QuickSort;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {


    @Override
    @SuppressWarnings("unchecked")
    public T[] sort(T[][] input) {

        // Determine the number of available processors
        int numProcessors = Runtime.getRuntime().availableProcessors();

        // Create a thread pool with the number of available processors
        ExecutorService executor = Executors.newFixedThreadPool(numProcessors);


        for (int i = 0; i < input.length; i++) {
            final int row = i;
            final AbstractSort<T> engine = new QuickSort<>();
            executor.submit(() -> engine.sort(input[row]));
        }

        for(int i = 0; i < input.length; i++){
            int n = input[i].length;
            if (input[i][n - 1].compareTo(input[i][0])>0){
                final int row = i;
                final AbstractSort<T> engine1 = new InsertionSort<>();
                executor.submit(() -> engine1.sort(input[row]));
            }
            else if(input[i][n-1].compareTo(input[i][0])<0){
                int left = 0;
                int right = input[i].length-1;

                while(left<=right){

                    T temp = input[i][left];
                    input[i][left]= input[i][right];
                    input[i][right] = temp;
                    left++;
                    right--;
                }

                final int row = i;
                final AbstractSort<T> engine2 = new InsertionSort<>();
                executor.submit(() -> engine2.sort(input[row]));

            }
            else
            {
                final int row = i;
                final AbstractSort<T> engine3 = new QuickSort<>();
                executor.submit(() -> engine3.sort(input[row]));
            }
        }


        // Wait for all tasks to complete
        executor.shutdown();

        int k = input.length;
        int n = input[0].length;
        T[] output = (T[]) new Comparable[k * n];


        for( n = 0,  k = n+1; n < input.length -1; n++) {
            int len = input[n].length + input[k].length;

            int indexone = 0;
            int indextwo = 0;

            for (int i = 0; i < len; i++) {
                if (indexone < input[n].length && indextwo < input[k].length) {
                    if (input[n][indexone].compareTo(input[k][indextwo]) <= 0) {
                        output[i] = input[n][indexone];

                        indexone++;
                    } else {
                        output[i] = input[k][indextwo];

                        indextwo++;
                    }
                } else {
                    if (indextwo < input[k].length) {
                        output[i] = input[k][indextwo];
                        indextwo++;
                    }

                    if (indexone < input[n].length) {
                        output[i] = input[n][indexone];
                        indexone++;
                    }
                }
            }
        }



        /*for(int i = 0; i< input.length; i++){
            for(int j = 0; j < input[0].length;j++ )
            {
                System.out.print(input[i][j]+ " ");
            }
        }*/

        return output;


    }


}
