package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;

import java.util.*;

public class MST3 implements MSTAll{

    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        List<SpanningTree> forest = new ArrayList<>();
        Map<Double,List<Edge>> collectedEdges = new HashMap<>();
        Map<Double,List<Edge>> minTreeEdge = new HashMap<>();
        Map<Double,List<List<Edge>>> allChoices = new HashMap<>();


        for(Edge e: graph.getAllEdges())
        {
            if(!collectedEdges.containsKey(e.getWeight()))
            {
                collectedEdges.put(e.getWeight(), new ArrayList<>());
                collectedEdges.get(e.getWeight()).add(e);
            }
            else
            {
                collectedEdges.get(e.getWeight()).add(e);
            }
        }

        SpanningTree tree1 = getMinimumSpanningTreeK(graph);


        for(Edge e: tree1.getEdges())
        {
            if(minTreeEdge.containsKey(e.getWeight()))
            {
                minTreeEdge.get(e.getWeight()).add(e);
            }
            else {
                minTreeEdge.put(e.getWeight(),new ArrayList<>());
                minTreeEdge.get(e.getWeight()).add(e);
            }
        }

        SpanningTree tree2 = getMinimumSpanningTreeK(graph);
        List<Double> weights = new ArrayList<>(minTreeEdge.keySet());
        List<List<Edge>> all = new ArrayList<>();

        for(double w: weights)
        {
            all.add(collectedEdges.get(w));
        }

        all = getAllCombinations(all);

        for(List<Edge> edges: all)
        {
            SpanningTree tree = new SpanningTree();
            for(int i = 0; i < edges.size(); i++)
            {
                tree.addEdge(edges.get(i));
            }
            if(tree.getCycles().isEmpty())
                forest.add(tree);
        }


            for (int i = 0; i < forest.size()-1;i++)
            {
                if(forest.get(i).compareTo(forest.get(i+1))!=0)
                {
                    forest.remove(i);
                    i = i -1;

                }
            }


        return forest;

    }


    public SpanningTree getMinimumSpanningTreeK(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(graph.getAllEdges());
        DisjointSet forest = new DisjointSet(graph.size());
        SpanningTree tree = new SpanningTree();

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

            if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {
                tree.addEdge(edge);

                // a spanning tree is found
                if (tree.size() + 1 == graph.size()) break;
                // merge forests
                forest.union(edge.getTarget(), edge.getSource());
            }
        }

        return tree;
    }

    public List<List<Edge>> getAllCombinations(List<List<Edge>> lists) {
        List<List<Edge>> result = new ArrayList<>();
        if (lists.size() == 0) {
            result.add(new ArrayList<>());
            return result;
        }
        List<Edge> firstList = lists.get(0);
        List<List<Edge>> remainingLists = getAllCombinations(lists.subList(1, lists.size()));
        for (Edge element : firstList) {
            for (List<Edge> remainingList : remainingLists) {
                List<Edge> combination = new ArrayList<>();
                combination.add(element);
                combination.addAll(remainingList);
                result.add(combination);
            }
        }
        return result;
    }
}
