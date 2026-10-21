
class Solution {
    public int minOperations(String s) {
        int n = s.length();

        if (n == 1) {
            return 0;
        }

        if (n == 2) {
            return s.charAt(0) > s.charAt(1) ? -1 : 0;
        }

        char min = 'z';
        char max = 'a';
        char first = s.charAt(0);
        char last = s.charAt(n - 1);

        int[] count = new int[26];
        boolean sorted = true;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            min = (char) Math.min(min, c);
            max = (char) Math.max(max, c);
            count[c - 'a']++;

            if (i > 0 && s.charAt(i - 1) > c) {
                sorted = false;
            }
        }

        if (sorted) {
            return 0;
        }

        if (first == min || last == max) {
            return 1;
        }

        if (first != max || last != min) {
            return 2;
        }

        // First is maximum and last is minimum.
        // Duplicates of either extreme allow 2 operations.
        if (count[max - 'a'] > 1 || count[min - 'a'] > 1) {
            return 2;
        }

        return 3;
    }
}
