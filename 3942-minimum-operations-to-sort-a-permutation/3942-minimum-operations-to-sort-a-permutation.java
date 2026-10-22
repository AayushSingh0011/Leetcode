
class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        if (n == 1) {
            return 0;
        }

        boolean ascending = true;
        boolean descending = true;

        for (int i = 0; i < n; i++) {
            if (nums[i] != (nums[0] + i) % n) {
                ascending = false;
            }

            if (nums[i] != (nums[0] - i + n) % n) {
                descending = false;
            }
        }

        if (ascending) {
            int zeroIndex = 0;

            while (nums[zeroIndex] != 0) {
                zeroIndex++;
            }

            return Math.min(zeroIndex, n - zeroIndex + 2);
        }

        if (descending) {
            int zeroIndex = 0;
            int maxIndex = 0;

            for (int i = 0; i < n; i++) {
                if (nums[i] == 0) {
                    zeroIndex = i;
                }
                if (nums[i] == n - 1) {
                    maxIndex = i;
                }
            }

            return Math.min(maxIndex + 1, n - zeroIndex);
        }

        return -1;
    }
}
