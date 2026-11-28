import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;

        // Count items by factor and find the cheapest price
        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        // Count how many distinct item types each factor can give
        int[] gain = new int[n + 1];

        for (int factor = 1; factor <= n; factor++) {
            int count = 0;

            for (int multiple = factor; multiple <= n; multiple += factor) {
                count += freq[multiple];
            }

            // Exclude the item itself, but keep other equal-factor items
            gain[factor] = count - 1;
        }

        // Encode useful deals: price in upper 32 bits, gain in lower 32
        long[] deals = new long[n];
        int size = 0;

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            if (gain[factor] > 0 && (long) price < 2L * minPrice) {
                deals[size++] = ((long) price << 32)
                        | (gain[factor] & 0xffffffffL);
            }
        }

        Arrays.sort(deals, 0, size);

        long answer = 0;
        long remaining = budget;

        // Buy the cheapest useful value-2 copies first
        for (int i = 0; i < size; i++) {
            long deal = deals[i];

            int price = (int) (deal >>> 32);
            int count = (int) deal;

            long buy = Math.min(count, remaining / price);

            answer += buy * 2;
            remaining -= buy * price;
        }

        // Spend the remaining budget on cheapest ordinary copies
        answer += remaining / minPrice;

        return (int) answer;
    }
}