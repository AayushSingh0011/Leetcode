class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer>ans =new ArrayList<>();
        int startrow =0;
        int startcol =0;
        int endrow = matrix.length-1;
        int endcol = matrix[0].length-1;

        while( startrow<=endrow && startcol<=endcol ){
            for( int j =startcol ;j<=endcol; j++){
                ans.add(matrix[startrow][j] );}
            for( int i =startrow+1 ;i<=endrow; i++){
               ans.add(matrix[i][endcol] );}
            if(startcol <endrow){   
             for( int j =endcol-1 ;j>=startcol; j--){
                ans.add(matrix[endrow][j] );}}
            if( startcol<endcol){    
             for( int i =endrow-1 ;i>=startrow+1; i--){
                ans.add(matrix[i][startcol] );        
            }
            }
             startrow++;
             startcol++;
             endrow--;
             endcol--;
        }
        return ans;
    }
}    
