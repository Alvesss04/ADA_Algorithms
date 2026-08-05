package Kruskal.java;

/**
 * A key-value pair used in the priority queue.
 *
 * @param <K> the key type (must be Comparable – used for ordering)
 * @param <V> the value type
 */
public interface Entry<K, V> {
    K getKey();
    V getValue();
}
