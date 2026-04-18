package Kruskal.java;

/**
 * Concrete key-value pair.
 */
public class EntryClass<K, V> implements Entry<K, V> {

    private final K key;
    private final V value;

    public EntryClass(K key, V value) {
        this.key   = key;
        this.value = value;
    }

    @Override
    public K getKey() { return key; }

    @Override
    public V getValue() { return value; }
}
