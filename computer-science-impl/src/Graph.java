
import java.util.*;
import java.util.Queue;

public class Graph {
    private Map<Integer, List<Integer>> adjList;
    private boolean directed;

    public Graph(boolean directed) {
        this.adjList = new HashMap<>();
        this.directed = directed;
    }

    // ── ADD NODE / EDGE ──────────────────────────────────────
    public void addNode(int node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(int from, int to) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        if (!directed) {
            adjList.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
        }
    }

    public List<Integer> getNeighbors(int node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }

    // ── BFS — duyệt theo tầng, dùng Queue ───────────────────
    public List<Integer> bfs(int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            result.add(curr);

            for (int neighbor : getNeighbors(curr)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return result;
    }

    // ── UTILS ────────────────────────────────────────────────
    public void print() {
        for (Map.Entry<Integer, List<Integer>> entry : adjList.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}
