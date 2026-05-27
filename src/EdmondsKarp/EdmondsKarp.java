package EdmondsKarp;

import java.util.LinkedList;
import java.util.Queue;

// ─────────────────────────────────────────────
// EdmondsKarp.java  —  Maximum Flow
// Slides Cap. XIII — Margarida Mamede, ADA 2025/26
//
// Applies the Ford-Fulkerson method, finding
// augmenting paths from source to sink using BFS
// on the RESIDUAL NETWORK.
//
// Using BFS guarantees shortest augmenting paths
// (each arc counts as distance 1), which bounds
// the number of iterations to O(|V| × |A|).
//
// KEY CONCEPTS:
//
//  - Flow network (rede de fluxos): the original
//    graph plus zero-capacity reverse arcs for
//    every arc that doesn't already have a reverse.
//
//  - Residual capacity of arc (v→w):
//      residue = capacity[v][w] - flow[v][w]
//    If residue > 0, we can still push more flow.
//
//  - Augmenting path: a path source→...→sink in
//    the residual graph (where residue > 0).
//
//  - Increment: the bottleneck (min residue) on
//    the augmenting path — how much flow to push.
//
//  - After pushing: flow[v][w] += increment
//                   flow[w][v] -= increment  (cancel/reverse)
//
// Complexity: O(|V| × |A|²)
// ─────────────────────────────────────────────
public class EdmondsKarp {

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
    // edmondsKarp
    //
    // Main method. Builds the flow network, then repeatedly finds
    // augmenting paths via BFS and updates the flow until none remain.
    // ────────────────────────────────────────────────────────────────────────
    public static Result edmondsKarp(Digraph graph, int source, int sink) {
        // Step 1: Build the flow network
        // Add a zero-capacity reverse arc for every arc that has no reverse
        Digraph network = buildNetwork(graph);
        int n = network.numNodes();

        // Step 2: Initialize flow to 0 on every arc
        int[][] flow = new int[n][n];
        for (int[] e : network.edges())
            flow[e[0]][e[1]] = 0;

        int[] via       = new int[n];
        int   flowValue = 0;
        int   increment;

        // Step 3: Repeat — find augmenting path, push flow
        while ((increment = findPath(network, flow, source, sink, via)) != 0) {
            flowValue += increment;

            // Walk back from sink to source via the via[] array and update flow
            int node = sink;
            while (node != source) {
                int origin = via[node];
                flow[origin][node] += increment;  // push flow forward
                flow[node][origin] -= increment;  // cancel flow on reverse arc
                node = origin;
            }
        }

        return new Result(flowValue, flow);
    }

    // ────────────────────────────────────────────────────────────────────────
    // buildNetwork
    //
    // Clones the original graph and adds zero-capacity reverse arcs
    // for every arc (v→w) where (w→v) does NOT already exist.
    // This ensures every arc has a usable reverse in the residual graph.
    // ────────────────────────────────────────────────────────────────────────
    private static Digraph buildNetwork(Digraph graph) {
        Digraph network = graph.clone();
        for (int[] e : graph.edges()) {
            int v = e[0], w = e[1];
            if (!graph.edgeExists(w, v))
                network.addEdge(w, v, 0); // reverse arc with capacity 0
        }
        return network;
    }

    // ────────────────────────────────────────────────────────────────────────
    // findPath
    //
    // BFS on the RESIDUAL GRAPH to find a shortest augmenting path
    // from source to sink. Returns the bottleneck (increment) of
    // that path, or 0 if no augmenting path exists.
    //
    // Fills via[node] = predecessor of node on the path.
    // ────────────────────────────────────────────────────────────────────────
    private static int findPath(Digraph network, int[][] flow,
                                int source, int sink, int[] via) {
        int n = network.numNodes();
        Queue<Integer> waiting  = new LinkedList<>();
        boolean[]      found    = new boolean[n];
        int[]          pathIncr = new int[n];

        // Initialize: source is discovered with infinite capacity
        for (int v = 0; v < n; v++) found[v] = false;

        waiting.add(source);
        found[source]    = true;
        via[source]      = source;
        pathIncr[source] = INF;

        // BFS — explore the residual graph
        do {
            int origin = waiting.poll();
            for (int destin = 0; destin < n; destin++) {
                // residue = remaining capacity on arc origin→destin
                int residue = network.capacity(origin, destin) - flow[origin][destin];
                if (!found[destin] && residue > 0) {
                    via[destin]      = origin;
                    pathIncr[destin] = Math.min(pathIncr[origin], residue);
                    if (destin == sink)
                        return pathIncr[destin]; // reached sink — return bottleneck
                    waiting.add(destin);
                    found[destin] = true;
                }
            }
        } while (!waiting.isEmpty());

        return 0; // no augmenting path found
    }
}