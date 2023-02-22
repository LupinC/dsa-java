package edu.emory.cs.sort.distribution;

        import java.util.ArrayDeque;
        import java.util.ArrayList;
        import java.util.Deque;
        import java.util.List;
        import java.util.stream.Collectors;
        import java.util.stream.Stream;

public class RadixSortQuiz extends RadixSort {

    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);

        ArrayDeque<Integer> a = new ArrayDeque<>();

        //       System.out.println("initial: " + array.length);
        for (int i = 0; i < array.length; i++)
        {
            a.addLast(array[i]);
        }

        //System.out.println("before: " + a.size());

        sort(a, maxBit);

        //System.out.println("after: " + a.size());


/*        for (var i: a) {
            System.out.print(i + " ");
        }*/

        for (int i = 0; !a.isEmpty(); i++)
        {
            array[i] = a.removeFirst();
        }
    }

    private void sort(ArrayDeque<Integer> array, int maxBit) {

        if(maxBit == 0)
        {return;}

        int div = (int) Math.pow(10, maxBit-1);

        List<ArrayDeque<Integer>> bucket2 = Stream.generate(ArrayDeque<Integer>::new).limit(10).collect(Collectors.toList());

        while (!array.isEmpty()){
            Integer a = array.removeFirst();
            bucket2.get((a/div)%10).add(a);
        }

        for (ArrayDeque<Integer> bucket : bucket2) {

            if(bucket.size()>1) {
                sort(bucket, maxBit - 1);
            }
            array.addAll(bucket);

        }
    }
}
