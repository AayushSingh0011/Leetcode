
import java.util.Arrays;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int answer = 0;

        // Check every divisor k of n
        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;

            // Each block must rotate into its matching sorted segment
            for (int start = 0; start < n; start += k) {
                if (!isCyclicMatch(nums, sorted, start, k)) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                answer += k;
            }
        }

        return answer;
    }

    private boolean isCyclicMatch(int[] nums, int[] sorted,
                                  int start, int k) {
        // Pattern is the sorted segment sorted[start ... start+k-1]
        int[] lps = new int[k];

        // Build KMP LPS array
        int len = 0;
        for (int i = 1; i < k; i++) {
            while (len > 0 && sorted[start + i] != sorted[start + len]) {
                len = lps[len - 1];
            }

            if (sorted[start + i] == sorted[start + len]) {
                len++;
            }

            lps[i] = len;
        }

        // Search pattern in the block repeated cyclically.
        // 2*k-1 elements cover every possible rotation.
        int j = 0;

        for (int t = 0; t < 2 * k - 1; t++) {
            int value = nums[start + (t % k)];

            while (j > 0 && value != sorted[start + j]) {
                j = lps[j - 1];
            }

            if (value == sorted[start + j]) {
                j++;
            }

            if (j == k) {
                return true;
            }
        }

        return false;
    }
}
