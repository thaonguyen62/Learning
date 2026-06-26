import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// Binary Search Tree: left < node < right
public class Tree {

    private static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    private Node root;

    // O(log n) avg, O(n) worst (unbalanced)
    public void insert(int val) {
        root = insertRec(root, val);
    }

    private Node insertRec(Node node, int val) {
        if (node == null) return new Node(val);
        if (val < node.val)      node.left  = insertRec(node.left,  val);
        else if (val > node.val) node.right = insertRec(node.right, val);
        // duplicate: ignore
        return node;
    }

    // O(log n) avg
    public boolean search(int val) {
        return searchRec(root, val);
    }

    private boolean searchRec(Node node, int val) {
        if (node == null) return false;
        if (val == node.val) return true;
        return val < node.val ? searchRec(node.left, val) : searchRec(node.right, val);
    }

    // O(log n) avg
    public void delete(int val) {
        root = deleteRec(root, val);
    }

    private Node deleteRec(Node node, int val) {
        if (node == null) return null;
        if (val < node.val) {
            node.left = deleteRec(node.left, val);
        } else if (val > node.val) {
            node.right = deleteRec(node.right, val);
        } else {
            // found — three cases:
            if (node.left == null) return node.right;   // no left child
            if (node.right == null) return node.left;   // no right child
            // two children: replace with in-order successor (min of right subtree)
            node.val = findMin(node.right);
            node.right = deleteRec(node.right, node.val);
        }
        return node;
    }

    private int findMin(Node node) {
        while (node.left != null) node = node.left;
        return node.val;
    }

    // --- Traversals ---

    // Left → Node → Right  →  sorted ascending for BST
    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(Node node, List<Integer> result) {
        if (node == null) return;
        inorderRec(node.left, result);
        result.add(node.val);
        inorderRec(node.right, result);
    }

    // Node → Left → Right
    public List<Integer> preorder() {
        List<Integer> result = new ArrayList<>();
        preorderRec(root, result);
        return result;
    }

    private void preorderRec(Node node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderRec(node.left, result);
        preorderRec(node.right, result);
    }

    // Left → Right → Node
    public List<Integer> postorder() {
        List<Integer> result = new ArrayList<>();
        postorderRec(root, result);
        return result;
    }

    private void postorderRec(Node node, List<Integer> result) {
        if (node == null) return;
        postorderRec(node.left, result);
        postorderRec(node.right, result);
        result.add(node.val);
    }

    // Level by level using queue (BFS)
    public List<List<Integer>> levelOrder() {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                Node node = queue.poll();
                level.add(node.val);
                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }

    // Height = max depth from root to leaf
    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }

    public static void main(String[] args) {
        Tree bst = new Tree();
        //        5
        //       / \
        //      3   7
        //     / \ / \
        //    2  4 6  8
        for (int v : new int[]{5, 3, 7, 2, 4, 6, 8}) bst.insert(v);

        System.out.println("Inorder   (sorted): " + bst.inorder());    // [2,3,4,5,6,7,8]
        System.out.println("Preorder  (root 1st): " + bst.preorder()); // [5,3,2,4,7,6,8]
        System.out.println("Postorder (root last): " + bst.postorder());// [2,4,3,6,8,7,5]
        System.out.println("Level order:          " + bst.levelOrder());// [[5],[3,7],[2,4,6,8]]
        System.out.println("Height: " + bst.height());                  // 3
        System.out.println("Search 4: " + bst.search(4));               // true
        System.out.println("Search 9: " + bst.search(9));               // false

        bst.delete(3);
        System.out.println("After delete(3) inorder: " + bst.inorder()); // [2,4,5,6,7,8]
    }
}
