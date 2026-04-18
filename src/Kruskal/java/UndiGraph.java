package Kruskal.java;

/**
 * Interface for an undirected weighted graph.
 * Nodes are identified by integers 0, 1, …, numNodes()-1.
 */
public interface UndiGraph<L> {

    /** Number of nodes (vertices). */
    int numNodes();

    /** Number of edges (arcs). */
    int numEdges();

    /** Iterable over all edges in the graph. */
    Iterable<Edge<L>> edges();
}
