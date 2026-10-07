class Solution {

    HashSet<String> result = new HashSet<>();

    public List<String> removeInvalidParentheses(String s) {

        int leftRemove = 0;
        int rightRemove = 0;

        // Find number of extra '(' and ')'
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '(') {
                leftRemove++;
            } 
            else if (s.charAt(i) == ')') {

                if (leftRemove > 0) {
                    leftRemove--;
                } 
                else {
                    rightRemove++;
                }
            }
        }

        backtrack(s, 0, 0, leftRemove, rightRemove, "");

        return new ArrayList<>(result);
    }

    public void backtrack(
        String s,
        int index,
        int open,
        int leftRemove,
        int rightRemove,
        String current
    ) {

        // End of string
        if (index == s.length()) {

            if (leftRemove == 0 &&
                rightRemove == 0 &&
                open == 0) {

                result.add(current);
            }

            return;
        }

        char ch = s.charAt(index);

        // Case 1: '('
        if (ch == '(') {

            // Remove '('
            if (leftRemove > 0) {
                backtrack(
                    s,
                    index + 1,
                    open,
                    leftRemove - 1,
                    rightRemove,
                    current
                );
            }

            // Keep '('
            backtrack(
                s,
                index + 1,
                open + 1,
                leftRemove,
                rightRemove,
                current + ch
            );
        }

        // Case 2: ')'
        else if (ch == ')') {

            // Remove ')'
            if (rightRemove > 0) {
                backtrack(
                    s,
                    index + 1,
                    open,
                    leftRemove,
                    rightRemove - 1,
                    current
                );
            }

            // Keep ')' only if there is '(' available
            if (open > 0) {
                backtrack(
                    s,
                    index + 1,
                    open - 1,
                    leftRemove,
                    rightRemove,
                    current + ch
                );
            }
        }

        // Case 3: Letter
        else {

            backtrack(
                s,
                index + 1,
                open,
                leftRemove,
                rightRemove,
                current + ch
            );
        }
    }
}