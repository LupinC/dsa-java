package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

public class NetworkFlowQuizExtraTest {
    public static void main(String[] args) {


        Graph graph = new Graph(6);
        graph.setUndirectedEdge(0, 1, 2);
        graph.setUndirectedEdge(0, 2, 1);
        graph.setUndirectedEdge(1, 3, 3);
        graph.setUndirectedEdge(2, 3, 1);
        graph.setUndirectedEdge(2, 4, 4);
        graph.setUndirectedEdge(3, 5, 2);
        graph.setUndirectedEdge(4, 5, 2);

        NetworkFlowQuizExtra maxFlow = new NetworkFlowQuizExtra();
        Set<Subgraph> augmentingPaths = maxFlow.getAugmentingPaths(graph, 0, 5);

        Set<Subgraph> expectedPaths = new HashSet<>();
        Subgraph path1 = new Subgraph();
        path1.addEdge(new Edge(0, 1, 2));
        path1.addEdge(new Edge(1, 3, 3));
        path1.addEdge(new Edge(3, 5, 2));
        expectedPaths.add(path1);

        Subgraph path2 = new Subgraph();
        path2.addEdge(new Edge(0, 2, 1));
        path2.addEdge(new Edge(2, 3, 1));
        path2.addEdge(new Edge(3, 5, 2));
        expectedPaths.add(path2);

        Subgraph path3 = new Subgraph();
        path3.addEdge(new Edge(0, 2, 1));
        path3.addEdge(new Edge(2, 4, 4));
        path3.addEdge(new Edge(4, 5, 2));
        expectedPaths.add(path3);

        //assertEquals(expectedPaths, augmentingPaths);
        System.out.println(augmentingPaths.size());

        for(Subgraph s: augmentingPaths)
            System.out.println(s);
    }
}
