package BFS;

// ─────────────────────────────────────────────
// Queue.java  —  FIFO queue
// enqueue, dequeue, isEmpty all Θ(1)
// The slides write: Queue<Node> waiting = new QueueIn...<>(?)
// A LinkedList gives O(1) for all operations.
// ─────────────────────────────────────────────
import java.util.LinkedList;

public class Queue<T> {
    private final LinkedList<T> list = new LinkedList<>();

    public void enqueue(T item){ list.addLast(item); }
    public T dequeue(){ return list.removeFirst(); }
    public boolean isEmpty() { return list.isEmpty(); }
}
