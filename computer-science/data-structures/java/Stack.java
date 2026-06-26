public class Stack<T> {

    // Backed by a singly linked list — O(1) push/pop guaranteed (no array resize)
    private static class Node<T> {
        T val;
        Node<T> next;
        Node(T val) { this.val = val; }
    }

    private Node<T> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    // O(1) — new node becomes top
    public void push(T val) {
        Node<T> node = new Node<>(val);
        node.next = top;
        top = node;
        size++;
    }

    // O(1) — remove and return top
    public T pop() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        T val = top.val;
        top = top.next;
        size--;
        return val;
    }

    // O(1) — view top without removing
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Stack is empty");
        return top.val;
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("top -> [");
        Node<T> cur = top;
        while (cur != null) {
            sb.append(cur.val);
            if (cur.next != null) sb.append(", ");
            cur = cur.next;
        }
        sb.append("]");
        return sb.toString();
    }

    // --- Classic Problems ---

    // Check balanced parentheses: (){}[]
    public static boolean isBalanced(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) return false;
                char open = stack.pop();
                if ((c == ')' && open != '(') ||
                    (c == '}' && open != '{') ||
                    (c == ']' && open != '[')) return false;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Stack: " + stack);           // top -> [3, 2, 1]
        System.out.println("Peek:  " + stack.peek());    // 3
        System.out.println("Pop:   " + stack.pop());     // 3
        System.out.println("Stack: " + stack);           // top -> [2, 1]

        System.out.println("\"({[]})\" balanced: " + isBalanced("({[]})"));  // true
        System.out.println("\"({[})\"  balanced: " + isBalanced("({[})"));   // false
    }
}
