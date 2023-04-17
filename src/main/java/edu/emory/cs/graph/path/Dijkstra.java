package edu.emory.cs.graph.path;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class Dijkstra extends AStar {
    /*
     * Dijkstra's algorithm is a more specific A* algorithm with the heuristic of 0
     */
    @Override
    protected double heuristic(int source, int target) {
        return 0;
    }
}