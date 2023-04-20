package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetworkFlowQuizExtraTest {
    public static void main(String[] args) {


        Graph graph = new Graph(6);
        graph.setDirectedEdge(0, 1, 4);
        graph.setDirectedEdge(0, 2, 2);
        graph.setDirectedEdge(1, 3, 3);
        graph.setDirectedEdge(3, 5, 2);
        graph.setDirectedEdge(3, 2, 1);
        graph.setDirectedEdge(2, 4, 3);
        graph.setDirectedEdge(2, 3, 2);
        graph.setDirectedEdge(4, 5, 4);

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
