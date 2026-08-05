package Kruskal.java;

/**
 * Minimum priority queue: always removes the entry with the smallest key.
 */
public interface MinPriorityQueue<K extends Comparable<K>, V> {

    /** Removes and returns the entry with the minimum key. */
    Entry<K, V> removeMin();

    /** Returns true if the queue is empty. */
    boolean isEmpty();

    /** Returns the number of entries in the queue. */
    int size();
}
