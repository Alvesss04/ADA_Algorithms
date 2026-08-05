package MaxMatch;

import java.util.List;

// ─────────────────────────────────────────────
// Main.java  —  demo using the slide examples
//
// Slide example (Cap. XV, p.3-6):
//   X = {e1, e2, e3, e4}  →  local indices 0,1,2,3
//   Y = {t1, t2, t3}      →  local indices 0,1,2
//   (global ids: e1=0,e2=1,e3=2,e4=3, t1=4,t2=5,t3=6)
//
//   Edges (from slide p.3):
//     1-5, 1-6, 2-5, 2-7, 3-5, 3-6, 3-7, 4-6
//   (using 1-based ids from slide)
//   In 0-based: e0-t0, e0-t1, e1-t0, e1-t2, e2-t0, e2-t1, e2-t2, e3-t1
//
//   Expected max matching = 3  (e.g. e1-t1, e2-t2, e4-t3 from slide p.6)
// ─────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        // ── Slide example ─────────────────────────────────────────────────
        // X = {e1,e2,e3,e4} (0-based: 0,1,2,3)
        // Y = {t1,t2,t3}    (0-based: 0,1,2)
        System.out.println("=== Max Matching — Slide Example ===");
        System.out.println("X = {e1, e2, e3, e4},  Y = {t1, t2, t3}");
        System.out.println("Edges: e1-t1, e1-t2, e2-t1, e2-t3, e3-t1, e3-t2, e3-t3, e4-t2");
        System.out.println();

        BipartiteGraph g = new BipartiteGraph(4, 3);
        // x indices: e1=0, e2=1, e3=2, e4=3
        // y indices: t1=0, t2=1, t3=2
        g.addEdge(0, 0); // e1 - t1
        g.addEdge(0, 1); // e1 - t2
        g.addEdge(1, 0); // e2 - t1
        g.addEdge(1, 2); // e2 - t3
        g.addEdge(2, 0); // e3 - t1
        g.addEdge(2, 1); // e3 - t2
        g.addEdge(2, 2); // e3 - t3
        g.addEdge(3, 1); // e4 - t2

        MaxMatch.Result r = MaxMatch.maxMatch(g);

        System.out.println("Maximum matching size: " + r.matchingSize);
        System.out.println("Matched pairs:");
        printMatching(r.matching, g.sizeX());

        // ── Second example: perfect matching ─────────────────────────────
        System.out.println("\n=== Second Example: Perfect Matching ===");
        System.out.println("X = {A, B, C},  Y = {1, 2, 3}");
        System.out.println("Edges: A-1, A-2, B-2, B-3, C-1, C-3");

        BipartiteGraph g2 = new BipartiteGraph(3, 3);
        g2.addEdge(0, 0); // A-1
        g2.addEdge(0, 1); // A-2
        g2.addEdge(1, 1); // B-2
        g2.addEdge(1, 2); // B-3
        g2.addEdge(2, 0); // C-1
        g2.addEdge(2, 2); // C-3

        MaxMatch.Result r2 = MaxMatch.maxMatch(g2);
        System.out.println("Maximum matching size: " + r2.matchingSize); // expect 3
        System.out.println("Matched pairs:");
        String[] xNames = {"A", "B", "C"};
        String[] yNames = {"1", "2", "3"};
        for (int[] pair : r2.matching) {
            int xLocal = pair[0];
            int yLocal = pair[1] - g2.sizeX();
            System.out.println("  " + xNames[xLocal] + " — " + yNames[yLocal]);
        }

        // ── Third example: not all nodes can be matched ───────────────────
        System.out.println("\n=== Third Example: Partial Matching ===");
        System.out.println("X = {A, B, C, D},  Y = {1, 2}");
        System.out.println("Edges: A-1, B-1, C-2, D-2");

        BipartiteGraph g3 = new BipartiteGraph(4, 2);
        g3.addEdge(0, 0); // A-1
        g3.addEdge(1, 0); // B-1
        g3.addEdge(2, 1); // C-2
        g3.addEdge(3, 1); // D-2

        MaxMatch.Result r3 = MaxMatch.maxMatch(g3);
        System.out.println("Maximum matching size: " + r3.matchingSize); // expect 2
        System.out.println("Matched pairs:");
        printMatching(r3.matching, g3.sizeX());
    }

    private static void printMatching(List<int[]> matching, int sizeX) {
        for (int[] pair : matching) {
            int xLocal = pair[0];
            int yLocal = pair[1] - sizeX;
            System.out.println("  X[" + xLocal + "] — Y[" + yLocal + "]"
                    + "  (global ids: " + pair[0] + " — " + pair[1] + ")");
        }
    }
}