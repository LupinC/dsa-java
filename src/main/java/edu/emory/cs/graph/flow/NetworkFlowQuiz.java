package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

public class NetworkFlowQuiz {
    /**
     * Using depth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> augmentingPaths = new HashSet<>();
        Subgraph subgraph = new Subgraph();
        Set<Integer> visited = new HashSet<>();
        MaxFlow mf = new MaxFlow(graph);
        visited.add(source);
        getAugmentingPathsHelper(graph, mf, augmentingPaths, subgraph, visited, source, target);
        return augmentingPaths;
    }

    private void getAugmentingPathsHelper(Graph graph, MaxFlow mf, Set<Subgraph> augmentingPaths, Subgraph subgraph, Set<Integer> visited, int current, int target) {
        if (current == target) {
            augmentingPaths.add(new Subgraph(subgraph));
        } else {
            for (Edge edge : graph.getOutgoingEdges().get(current)) {
                if (visited.contains(edge.getTarget()) || mf.getResidual(edge)<0) {
                    continue;
                }
                subgraph.addEdge(edge);
                visited.add(edge.getTarget());
                getAugmentingPathsHelper(graph, mf, augmentingPaths, subgraph, visited, edge.getTarget(), target);
                subgraph.getEdges().remove(edge);
                visited.remove(edge.getTarget());
            }
        }
    }
}