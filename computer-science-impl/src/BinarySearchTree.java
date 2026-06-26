
import java.util.LinkedList;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;
        Node(T data) {
            this.data = data;
        }
    }

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    // ── INSERT ──────────────────────────────────────────────
    public void insert(T data) {
        root = insertRec(root, data);
        size++;
    }

    private Node<T> insertRec(Node<T> node, T data) {
        if (node == null) return new Node<>(data);
        int cmp = data.compareTo(node.data);
        if (cmp < 0)      node.left  = insertRec(node.left,  data);
        else if (cmp > 0) node.right = insertRec(node.right, data);
        // cmp == 0: duplicate, ignore
        return node;
    }

    // ── SEARCH ──────────────────────────────────────────────
    public boolean search(T data) {
        return searchRec(root, data);
    }

    private boolean searchRec(Node<T> node, T data) {
        if (node == null) return false;
        int cmp = data.compareTo(node.data);
        if (cmp < 0) return searchRec(node.left,  data);
        if (cmp > 0) return searchRec(node.right, data);
        return true;
    }

    // ── DELETE ──────────────────────────────────────────────
    public void delete(T data) {
        if (search(data)) {
            root = deleteRec(root, data);
            size--;
        }
    }

    private Node<T> deleteRec(Node<T> node, T data) {
        if (node == null) return null;
        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left  = deleteRec(node.left,  data);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, data);
        } else {
            // case 1: leaf hoặc 1 con
            if (node.left  == null) return node.right;
            if (node.right == null) return node.left;
            // case 2: 2 con → thay bằng in-order successor (min của right subtree)
            Node<T> successor = minNode(node.right);
            node.data  = successor.data;
            node.right = deleteRec(node.right, successor.data);
        }
        return node;
    }

    // ── MIN / MAX ────────────────────────────────────────────
    public T min() {
        if (root == null) throw new java.util.NoSuchElementException();
        return minNode(root).data;
    }

    public T max() {
        if (root == null) throw new java.util.NoSuchElementException();
        Node<T> curr = root;
        while (curr.right != null) curr = curr.right;
        return curr.data;
    }

    private Node<T> minNode(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // ── TRAVERSALS ───────────────────────────────────────────
    // left → root → right  (BST → sorted order)
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node<T> node) {
        if (node == null) return;
        inorderRec(node.left);
        System.out.print(node.data + " ");
        inorderRec(node.right);
    }

    // root → left → right
    public void preorder() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(Node<T> node) {
        if (node == null) return;
        System.out.print(node.data + " ");
        preorderRec(node.left);
        preorderRec(node.right);
    }

    // left → right → root
    public void postorder() {
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(Node<T> node) {
        if (node == null) return;
        postorderRec(node.left);
        postorderRec(node.right);
        System.out.print(node.data + " ");
    }

    // từng tầng (BFS)
    public void levelOrder() {
        if (root == null) return;
        LinkedList<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> curr = queue.poll();
            System.out.print(curr.data + " ");
            if (curr.left  != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
        System.out.println();
    }

    // ── UTILS ────────────────────────────────────────────────
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }
}
