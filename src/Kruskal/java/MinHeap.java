package Kruskal.java;

/**
 * Array-based binary min-heap.
 *
 * <p>The constructor accepts an existing Entry array and builds the heap
 * in-place in Θ(n) time (bottom-up heapification), exactly as described
 * in the slides:
 * <pre>  MinPriorityQueue priQueue = new MinHeap<>(auxArray);</pre>
 */
@SuppressWarnings("unchecked")
public class MinHeap<K extends Comparable<K>, V>
        implements MinPriorityQueue<K, V> {

    private Entry<K, V>[] heap;
    private int size;

    /**
     * Builds a min-heap from an arbitrary array of entries in O(n) time.
     *
     * @param array the source array (will be copied, not mutated)
     */
    public MinHeap(Entry<K, V>[] array) {
        size = array.length;
        heap = (Entry<K, V>[]) new Entry[size];
        System.arraycopy(array, 0, heap, 0, size);

        // Bottom-up heapification: start at the last internal node
        for (int i = size / 2 - 1; i >= 0; i--)
            siftDown(i);
    }

    @Override
    public Entry<K, V> removeMin() {
        if (isEmpty())
            throw new RuntimeException("Priority queue is empty");

        Entry<K, V> min = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;          // help GC
        if (!isEmpty()) siftDown(0);
        return min;
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

    /** Restores the heap property downward from position i. */
    private void siftDown(int i) {
        while (true) {
            int left  = 2 * i + 1;
            int right = 2 * i + 2;
            int min   = i;

            if (left  < size && heap[left].getKey() .compareTo(heap[min].getKey()) < 0)
                min = left;
            if (right < size && heap[right].getKey().compareTo(heap[min].getKey()) < 0)
                min = right;

            if (min == i) break;

            Entry<K, V> tmp = heap[i];
            heap[i]   = heap[min];
            heap[min] = tmp;
            i = min;
        }
    }
}
