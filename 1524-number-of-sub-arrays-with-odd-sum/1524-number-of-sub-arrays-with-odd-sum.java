class Solution {
    public int numOfSubarrays(int[] arr) {
        long even = 1, odd = 0, ans = 0;
        int prefix = 0;
        int MOD = 1_000_000_007;

        for (int num : arr) {
            prefix ^= (num & 1);

            if (prefix == 1) {
                ans = (ans + even) % MOD;
                odd++;
            } else {
                ans = (ans + odd) % MOD;
                even++;
            }
        }

        return (int) ans;
    }
}