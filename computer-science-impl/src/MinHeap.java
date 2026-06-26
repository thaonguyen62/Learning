
import java.util.ArrayList;

public class MinHeap<T extends Comparable<T>> {
    private ArrayList<T> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    // ── INDEX HELPERS ────────────────────────────────────────
    private int parent(int i)     { return (i - 1) / 2; }
    private int leftChild(int i)  { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    private void swap(int i, int j) {
        T tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    // ── INSERT — thêm cuối, bubble UP ────────────────────────
    public void insert(T data) {
        heap.add(data);
        bubbleUp(heap.size() - 1);
    }

    private void bubbleUp(int i) {
        while (i > 0 && heap.get(i).compareTo(heap.get(parent(i))) < 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // ── POLL — lấy root (min), bubble DOWN ──────────────────
    public T poll() {
        if (heap.isEmpty()) return null;
        T min = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            bubbleDown(0);
        }
        return min;
    }

    private void bubbleDown(int i) {
        int size = heap.size();
        while (true) {
            int smallest = i;
            int left  = leftChild(i);
            int right = rightChild(i);

            if (left  < size && heap.get(left).compareTo(heap.get(smallest))  < 0) smallest = left;
            if (right < size && heap.get(right).compareTo(heap.get(smallest)) < 0) smallest = right;

            if (smallest == i) break;
            swap(i, smallest);
            i = smallest;
        }
    }

    // ── REMOVE — như poll nhưng throw nếu rỗng ──────────────
    public T remove() {
        if (heap.isEmpty()) throw new java.util.NoSuchElementException();
        return poll();
    }

    // ── PEEK — xem root không xóa ───────────────────────────
    public T peek() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    public T element() {
        if (heap.isEmpty()) throw new java.util.NoSuchElementException();
        return heap.get(0);
    }

    // ── UTILS ────────────────────────────────────────────────
    public boolean contains(Object o) {
        return heap.contains(o);
    }

    public void clear() {
        heap.clear();
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // in array để debug
    public void print() {
        System.out.println(heap);
    }
}
