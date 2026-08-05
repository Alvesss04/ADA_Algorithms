package DFS;

// ─────────────────────────────────────────────
// DFS.java  —  Depth-First Search
//
// Two versions, both matching the slides exactly:
//
//  RECURSIVE  (slides p.23-24)
//    — uses the call stack implicitly
//    — array is called "processed"
//    — mark on entry (before iterating successors)
//    — Space: O(|V|) call stack
//
//  ITERATIVE  (slides p.47-48)
//    — uses an explicit Stack<Integer>
//    — array is called "processed"
//    — mark on pop (not on push!) because the same
//      node can be pushed multiple times before
//      it is ever popped
//    — Space: O(|A|) stack (can hold duplicates)
// ─────────────────────────────────────────────
public class DFS {

    // ── RECURSIVE VERSION ────────────────────

    /**
     * DFS traversal — recursive (slide p.23).
     * Handles disconnected graphs.
     *
     * Time:  Θ(|V| + |A|)  — adjacency-list graph
     * Space: Θ(|V|)         — call stack depth
     */
    public static void dfsTraversalRec(Digraph graph) {
        boolean[] processed = new boolean[graph.numNodes()];

        for (int v = 0; v < graph.numNodes(); v++)
            processed[v] = false;

        for (int v = 0; v < graph.numNodes(); v++)
            if (!processed[v])
                dfsExploreRec(graph, processed, v);
    }

    /**
     * Recursive DFS from a single root (slide p.24).
     * Processes root immediately, then recurses into
     * every unvisited successor.
     */
    public static void dfsExploreRec(Digraph graph, boolean[] processed, int root) {
        System.out.print(root + " ");
        processed[root] = true;

        for (int v : graph.outAdjacentNodes(root))
            if (!processed[v])
                dfsExploreRec(graph, processed, v);
    }

    // ── ITERATIVE VERSION ────────────────────

    /**
     * DFS traversal — iterative (slide p.47).
     * Handles disconnected graphs.
     *
     * Time:  Θ(|V| + |A|)  — adjacency-list graph
     * Space: O(|V| + |A|)   — explicit stack may hold duplicates
     */
    public static void dfsTraversalIter(Digraph graph) {
        boolean[] processed = new boolean[graph.numNodes()];

        for (int v = 0; v < graph.numNodes(); v++)
            processed[v] = false;

        for (int v = 0; v < graph.numNodes(); v++)
            if (!processed[v])
                dfsExploreIter(graph, processed, v);
    }

    /**
     * Iterative DFS from a single root (slide p.48).
     *
     * Key difference from BFS: uses a Stack instead of a Queue,
     * and marks nodes as processed on POP (not on push), because
     * the same node can be pushed multiple times before being popped.
     */
    public static void dfsExploreIter(Digraph graph, boolean[] processed, int root) {
        Stack<Integer> foundUnprocessed = new Stack<Integer>();

        foundUnprocessed.push(root);

        do {
            int node = foundUnprocessed.pop();

            if (!processed[node]) {
                // PROCESS(node) — replace with real logic as needed
                System.out.print(node + " ");
                processed[node] = true;

                for (int v : graph.outAdjacentNodes(node))
                    if (!processed[v])
                        foundUnprocessed.push(v);
            }
        } while (!foundUnprocessed.isEmpty());
    }
}
