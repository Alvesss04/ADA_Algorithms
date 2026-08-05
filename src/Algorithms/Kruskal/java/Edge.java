package Kruskal.java;

public interface Edge<L> {

    /** Returns the edge weight / label. */
    L label();

    /** Returns the first endpoint node ID. */
    int firstNode();

    /** Returns the second endpoint node ID. */
    int secondNode();
}
