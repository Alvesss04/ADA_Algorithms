package Kahn;

// ─────────────────────────────────────────────
// Bag.java  —  generic unordered collection
// add, remove, isEmpty all Θ(1)
// ─────────────────────────────────────────────
import java.util.LinkedList;

public class Bag<T> {
    private final LinkedList<T> list = new LinkedList<>();

    public void add(T item) { list.addLast(item); }
    public T remove() { return list.removeFirst(); }
    public boolean isEmpty() { return list.isEmpty(); }
}