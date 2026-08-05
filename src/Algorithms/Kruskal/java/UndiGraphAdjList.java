package Kruskal.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple adjacency-list undirected graph.
 * Nodes are integers 0 … numNodes-1.
 */
public class UndiGraphAdjList<L> implements UndiGraph<L> {

    private final int numNodes;
    private final List<Edge<L>> edges;       // all edges (no duplicates)
    private final List<List<Edge<L>>> adj;   // per-node adjacency lists

    public UndiGraphAdjList(int numNodes) {
        this.numNodes = numNodes;
        this.edges    = new ArrayList<>();
        this.adj      = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++)
            adj.add(new ArrayList<>());
    }

    /**
     * Adds an undirected edge between u and v with the given label.
     */
    public void addEdge(int u, int v, L label) {
        Edge<L> e = new EdgeClass<>(label, u, v);
        edges.add(e);
        adj.get(u).add(e);
        adj.get(v).add(e);
    }

    @Override
    public int numNodes() { return numNodes; }

    @Override
    public int numEdges() { return edges.size(); }

    @Override
    public Iterable<Edge<L>> edges() { return edges; }
}
