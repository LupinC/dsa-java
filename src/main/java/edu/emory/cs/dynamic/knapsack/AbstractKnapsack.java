package edu.emory.cs.dynamic.knapsack;

import java.util.Collection;
import java.util.List;

public abstract class AbstractKnapsack {
    /**
     * @param items     items to be entered into the knapsack.
     * @param maxWeight the maximum weight that the knapsack can hold.
     * @return a list of items maximizing the total value given {@code items} and {@code maxWeight}.
     */
    public abstract List<KnapsackItem> solve(KnapsackItem[] items, int maxWeight);

    /**
     * @return the total value of the specific items.
     */
    protected int getTotalValue(Collection<KnapsackItem> items) {
        int total = 0;

        for (KnapsackItem item : items)
            total += item.getValue();

        return total;
    }
}
