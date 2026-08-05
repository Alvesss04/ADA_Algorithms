package Kruskal.java;

/**
 * Concrete implementation of an undirected weighted edge.
 */
public class EdgeClass<L> implements Edge<L> {

    private final L label;
    private final int first;
    private final int second;

    public EdgeClass(L label, int first, int second) {
        this.label  = label;
        this.first  = first;
        this.second = second;
    }

    @Override
    public L label() { return label; }

    @Override
    public int firstNode() { return first; }

    @Override
    public int secondNode() { return second; }

    @Override
    public String toString() {
        return "(" + first + " --[" + label + "]--> " + second + ")";
    }
}
