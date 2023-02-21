package edu.emory.cs.sort.distribution;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RadixSortQuiz extends RadixSort{

    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);



        sort(array, beginIndex, endIndex, maxBit);


/*        for(int i = 0; i < array.length; i++){
            System.out.print(array[i]+" ");
        }*/
    }


    private void sort(Integer[] array, int beginIndex, int endIndex,  int maxBit) {

        if(maxBit == 0)
        {return;}

        int div = (int) Math.pow(10, maxBit-1);

        List<Deque<Integer>> bucket2 = Stream.generate(ArrayDeque<Integer>::new).limit(10).collect(Collectors.toList());

        sort(array, beginIndex, endIndex, key -> (key / div) % 10);

        for(int i = beginIndex;i < endIndex;i++){
            bucket2.get((array[i]/div)%10).add(array[i]);
        }


        for (Deque<Integer> bucket : bucket2) {


            int index = beginIndex;
            while (!bucket.isEmpty()) {
                array[beginIndex++] = bucket.remove();
                sort(array, index, beginIndex, maxBit-1);
            }


        }
    }
}
