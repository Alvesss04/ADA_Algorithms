package PraticalExs.AttackMode;

public class Street {
    int to;         // destination square
    int time;       // normal travel time (RTU), always even
    boolean attack; // true if attack mode can be activated here

    public Street(int to, int time, boolean attack) {
        this.to     = to;
        this.time   = time;
        this.attack = attack;
    }
}
