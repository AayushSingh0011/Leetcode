
class Solution {

    private long getMaxSum(int l, int r, int[] nums,
                           int[][] jump, long[][] sum) {

        long total = 0;
        int cur = l;
        int LOG = jump.length;

        // Sum the prefix maximums using binary lifting
        for (int p = LOG - 1; p >= 0; p--) {
            if (jump[p][cur] <= r) {
                total += sum[p][cur];
                cur = jump[p][cur];
            }
        }

        total += (long) nums[cur] * (r - cur + 1);

        return total;
    }

    public long countNonDecreasingSubarrays(int[] nums, int k) {

        int n = nums.length;

        // Find the next strictly greater element
        int[] next = new int[n];
        int[] stack = new int[n];
        int top = 0;

        for (int i = n - 1; i >= 0; i--) {

            while (top > 0 &&
                   nums[stack[top - 1]] <= nums[i]) {
                top--;
            }

            next[i] = (top == 0) ? n : stack[top - 1];

            stack[top++] = i;
        }

        // Build binary lifting tables
        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] jump = new int[LOG][n];
        long[][] sum = new long[LOG][n];

        for (int i = 0; i < n; i++) {
            jump[0][i] = next[i];
            sum[0][i] = (long) nums[i] * (next[i] - i);
        }

        for (int p = 1; p < LOG; p++) {
            for (int i = 0; i < n; i++) {

                int mid = jump[p - 1][i];

                if (mid < n) {
                    jump[p][i] = jump[p - 1][mid];
                    sum[p][i] = sum[p - 1][i]
                              + sum[p - 1][mid];
                } else {
                    jump[p][i] = n;
                    sum[p][i] = sum[p - 1][i];
                }
            }
        }

        // Prefix sums of original array
        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Sliding window
        int left = 0;
        long ans = 0;

        for (int right = 0; right < n; right++) {

            while (left <= right) {

                long maxSum = getMaxSum(
                    left, right, nums, jump, sum
                );

                long originalSum =
                    prefix[right + 1] - prefix[left];

                long operations = maxSum - originalSum;

                if (operations <= k) {
                    break;
                }

                left++;
            }

            ans += right - left + 1;
        }

        return ans;
    }
}

