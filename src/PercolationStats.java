import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	protected int n;
	protected int trials;
	
	// The constructor should throw a java.lang.IllegalArgumentException if either n
	// ≤ 0 or trials ≤ 0.
	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0) throw new IllegalArgumentException("inputs n and trials should not be less than or equal to 0");
	}

// sample mean of percolation threshold
	public double mean() {
		return 0;
	}

// sample standard deviation of percolation threshold
	public double stddev() {
		if (this.trials == 1) return Double.NaN;
		
		return 0;
	}

// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return 0;
	}

// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return 0;
	}

	// test client (described below)
	public static void main(String[] args) {
		// Also, include a main() method that takes two command-line arguments n and T,
		// performs T independent computational experiments (discussed above) on an
		// n-by-n grid,
		// and prints the sample mean, sample standard deviation, and the 95% confidence
		// interval
		// for the percolation threshold. Use StdRandom to generate random numbers;
		// use StdStats to compute the sample mean and sample standard deviation.
	}
}