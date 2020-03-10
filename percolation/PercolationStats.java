/* *****************************************************************************
 *  Name: Xiren Ma
 *  Date: 2020.2.26
 *  Description: percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // perform independent trials on an n-by-n grid

    private final int trials;
    // private int numberofopensites;
    // private int state;
    private double average = -1;
    private double std = -1;
    private double[] x;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "The n is: " + Integer.toString(n) + " The trails are: " + Integer
                            .toString(trials));
        }

        this.trials = trials;
        // StdRandom random = new StdRandom();
        this.x = new double[this.trials];
        int count = 0;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int number = StdRandom.uniform(n * n);
                int row = number / n + 1;
                int col = number % n + 1;
                percolation.open(row, col);
            }
            int numberofopensites = percolation.numberOfOpenSites();
            // System.out.println(numberofopensites);
            double ave = numberofopensites / (double) (n * n);
            // System.out.println(ave);
            this.x[count++] = ave;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        this.average = StdStats.mean(x);
        return average;
    }

    //
    // // sample standard deviation of percolation threshold
    public double stddev() {
        this.std = StdStats.stddev(x);
        return std;
    }

    // // low endpoint of 95% confiden}ce interval
    public double confidenceLo() {
        double low;
        if (this.average == -1 || this.std == -1) {
            low = StdStats.mean(x) - 1.96 * StdStats.stddev(x) / Math.sqrt(this.trials);

        }
        else {
            low = this.average - 1.96 * this.std / Math.sqrt(this.trials);
        }
        return low;
    }

    //
    // // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double high;
        if (this.average == -1 || this.std == -1) {
            high = StdStats.mean(x) + 1.96 * StdStats.stddev(x) / Math.sqrt(this.trials);

        }
        else {
            high = this.average + 1.96 * this.std / Math.sqrt(this.trials);
        }
        return high;
    }


    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        // int n = 2;
        // int t = 10000;
        Stopwatch watch = new Stopwatch();
        PercolationStats ps = new PercolationStats(n, t);
        double time = watch.elapsedTime();
        System.out.println("The running time is: " + time);
        System.out.print("mean                          = ");
        System.out.println(ps.mean());
        System.out.print("stddev                        = ");
        System.out.println(ps.stddev());
        System.out.print("95% confidence interval       = ");
        System.out.println("[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        System.out.println("[" + ps.confidenceHi() + ", " + ps.confidenceLo() + "]");

    }
}
