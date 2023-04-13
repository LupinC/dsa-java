package edu.emory.cs.graph.span;


import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;

import java.util.*;

public class MSTAllHW implements MSTAll {

    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        List<Edge> allEdges = new ArrayList<>(graph.getAllEdges());
        allEdges.sort(Comparator.comparingDouble(Edge::getWeight));
        List<SpanningTree> forest = new ArrayList<>();

        SpanningTree dummy = getMinimumSpanningTree(graph);
        if(graph.size()==1)
        {
            forest.add(dummy);
            return forest;
        }
        findSpanningTrees(allEdges, graph.size(), new SpanningTree(), 0, new UnionFind(graph.size()), forest);
        List<String> check = new ArrayList<>();
        for (int i = forest.size()-1; i >= 0; i--)
        {
                //System.out.println(forest.get(i));
                //System.out.println();
                //System.out.println(forest.get(i).getUndirectedSequence());
                //System.out.println();

            String t = forest.get(i).getUndirectedSequence();
            if(!check.contains(t))
            {
                check.add(t);
            }
            else
            {
                forest.remove(i);
            }
        }

        return forest;
    }

    protected void remove(SpanningTree tree, int i)
    {
        tree.getEdges().remove(i);
    }


    private void findSpanningTrees(List<Edge> edges, int vertex, SpanningTree currentTree, int edgeIndex, UnionFind uf, List<SpanningTree> forest) {

        if (currentTree.size() == vertex - 1 && visitedAll(currentTree, vertex)) {
            forest.add(new SpanningTree(currentTree));
            return;
        }

        for (int i = edgeIndex; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if (uf.union(edge.getSource(), edge.getTarget())) {
                currentTree.addEdge(edge);
                UnionFind ufNext = new UnionFind(uf);
                findSpanningTrees(edges, vertex, currentTree, i + 1, ufNext, forest);
                remove(currentTree,currentTree.size()-1);
                uf.undo();
            }
        }
    }

    static class UnionFind {
        int[] parent;
        int[] rank;
        List<int[]> history;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            history = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public UnionFind(UnionFind uf) {
            this.parent = uf.parent.clone();
            this.rank = uf.rank.clone();
            this.history = new ArrayList<>(uf.history);
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return false;
            }

            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
                history.add(new int[]{rootY, rootX});
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
                history.add(new int[]{rootX, rootY});
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
                history.add(new int[]{rootY, rootX});
            }

            return true;
        }

        public void undo() {
            if (!history.isEmpty()) {
                int[] lastOperation = history.remove(history.size() - 1);
                int x = lastOperation[0];
                int y = lastOperation[1];

                parent[x] = x;

                if (rank[x] == rank[y]) {
                    rank[y]--;
                }
            }
        }
    }

    public SpanningTree getMinimumSpanningTree(Graph graph) {
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

    private boolean visitedAll(SpanningTree currentTree, int vertices)
    {
        List<Integer> src = new ArrayList<>();
        List<Integer> dest = new ArrayList<>();

        for(Edge e: currentTree.getEdges()){
            src.add(e.getSource());
            dest.add(e.getTarget());}

        List<Integer> check = new ArrayList<>(src);

        for(int a: dest)
        {
            if(!check.contains(a))
            {
                check.add(a);
            }
        }

        if(check.size()!=vertices)
        {
            return false;
        }
        else
            return true;


    }
}

