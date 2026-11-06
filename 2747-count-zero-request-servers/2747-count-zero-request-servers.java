
import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int m = queries.length;
        int[][] sortedQueries = new int[m][2];

        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        Arrays.sort(sortedQueries, (a, b) ->
            Integer.compare(a[0], b[0]));

        int[] ans = new int[m];

        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int right = 0;

        for (int[] q : sortedQueries) {
            int time = q[0];
            int index = q[1];

            // Add logs within the right boundary
            while (right < logs.length && logs[right][1] <= time) {
                int server = logs[right][0];
                map.put(server, map.getOrDefault(server, 0) + 1);
                right++;
            }

            // Remove logs outside the left boundary
            while (left < right && logs[left][1] < time - x) {
                int server = logs[left][0];

                map.put(server, map.get(server) - 1);

                if (map.get(server) == 0) {
                    map.remove(server);
                }

                left++;
            }

            ans[index] = n - map.size();
        }

        return ans;
    }
}
