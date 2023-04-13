package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.*;

public class MSTAllHW1 implements MSTAll {

    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        List<Edge> allEdge = new ArrayList<>(graph.getAllEdges());
        allEdge.sort(Comparator.comparingDouble(Edge::getWeight));
        List<SpanningTree> forest = new ArrayList<>();
        findSpanningTrees(allEdge, graph.size(), new SpanningTree(), 0, new UnionFind(graph.size()), forest);

        return forest;
    }

    protected static void remove(SpanningTree tree, int i)
    {
        tree.getEdges().remove(i);
    }

    private static void findSpanningTrees(List<Edge> edges, int vertex, SpanningTree currentTree, int edgeIndex, UnionFind uf, List<SpanningTree> forest) {

        if (currentTree.size() == vertex-1) {
            forest.add(new SpanningTree(currentTree));
            return;
        }

        for (int i = edgeIndex; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if (uf.union(edge.getSource(), edge.getTarget())) {
                currentTree.addEdge(edge);
                findSpanningTrees(edges, vertex, currentTree, i + 1, uf, forest);
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

        public UnionFind(MSTAllHW.UnionFind uf) {
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

}
