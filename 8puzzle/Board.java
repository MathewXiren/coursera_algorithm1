/* * ****************************************************************************
 *  Name: Xiren Ma
 *  Date: 3.10.2020
 *  Description: 8 puzzle board
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.Iterator;

public class Board {
    private final int[][] tiles;
    private final int N;
    private int ham;
    private int manh;
    private int x; // zero location

    public Board(int[][] tiles) {
        this.N = tiles.length;
        this.tiles = new int[N][N];

        for (int i = 0; i < N; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }

        ham = 0;
        manh = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != 0 && this.tiles[i][j] != (i * N + j + 1)) {
                    ham++;
                }
                if (this.tiles[i][j] != 0 && this.tiles[i][j] != (i * N + j + 1)) {
                    int c = Math.abs((this.tiles[i][j] - 1) / N - i) + Math
                            .abs((this.tiles[i][j] - 1) % N - j);
                    manh += c;
                }
                if (tiles[i][j] == 0) {
                    x = i * N + j;
                }
            }
        }

    }

    // // string representation of this board
    public String toString() {
        String s = String.valueOf(N);
        for (int i = -1; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == -1) {
                    s += ' ';
                }
                else {
                    s = s + " " + tiles[i][j];
                }
            }
            s = s + "\n";
        }
        return s;
    }

    //
    // // board dimension n
    public int dimension() {
        return N;
    }

    //
    // // number of tiles out of place
    public int hamming() {
        return ham;
    }

    // // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manh;
    }

    // // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    //
    // // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass().getName().equals("Board")) {

            return this.toString().compareTo(y.toString()) == 0;
        }
        else {
            return false;
        }
    }

    public Board twin() {
        int m;
        int[][] tt = new int[N][N];
        for (int i = 0; i < N; i++) {
            tt[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
        // System.out.println("x:" + x);
        if (x < N * N - 2) {
            m = x + 1;
            int temp = tt[m / N][m % N];
            tt[m / N][m % N] = tt[(m + 1) / N][(m + 1) % N];
            tt[(m + 1) / N][(m + 1) % N] = temp;
        }
        else if (x >= N * N - 2) {
            m = x - 1;
            int r = m - 1;
            int temp = tt[m / N][m % N];
            tt[m / N][m % N] = tt[r / N][r % N];
            tt[r / N][r % N] = temp;
        }
        return new Board(tt);
    }

    // // all neighboring boards
    public Iterable<Board> neighbors() {
        return new BoardIterator(tiles);
    }

    private class BoardIterator implements Iterable<Board> {
        private Queue<Board> neightbors = new Queue<Board>();
        // private final int[][] board = tiles.clone();

        public BoardIterator(int[][] tiles) {
            // int[][] tt = tiles.clone();
            int N = tiles.length;
            int x = 0;
            int y = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (tiles[i][j] == 0) {
                        x = i;
                        y = j;
                        break;
                    }
                }
            }

            if (x > 0) {
                int[][] b = new int[N][N];
                for (int i = 0; i < N; i++) {
                    b[i] = Arrays.copyOf(tiles[i], tiles[i].length);
                }
                // int[][] b = tiles.clone();
                b[x][y] = b[x - 1][y];
                b[x - 1][y] = 0;
                neightbors.enqueue(new Board(b));
            }
            // down
            if (x < N - 1) {
                int[][] b = new int[N][N];
                for (int i = 0; i < N; i++) {
                    b[i] = Arrays.copyOf(tiles[i], tiles[i].length);
                }
                b[x][y] = b[x + 1][y];
                b[x + 1][y] = 0;
                neightbors.enqueue(new Board(b));
            }
            // left
            if (y > 0) {
                int[][] b = new int[N][N];
                for (int i = 0; i < N; i++) {
                    b[i] = Arrays.copyOf(tiles[i], tiles[i].length);
                }
                b[x][y] = b[x][y - 1];
                b[x][y - 1] = 0;
                neightbors.enqueue(new Board(b));
            }
            // right
            if (y < N - 1) {
                int[][] b = new int[N][N];
                for (int i = 0; i < N; i++) {
                    b[i] = Arrays.copyOf(tiles[i], tiles[i].length);
                }
                b[x][y] = b[x][y + 1];
                b[x][y + 1] = 0;
                neightbors.enqueue(new Board(b));
            }
        }

        public Iterator<Board> iterator() {
            return neightbors.iterator();
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();

        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board initial = new Board(tiles);
        System.out.println(initial);
        System.out.println("hamming:" + initial.hamming());
        System.out.println("Manhattan:" + initial.hamming());
        Iterable<Board> loop = initial.neighbors();
        Iterator<Board> it = loop.iterator();
        while (it.hasNext()) {
            Board nei = it.next();
            System.out.println(nei);
            System.out.println(initial.equals(nei));

        }
    }
}

