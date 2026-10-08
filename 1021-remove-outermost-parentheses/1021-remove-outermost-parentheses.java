class Solution {
    public String removeOuterParentheses(String s) {

        StringBuilder ans = new StringBuilder();
        int level = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (ch == '(') {

                // If level > 0, this is not outermost
                if (level > 0) {
                    ans.append(ch);
                }

                level++;
            }

            else {

                level--;

                // If level > 0, this is not outermost
                if (level > 0) {
                    ans.append(ch);
                }
            }
        }

        return ans.toString();
    }
}