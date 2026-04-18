package Kruskal.java;
public class Main {

    public static void main(String[] args) {

        UndiGraphAdjList<Integer> g = new UndiGraphAdjList<>(6);

        g.addEdge(0, 5, 1);
        g.addEdge(2, 3, 1);
        g.addEdge(2, 4, 2);
        g.addEdge(2, 5, 3);
        g.addEdge(0, 2, 4);
        g.addEdge(0, 1, 5);
        g.addEdge(4, 5, 5);
        g.addEdge(3, 4, 6);
        g.addEdge(1, 2, 7);
        g.addEdge(1, 3, 8);
        g.addEdge(0, 4, 9);

        KruskalMST<Integer> kruskal = new KruskalMST<>();
        kruskal.printMST(g);
    }
}
