package Dinic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// ─────────────────────────────────────────────
// Dinic.java  —  Maximum Flow (Dinic / Dinitz)
// Slides Cap. XIV — Margarida Mamede, ADA 2025/26
//
// IMPROVEMENT OVER EDMONDS-KARP:
//   Instead of finding ONE augmenting path per BFS,
//   Dinic finds ALL shortest augmenting paths in a
//   PHASE, using the concept of a LEVEL GRAPH.
//
// KEY CONCEPTS:
//
//  - Level of a node v: the shortest distance from
//    source to v in the RESIDUAL graph (using BFS).
//    level[source] = 0.
//    level[v] = INF if v is not reachable.
//
//  - Level graph (rede residual por camadas):
//    Contains only arcs (v→w) from the residual graph
//    where level[w] = level[v] + 1.
//    This ensures every path in the level graph has
//    the SAME length (= level[sink]) and is shortest.
//
//  - Blocking flow: a flow in the level graph such
//    that every source→sink path has at least one
//    saturated arc. Found by repeated DFS.
//
// ALGORITHM (slides p.11):
//   1. Build flow network (add zero reverse arcs)
//   2. BFS to compute levels in residual graph
//   3. If sink is unreachable (level[sink] = INF): done
//   4. Else: repeatedly find augmenting paths in the
//      LEVEL GRAPH via DFS and update flow
//      (this is one full PHASE / blocking flow)
//   5. Go to step 2
//
// WHY FASTER THAN EDMONDS-KARP:
//   - Each phase saturates at least one arc on every
//     shortest path, so the distance source→sink
//     strictly increases after each phase.
//   - At most |V|-1 phases.
//   - Each phase: at most |A| paths, each found in O(|V|)
//     → O(|V|·|A|) per phase → O(|V|²·|A|) total.
//
// Complexity: O(|V|² × |A|)
//   vs Edmonds-Karp: O(|V| × |A|²)
//   Dinic wins when |V| << |A| (dense graphs)
// ─────────────────────────────────────────────
public class Dinic {

    public static final int INF = Integer.MAX_VALUE / 2;

    // ── Result: max flow value + final flow matrix ──────────────────────────
    public static class Result {
        public final int     flowValue;
        public final int[][] flow;
        Result(int flowValue, int[][] flow) {
            this.flowValue = flowValue;
            this.flow      = flow;
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // dinic — main method
    // ────────────────────────────────────────────────────────────────────────
    public static Result dinic(Digraph graph, int source, int sink) {
        // Step 1: build the flow network (add zero-capacity reverse arcs)
        Digraph network = buildNetwork(graph);
        int n = network.numNodes();

        // Initialize flow to 0 on every arc
        int[][] flow = new int[n][n];

        int   flowValue = 0;
        int[] level     = new int[n];

        // Outer loop: each iteration is one PHASE
        // Phase = compute level graph + find all augmenting paths in it (blocking flow)
        while (computeLevels(network, flow, source, sink, level)) {
            // level[sink] < INF → there is a path; run blocking flow via DFS

            int increment;
            // Inner loop: keep finding augmenting paths in the LEVEL GRAPH
            // until none remain (DFS returns 0)
            while ((increment = findPath(network, flow, level, source, sink)) != 0) {
                flowValue += increment;
            }
            // After the inner loop, the level graph is exhausted (blocking flow done).
            // Go back to BFS to recompute levels for the next phase.
        }

        return new Result(flowValue, flow);
    }

    // ────────────────────────────────────────────────────────────────────────
    // buildNetwork — same as Edmonds-Karp:
    // clone graph, add zero-capacity reverse arcs where missing
    // ────────────────────────────────────────────────────────────────────────
    private static Digraph buildNetwork(Digraph graph) {
        Digraph network = graph.clone();
        for (int[] e : graph.edges()) {
            int v = e[0], w = e[1];
            if (!graph.edgeExists(w, v))
                network.addEdge(w, v, 0);
        }
        return network;
    }

    // ────────────────────────────────────────────────────────────────────────
    // computeLevels — BFS on the RESIDUAL graph to assign levels
    //
    // level[v] = shortest distance from source to v in the residual graph.
    // level[v] = INF if unreachable.
    //
    // Returns true if sink is reachable (level[sink] < INF),
    // meaning another phase is needed.
    // Returns false → algorithm terminates.
    // ────────────────────────────────────────────────────────────────────────
    private static boolean computeLevels(Digraph network, int[][] flow,
                                         int source, int sink, int[] level) {
        int n = network.numNodes();
        Arrays.fill(level, -1);          // -1 = not yet visited

        Queue<Integer> queue = new LinkedList<>();
        level[source] = 0;
        queue.add(source);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int w = 0; w < n; w++) {
                // Arc v→w is in the residual graph if residue > 0
                int residue = network.capacity(v, w) - flow[v][w];
                if (level[w] == -1 && residue > 0) {
                    level[w] = level[v] + 1;
                    queue.add(w);
                }
            }
        }

        // Sink is reachable iff level[sink] >= 0
        return level[sink] >= 0;
    }

    // ────────────────────────────────────────────────────────────────────────
    // findPath — DFS on the LEVEL GRAPH to find ONE augmenting path
    //
    // The level graph only allows arcs (v→w) where:
    //   1. residue > 0  (arc exists in residual graph)
    //   2. level[w] = level[v] + 1  (arc goes to the next level)
    //
    // Uses a via[] array to reconstruct the path and update flow inline.
    // Returns the bottleneck of the path found, or 0 if none.
    // ────────────────────────────────────────────────────────────────────────
    private static int findPath(Digraph network, int[][] flow,
                                int[] level, int source, int sink) {
        int n = network.numNodes();
        int[] via      = new int[n];
        int[] pathIncr = new int[n];
        boolean[] inPath = new boolean[n];

        Arrays.fill(via, -1);
        Arrays.fill(pathIncr, 0);
        Arrays.fill(inPath, false);

        // Iterative DFS using an explicit stack
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        stack.push(source);
        inPath[source] = true;
        via[source]    = source;
        pathIncr[source] = INF;

        while (!stack.isEmpty()) {
            int v = stack.peek();

            if (v == sink) {
                // Found a path to sink — update flow along via[]
                int increment = pathIncr[sink];

                int node = sink;
                while (node != source) {
                    int origin = via[node];
                    flow[origin][node] += increment;
                    flow[node][origin] -= increment;
                    node = origin;
                }
                return increment;
            }

            // Try to advance to an unvisited neighbour in the level graph
            boolean advanced = false;
            for (int w = 0; w < n; w++) {
                int residue = network.capacity(v, w) - flow[v][w];
                // Only follow arcs that go to the NEXT level and have residue > 0
                if (!inPath[w] && residue > 0 && level[w] == level[v] + 1) {
                    via[w]      = v;
                    pathIncr[w] = Math.min(pathIncr[v], residue);
                    inPath[w]   = true;
                    stack.push(w);
                    advanced = true;
                    break; // DFS: go deeper immediately
                }
            }

            if (!advanced) {
                // Dead end in the level graph — backtrack
                // Block this node from being revisited at this level
                // by invalidating its level (dead-end pruning)
                level[v] = -1;
                stack.pop();
                if (!stack.isEmpty())
                    inPath[stack.peek()] = true; // keep parent in path
                // Actually reset inPath[v] so it might be reused by other paths
                inPath[v] = false;
            }
        }

        return 0; // no augmenting path found in level graph
    }
}