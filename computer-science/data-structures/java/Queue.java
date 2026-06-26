public class Queue<T> {

    // Backed by doubly-ended linked list: O(1) enqueue at tail, O(1) dequeue at head
    private static class Node<T> {
        T val;
        Node<T> next;
        Node(T val) { this.val = val; }
    }

    private Node<T> head;  // front — dequeue here
    private Node<T> tail;  // back  — enqueue here
    private int size;

    public Queue() {
        head = tail = null;
        size = 0;
    }

    // O(1) — add to tail
    public void enqueue(T val) {
        Node<T> node = new Node<>(val);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // O(1) — remove from head
    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        T val = head.val;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return val;
    }

    // O(1) — view front without removing
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return head.val;
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("front -> [");
        Node<T> cur = head;
        while (cur != null) {
            sb.append(cur.val);
            if (cur.next != null) sb.append(", ");
            cur = cur.next;
        }
        sb.append("] <- back");
        return sb.toString();
    }

    // --- Circular Queue (fixed capacity, O(1) all ops) ---
    public static class CircularQueue {
        private int[] data;
        private int head, tail, size, capacity;

        public CircularQueue(int capacity) {
            this.capacity = capacity;
            data = new int[capacity];
            head = tail = size = 0;
        }

        public boolean enqueue(int val) {
            if (size == capacity) return false;  // full
            data[tail] = val;
            tail = (tail + 1) % capacity;        // wrap around
            size++;
            return true;
        }

        public int dequeue() {
            if (size == 0) throw new RuntimeException("Queue is empty");
            int val = data[head];
            head = (head + 1) % capacity;        // wrap around
            size--;
            return val;
        }

        public boolean isFull() { return size == capacity; }
        public boolean isEmpty() { return size == 0; }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Queue:   " + queue);           // front -> [1, 2, 3] <- back
        System.out.println("Peek:    " + queue.peek());    // 1
        System.out.println("Dequeue: " + queue.dequeue()); // 1
        System.out.println("Queue:   " + queue);           // front -> [2, 3] <- back

        System.out.println("\n--- Circular Queue ---");
        CircularQueue cq = new CircularQueue(3);
        cq.enqueue(10);
        cq.enqueue(20);
        cq.enqueue(30);
        System.out.println("Full: " + cq.isFull());        // true
        System.out.println("Dequeue: " + cq.dequeue());    // 10
        cq.enqueue(40);                                      // wraps slot 0
        System.out.println("Dequeue: " + cq.dequeue());    // 20
        System.out.println("Dequeue: " + cq.dequeue());    // 30
        System.out.println("Dequeue: " + cq.dequeue());    // 40
    }
}
