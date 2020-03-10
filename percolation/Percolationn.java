/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 2020.2.26
 *  Description: percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.QuickFindUF;

public class Percolationn {

    private final int size;
    private int[][] grid;
    private int[][] board;
    // private int top;
    // private int bottom;
    public QuickFindUF unionFound;
    private int count;

    public Percolationn(int n) {
        this.size = n;
        this.count = 0;
        if (n <= 0) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        this.grid = new int[n][n];
        this.board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = 0;
            }
        }
        // n*n is the top virtual point, n*n+1 is the bottom virtual point
        this.unionFound = new QuickFindUF(n * n + 2);
        // int c = 0;
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         this.board[i][j] = c;
        //         c++;
        //     }
        // }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > this.size || col <= 0 || col > this.size) {
            throw new IllegalArgumentException(
                    "row is: " + Integer.toString(row) + " col is: " + Integer.toString(col));
        }
        if (!this.isOpen(row, col)) {
            this.grid[row - 1][col - 1] = 1;
            this.count++;
            // up union
            if (row - 1 == 0) {
                int p = (row - 1) * this.size + col - 1;
                int q = this.size * this.size;
                // System.out.println(p);
                // System.out.println(q);
                this.unionFound.union(p, q);
                // System.out.println("union to top");
            }
            else if (row - 2 >= 0) {
                if (isOpen(row - 1, col)) {
                    int p = (row - 1) * this.size + col - 1;
                    int q = (row - 1 - 1) * this.size + col - 1;
                    if (!this.unionFound.connected(p, q)) {
                        this.unionFound.union(p, q);
                    }
                }

            }
            //down
            if (row - 1 == this.size - 1) {
                int p = (row - 1) * this.size + col - 1;
                int q = this.size * this.size + 1;
                this.unionFound.union(p, q);
                // System.out.println(p);
                // System.out.println(q);
                // System.out.println("union to bottom");
            }
            else if (row - 2 <= this.size - 1) {
                if (isOpen(row + 1, col)) {
                    int p = (row - 1) * this.size + col - 1;
                    int q = (row + 1 - 1) * this.size + col - 1;
                    if (!this.unionFound.connected(p, q)) {
                        this.unionFound.union(p, q);
                    }
                }
            }

            //left
            if (col - 2 >= 0) {
                if (isOpen(row, col - 1)) {
                    int p = (row - 1) * this.size + col - 1;
                    int q = (row - 1) * this.size + col - 1 - 1;
                    if (!this.unionFound.connected(p, q)) {
                        this.unionFound.union(p, q);
                    }
                }

            }


            //right
            if (col + 1 <= this.size) {
                if (isOpen(row, col + 1)) {
                    int p = (row - 1) * this.size + col - 1;
                    int q = (row - 1) * this.size + col + 1 - 1;
                    if (!this.unionFound.connected(p, q)) {
                        this.unionFound.union(p, q);
                    }
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
        if (this.grid[row - 1][col - 1] == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    //
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int p = (row - 1) * this.size + col - 1;
        int q = this.size * this.size;
        if (this.unionFound.connected(p, q)) {
            return true;
        }
        else {
            return false;
        }
    }

    //
    // // returns the number of open sites
    public int numberOfOpenSites() {
        return this.count;
    }

    //
    // // does the system percolate?
    public boolean percolates() {
        return this.unionFound.connected(this.size * this.size, this.size * this.size + 1);
    }
    // private void union(int row, int col) {
    //     this.isOpen( int row, int col);
    //
    //     row - 1, col
    //     row + 1, col
    //     row, col - 1
    //     row, col + 1
    //
    //
    // }

    public static void main(String[] args) {
        System.out.println("hello");
        Percolationn obj = new Percolationn(2);
        obj.open(1, 2);
        System.out.println("1,2 is open:");
        System.out.println(obj.isOpen(1, 2));
        obj.open(2, 2);
        System.out.println("2,2 is open:");
        System.out.println(obj.isOpen(2, 2));
        System.out.println("the system is percolated:");
        System.out.println(obj.percolates());
        System.out.println(obj.unionFound.connected(1, 3));
    }


}
