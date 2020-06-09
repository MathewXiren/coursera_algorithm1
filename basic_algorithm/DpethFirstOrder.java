/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class DpethFirstOrder {
    private boolean[] marked;
    private Stack<Integer> reversePost;

    public DpethFirstOrder(Graph G) {

        reversePost = new Stack<Integer>();
        int v = G.getV();
        marked = new boolean[v];
        for (int i = 0; i < v; i++) {
            marked[i] = false;
        }

        for (int i = 0; i < v; i++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }

    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }


    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost() {
        return reversePost();
    }

    // public Iterable<Integer> pathTo(int v) {
    //     if (!hasPathTo(v)) return null;
    //     Stack<Integer> path = new Stack<Integer>();
    //     for (int x = v; x != s; x = edgeTo[x]) {
    //         path.push(x);
    //     }
    //     path.push(s);
    //     return path;
    // }

    public static void main(String[] args) {

    }
}
