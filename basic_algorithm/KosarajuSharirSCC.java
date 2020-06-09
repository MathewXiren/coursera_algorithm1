/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class KosarajuSharirSCC {

    private boolean marked[];
    private int[] id;
    private int count;

    public KosarajuSharirSCC(Graph G) {
        marked = new boolean[G.getV()];
        id = new int[G.getV()];
        DepthFirstOrder dfs new DepthFirstOrder(G.reverse());
        for (int v = 0; v < dfs.reversePost(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count = ++;
            }
        }
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        return id[v];

    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public static void main(String[] args) {

    }
}
