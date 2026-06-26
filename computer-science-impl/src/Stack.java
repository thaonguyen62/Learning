
public class Stack<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) {
            this.data = data;
        }
    }

    public Stack() {
        head = null;
        size = 0;
    }

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public boolean offer(T data) {
        push(data);
        return true;
    }

    public T pop() {
        if (head == null) return null;
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    public T remove() {
        if (head == null) throw new java.util.NoSuchElementException();
        return pop();
    }

    public T peek() {
        if (head == null) return null;
        return head.data;
    }

    public T element() {
        if (head == null) throw new java.util.NoSuchElementException();
        return head.data;
    }

    public boolean contains(Object o) {
        Node<T> curr = head;
        while (curr != null) {
            if (curr.data == null ? o == null : curr.data.equals(o)) return true;
            curr = curr.next;
        }
        return false;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
