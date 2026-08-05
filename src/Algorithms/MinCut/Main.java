package MinCut;

import java.util.Arrays;

// ─────────────────────────────────────────────
// Main.java  —  demo using the slide example
//
// Graph from Cap. XVI (slides p.2, p.8):
//   Nodes: 0, 1, 2, 3, 4, 5
//   source = 0,  sink = 3
//   Edges (undirected, with weights):
//     0–4: 4,  0–5: 3 (slide reads the arc from 0)
//              actually from slide p.2:
//     0–4: 4,  0–2: 5,  1–2: 2,  1–3: 1,
//     2–3: 2,  4–5: 2,  5–2: 2 (checking slide layout...)
//
// From slide p.8 solution:
//   F = {0, 1, 2, 5},  D = {3, 4}
//   Cut edges: those between {0,1,2,5} and {3,4}
//   The cut capacity = max flow value (from the theorem)
//
// Reconstructing the slide graph (p.2):
//   0–4:4, 0–5:3, 1–5:2, 1–3:1, 2–3:2, 2–5:2, 4–5:2
// ─────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        // ── Slide example (Cap. XVI, p.2/p.8) ────────────────────────────
        // 6 nodes: source=0, sink=3
        // From the slide diagram:
        //   0–4:4, 0–5:3, 1–5:2, 1–3:1, 2–3:2, 2–5:2, 4–5:2
        // Expected: F={0,1,2,5}, D={3,4}, cut capacity = max flow
        System.out.println("=== Min Cut — Slide Example (Cap. XVI) ===");
        System.out.println("Source: 0   Sink: 3");
        System.out.println();

        UndiGraph g = new UndiGraph(6);
        g.addEdge(0, 4, 4);
        g.addEdge(0, 5, 3);
        g.addEdge(1, 5, 2);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 3, 2);
        g.addEdge(2, 5, 2);
        g.addEdge(4, 5, 2);

        MinCut.Result r = MinCut.minCut(g, 0, 3);

        System.out.println("Max flow = Min cut capacity: " + r.cutCapacity);
        System.out.println();

        System.out.print("Partition F (source side): {");
        printPartition(r.inF, true, g.numNodes());
        System.out.print("Partition D (sink side):   {");
        printPartition(r.inF, false, g.numNodes());

        System.out.println("\nCut edges (crossing F→D):");
        for (int[] e : r.cutEdges)
            System.out.println("  " + e[0] + " — " + e[1] + "  (weight " + e[2] + ")");

        // Verify: expected F={0,1,2,5}, D={3,4} from slides p.8
        System.out.println("\nExpected from slides: F={0,1,2,5}, D={3,4}");

        // ── Second example: simple 4-node graph ──────────────────────────
        System.out.println("\n=== Second Example ===");
        System.out.println("Source: 0   Sink: 3");
        UndiGraph g2 = new UndiGraph(4);
        g2.addEdge(0, 1, 10);
        g2.addEdge(0, 2,  5);
        g2.addEdge(1, 2,  3);
        g2.addEdge(1, 3,  7);
        g2.addEdge(2, 3,  8);

        MinCut.Result r2 = MinCut.minCut(g2, 0, 3);
        System.out.println("Min cut capacity: " + r2.cutCapacity);
        System.out.print("F: {");
        printPartition(r2.inF, true, g2.numNodes());
        System.out.print("D: {");
        printPartition(r2.inF, false, g2.numNodes());
        System.out.println("Cut edges:");
        for (int[] e : r2.cutEdges)
            System.out.println("  " + e[0] + " — " + e[1] + "  (weight " + e[2] + ")");
    }

    private static void printPartition(boolean[] inF, boolean side, int n) {
        boolean first = true;
        for (int v = 0; v < n; v++) {
            if (inF[v] == side) {
                if (!first) System.out.print(", ");
                System.out.print(v);
                first = false;
            }
        }
        System.out.println("}");
    }
}