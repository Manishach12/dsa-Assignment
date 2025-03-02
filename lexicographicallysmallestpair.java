import java.util.Arrays;

class LexicographicallySmallestPair {

    // Custom class to store point data
    static class Point {
        int x, y, index;

        Point(int x, int y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
        }
    }

    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length;
        int minDistance = Integer.MAX_VALUE;
        int[] closestPair = new int[2];

        Point[] points = new Point[n];

        // Step 1: Transform points into (X + Y) space
        for (int i = 0; i < n; i++) {
            points[i] = new Point(x_coords[i] + y_coords[i], x_coords[i] - y_coords[i], i);
        }

        // Step 2: Sort by transformed coordinate (X + Y)
        Arrays.sort(points, (a, b) -> a.x - b.x);

        // Step 3: Check consecutive elements for minimum Manhattan distance
        for (int i = 0; i < n - 1; i++) {
            int distance = Math.abs(points[i].x - points[i + 1].x) + Math.abs(points[i].y - points[i + 1].y);

            // Step 4: Update closest pair if distance is smaller
            if (distance < minDistance || (distance == minDistance && isLexicographicallySmaller(points[i].index,
                    points[i + 1].index, closestPair[0], closestPair[1]))) {
                minDistance = distance;
                closestPair[0] = points[i].index;
                closestPair[1] = points[i + 1].index;
            }
        }

        Arrays.sort(closestPair); // Ensure lexicographical order
        return closestPair;
    }

    // Helper method to check lexicographical order
    private static boolean isLexicographicallySmaller(int i1, int j1, int i2, int j2) {
        if (i1 < i2)
            return true;
        if (i1 == i2 && j1 < j2)
            return true;
        return false;
    }

    public static void main(String[] args) {
        // Example Input
        int[] x_coords = { 1, 2, 3, 2, 4 };
        int[] y_coords = { 2, 3, 1, 2, 3 };

        // Find the closest pair
        int[] result = findClosestPair(x_coords, y_coords);

        // Print the result
        System.out.println("Closest Pair Indices: [" + result[0] + ", " + result[1] + "]");
    }
}
