import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final int size;
  private final int trials;
  private double[] threshold;

  /**
   * Perform trials as independent experiments on an n-by-n grid.
   * 
   * @param n size
   * @param t trials
   * 
   */
  public PercolationStats(int n, int t) {
    if (n <= 0 || t <= 0) {
      throw new IllegalArgumentException("inputs should not be less than or equal to 0");
    }

    this.size = n;
    this.trials = t;
    this.threshold = new double[trials];
  }

  /**
   * Sample mean of the percolation threshold.
   * 
   * @return double
   * 
   */
  public double mean() {
    return StdStats.mean(this.threshold);
  }

  /**
   * Sample standard deviation of the percolation threshold.
   * 
   * @return double
   * 
   */
  public double stddev() {
    if (this.trials == 1) {
      return Double.NaN;
    }

    return StdStats.stddev(this.threshold);
  }

  /*
   * Low endpoint of 95% confidence interval.
   * 
   * @return double
   * 
   */
  public double confidenceLo() {
    return 0;
  }

  /*
   * High endpoint of 95% confidence interval.
   * 
   * @return double
   * 
   */
  public double confidenceHi() {
    return 0;
  }

  private int random(int n) {
    return StdRandom.uniform(1, n + 1);
  }

  private void performTrials() {
    for (int run = 0; run < this.trials; ++run) {
      this.threshold[run] = performTrial();
    }
  }

  private double performTrial() {
    int openSites = 0;
    int row = 0;
    int col = 0;

    Percolation percolation = new Percolation(this.size);

    do {
      row = random(this.size);
      col = random(this.size);

      if (!percolation.isOpen(row, col)) {
        percolation.open(row, col);
        ++openSites;
      }
    } while (!percolation.percolates());

    return openSites / this.size * this.size;
  }

  /**
   * Test client. Takes two command-line arguments n and T, performs T independent
   * computational experiments on an n-by-n grid, and prints the sample mean,
   * sample standard deviation, and the 95% confidence interval for the
   * percolation threshold. Use StdRandom to generate random numbers; use StdStats
   * to compute the sample mean and sample standard deviation.
   * 
   * @Param args arguments
   * 
   */
  public static void main(String[] args) {
    int size = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);

    PercolationStats percolationStats = new PercolationStats(size, trials);
    percolationStats.performTrials();

    System.out.format("mean = %f", percolationStats.mean());
    System.out.format("stddev = %f", percolationStats.stddev());
    System.out.format("95% confidence interval = [%f, %f]", percolationStats.confidenceLo(),
        percolationStats.confidenceHi());
  }
}