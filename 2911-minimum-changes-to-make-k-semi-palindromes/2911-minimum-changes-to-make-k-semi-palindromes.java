
import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] change = new int[n][n];

        // Step 1: Precompute minimum changes for every substring
        for (int len = 2; len <= n; len++) {
            for (int l = 0; l + len <= n; l++) {
                int r = l + len - 1;
                change[l][r] = Integer.MAX_VALUE;

                for (int d = 1; d < len; d++) {
                    if (len % d != 0) continue;

                    int count = 0;

                    for (int offset = 0; offset < d; offset++) {
                        int left = l + offset;
                        int right = l + offset
                                + ((len - 1 - offset) / d) * d;

                        while (left < right) {
                            if (s.charAt(left) != s.charAt(right)) {
                                count++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    change[l][r] = Math.min(change[l][r], count);
                }
            }
        }

        // Step 2: Dynamic Programming
        int INF = 1_000_000_000;
        int[][] dp = new int[k + 1][n + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {
            for (int end = 2 * parts; end <= n; end++) {
                for (int start = 2 * (parts - 1);
                     start <= end - 2; start++) {

                    if (dp[parts - 1][start] == INF) continue;

                    dp[parts][end] = Math.min(
                        dp[parts][end],
                        dp[parts - 1][start]
                            + change[start][end - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
