package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetworkFlowQuizTest {

    @Test
    public void test1() {

        Graph graph = new Graph(6);
        graph.setDirectedEdge(0, 1, 2);
        graph.setDirectedEdge(0, 2, 1);
        graph.setDirectedEdge(1, 3, 3);
        graph.setDirectedEdge(2, 3, 1);
        graph.setDirectedEdge(2, 4, 4);
        graph.setDirectedEdge(3, 5, 2);
        graph.setDirectedEdge(4, 5, 2);

        NetworkFlowQuiz maxFlow = new NetworkFlowQuiz();
        Set<Subgraph> augmentingPaths = maxFlow.getAugmentingPaths(graph, 0, 5);
        Set<String> actualPaths = new HashSet<>();
        for (var s : augmentingPaths) {
            actualPaths.add(s.toString());
        }

        Set<Subgraph> expectedPaths = new HashSet<>();
        {
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
        }

        Set<Subgraph> expectedPaths2 = new HashSet<>();
        {
            Subgraph path1 = new Subgraph();
            path1.addEdge(new Edge(0, 1, 2));
            path1.addEdge(new Edge(1, 3, 3));
            path1.addEdge(new Edge(3, 5, 2));
            expectedPaths2.add(path1);

            Subgraph path2 = new Subgraph();
            path2.addEdge(new Edge(0, 2, 1));
            path2.addEdge(new Edge(2, 3, 1));
            path2.addEdge(new Edge(3, 5, 2));
            expectedPaths2.add(path2);

            Subgraph path3 = new Subgraph();
            path3.addEdge(new Edge(0, 2, 1));
            path3.addEdge(new Edge(2, 4, 4));
            path3.addEdge(new Edge(4, 5, 2));
            expectedPaths2.add(path3);
        }

        assertEquals(expectedPaths2, expectedPaths);
        for (Subgraph s : augmentingPaths)
            System.out.println(s);

/*        for (Subgraph s: expectedPaths)
            System.out.println(s);*/
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

            assertEquals(5,5);

            Set<Subgraph> augmentingPaths = new HashSet<>(a.getAugmentingPaths(graph, source, target));

            System.out.println(augmentingPaths.size());

            for (Subgraph path : augmentingPaths) {
                System.out.println("Augmenting path:");
                for (Edge edge : path.getEdges()) {
                    System.out.println(edge.toString());
                }
            }

            System.out.println("--------------------");

            Graph graph2 = new Graph(6);
            graph2.setDirectedEdge(0, 1, 2);
            graph2.setDirectedEdge(0, 2, 1);
            graph2.setDirectedEdge(1, 3, 3);
            graph2.setDirectedEdge(2, 3, 1);
            graph2.setDirectedEdge(2, 4, 4);
            graph2.setDirectedEdge(3, 5, 2);
            graph2.setDirectedEdge(4, 5, 2);

            NetworkFlowQuiz maxFlow = new NetworkFlowQuiz();
            Set<Subgraph> augmentingPath = maxFlow.getAugmentingPaths(graph2, 0, 5);

            for (Subgraph path : augmentingPath) {
                System.out.println("Augmenting path:");
                for (Edge edge : path.getEdges()) {
                    System.out.println(edge.toString());
                }
            }

        }
    }


