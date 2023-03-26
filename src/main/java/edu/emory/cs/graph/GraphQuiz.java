package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphQuiz extends Graph {
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }

    /** @return the total number of atomic cycles in this graph. */
    public int numberOfCycles() {
        int n = incoming_edges.size();
        boolean[] visited = new boolean[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                Stack<Integer> stack = new Stack<>();
                stack.push(i);

                while (!stack.isEmpty()) {
                    int node = stack.pop();
                    visited[node] = true;

                    for (Edge neighbor : incoming_edges.get(node)) {
                        int src = neighbor.getSource();
                        if (!visited[src]) {
                            stack.push(src);
                        } else if (visited[src] && src != node && isAnAtomicCycle(node, src)) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    private boolean isAnAtomicCycle(int node1, int node2) {
        Set<Integer> nodeSet = new HashSet<>();
        nodeSet.add(node1);
        nodeSet.add(node2);
        Stack<Integer> stack = new Stack<>();
        stack.push(node1);

        while (!stack.isEmpty()) {
            int node = stack.pop();

            for (Edge neighbor : incoming_edges.get(node)) {
                int src = neighbor.getSource();
                if (nodeSet.contains(src)) {
                    return true;
                } else {
                    nodeSet.add(src);
                    stack.push(src);
                }
            }
        }

        return false;
    }





}

