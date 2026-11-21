
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] children = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        int[] newParent = new int[n];
        int[] oldLast = new int[n];

        int[] last = new int[26];
        Arrays.fill(last, -1);

        // Iterative DFS with enter/exit events
        int[] stack = new int[2 * n + 5];
        int top = 0;

        stack[top++] = 0;

        while (top > 0) {
            int v = stack[--top];

            // Exit event
            if (v < 0) {
                int node = ~v;
                last[s.charAt(node) - 'a'] = oldLast[node];
                continue;
            }

            // Enter event
            int c = s.charAt(v) - 'a';

            oldLast[v] = last[c];

            if (last[c] == -1) {
                newParent[v] = parent[v];
            } else {
                newParent[v] = last[c];
            }

            last[c] = v;

            // Process exit after all children
            stack[top++] = ~v;

            for (int child : children[v]) {
                stack[top++] = child;
            }
        }

        // Build modified tree
        List<Integer>[] modified = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            modified[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            modified[newParent[i]].add(i);
        }

        // Get traversal order
        int[] order = new int[n];
        top = 0;
        int size = 0;

        stack[top++] = 0;

        while (top > 0) {
            int v = stack[--top];
            order[size++] = v;

            for (int child : modified[v]) {
                stack[top++] = child;
            }
        }

        // Calculate subtree sizes in reverse order
        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = size - 1; i >= 0; i--) {
            int v = order[i];

            if (v != 0) {
                ans[newParent[v]] += ans[v];
            }
        }

        return ans;
    }
}
