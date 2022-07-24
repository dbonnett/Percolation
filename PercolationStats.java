import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {


    private final double[] counter;
    private static int n;
    private static int trials;
    private final double t = trials;

    public static void main(String[] args) {
        n = Integer.parseInt(args[0]);

        if (n < 1) {
            throw new IllegalArgumentException("n must be > 0");
        }
        trials = Integer.parseInt(args[1]);
        if (trials < 1) {
            throw new IllegalArgumentException("T must be > 0");
        }
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int T) {
        Percolation perc;
        int row;
        int col;
        counter = new double[T];
        for (int i = 0; i < T; i++) {
            double count = 0;
            perc = new Percolation(n);
            while (!perc.percolates()) {
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    count += 1.0;
                }
            }
            counter[i] = count;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(counter) / (n * n);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(counter) / (n * n);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(t));
    }

}
