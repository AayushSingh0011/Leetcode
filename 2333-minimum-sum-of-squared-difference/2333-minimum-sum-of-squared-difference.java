
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {

        int n = nums1.length;
        int[] diff = new int[n];

        int max = 0;
        long total = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
            total += diff[i];
        }

        long k = (long) k1 + k2;

        if (k >= total) {
            return 0;
        }

        int low = 0, high = max;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int limit = low;
        long used = 0;
        long ans = 0;
        long count = 0;

        for (int d : diff) {
            int reduced = Math.min(d, limit);
            ans += (long) reduced * reduced;
            used += d - reduced;

            if (d >= limit && limit > 0) {
                count++;
            }
        }

        long remaining = k - used;
        long reductions = Math.min(remaining, count);

        ans -= reductions * (2L * limit - 1);

        return ans;
    }
}
