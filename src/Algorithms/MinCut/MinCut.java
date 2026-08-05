package MinCut;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// ─────────────────────────────────────────────
// MinCut.java  —  Minimum f–d Cut in an Undirected Weighted Graph
// Slides Cap. XVI — Margarida Mamede, ADA 2025/26
//
// PROBLEM:
//   Given an undirected weighted graph G = (V, A) with non-negative
//   weights, and two distinct vertices f (source) and d (sink),
//   find a minimum f–d cut: a partition {F, D} of V with f∈F, d∈D
//   that minimises the total weight of edges crossing the cut.
//
// DEFINITION (slides p.3):
//   A cut {F, D} is a partition of V with f∈F and d∈D.
//   Cut edges: C_{F,D} = { (v,w) ∈ A | v∈F, w∈D }
//   Cut capacity = sum of weights of cut edges.
//
// MAX-FLOW MIN-CUT THEOREM (slides p.6):
//   max-flow value (f→d) = min-cut capacity
//
// ALGORITHM (slides p.7–8) — 3 steps:
//   1. Compute max flow φ from f to d in the DIRECTED version
//      of G (each undirected edge becomes TWO directed arcs,
//      both with the same weight as the original edge).
//
//   2. In the residual network induced by φ, find the set F of all
//      vertices reachable from f via BFS.
//
//   3. The partition {F, V\F} is a minimum f–d cut.
//      The cut edges are all original edges (v,w) with v∈F and w∈D
//      (or v∈D and w∈F, since the graph is undirected).
//
// Complexity: same as the max-flow algorithm used — Edmonds-Karp here:
//   O(|V| × |A|²)
// ─────────────────────────────────────────────
public class MinCut {

    public static final int INF = Integer.MAX_VALUE / 2;

    // ── Result ─────────────────────────────────────────────────────────────
    public static class Result {
        public final int        cutCapacity;  // total weight of cut edges
        public final boolean[]  inF;          // inF[v] = true → v is in partition F (source side)
        public final List<int[]> cutEdges;    // original undirected edges crossing the cut

        Result(int cutCapacity, boolean[] inF, List<int[]> cutEdges) {
            this.cutCapacity = cutCapacity;
            this.inF         = inF;
            this.cutEdges    = cutEdges;
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // minCut — main method
    // ────────────────────────────────────────────────────────────────────────
    public static Result minCut(UndiGraph graph, int source, int sink) {
        int n = graph.numNodes();

        // ── Step 1: Build the directed flow network ───────────────────────
        // Each undirected edge (v, w, weight) becomes TWO directed arcs:
        //   v→w with capacity = weight
        //   w→v with capacity = weight
        // (This is the key difference from Edmonds-Karp on a directed graph:
        //  both directions get the FULL original weight, not zero.)
        int[][] cap     = new int[n][n];
        boolean[][] hasEdge = new boolean[n][n];

        for (int[] e : graph.edges()) {
            int v = e[0], w = e[1], wt = e[2];
            // Add both directions with the original weight
            cap[v][w] += wt;  // += handles parallel edges correctly
            cap[w][v] += wt;
            hasEdge[v][w] = true;
            hasEdge[w][v] = true;
        }

        // The network is already symmetric — no extra reverse arcs needed
        // (every arc already has a reverse with the same capacity).
        // BUT Edmonds-Karp needs the flow matrix, which can go negative
        // on reverse arcs. The net residue = cap[v][w] - flow[v][w].

        // ── Step 1 cont: Run Edmonds-Karp max flow ────────────────────────
        int[][] flow  = new int[n][n];
        int[]   via   = new int[n];
        int     flowValue = 0;
        int     increment;

        while ((increment = findPath(cap, flow, n, source, sink, via)) != 0) {
            flowValue += increment;
            int node = sink;
            while (node != source) {
                int origin = via[node];
                flow[origin][node] += increment;
                flow[node][origin] -= increment;
                node = origin;
            }
        }

        // ── Step 2: BFS on residual graph to find set F ───────────────────
        // F = set of vertices reachable from source in the residual network.
        // A directed arc v→w is in the residual network iff residue > 0,
        // i.e. cap[v][w] - flow[v][w] > 0.
        boolean[] inF = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        inF[source] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int w = 0; w < n; w++) {
                int residue = cap[v][w] - flow[v][w];
                if (!inF[w] && residue > 0) {
                    inF[w] = true;
                    queue.add(w);
                }
            }
        }

        // ── Step 3: Extract cut edges and compute capacity ─────────────────
        // An original undirected edge (v, w) is a cut edge iff
        // exactly one endpoint is in F.
        // Capacity = sum of weights of cut edges.
        List<int[]> cutEdges = new ArrayList<>();
        int cutCapacity = 0;

        for (int[] e : graph.edges()) {
            int v = e[0], w = e[1], wt = e[2];
            if (inF[v] != inF[w]) {  // one in F, one in D
                cutEdges.add(e);
                cutCapacity += wt;
            }
        }

        return new Result(cutCapacity, inF, cutEdges);
    }

    // ────────────────────────────────────────────────────────────────────────
    // findPath — BFS on residual graph (Edmonds-Karp)
    // ────────────────────────────────────────────────────────────────────────
    private static int findPath(int[][] cap, int[][] flow, int n,
                                int source, int sink, int[] via) {
        Queue<Integer> waiting  = new LinkedList<>();
        boolean[]      found    = new boolean[n];
        int[]          pathIncr = new int[n];

        for (int v = 0; v < n; v++) found[v] = false;

        waiting.add(source);
        found[source]    = true;
        via[source]      = source;
        pathIncr[source] = INF;

        do {
            int origin = waiting.poll();
            for (int destin = 0; destin < n; destin++) {
                int residue = cap[origin][destin] - flow[origin][destin];
                if (!found[destin] && residue > 0) {
                    via[destin]      = origin;
                    pathIncr[destin] = Math.min(pathIncr[origin], residue);
                    if (destin == sink)
                        return pathIncr[destin];
                    waiting.add(destin);
                    found[destin] = true;
                }
            }
        } while (!waiting.isEmpty());

        return 0;
    }
}