package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphQuiz extends Graph {
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }

    /** @return the total number of atomic cycles in this graph. */
    public int numberOfCycles() {
        int[] count = new int[1];
        boolean[] visited = new boolean[size()];
        boolean[] marked = new boolean[size()];

        Set<List<Integer>> cycles = new HashSet<>();

        for (int i = 0; i < size(); i++) {
            if (!visited[i]) {
                Deque<Integer> stack = new ArrayDeque<>();
                stack.push(i);
                dfs(i, visited, marked, stack, count, cycles);
            }
        }

        return count[0];
    }

    private void dfs(int vertex, boolean[] visited, boolean[] marked, Deque<Integer> stack, int[] count,
                     Set<List<Integer>> cycles) {
        visited[vertex] = true;
        marked[vertex] = true;

        for (Edge edge : getOutgoingEdges().get(vertex)) {

            if (!visited[edge.getTarget()]) {
                stack.push(edge.getTarget());
                dfs(edge.getTarget(), visited, marked, stack, count, cycles);
            }
            else if (marked[edge.getTarget()]) {

                List<Integer> cycle = new ArrayList<>();

                for (int v : stack) {
                    cycle.add(v);
                    if (v == edge.getTarget()) {
                        break;
                    }
                }

                List<List<Integer>> Cycles = new ArrayList<>();

                for (int i = 0; i < cycle.size(); i++) {
                    Cycles.add(smallestIndex(cycle));
                }

                boolean atomic = false;

                for (int i = 0; i < Cycles.size();i++)
                {
                    if(cycles.contains(Cycles.get(i))){
                        atomic = true;
                        break;
                    }
                }

                if (!atomic) {
                    cycles.add(cycle);
                    count[0]++;
                }
            }
        }

        marked[vertex] = false;
        visited[vertex] = false;
        stack.pop();
    }

    public List<Integer> smallestIndex(List<Integer> cycle) {

        int minIndex = 0;

        for (int i = 1; i < cycle.size(); i++) {
            if (cycle.get(i) < cycle.get(minIndex)) {
                minIndex = i;
            }
        }

        Collections.rotate(cycle, -minIndex);

        return cycle;
    }
}

