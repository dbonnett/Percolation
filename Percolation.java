import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private WeightedQuickUnionUF w;
    private int count = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        this.n = n;
        w = new WeightedQuickUnionUF(n * n * 2 + 2);
    }

    // opens the site (row, col) if it is not open already
    // must make sure if open, parent[i] > n*n+1
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        count += 1;
        int i = (n * (row - 1) + col);
        // open default, ensures root is ghost
        w.union(i + (n * n + 1), i);
        // check up
        if (i <= n) {
            w.union(i, 0);
        } else {
            if (isOpen(row - 1, col)) {
                w.union(i, i - n);
            }
        }

        // check down
        if (i > n * (n - 1)) {
            w.union(i, n * n + 1);
        } else {
            if (isOpen(row + 1, col)) {
                w.union(i, i + n);
            }
        }

        // check left
        if (i % n != 1 && isOpen(row, col - 1)) {
            w.union(i, i - 1);
        }

        // check right
        if (i % n != 0 && isOpen(row, col + 1)) {
            w.union(i, i + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        int i = (n * (row - 1) + col);
        return w.find(i) > (n * n + 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        int i = (n * (row - 1) + col);
        return w.find(0) == w.find(i);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count + 1;
    }

    // does the system percolate?
    public boolean percolates() {
        return w.find(0) == w.find(n * n + 1);
    }
}
