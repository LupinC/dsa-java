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
        return RemoveDup(augmentingPaths);
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

    protected Set<Subgraph> RemoveDup(Set<Subgraph> aug)
    {
        Set<Subgraph> result = new HashSet<>();
        HashSet<String> res2 = new HashSet<>();

        for (Subgraph s: aug)
        {
            List<Edge> allEdges = new ArrayList<>(s.getEdges());

            for(int i = allEdges.size()-1; i>0; i--)
            {
                if(allEdges.get(i).getSource()!=allEdges.get(i-1).getTarget())
                    allEdges.remove(i-1);
            }

            Subgraph a = new Subgraph();

            for(Edge e: allEdges)
            {
                a.addEdge(e);
            }

            if(!res2.contains(a.toString()))
            {
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