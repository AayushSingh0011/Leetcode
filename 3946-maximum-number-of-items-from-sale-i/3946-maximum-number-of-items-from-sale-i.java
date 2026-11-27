class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] dp = new int[budget + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];

            minPrice = Math.min(minPrice, price);

            // First copy + all free copies
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (items[j][0] % factor == 0) {
                    count++;
                }
            }

            // 0/1 knapsack: activate this item type once
            for (int b = budget; b >= price; b--) {
                dp[b] = Math.max(dp[b], dp[b - price] + count);
            }
        }

        int answer = 0;

        // Spend remaining budget on the cheapest item
        for (int b = 0; b <= budget; b++) {
            int extra = (budget - b) / minPrice;
            answer = Math.max(answer, dp[b] + extra);
        }

        return answer;
    }
}