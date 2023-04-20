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
        Subgraph initialPath = new Subgraph();
        MaxFlow mf = new MaxFlow(graph);
        initialPath.getVertices().add(source);
        queue.add(initialPath);

        while (!queue.isEmpty()) {
            Subgraph currentPath = queue.poll();
            int lastVertex = currentPath.getVertices().stream().mapToInt(Integer::intValue).max().orElse(-1);
            if (lastVertex == target) {
                augmentingPaths.add(currentPath);
                continue;
            }
            for (Edge edge : graph.getOutgoingEdges().get(lastVertex)) {
                int nextVertex = edge.getTarget();
                if (!currentPath.contains(nextVertex)&&mf.getResidual(edge)>0) {
                    Subgraph newPath = new Subgraph(currentPath);
                    newPath.addEdge(edge);
                    queue.add(newPath);
                }
            }
        }

        return RemoveDup(augmentingPaths);

    }

    protected Set<Subgraph> RemoveDup(Set<Subgraph> aug)
    {
        Set<Subgraph> result = new HashSet<>();
        HashSet<String> res2 = new HashSet<>();
        for (Subgraph s: aug) {
            List<Edge> allEdges = new ArrayList<>(s.getEdges());
            for(int i = allEdges.size()-1; i>0; i--) {
                if(allEdges.get(i).getSource()!=allEdges.get(i-1).getTarget())
                    allEdges.remove(i-1);
            }
            Subgraph a = new Subgraph();
            for(Edge e: allEdges) {
                a.addEdge(e);
            }
            if(!res2.contains(a.toString())) {
                res2.add(a.toString());
                result.add(a);
            }
        }
        return result;
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

        NetworkFlowQuizExtra a = new NetworkFlowQuizExtra();

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