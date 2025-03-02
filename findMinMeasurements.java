class FindMinMeasurementsBinarySearch {

    private static int binomialCoefficient(int m, int k) {
        if (k == 0 || k == m)
            return 1;
        if (k > m)
            return 0;

        long res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (m - i + 1) / i;
            if (res > Integer.MAX_VALUE)
                return Integer.MAX_VALUE; // Prevent overflow
        }
        return (int) res;
    }

    // Function to check how many levels we can test with m measurements and k
    // materials
    private static int getMaxLevels(int m, int k) {
        int sum = 0;
        for (int i = 1; i <= k; i++) {
            sum += binomialCoefficient(m, i);
            if (sum >= Integer.MAX_VALUE)
                return Integer.MAX_VALUE; // Prevent overflow
        }
        return sum;
    }

    // Binary search to find the minimum number of measurements
    public static int findMinMeasurements(int k, int n) {
        int left = 1, right = n, ans = n;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (getMaxLevels(mid, k) >= n) {
                ans = mid;
                right = mid - 1; // Try for a smaller number of measurements
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        // Example 1
        int k1 = 1, n1 = 2;
        System.out.println("Example 1: k = " + k1 + ", n = " + n1);
        System.out.println("Minimum measurements: " + findMinMeasurements(k1, n1)); // Output: 2

        // Example 2
        int k2 = 2, n2 = 6;
        System.out.println("Example 2: k = " + k2 + ", n = " + n2);
        System.out.println("Minimum measurements: " + findMinMeasurements(k2, n2)); // Output: 3

        // Example 3
        int k3 = 3, n3 = 14;
        System.out.println("Example 3: k = " + k3 + ", n = " + n3);
        System.out.println("Minimum measurements: " + findMinMeasurements(k3, n3)); // Output: 4
    }
}
