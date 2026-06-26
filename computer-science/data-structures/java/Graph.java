import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

// Undirected weighted graph using adjacency list
public class Graph {

    private static class Edge {
        int to, weight;
        Edge(int to, int weight) { this.to = to; this.weight = weight; }
    }

    private final int vertices;
    private final List<List<Edge>> adj;

    public Graph(int vertices) {
        this.vertices = vertices;
        adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) adj.add(new ArrayList<>());
    }

    // Add undirected edge
    public void addEdge(int u, int v) {
        addEdge(u, v, 1);
    }

    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Edge(v, weight));
        adj.get(v).add(new Edge(u, weight));  // remove this line for directed graph
    }

    // --- BFS: shortest path (unweighted), level-order traversal ---
    // O(V + E)
    public List<Integer> bfs(int start) {
        List<Integer> visited_order = new ArrayList<>();
        boolean[] visited = new boolean[vertices];
        Deque<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            visited_order.add(node);
            for (Edge e : adj.get(node)) {
                if (!visited[e.to]) {
                    visited[e.to] = true;
                    queue.offer(e.to);
                }
            }
        }
        return visited_order;
    }

    // BFS shortest path: returns hop count from start to end (-1 if unreachable)
    public int shortestPath(int start, int end) {
        boolean[] visited = new boolean[vertices];
        int[] dist = new int[vertices];
        Arrays.fill(dist, -1);
        Deque<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        dist[start] = 0;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == end) return dist[node];
            for (Edge e : adj.get(node)) {
                if (!visited[e.to]) {
                    visited[e.to] = true;
                    dist[e.to] = dist[node] + 1;
                    queue.offer(e.to);
                }
            }
        }
        return -1;
    }

    // --- DFS: detect cycle, topological sort, connected components ---
    // O(V + E)
    public List<Integer> dfs(int start) {
        List<Integer> visited_order = new ArrayList<>();
        boolean[] visited = new boolean[vertices];
        dfsRec(start, visited, visited_order);
        return visited_order;
    }

    private void dfsRec(int node, boolean[] visited, List<Integer> result) {
        visited[node] = true;
        result.add(node);
        for (Edge e : adj.get(node)) {
            if (!visited[e.to]) dfsRec(e.to, visited, result);
        }
    }

    // Check if graph has cycle (undirected)
    public boolean hasCycle() {
        boolean[] visited = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            if (!visited[i] && hasCycleRec(i, visited, -1)) return true;
        }
        return false;
    }

    private boolean hasCycleRec(int node, boolean[] visited, int parent) {
        visited[node] = true;
        for (Edge e : adj.get(node)) {
            if (!visited[e.to]) {
                if (hasCycleRec(e.to, visited, node)) return true;
            } else if (e.to != parent) {
                return true;  // back edge to non-parent = cycle
            }
        }
        return false;
    }

    // Count connected components
    public int countComponents() {
        boolean[] visited = new boolean[vertices];
        int count = 0;
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfsRec(i, visited, new ArrayList<>());
                count++;
            }
        }
        return count;
    }

    // --- Dijkstra: shortest weighted path from start to all nodes ---
    // O((V + E) log V) with priority queue
    public int[] dijkstra(int start) {
        int[] dist = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // PriorityQueue: [distance, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, start});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], node = curr[1];

            if (d > dist[node]) continue;  // stale entry

            for (Edge e : adj.get(node)) {
                int newDist = dist[node] + e.weight;
                if (newDist < dist[e.to]) {
                    dist[e.to] = newDist;
                    pq.offer(new int[]{newDist, e.to});
                }
            }
        }
        return dist;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices; i++) {
            sb.append(i).append(": ");
            for (Edge e : adj.get(i)) sb.append(e.to).append("(w=").append(e.weight).append(") ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Graph:
        // 0 -- 1 -- 2
        // |    |
        // 3 -- 4
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 4);
        g.addEdge(3, 4);

        System.out.println("Adjacency list:\n" + g);
        System.out.println("BFS from 0: " + g.bfs(0));          // [0,1,3,2,4]
        System.out.println("DFS from 0: " + g.dfs(0));          // [0,1,2,4,3]
        System.out.println("Has cycle:  " + g.hasCycle());       // true (0-1-4-3-0)
        System.out.println("Components: " + g.countComponents()); // 1
        System.out.println("Shortest 0→2 (hops): " + g.shortestPath(0, 2)); // 2

        System.out.println("\n--- Dijkstra (weighted) ---");
        Graph wg = new Graph(4);
        wg.addEdge(0, 1, 4);
        wg.addEdge(0, 2, 1);
        wg.addEdge(2, 1, 2);
        wg.addEdge(1, 3, 1);
        int[] dist = wg.dijkstra(0);
        System.out.println("Distances from 0: " + Arrays.toString(dist)); // [0, 3, 1, 4]
    }
}
