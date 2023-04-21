package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;
import edu.emory.cs.set.DisjointSet;

import java.util.*;

public class NetworkFlowQuizExtra {
    /**
     * Using breadth-first traverse.
     *
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> augmentingPaths = new HashSet<>();
        Queue<Subgraph> queue = new LinkedList<>();
        Queue<Integer> visited = new LinkedList<>();
        Subgraph initialPath = new Subgraph();
        queue.add(initialPath);
        visited.add(source);

        while (!queue.isEmpty()) {
            Subgraph currentPath = queue.poll();
            int src = visited.poll();
            if (src == target) {
                augmentingPaths.add(currentPath);
                continue;
            }
            for (Edge edge : graph.getOutgoingEdges().get(src)) {
                if (!currentPath.contains(edge.getTarget())) {
                    Subgraph newPath = new Subgraph(currentPath);
                    newPath.addEdge(edge);
                    visited.add(edge.getTarget());
                    queue.add(newPath);
                }
            }
        }
        return augmentingPaths;
    }

/*    public static void main(String[] args) {
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

        NetworkFlowQuizExtra a = new NetworkFlowQuizExtra();

        Set<Subgraph> augmentingPaths = new HashSet<>(a.getAugmentingPaths(graph, source, target));

        System.out.println(augmentingPaths.size());

        for (Subgraph path : augmentingPaths) {
            System.out.println("Augmenting path:");
            for (Edge edge : path.getEdges()) {
                System.out.println(edge.toString());
            }
        }

    }*/
}