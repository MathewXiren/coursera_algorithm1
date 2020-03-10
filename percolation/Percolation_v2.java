/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 2020.2.26
 *  Description: percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation_v2 {

    private final int size;
    private int[][] grid;
    private WeightedQuickUnionUF unionFound;
    private int count;
    private int[] check;
    private boolean p = false;

    public Percolation_v2(int n) {
        this.size = n;
        this.count = 0;
        if (n <= 0) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        this.grid = new int[n][n];
        this.check = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = 0;
            }
            this.check[i] = 0;  // check whether bottom dot is connected
        }
        // n*n is the top virtual point, n*n+1 is the bottom virtual point
        this.unionFound = new WeightedQuickUnionUF(n * n + 2);

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
            // down
            // if (row - 1 == this.size - 1) {
            //     int p = (row - 1) * this.size + col - 1;
            //     int q = this.size * this.size + 1;
            //     if (this.unionFound.find(p) != p) {
            //         this.unionFound.union(p, q);
            //     }
            //     // System.out.println(p);
            //     // System.out.println(q);
            //     // System.out.println("union to bottom");
            // }
            // else if (row == this.size - 1) { // if the point  is the second to the bottom
            //     if (isOpen(row + 1, col)) {
            //         int p = (row - 1) * this.size + col - 1;
            //         int q = (row + 1 - 1) * this.size + col - 1;
            //         if (!this.unionFound.connected(p, q)) {
            //             this.unionFound.union(p, q);
            //         }
            //         if (!this.unionFound.connected(q, q + 2)) { //
            //             this.unionFound.union(q, q + 2);
            //         }
            //     }
            // }
            if (row <= this.size - 1) {
                if (isOpen(row + 1, col)) {
                    int p = (row + 1 - 1) * this.size + col - 1;
                    int q = (row - 1) * this.size + col - 1;
                    if (!this.unionFound.connected(p, q)) {
                        this.unionFound.union(p, q);
                    }
                }
            }

            // left
            if (col - 2 >= 0) {
                if (isOpen(row, col - 1)) {
                    int q = (row - 1) * this.size + col - 1;
                    int p = (row - 1) * this.size + col - 1 - 1;
                    if (!this.unionFound.connected(p, q)) {
                        this.unionFound.union(p, q);
                    }
                }

            }


            // right
            if (col <= this.size - 1) {
                if (isOpen(row, col + 1)) {
                    int p = (row - 1) * this.size + col - 1;
                    int q = (row - 1) * this.size + col + 1 - 1;
                    if (!this.unionFound.connected(p, q)) {
                        this.unionFound.union(p, q);
                    }
                }
            }
            // connected to virtual bottom point
            int j = 1;
            while (j <= this.size) {
                int p = (this.size - 1) * this.size + (j - 1);
                if (this.check[j - 1] == 0 && this.isOpen(this.size, j)
                        && this.count >= this.size) {
                    if (this.unionFound.connected(p, this.size * this.size)) {
                        // this.unionFound.union(p, this.size * this.size + 1);
                        // if (this.unionFound.find(p) == this.size * this.size) {
                        this.check[j - 1] = 1;
                        this.p = true;
                    }
                }
                j++;
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
        if (row <= 0 || row > this.size || col <= 0 || col > this.size) {
            throw new IllegalArgumentException(
                    "row is: " + Integer.toString(row) + " col is: " + Integer.toString(col));
        }

        int p = (row - 1) * this.size + col - 1;
        int q = this.size * this.size;
        return this.unionFound.connected(p, q);
    }

    //
    // // returns the number of open sites
    public int numberOfOpenSites() {
        return this.count;
    }

    //
    // // does the system percolate?
    public boolean percolates() {
        // int j = 1;
        // while (j <= this.size) {
        //     int p = (this.size - 1) * this.size + (j - 1);
        //     if (this.check[j - 1] == 1) {
        //         return true;
        //     }
        //     else if (this.check[j - 1] == 0 && this.isOpen(this.size, j)) {
        //         if (this.unionFound.connected(p, this.size * this.size)) {
        //             // this.unionFound.union(p, this.size * this.size + 1);
        //             this.check[j - 1] = 1;
        //             return true;
        //         }
        //     }
        //     j++;
        // }
        // return false;
        return this.p;
        // return this.unionFound.connected(this.size * this.size, this.size * this.size + 1);
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
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Percolation_v2 perc = new Percolation_v2(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);

        }
        System.out.println(perc.isFull(19, 13));
        System.out.println(perc.percolates());

        //     Percolation obj = new Percolation(2);
        //     obj.open(1, 2);
        //     System.out.println("1,2 is open:");
        //     System.out.println(obj.isOpen(1, 2));
        //     obj.open(2, 2);
        //     System.out.println("2,2 is open:");
        //     System.out.println(obj.isOpen(2, 2));
        //     System.out.println("the system is percolated:");
        //     System.out.println(obj.percolates());
        //     System.out.println(obj.unionFound.connected(1, 3));
    }
}
