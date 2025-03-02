
import java.util.*;

class UnionFind {
    private int[] parent, rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return false;  // Already connected
        
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        
        return true;
    }
}

class Solution {
    public int minTotalCost(int n, int[] modules, int[][] connections) {
        List<int[]> edges = new ArrayList<>();
        
        // Virtual edges for installing communication modules
        for (int i = 0; i < n; i++) {
            edges.add(new int[]{modules[i], 0, i + 1}); // (cost, virtual node 0, device i+1)
        }
        
        // Direct connections
        for (int[] conn : connections) {
            edges.add(new int[]{conn[2], conn[0], conn[1]}); // (cost, device1, device2)
        }
        
        // Sort edges by cost (ascending order)
        edges.sort(Comparator.comparingInt(a -> a[0]));

        UnionFind uf = new UnionFind(n + 1);  // Including virtual node 0
        int minCost = 0, connected = 0;
        
        // Kruskal's Algorithm
        for (int[] edge : edges) {
            int cost = edge[0], u = edge[1], v = edge[2];
            if (uf.union(u, v)) {
                minCost += cost;
                connected++;
            }
            if (connected == n) break; // We need exactly n connections
        }
        
        return connected == n ? minCost : -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int n = 3;
        int[] modules = {1, 2, 2};
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};

        System.out.println(solution.minTotalCost(n, modules, connections)); // Output: 3
    }
}
