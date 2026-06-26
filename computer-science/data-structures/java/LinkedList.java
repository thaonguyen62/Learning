public class LinkedList<T> {

    // Each node holds data + pointer to next
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    // O(1) — new node becomes head
    public void prepend(T data) {
        Node<T> node = new Node<>(data);
        node.next = head;
        head = node;
        size++;
    }

    // O(n) — walk to tail
    public void append(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
        } else {
            Node<T> cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node;
        }
        size++;
    }

    // O(n) — scan to find and unlink
    public boolean delete(T data) {
        if (head == null) return false;
        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }
        Node<T> cur = head;
        while (cur.next != null) {
            if (cur.next.data.equals(data)) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    // O(n)
    public boolean contains(T data) {
        Node<T> cur = head;
        while (cur != null) {
            if (cur.data.equals(data)) return true;
            cur = cur.next;
        }
        return false;
    }

    // O(n) — reverse in-place, three-pointer technique
    public void reverse() {
        Node<T> prev = null;
        Node<T> cur = head;
        while (cur != null) {
            Node<T> next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        head = prev;
    }

    // O(n) — Floyd's tortoise & hare
    public boolean hasCycle() {
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> cur = head;
        while (cur != null) {
            sb.append(cur.data);
            if (cur.next != null) sb.append(" -> ");
            cur = cur.next;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);
        list.prepend(0);
        System.out.println("After append/prepend: " + list);   // [0 -> 1 -> 2 -> 3]
        list.delete(2);
        System.out.println("After delete(2):       " + list);  // [0 -> 1 -> 3]
        list.reverse();
        System.out.println("After reverse:         " + list);  // [3 -> 1 -> 0]
        System.out.println("Contains 1: " + list.contains(1)); // true
        System.out.println("Has cycle:  " + list.hasCycle());  // false
    }
}
