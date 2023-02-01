package edu.emory.cs.queue;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityQueueTest {

    /**
     * @param pq   a priority queue.
     * @param keys a list of comparable keys.
     * @param comp a comparator used for sorting.
     */
    <T extends Comparable<T>> void testRobustness(AbstractPriorityQueue<T> pq, List<T> keys, Comparator<T> comp) {
        keys.forEach(pq::add);
        keys = keys.stream().sorted(comp).collect(Collectors.toList());
        keys.forEach(key -> assertEquals(key, pq.remove()));
    }

    @Test
    public void testRobustness() {
        List<Integer> keys = List.of(4, 1, 3, 2, 5, 6, 8, 3, 4, 7, 5, 9, 7);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new LazyPriorityQueue<>(), keys, reverse);
        testRobustness(new EagerPriorityQueue<>(), keys, reverse);
        testRobustness(new BinaryHeap<>(), keys, reverse);

        testRobustness(new LazyPriorityQueue<>(reverse), keys, natural);
        testRobustness(new EagerPriorityQueue<>(reverse), keys, natural);
        testRobustness(new BinaryHeap<>(reverse), keys, natural);
    }

    static class Time {
        long add = 0;
        long remove = 0;
    }

    <T extends Comparable<T>> void addRuntime(AbstractPriorityQueue<T> queue, Time time, List<T> keys) {
        long st, et;

        // runtime for add()
        st = System.currentTimeMillis();
        keys.forEach(queue::add);
        et = System.currentTimeMillis();
        time.add += et - st;

        // runtime for remove()
        st = System.currentTimeMillis();
        while (!queue.isEmpty()) queue.remove();
        et = System.currentTimeMillis();
        time.remove += et - st;
    }
}
