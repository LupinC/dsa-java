package edu.emory.cs.dynamic.knapsack;

public class KnapsackItem implements Comparable<KnapsackItem> {
    private int i_weight;
    private int i_value;

    public KnapsackItem(int weight, int value) {
        set(weight, value);
    }

    public void set(int weight, int value) {
        i_weight = weight;
        i_value = value;
    }

    public int getWeight() {
        return i_weight;
    }

    public int getValue() {
        return i_value;
    }

    @Override
    public int compareTo(KnapsackItem o) {
        return i_weight - o.i_weight;
    }
}
