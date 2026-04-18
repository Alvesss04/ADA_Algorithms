package Kahn;

// ─────────────────────────────────────────────
// Digraph.java  —  directed graph
// Nodes are plain ints: 0 .. numNodes-1
// Stored as adjacency lists of successors +
// a pre-computed inDegree[] array so
// graph.inDegree(v) is Θ(1)
// ─────────────────────────────────────────────
import java.util.ArrayList;
import java.util.List;

public class Digraph {
    private final int numNodes;
    private final List<Integer>[] adjOut;
    private final int[] inDeg;

    @SuppressWarnings("unchecked")
    public Digraph(int numNodes) {
        this.numNodes = numNodes;
        this.adjOut   = new ArrayList[numNodes];
        this.inDeg    = new int[numNodes];
        for (int v = 0; v < numNodes; v++)
            adjOut[v] = new ArrayList<>();
    }

    public void addEdge(int from, int to) {
        adjOut[from].add(to);
        inDeg[to]++;
    }

    public int numNodes() { return numNodes; }
    public int inDegree(int v) { return inDeg[v]; }
    public List<Integer> outAdjacentNodes(int v) { return adjOut[v]; }
}
