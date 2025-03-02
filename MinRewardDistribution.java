import java.util.*;

public class MinRewardDistribution {

    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        int[] rewards = new int[n];
        Arrays.fill(rewards, 1); // Every employee gets at least 1 reward

        // Build the graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges from lower-rated employees to higher-rated employees
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                graph.get(i - 1).add(i); // Edge from i-1 to i
            } else if (ratings[i] < ratings[i - 1]) {
                graph.get(i).add(i - 1); // Edge from i to i-1
            }
        }

        // Perform BFS to distribute rewards
        Queue<Integer> queue = new LinkedList<>();
        int[] inDegree = new int[n];

        // Calculate in-degrees
        for (int i = 0; i < n; i++) {
            for (int neighbor : graph.get(i)) {
                inDegree[neighbor]++;
            }
        }

        // Add employees with in-degree 0 to the queue
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // BFS
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : graph.get(current)) {
                // Ensure the neighbor gets more rewards than the current employee
                rewards[neighbor] = Math.max(rewards[neighbor], rewards[current] + 1);
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Sum up all rewards
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }

        return totalRewards;
    }

    public static void main(String[] args) {
        int[] ratings1 = { 1, 0, 2 };
        System.out.println(minRewards(ratings1)); // Output: 5

        int[] ratings2 = { 1, 2, 2 };
        System.out.println(minRewards(ratings2)); // Output: 4
    }
}