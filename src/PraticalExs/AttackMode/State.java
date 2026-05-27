package PraticalExs.AttackMode;

public class State implements Comparable<State> {
    int cost;
    int node;
    int attacksUsed;

    public State(int cost, int node, int attacksUsed) {
        this.cost        = cost;
        this.node        = node;
        this.attacksUsed = attacksUsed;
    }

    // Min-heap: always pop the state with the lowest cost first
    @Override
    public int compareTo(State other) {
        return Integer.compare(this.cost, other.cost);
    }
}
