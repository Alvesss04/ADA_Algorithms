package Kahn;

// ─────────────────────────────────────────────
// Kahn.java  —  Kahn's algorithm
// Nodes are ints — matches the slides exactly:
//   inDegree[v] = graph.inDegree(v)
//   if (inDegree[v] == 0) ready.add(v)
// ─────────────────────────────────────────────
public class Kahn {

    /**
     * Topological sort (slides p.8-9)
     *
     * Returns an int[] with nodes in topological order.
     * Time:  Θ(|V| + |A|)   — adjacency-list graph
     * Space: Θ(|V|)
     */
    public static int[] topologicalSort(Digraph graph) {
        int[] permutation = new int[graph.numNodes()];
        int permSize = 0;
        Bag<Integer> ready = new Bag<Integer>();
        int[] inDegree = new int[graph.numNodes()];

        // First loop — seed in-degrees and the ready bag
        for (int v = 0; v < graph.numNodes(); v++) {
            inDegree[v] = graph.inDegree(v);
            if (inDegree[v] == 0)
                ready.add(v);
        }

        // Second loop — process nodes in topological order
        do {
            int node = ready.remove();
            permutation[permSize++] = node;
            for (int v : graph.outAdjacentNodes(node)) {
                inDegree[v]--;
                if (inDegree[v] == 0)
                    ready.add(v);
            }
        } while (!ready.isEmpty());
        return permutation;
    }

    /**
     * Acyclicity test (slides p.13-14)
     *
     * Returns true  → acyclic (valid DAG)
     * Returns false → has at least one cycle
     *
     * Cycle nodes never reach inDegree 0,
     * so numProcNodes ends up < |V|.
     *
     * Time:  O(|V| + |A|)   — adjacency-list graph
     * Space: Θ(|V|)
     */
    public static boolean isAcyclic(Digraph graph) {
        int numProcNodes = 0;
        Bag<Integer> ready = new Bag<Integer>();
        int[] inDegree = new int[graph.numNodes()];

        for (int v = 0; v < graph.numNodes(); v++) {
            inDegree[v] = graph.inDegree(v);
            if (inDegree[v] == 0)
                ready.add(v);
        }

        while (!ready.isEmpty()) {
            int node = ready.remove();
            numProcNodes++;

            for (int v : graph.outAdjacentNodes(node)) {
                inDegree[v]--;
                if (inDegree[v] == 0)
                    ready.add(v);
            }
        }

        return numProcNodes == graph.numNodes();
    }
}

