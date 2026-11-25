
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        long MOD1 = 1_000_000_007L;
        long MOD2 = 1_000_000_009L;

        long BASE1 = 131;
        long BASE2 = 137;

        long[] pow1 = new long[n + 1];
        long[] pow2 = new long[n + 1];

        long[] prefix1 = new long[n + 1];
        long[] prefix2 = new long[n + 1];

        pow1[0] = 1;
        pow2[0] = 1;

        // Build prefix hashes and powers
        for (int i = 0; i < n; i++) {
            long value = nums[i] + 1L;

            prefix1[i + 1] =
                (prefix1[i] * BASE1 + value) % MOD1;

            prefix2[i + 1] =
                (prefix2[i] * BASE2 + value) % MOD2;

            pow1[i + 1] = (pow1[i] * BASE1) % MOD1;
            pow2[i + 1] = (pow2[i] * BASE2) % MOD2;
        }

        int left = 1;
        int right = n;
        int answer = n;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (hasUniqueSubarray(
                    nums, mid, prefix1, prefix2, pow1, pow2,
                    MOD1, MOD2)) {

                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private boolean hasUniqueSubarray(
            int[] nums,
            int len,
            long[] prefix1,
            long[] prefix2,
            long[] pow1,
            long[] pow2,
            long MOD1,
            long MOD2) {

        int n = nums.length;

        HashMap<Long, Integer> freq = new HashMap<>();

        for (int i = 0; i + len <= n; i++) {
            int j = i + len;

            long hash1 = (
                prefix1[j] - prefix1[i] * pow1[len] % MOD1
                + MOD1
            ) % MOD1;

            long hash2 = (
                prefix2[j] - prefix2[i] * pow2[len] % MOD2
                + MOD2
            ) % MOD2;

            // Combine both hashes into one long key
            long key = (hash1 << 32) | hash2;

            freq.put(key, freq.getOrDefault(key, 0) + 1);
        }

        for (int count : freq.values()) {
            if (count == 1) {
                return true;
            }
        }

        return false;
    }
}
