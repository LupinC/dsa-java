package edu.emory.cs.dynamic;

import edu.emory.cs.dynamic.hanoi.Hanoi;
import edu.emory.cs.dynamic.hanoi.HanoiDynamic;
import edu.emory.cs.dynamic.hanoi.HanoiRecursive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HanoiTest {
    @Test
    public void compare() {
        final char source = 'S';
        final char intermediate = 'I';
        final char destination = 'D';

        Hanoi recursive = new HanoiRecursive();
        Hanoi dynamic = new HanoiDynamic();

        for (int k = 1; k < 20; k++)
            assertEquals(recursive.solve(k, source, intermediate, destination), dynamic.solve(k, source, intermediate, destination));
    }

    @Test
    @Disabled
    public void testSpeed() {
        final int ITERATIONS = 100;
        final int MAX_K = 20;

        Hanoi recursive = new HanoiRecursive();
        Hanoi dynamic = new HanoiDynamic();

        for (int k = 2; k < MAX_K; k++)
            System.out.println(testSpeed(ITERATIONS, k, dynamic, recursive));
    }

    String testSpeed(final int iterations, final int k, Hanoi... solver) {
        StringJoiner join = new StringJoiner("\t");
        int i, j, len = solver.length;
        long st, et;

        join.add(Integer.toString(k));
        for (i = 0; i < len; i++) {
            st = System.currentTimeMillis();
            for (j = 0; j < iterations; j++) solver[i].solve(k, 'S', 'I', 'D');
            et = System.currentTimeMillis();
            join.add(Long.toString(et - st));
        }

        return join.toString();
    }
}
