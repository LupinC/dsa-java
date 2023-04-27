package edu.emory.cs.dynamic.hanoi;

import java.util.ArrayList;
import java.util.List;

public class HanoiRecursive extends Hanoi {
    @Override
    public List<String> solve(int n, char source, char intermediate, char destination) {
        List<String> list = new ArrayList<>();
        int[] count = {0};
        solve(list, n, source, intermediate, destination, count);
        System.out.println(count[0]);
        return list;
    }

    private void solve(List<String> list, int n, char source, char intermediate, char destination, int[] count) {
        count[0]++;
        if (n == 0) return;

        //Move all plates from 'source' to 'intermediate' via 'destination' as medium
        solve(list, n - 1, source, destination, intermediate, count);

        //Record the step
        list.add(getKey(n, source, destination));

        //Move all plates from 'intermediate' to 'destination' via 'source' as medium
        solve(list, n - 1, intermediate, source, destination, count);
    }
}
