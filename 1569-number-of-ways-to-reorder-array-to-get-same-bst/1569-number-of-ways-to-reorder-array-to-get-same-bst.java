
class Solution {
    static final int MOD = 1_000_000_007;
    long[][] comb;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        // Precompute combinations: C(n, r)
        comb = new long[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            comb[i][0] = comb[i][i] = 1;

            for (int j = 1; j < i; j++) {
                comb[i][j] = (comb[i - 1][j - 1]
                            + comb[i - 1][j]) % MOD;
            }
        }

        long ways = countWays(nums);
        return (int) ((ways - 1 + MOD) % MOD);
    }

    private long countWays(int[] nums) {
        int n = nums.length;

        if (n <= 2) {
            return 1;
        }

        int root = nums[0];

        int[] left = new int[n - 1];
        int[] right = new int[n - 1];

        int l = 0, r = 0;

        // Separate values into left and right subtrees
        for (int i = 1; i < n; i++) {
            if (nums[i] < root) {
                left[l++] = nums[i];
            } else {
                right[r++] = nums[i];
            }
        }

        // Trim arrays to their actual sizes
        int[] leftArr = new int[l];
        int[] rightArr = new int[r];

        System.arraycopy(left, 0, leftArr, 0, l);
        System.arraycopy(right, 0, rightArr, 0, r);

        // Choose positions for left-subtree elements
        long waysToInterleave = comb[l + r][l];

        long leftWays = countWays(leftArr);
        long rightWays = countWays(rightArr);

        return waysToInterleave * leftWays % MOD
                                  * rightWays % MOD;
    }
}
