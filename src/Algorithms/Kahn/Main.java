package Kahn;

// ─────────────────────────────────────────────
// Main.java  —  demo using the course example
// Graph: IP → POO, LAP; POO → AED; LAP → AED;
//        AED → DLC, ADA
// ─────────────────────────────────────────────
// ─────────────────────────────────────────────
// Main.java  —  demo with the course example
// IP=0, POO=1, LAP=2, AED=3, DLC=4, ADA=5
// ─────────────────────────────────────────────
public class Main {
    public static void main(String[] args) {
        Digraph g = new Digraph(6);

        g.addEdge(0, 1); // IP  → POO
        g.addEdge(0, 2); // IP  → LAP
        g.addEdge(1, 3); // POO → AED
        g.addEdge(2, 3); // LAP → AED
        g.addEdge(3, 4); // AED → DLC
        g.addEdge(3, 5); // AED → ADA

        System.out.println("Is acyclic? " + Kahn.isAcyclic(g));
        // → true

        int[] order = Kahn.topologicalSort(g);
        System.out.print("Topological order: ");
        for (int v : order)
            System.out.print(v + " ");
        // → 0 1 2 3 4 5  (i.e. IP POO LAP AED DLC ADA)
    }
}
