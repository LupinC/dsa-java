package edu.emory.cs.dynamic.fibonacci;

import java.util.Arrays;

public class FibonacciDynamic implements Fibonacci {
    @Override
    public int get(int k) {
        return getAux(k, createTable(k));
    }

    /**
     * @param k size of dynamic table
     * @return dynamic table
     */
    private int[] createTable(int k) {
        int[] table = new int[k + 1];
        table[0] = 0;
        table[1] = 1;
        Arrays.fill(table, 2, k + 1, -1);
        return table;
    }

    private int getAux(int k, int[] table) {
        if (table[k] >= 0) return table[k];
        return table[k] = getAux(k - 1, table) + getAux(k - 2, table);
    }
}
