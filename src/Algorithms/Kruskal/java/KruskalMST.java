package Kruskal.java;
import Kruskal.UnionFind.UnionFindInArray;

/**
 * Kruskal's Minimum Spanning Tree algorithm.
 *
 * <p>Uses:
 * <ul>
 *   <li>A {@link MinHeap} built in Θ(|E|) from all edges.</li>
 *   <li>A {@link UnionFindInArray} partition with union-by-rank and
 *       path compression, giving overall complexity <b>O(|E| log |V|)</b>.</li>
 * </ul>
 *
 * <p>The two private methods match the slide pseudocode verbatim:
 * <pre>
 *   buildQueue(graph)   – slide 17
 *   mstKruskal(graph)   – slides 18-19
 * </pre>
 */
public class KruskalMST<L extends Comparable<L>> {

    // ------------------------------------------------------------------ //
    //  buildQueue  (slide 17)                                              //
    // ------------------------------------------------------------------ //

    /**
     * Creates a min-priority queue of all edges ordered by label (weight).
     * Built in Θ(|E|) using the bottom-up heap constructor.
     */
    @SuppressWarnings("unchecked")
    private MinPriorityQueue<L, Edge<L>> buildQueue(UndiGraph<L> graph) {

        Entry<L, Edge<L>>[] auxArray = new Entry[graph.numEdges()];
        int pos = 0;

        for (Edge<L> e : graph.edges())
            auxArray[pos++] = new EntryClass<>(e.label(), e);

        MinPriorityQueue<L, Edge<L>> priQueue = new MinHeap<>(auxArray);
        return priQueue;
    }

    // ------------------------------------------------------------------ //
    //  mstKruskal  (slides 18-19)                                          //
    // ------------------------------------------------------------------ //

    /**
     * Returns an array of |V|-1 edges that form the MST.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Sort all edges by weight (via min-heap).</li>
     *   <li>Greedily pick the cheapest edge whose endpoints belong to
     *       different components (checked with {@code find}).</li>
     *   <li>Merge the two components ({@code union}).</li>
     *   <li>Stop when |V|-1 edges have been chosen.</li>
     * </ol>
     */
    @SuppressWarnings("unchecked")
    public Edge<L>[] mstKruskal(UndiGraph<L> graph) {

        // Step 1 – build the edge priority queue in Θ(|E|)
        MinPriorityQueue<L, Edge<L>> allEdges = buildQueue(graph);

        // Step 2 – create the partition {{0}, {1}, ..., {|V|-1}}
        UnionFindInArray nodesPartition = new UnionFindInArray(graph.numNodes());

        int mstFinalSize = graph.numNodes() - 1;
        Edge<L>[] mst    = new Edge[mstFinalSize];
        int mstSize      = 0;

        // Step 3 – greedy selection
        while (mstSize < mstFinalSize) {

            Edge<L> edge = allEdges.removeMin().getValue();

            int rep1 = nodesPartition.find(edge.firstNode());
            int rep2 = nodesPartition.find(edge.secondNode());

            // Only add the edge if it connects two different components
            // (adding it otherwise would create a cycle)
            if (rep1 != rep2) {
                mst[mstSize++] = edge;
                nodesPartition.unionBySize(rep1, rep2);
                //nodesPartition.unionByHeight(rep1, rep2);
            }
        }

        return mst;
    }

    // ------------------------------------------------------------------ //
    //  Convenience helper                                                  //
    // ------------------------------------------------------------------ //

    /** Computes and prints the MST cost and edges (useful for testing). */
    public void printMST(UndiGraph<L> graph) {

        Edge<L>[] mst = mstKruskal(graph);

        System.out.println("=== Minimum Spanning Tree ===");
        L total = null;

        for (Edge<L> e : mst) {
            System.out.println("  " + e);

            // Accumulate total cost (works for Integer / Double labels)
            if (e.label() instanceof Integer) {
                int cur   = (total == null) ? 0 : (Integer) total;
                total     = (L)(Integer)(cur + (Integer) e.label());
            } else if (e.label() instanceof Double) {
                double cur = (total == null) ? 0.0 : (Double) total;
                total      = (L)(Double)(cur + (Double) e.label());
            }
        }

        if (total != null)
            System.out.println("Total cost: " + total);
    }
}
