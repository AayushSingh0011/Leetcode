
import java.util.*;

class Solution {

    class SlidingMedian {
        TreeMap<Integer, Integer> low = new TreeMap<>();
        TreeMap<Integer, Integer> high = new TreeMap<>();

        int lowSize = 0, highSize = 0;
        long lowSum = 0, highSum = 0;

        void addMap(TreeMap<Integer, Integer> map, int val) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        void removeMap(TreeMap<Integer, Integer> map, int val) {
            int count = map.get(val);
            if (count == 1) map.remove(val);
            else map.put(val, count - 1);
        }

        void add(int val) {
            if (low.isEmpty() || val <= low.lastKey()) {
                addMap(low, val);
                lowSize++;
                lowSum += val;
            } else {
                addMap(high, val);
                highSize++;
                highSum += val;
            }
            balance();
        }

        void remove(int val) {
            if (low.containsKey(val)) {
                removeMap(low, val);
                lowSize--;
                lowSum -= val;
            } else {
                removeMap(high, val);
                highSize--;
                highSum -= val;
            }
            balance();
        }

        void balance() {
            int target = (lowSize + highSize + 1) / 2;

            while (lowSize > target) {
                int val = low.lastKey();
                removeMap(low, val);
                lowSize--;
                lowSum -= val;

                addMap(high, val);
                highSize++;
                highSum += val;
            }

            while (lowSize < target) {
                int val = high.firstKey();
                removeMap(high, val);
                highSize--;
                highSum -= val;

                addMap(low, val);
                lowSize++;
                lowSum += val;
            }
        }

        long getCost() {
            int median = low.lastKey();

            return (long) median * lowSize - lowSum
                 + highSum - (long) median * highSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        // Step 1: Calculate the cost of every window of size x
        long[] cost = new long[n - x + 1];

        SlidingMedian sm = new SlidingMedian();

        for (int i = 0; i < x; i++) {
            sm.add(nums[i]);
        }

        cost[0] = sm.getCost();

        for (int i = x; i < n; i++) {
            sm.remove(nums[i - x]);
            sm.add(nums[i]);

            cost[i - x + 1] = sm.getCost();
        }

        // Step 2: Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        Arrays.fill(prev, 0);

        for (int j = 1; j <= k; j++) {
            long[] curr = new long[n + 1];
            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Do not select a window ending at i
                curr[i] = curr[i - 1];

                // Select window [i-x, i-1]
                if (i >= x && prev[i - x] != INF) {
                    curr[i] = Math.min(
                        curr[i],
                        prev[i - x] + cost[i - x]
                    );
                }
            }

            prev = curr;
        }

        return prev[n];
    }
}
