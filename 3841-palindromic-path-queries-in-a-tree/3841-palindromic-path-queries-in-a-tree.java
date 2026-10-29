
import java.util.*;

class Solution {
    private int n, timer;
    private List<Integer>[] adj;
    private int[] tin, tout, depth, parent;
    private int[][] up;
    private int[] bit;
    private char[] s;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        this.n = n;
        this.s = s.toCharArray();

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        parent = new int[n];
        up = new int[LOG][n];
        timer = 0;

        dfs(0, -1, 0);

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        bit = new int[timer + 2];

        for (int i = 0; i < n; i++) {
            int mask = 1 << (this.s[i] - 'a');
            add(tin[i], mask);
            add(tout[i], mask);
        }

        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                if (this.s[u] != c) {
                    int change = (1 << (this.s[u] - 'a'))
                               ^ (1 << (c - 'a'));

                    add(tin[u], change);
                    add(tout[u], change);
                    this.s[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);
                int l = lca(u, v);

                int mask = prefix(tin[u]) ^ prefix(tin[v])
                         ^ (1 << (this.s[l] - 'a'));

                result.add(Integer.bitCount(mask) <= 1);
            }
        }

        return result;
    }

    private void dfs(int u, int p, int d) {
        parent[u] = (p == -1) ? u : p;
        up[0][u] = parent[u];
        depth[u] = d;
        tin[u] = ++timer;

        for (int v : adj[u]) {
            if (v != p) {
                dfs(v, u, d + 1);
            }
        }

        tout[u] = ++timer;
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; diff > 0; j++, diff >>= 1) {
            if ((diff & 1) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = up.length - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }

    private void add(int i, int mask) {
        for (; i < bit.length; i += i & -i) {
            bit[i] ^= mask;
        }
    }

    private int prefix(int i) {
        int mask = 0;

        for (; i > 0; i -= i & -i) {
            mask ^= bit[i];
        }

        return mask;
    }
}
