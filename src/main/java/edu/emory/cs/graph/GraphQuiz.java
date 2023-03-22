package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;

public class GraphQuiz extends Graph {
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }

    /** @return the total number of cycles in this graph. */
    public int numberOfCycles() {
        int n = size();
        int count = 0;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            // perform a depth-first search starting from vertex i
            count += dfs(0, 0, visited);
        }

        return count;
    }

    private int dfs(int start, int current, boolean[] visited) {
        int count = 0;
        visited[current] = true;

        for (Edge edge : getIncomingEdges(current)) {
            int neighbor = edge.getSource();

            // ignore self-loops and edges that go back to the starting vertex
            if (neighbor == start) {
                count++;

            } else if (!visited[neighbor]) {
                count += dfs(start, neighbor, visited);
            }
        }

        visited[current] = false;
        return count;
    }


}
