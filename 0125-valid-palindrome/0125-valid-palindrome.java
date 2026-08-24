class Solution {
    
    public boolean isPalindrome(String s) {

        StringBuilder str = new StringBuilder();

        
        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                str.append(Character.toLowerCase(ch));
            }
        }

      
        for (int i = 0; i < str.length(); i++) {

            int n = str.length();

            if (str.charAt(i) != str.charAt(n - 1 - i)) {
                return false;
            }
        }

        return true;
    }
}
    
