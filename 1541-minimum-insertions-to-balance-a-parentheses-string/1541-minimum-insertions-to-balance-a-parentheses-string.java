
class Solution {
    public int minInsertions(String s) {
        int open = 0;
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '(') {
                open++;
            } else {

                // If the next character is not ')',
                // insert one ')' to complete the pair
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++;
                } else {
                    ans++;
                }

                // Match the '))' pair with an opening '('
                if (open > 0) {
                    open--;
                } else {
                    // No opening '(' available
                    ans++;
                }
            }
        }

        return ans + open * 2;
    }
}
