package DFS;

// ─────────────────────────────────────────────
// Digraph.java  —  directed graph
// Nodes are plain ints: 0 .. numNodes-1
// Stored as adjacency lists of successors
// so dfsTraversal runs in Θ(|V| + |A|)
// ─────────────────────────────────────────────
import java.util.ArrayList;
import java.util.List;

public class Digraph {
    private final int numNodes;
    private final List<Integer>[] adjOut;   // adjOut[v] = successors of v

    @SuppressWarnings("unchecked")
    public Digraph(int numNodes) {
        this.numNodes = numNodes;
        this.adjOut  = new ArrayList[numNodes];
        for (int v = 0; v < numNodes; v++)
            adjOut[v] = new ArrayList<>();
    }

    /** Add directed edge from → to */
    public void addEdge(int from, int to) {
        adjOut[from].add(to);
    }

    public int numNodes() { return numNodes; }
    public List<Integer> outAdjacentNodes(int v) { return adjOut[v]; }
}
