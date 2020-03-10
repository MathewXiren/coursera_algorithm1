/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 2020.2.26
 *  Description: percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private boolean[][] grid;
    private WeightedQuickUnionUF unionFound; // check whether a point is percolate
    private WeightedQuickUnionUF unionFound2; // check whether a point is full

    private int count;

    public Percolation(int n) {
        this.size = n;
        this.count = 0;
        if (n <= 0) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        this.grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = false;
            }
        }
        // n*n is the top virtual point, n*n+1 is the bottom virtual point
        this.unionFound = new WeightedQuickUnionUF(n * n + 2);
        this.unionFound2 = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > this.size || col <= 0 || col > this.size) {
            throw new IllegalArgumentException(
                    "row is: " + Integer.toString(row) + " col is: " + Integer.toString(col));
        }

        if (!this.isOpen(row, col)) {
            this.grid[row - 1][col - 1] = true;
            this.count++;
            // up union
            if (row - 1 == 0) {
                int q = (row - 1) * this.size + col - 1;
                int p = this.size * this.size;
                this.unionFound.union(p, q);
                this.unionFound2.union(p, q);

            }
            else if ((row - 2) >= 0) {
                if (isOpen(row - 1, col)) {
                    int q = (row - 1) * this.size + col - 1;
                    int p = (row - 1 - 1) * this.size + col - 1;
                    // if (!this.unionFound.connected(p, q)) {
                    this.unionFound.union(p, q);

                    // }
                    this.unionFound2.union(p, q);
                }

            }

            // down
            if (row - 1 == this.size - 1) {
                int q = (row - 1) * this.size + col - 1;
                int p = this.size * this.size + 1;
                // if (this.unionFound.find(p) != p) {
                this.unionFound.union(p, q);
                // }
            }
            else if (row <= this.size - 1) {
                if (isOpen(row + 1, col)) {
                    int q = (row + 1 - 1) * this.size + col - 1;
                    int p = (row - 1) * this.size + col - 1;
                    // if (!this.unionFound.connected(p, q)) {
                    this.unionFound.union(p, q);

                    // }
                    this.unionFound2.union(p, q);
                }
            }

            // left
            if (col - 2 >= 0) {
                if (isOpen(row, col - 1)) {
                    int q = (row - 1) * this.size + col - 1;
                    int p = (row - 1) * this.size + col - 1 - 1;
                    // if (!this.unionFound.connected(p, q)) {
                    this.unionFound.union(p, q);

                    // }
                    this.unionFound2.union(p, q);
                }

            }
            // right
            if (col <= this.size - 1) {
                if (isOpen(row, col + 1)) {
                    int q = (row - 1) * this.size + col - 1;
                    int p = (row - 1) * this.size + col + 1 - 1;
                    // if (!this.unionFound.connected(p, q)) {
                    this.unionFound.union(p, q);

                    // }
                    this.unionFound2.union(p, q);
                }
            }

        }


    }

    // // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > this.size || col <= 0 || col > this.size) {
            throw new IllegalArgumentException(
                    "row is" + Integer.toString(row) + " col is" + Integer.toString(col));
        }
        return this.grid[row - 1][col - 1];
    }

    //
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > this.size || col <= 0 || col > this.size) {
            throw new IllegalArgumentException(
                    "row is: " + Integer.toString(row) + " col is: " + Integer.toString(col));
        }

        int p = (row - 1) * this.size + col - 1;
        int q = this.size * this.size;
        return this.unionFound2.connected(p, q);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.count;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.unionFound.connected(this.size * this.size, this.size * this.size + 1);
    }


    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);

        }
        System.out.println(perc.isFull(19, 13));
        System.out.println(perc.percolates());

    }
}
