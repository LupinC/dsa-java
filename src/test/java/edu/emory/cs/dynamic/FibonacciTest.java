package edu.emory.cs.dynamic;

import edu.emory.cs.dynamic.fibonacci.Fibonacci;
import edu.emory.cs.dynamic.fibonacci.FibonacciDynamic;
import edu.emory.cs.dynamic.fibonacci.FibonacciIterative;
import edu.emory.cs.dynamic.fibonacci.FibonacciRecursive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class FibonacciTest {
    @Test
    public void compare() {
        Fibonacci recursive = new FibonacciRecursive();
        Fibonacci iterative = new FibonacciIterative();
        Fibonacci dynamic = new FibonacciDynamic();

        for (int k = 0; k < 20; k++)
            assertEquals(recursive.get(k), iterative.get(k), dynamic.get(k));
    }
}
