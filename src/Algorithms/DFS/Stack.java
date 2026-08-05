package DFS;

// ─────────────────────────────────────────────
// Stack.java  —  LIFO stack
// push, pop, isEmpty all Θ(1)
// The slides write: Stack<Node> foundUnprocessed = new StackIn...<>(?)
// A LinkedList gives O(1) for all operations.
// ─────────────────────────────────────────────
import java.util.LinkedList;

public class Stack<T> {
    private final LinkedList<T> list = new LinkedList<>();

    public void push(T item) { list.addFirst(item); }
    public T pop() { return list.removeFirst(); }
    public boolean isEmpty() { return list.isEmpty(); }
}


