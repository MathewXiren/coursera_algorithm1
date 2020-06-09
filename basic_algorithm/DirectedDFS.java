/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class DirectedDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    // DFS marks all vertices connected to s in time proportional to the sum of their degrees.

    public DirectedDFS(Graph G, int s) {
        this.s = s;
        int v = G.getV();
        marked = new boolean[v];
        edgeTo = new int[v];
        for (int i = 0; i < v; i++) {
            marked[i] = false;
        }

        dfs(G, s);
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }


    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
                edgeTo[w] = v;
            }
        }
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public static void main(String[] args) {

    }
}
