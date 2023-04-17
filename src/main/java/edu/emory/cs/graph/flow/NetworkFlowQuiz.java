package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

/** @author Jinho D. Choi */
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
        visited.add(source);
        getAugmentingPathsHelper(graph, augmentingPaths, subgraph, visited, source, target);
        return augmentingPaths;
    }

    private void getAugmentingPathsHelper(Graph graph, Set<Subgraph> augmentingPaths, Subgraph subgraph, Set<Integer> visited, int current, int target) {
        if (current == target) {
            augmentingPaths.add(new Subgraph(subgraph));
        } else {
            for (Edge edge : graph.getOutgoingEdges().get(current)) {
                if (visited.contains(edge.getTarget()) || edge.getWeight() == 0) {
                    continue;
                }
                subgraph.addEdge(edge);
                visited.add(edge.getTarget());
                getAugmentingPathsHelper(graph, augmentingPaths, subgraph, visited, edge.getTarget(), target);
                subgraph.getEdges().remove(edge);
                visited.remove(edge.getTarget());
            }
        }
    }



    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.setDirectedEdge(0, 1, 16);
        graph.setDirectedEdge(0, 2, 13);
        graph.setDirectedEdge(1, 2, 10);
        graph.setDirectedEdge(2, 1, 4);
        graph.setDirectedEdge(1, 3, 12);
        graph.setDirectedEdge(2, 4, 14);
        graph.setDirectedEdge(3, 2, 9);
        graph.setDirectedEdge(3, 5, 20);
        graph.setDirectedEdge(4, 3, 7);
        graph.setDirectedEdge(4, 5, 4);

        int source = 0;
        int target = 5;

        NetworkFlowQuiz a = new NetworkFlowQuiz();

        Set<Subgraph> augmentingPaths = new HashSet<>(a.getAugmentingPaths(graph, source, target));

        System.out.println(augmentingPaths.size());

        for (Subgraph path : augmentingPaths) {
            System.out.println("Augmenting path:");
            for (Edge edge : path.getEdges()) {
                System.out.println(edge.toString());
            }
        }

    }
}