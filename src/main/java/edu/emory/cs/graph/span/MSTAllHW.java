package edu.emory.cs.graph.span;

import java.util.*;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;

public class MSTAllHW implements MSTAll {
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        if (graph.size()==1)
        {   List<SpanningTree> res = new ArrayList<>();
            SpanningTree empty = new SpanningTree();
            res.add(empty);
            return res;
        }

        List<Edge> allEdges = new ArrayList<>(graph.getAllEdges());
        if(!isCompleteGraph(graph) && graph.size() > 2)
            allEdges.sort(Comparator.comparingDouble(Edge::getWeight));
        else
            return generateAllSpanningTrees(graph, graph.size());

        List<SpanningTree> forest = new ArrayList<>();

        SpanningTree dummy = getMinimumSpanningTree(graph);
        double Weight = dummy.getTotalWeight();
        findSpanningTrees(allEdges, graph.size(), new SpanningTree(), 0, new UnionSet(graph.size()), forest, Weight, 0);
        List<String> check = new ArrayList<>();
        for (int i = forest.size() - 1; i >= 0; i--) {
            String t = forest.get(i).getUndirectedSequence();
            if (!check.contains(t)) {
                check.add(t);
            } else {
                forest.remove(i);
            }
        }
        //System.out.println(forest);
        //System.out.println(forest.size());
        return forest;
    }

    private void findSpanningTrees(List<Edge> edges, int vertex, SpanningTree currentTree, int edgeIndex, UnionSet us,
                                   List<SpanningTree> forest, double Weight, double totalWeight) {

        double thisWeight;

        if (totalWeight > Weight) {
            return;
        }

        if (currentTree.size() == vertex - 1 && visitedAll(currentTree, vertex)) {
            forest.add(new SpanningTree(currentTree));
            return;
        }

        for (int i = edgeIndex; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if (us.union(edge.getSource(), edge.getTarget())) {
                thisWeight = totalWeight + edge.getWeight();
                if(thisWeight <= Weight) {
                    currentTree.addEdge(edge);
                    UnionSet usNext = new UnionSet(us);
                    totalWeight += edge.getWeight();
                    findSpanningTrees(edges, vertex, currentTree, i + 1, usNext, forest, Weight, totalWeight);
                    totalWeight -= currentTree.getEdges().remove(currentTree.size() - 1).getWeight();
                    us.undo();
                }
            }
        }
    }

    public boolean isCompleteGraph(Graph graph) {
        int n = graph.size();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (!hasEdge(graph, i, j)) {
                    return false;
                }
            }
        }

        double weight = 0;
        for(int i = 0; i < graph.getAllEdges().size(); i++)
        {
            Edge edge = graph.getAllEdges().get(i);
            weight = weight + edge.getWeight();

        }
        return weight / (graph.size() - 1) == graph.size();
    }

    private boolean visitedAll(SpanningTree currentTree, int vertices) {
        HashSet<Integer> src = new HashSet<>();
        HashSet<Integer> dest = new HashSet<>();

        for (Edge e : currentTree.getEdges()) {
            src.add(e.getSource());

            src.add(e.getTarget());
        }

        return src.size() == vertices;

    }

    public List<SpanningTree> generateAllSpanningTrees(Graph graph, int n) {
        List<SpanningTree> forest = new ArrayList<>();

        if(n <= 2)
        {
            SpanningTree tree = getMinimumSpanningTree(graph);
            forest.add(tree);
            return forest;
        }

        Edge edge;

        for (int i = 0; i < (int)Math.pow(n, n-2); i++) {
            int[] prufer = getPruferSequence(n, i);
            SpanningTree tree = constructSpanningTree(prufer);
            boolean pvisted = false;
            edge = new Edge(0, 0);
            for(Edge e: tree.getEdges())
            {
                if (e.getSource() == e.getTarget())
                {
                    edge = new Edge(e);
                    pvisted = true;
                    break;
                }
            }
            if(pvisted) {
                SpanningTree tree2 = new SpanningTree();
                List<Edge> edges = tree.getEdges();
                for(Edge e : edges)
                {
                    if(!(e.getTarget() == edge.getTarget() && e.getSource() == edge.getSource()))
                    {
                        tree2.addEdge(e);
                    }
                }
                forest.add(tree2);
            }
            else
            {
                forest.add(tree);
            }
        }
        //System.out.println(forest);
        return forest;
    }

    private int[] getPruferSequence(int n, int k) {
        int[] prufer = new int[n-2];
        for (int i = n-3; i >= 0; i--) {
            prufer[i] = k % n + 1;
            k /= n;
        }
        return prufer;
    }

    private SpanningTree constructSpanningTree(int[] prufer) {
        int n = prufer.length + 2;
        int[] degree = new int[n+1];
        for (int i = 0; i < n-2; i++)
        {
            degree[prufer[i]]++;
        }

        int[] ptr = new int[n+1];
        for (int i = 1; i <= n; i++)
        {
            ptr[i] = 1;
        }

        SpanningTree tree = new SpanningTree();
        for (int i = 0; i < n-2; i++) {
            int v = prufer[i];
            int u = 0;
            for (int j = ptr[v]; j <= n; j++) {
                if (degree[j] == 0) {
                    u = j;
                    ptr[v] = j+1;
                    break;
                }
            }
            degree[v]--;
            degree[u]--;
            tree.addEdge(new Edge(v-1, u-1, 1));
        }

        for (int i = 1; i <= n; i++) {
            if (degree[i] == 0) {
                int u = 0;
                for (int j = ptr[i]; j <= n; j++) {
                    if (degree[j] == 0) {
                        u = j;
                        ptr[i] = j+1;
                    }
                }
                degree[i]--;
                degree[u]--;
                tree.addEdge(new Edge(i -1, u-1, 1));

            }
        }
        return tree;
    }

    public void remove(int i, SpanningTree currentTree) {
        currentTree.getEdges().remove(i);
    }

    static class UnionSet {
        int[] parent;
        int[] rank;
        List<int[]> history;

        public UnionSet(int size) {
            parent = new int[size];
            rank = new int[size];
            history = new ArrayList<>();
            Arrays.fill(rank, 1);

            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public UnionSet(UnionSet us) {
            this.parent = us.parent.clone();
            this.rank = us.rank.clone();
            this.history = new ArrayList<>(us.history);
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
                history.add(new int[] { rootY, rootX });
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
                history.add(new int[] { rootX, rootY });
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
                history.add(new int[] { rootY, rootX });
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

    public boolean hasEdge(Graph graph, int u, int v) {
        for (Edge edge : graph.getAllEdges()) {
            if ((edge.getSource() == u && edge.getTarget() == v) || (edge.getSource() == v && edge.getTarget() == u)) {
                return true;
            }
        }
        return false;
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
                if (tree.size() + 1 == graph.size())
                    break;
                // merge forests
                forest.union(edge.getTarget(), edge.getSource());
            }
        }

        return tree;
    }


}
