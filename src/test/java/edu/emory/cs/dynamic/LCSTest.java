package edu.emory.cs.dynamic;

import edu.emory.cs.dynamic.lcs.LCS;
import edu.emory.cs.dynamic.lcs.LCSDynamic;
import edu.emory.cs.dynamic.lcs.LCSRecursive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class LCSTest {
    @Test
    public void compare() {
        LCS r = new LCSRecursive();
        LCS d = new LCSDynamic();

        String a = "ACGTCGTGT";
        String b = "CTAGTGGAG";

        assertEquals(r.solve(a, b), d.solve(a, b));

        a = "GAATGTCCTTTCTCTAAGTCCTAAG";
        b = "GGAGACTTACAGGAAAGAGATTCAGGATTCAGGAGGCCTACCATGAAGATCAAG";
    }
}

