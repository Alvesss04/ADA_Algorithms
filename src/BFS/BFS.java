package BFS;

// ─────────────────────────────────────────────
// BFS.java  —  Breadth-First Search
// Matches the slides exactly (p.20-21):
//   found[v]  — bool array, plain int index
//   waiting   — FIFO Queue<Integer>
// ─────────────────────────────────────────────
public class BFS {

    /**
     * BFS traversal over the whole graph (slide p.20).
     * Handles disconnected graphs — calls bfsExplore
     * for every unvisited node.
     *
     * Time:  Θ(|V| + |A|)  — adjacency-list graph
     * Space: Θ(|V|)
     */
    public static void bfsTraversal(Digraph graph) {
        boolean[] found = new boolean[graph.numNodes()];

        for (int v = 0; v < graph.numNodes(); v++)
            found[v] = false;

        for (int v = 0; v < graph.numNodes(); v++)
            if (!found[v])
                bfsExplore(graph, found, v);
    }

    /**
     * BFS from a single root node (slide p.21).
     * Visits all nodes reachable from root in breadth-first order.
     *
     * @param root the starting node
     */
    public static void bfsExplore(Digraph graph, boolean[] found, int root) {
        Queue<Integer> waiting = new Queue<Integer>();

        waiting.enqueue(root);
        found[root] = true;

        do {
            int node = waiting.dequeue();

            // PROCESS(node) — replace with real logic as needed
            System.out.print(node + " ");

            for (int v : graph.outAdjacentNodes(node))
                if (!found[v]) {
                    waiting.enqueue(v);
                    found[v] = true;
                }
        } while (!waiting.isEmpty());
    }
}
