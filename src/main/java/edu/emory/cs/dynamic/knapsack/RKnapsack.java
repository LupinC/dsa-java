package edu.emory.cs.dynamic.knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RKnapsack extends AbstractKnapsack {
    @Override
    public List<KnapsackItem> solve(KnapsackItem[] items, int maxWeight) {
        Arrays.sort(items);
        return solve(items, maxWeight, items.length - 1);
    }

    /**
     * @param items sorted by their weights in ascending order.
     */
    private List<KnapsackItem> solve(KnapsackItem[] items, int maxWeight, int index) {
        if (index < 0 || maxWeight == 0) return new ArrayList<>();
        KnapsackItem item = items[index];

        if (item.getWeight() > maxWeight)
            return solve(items, maxWeight, index - 1);
        else {
            List<KnapsackItem> with = solve(items, maxWeight - item.getWeight(), index - 1);
            List<KnapsackItem> without = solve(items, maxWeight, index - 1);
            with.add(item);
            return (getTotalValue(with) > getTotalValue(without)) ? with : without;
        }
    }
}
