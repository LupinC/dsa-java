package edu.emory.cs.dynamic.lcs;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class LCSQuiz extends LCSDynamic {
    /**
     * @param a the first string.
     * @param b the second string.
     * @return a set of all longest common sequences between the two strings.
     */
    public Set<String> solveAll(String a, String b) {
        char[] c = a.toCharArray();
        char[] d = b.toCharArray();
        int[][] table = createTable(c, d);
        int max = table[c.length - 1][d.length - 1];
        Set<String> result = new HashSet<>();

        findAll(c, d, c.length - 1, d.length - 1, table, new StringBuilder(), max, result);

        return result;
    }

    private void findAll(char[] c, char[] d, int i, int j, int[][] table, StringBuilder currentLCS, int max, Set<String> result) {
        if (i < 0 || j < 0 || table[i][j] == 0) {
            if (currentLCS.length() == max) {
                result.add(currentLCS.reverse().toString());
                currentLCS.reverse();
            }
            return;
        }
        if (i == 0) findAll(c, d, i, j - 1, table, currentLCS, max, result);

        if (j == 0) findAll(c, d, i - 1, j, table, currentLCS, max, result);

        if (c[i] == d[j]) {
            currentLCS.append(c[i]);
            findAll(c, d, i - 1, j - 1, table, currentLCS, max, result);
            currentLCS.deleteCharAt(currentLCS.length() - 1);
        }

        if (table[i][j] == table[i - 1][j])
            findAll(c, d, i - 1, j, table, currentLCS, max, result);

        if (table[i][j] == table[i][j - 1])
            findAll(c, d, i, j - 1, table, currentLCS, max, result);
    }


    public static void main(String[] args) {
        LCSQuiz a = new LCSQuiz();

        String c = "ACGTCGTGT";
        String b = "CTAGTGGAG";

        Set<String> d = new HashSet<>(a.solveAll(c,b));

        System.out.println(d);
    }
}
