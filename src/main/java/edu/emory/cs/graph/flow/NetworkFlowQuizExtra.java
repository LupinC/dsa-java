package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

/** @author Jinho D. Choi */
public class NetworkFlowQuizExtra {
    /**
     * Using breadth-first traverse.
     *
     * @param graph  a directed graph.
     * @param source the ource vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> augmentingPaths = new HashSet<>();
        Subgraph subgraph = new Subgraph();
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        visited.add(source);
        queue.add(source);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Edge edge : graph.getIncomingEdges(current)) {
                if (visited.contains(edge.getSource()) || edge.getWeight() == 0) {
                    continue;
                }

                visited.add(edge.getSource());
                subgraph.addEdge(edge);

                if (edge.getSource() == source && edge.getTarget() == target) {
                    augmentingPaths.add(new Subgraph(subgraph));
                } else {
                    queue.add(edge.getSource());
                }

                visited.remove(edge.getSource());
                subgraph.getEdges().remove(edge);
            }
        }

        return augmentingPaths;
    }
}