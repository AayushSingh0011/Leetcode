class Solution {
    public String lexGreaterPermutation(String s, String target) {

        int n = s.length();

        // Frequency of characters in s
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Try every position from right to left
        for (int i = n - 1; i >= 0; i--) {

            // Copy frequency so we can check the prefix
            int[] remaining = freq.clone();

            boolean possible = true;

            // Use target[0 ... i-1]
            for (int j = 0; j < i; j++) {

                int ch = target.charAt(j) - 'a';

                if (remaining[ch] == 0) {
                    possible = false;
                    break;
                }

                remaining[ch]--;
            }

            if (!possible) {
                continue;
            }

            // At position i, find the smallest
            // character greater than target[i]
            int targetChar = target.charAt(i) - 'a';

            for (int ch = targetChar + 1; ch < 26; ch++) {

                if (remaining[ch] > 0) {

                    StringBuilder ans = new StringBuilder();

                    // Same prefix as target
                    for (int j = 0; j < i; j++) {
                        ans.append(target.charAt(j));
                    }

                    // Make this position bigger
                    ans.append((char) ('a' + ch));
                    remaining[ch]--;

                    // Add everything else in sorted order
                    for (int j = 0; j < 26; j++) {
                        while (remaining[j] > 0) {
                            ans.append((char) ('a' + j));
                            remaining[j]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}