package edu.emory.cs.sort;


import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.comparison.ShellSortQuiz;
import edu.emory.cs.sort.distribution.MSDRadixSort;
import org.junit.jupiter.api.Test;
import edu.emory.cs.sort.distribution.LSDRadixSort;
//import edu.emory.cs.sort.distribution.MSDRadixSort;
import edu.emory.cs.sort.distribution.RadixSortQuiz;


public class SortQuizTest extends SortTest {
    @Test
    public void testRobustness() {
        testRobustness(new ShellSortQuiz<>());
        testRobustness(new RadixSortQuiz());
    }

    @Test
    public void testRuntime() {
        //testRuntime(new ShellSortKnuth<>(), new ShellSortQuiz<>());
        testRuntime(new LSDRadixSort(), new RadixSortQuiz());
    }
}
