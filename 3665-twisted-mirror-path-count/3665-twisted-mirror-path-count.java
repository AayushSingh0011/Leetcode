
class Solution {
    public int uniquePaths(int[][] grid) {
        int MOD = 1_000_000_007;

        int m = grid.length;
        int n = grid[0].length;

        // Ways arriving from left (moving right)
        long[][] right = new long[m][n];

        // Ways arriving from top (moving down)
        long[][] down = new long[m][n];

        right[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                long r = right[i][j];
                long d = down[i][j];

                if (grid[i][j] == 1) {

                    // Entered mirror from left: turn down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + r) % MOD;
                    }

                    // Entered mirror from top: turn right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + d) % MOD;
                    }

                } else {

                    // Empty cell: continue in either direction
                    long ways = (r + d) % MOD;

                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + ways) % MOD;
                    }

                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + ways) % MOD;
                    }
                }
            }
        }

        return (int)((right[m - 1][n - 1]
                    + down[m - 1][n - 1]) % MOD);
    }
}
