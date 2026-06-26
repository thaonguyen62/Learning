import java.util.Arrays;

// Min-Heap: parent always <= children → root is always minimum
// Stored in array: parent(i) = (i-1)/2, left(i) = 2i+1, right(i) = 2i+2
public class Heap {

    private int[] data;
    private int size;
    private int capacity;

    public Heap(int capacity) {
        this.capacity = capacity;
        this.data = new int[capacity];
        this.size = 0;
    }

    // O(log n) — add at end, bubble up
    public void insert(int val) {
        if (size == capacity) throw new RuntimeException("Heap is full");
        data[size] = val;
        bubbleUp(size);
        size++;
    }

    // O(log n) — remove root (min), replace with last, bubble down
    public int extractMin() {
        if (size == 0) throw new RuntimeException("Heap is empty");
        int min = data[0];
        data[0] = data[size - 1];
        size--;
        bubbleDown(0);
        return min;
    }

    // O(1) — just peek root
    public int peekMin() {
        if (size == 0) throw new RuntimeException("Heap is empty");
        return data[0];
    }

    // Walk up: swap with parent while smaller than parent
    private void bubbleUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (data[i] < data[parent]) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    // Walk down: swap with smaller child while larger than either child
    private void bubbleDown(int i) {
        while (true) {
            int left  = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left  < size && data[left]  < data[smallest]) smallest = left;
            if (right < size && data[right] < data[smallest]) smallest = right;

            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int a, int b) {
        int tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }

    // O(n) — build heap from unsorted array (better than n insertions)
    public static Heap buildHeap(int[] arr) {
        Heap h = new Heap(arr.length);
        h.data = Arrays.copyOf(arr, arr.length);
        h.size = arr.length;
        // start from last non-leaf and heapify down each node
        for (int i = (h.size / 2) - 1; i >= 0; i--) {
            h.bubbleDown(i);
        }
        return h;
    }

    // Heap sort: build heap, extract all → sorted ascending — O(n log n)
    public static int[] heapSort(int[] arr) {
        // Max-heap for ascending sort (inline to avoid extra class)
        int n = arr.length;
        int[] a = Arrays.copyOf(arr, n);

        // Build max-heap
        for (int i = n / 2 - 1; i >= 0; i--) siftDownMax(a, i, n);

        // Extract max to end repeatedly
        for (int i = n - 1; i > 0; i--) {
            int tmp = a[0]; a[0] = a[i]; a[i] = tmp;  // swap root with last
            siftDownMax(a, 0, i);
        }
        return a;
    }

    private static void siftDownMax(int[] a, int i, int n) {
        while (true) {
            int left = 2 * i + 1, right = 2 * i + 2, largest = i;
            if (left  < n && a[left]  > a[largest]) largest = left;
            if (right < n && a[right] > a[largest]) largest = right;
            if (largest != i) {
                int tmp = a[i]; a[i] = a[largest]; a[largest] = tmp;
                i = largest;
            } else break;
        }
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(data, size));
    }

    public static void main(String[] args) {
        System.out.println("--- Min-Heap ---");
        Heap heap = new Heap(10);
        for (int v : new int[]{5, 3, 8, 1, 9, 2}) heap.insert(v);
        System.out.println("Heap array: " + heap);         // [1, 3, 2, 5, 9, 8]
        System.out.println("Min:        " + heap.peekMin()); // 1
        System.out.println("ExtractMin: " + heap.extractMin()); // 1
        System.out.println("ExtractMin: " + heap.extractMin()); // 2
        System.out.println("Heap array: " + heap);         // [3, 5, 8, 9]

        System.out.println("\n--- Build Heap from array (O(n)) ---");
        Heap built = Heap.buildHeap(new int[]{5, 3, 8, 1, 9, 2});
        System.out.println("Built heap: " + built);

        System.out.println("\n--- Heap Sort ---");
        int[] sorted = Heap.heapSort(new int[]{5, 3, 8, 1, 9, 2});
        System.out.println("Sorted: " + Arrays.toString(sorted)); // [1,2,3,5,8,9]
    }
}
