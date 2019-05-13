import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// use WeightedQuickUnionUF.java

// Submission:

// We will supply algs4.jar.
// Your submission may not call library functions except those in
// StdIn, StdOut, StdRandom, StdStats, WeightedQuickUnionUF, and java.lang.

// Estimates the value of the *percolation threshold* via Monte Carlo simulation

// Tips:
// It's OK to use an extra row and/or column to deal with the 1-based
// indexing of the percolation grid.
// (less efficient, but results in cleaner code to account for 1-based indexing)

// Each of the methods (except the constructor) in Percolation must use a 
// constant number of union-find operations
// NO FOR LOOPS!
// instead, virtual-top / virtual-bottom trick
// Introduce 2 virtual sites (and connections to top and bottom). (like extra node on top + bot)
// Percolates iff virtual top site is connected to virtual bottom site.

//  (For the insulating/metallic materials example, the open sites correspond to metallic materials, 
// so that a system that percolates has a metallic path from top to bottom, with full sites
// conducting. For the porous substance example, the open sites correspond to empty space
// through which water might flow, so that a system that percolates lets water fill open sites,
// flowing from top to bottom.)

public class Percolation {
	private int size;

	/**
	 * Connections to top and bottom of grid. System percolates iff virtual top site
	 * is connected to virtual bottom site.
	 * 
	 */
	private final int virtualTop, virtualBottom;

	/**
	 * Grid of sites. Site value is false if closed; true if open.
	 * 
	 */
	private boolean[][] grid;
	
	private WeightedQuickUnionUF weightedQuickUnionUF;

	/**
	 * Creates a percolation system modeled by an n-by-n grid with all sites
	 * blocked.
	 *
	 * @param n - grid size
	 */
	public Percolation(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Input n must be greater than 0");

		this.size = n;
		this.virtualTop = n ^ 2;
		this.virtualBottom = n ^ 2 + 1;

		grid = new boolean[n + 1][n + 1];

		weightedQuickUnionUF = new WeightedQuickUnionUF(n ^ 2 + 2);
	}

	// Monte carlo alg:
	// Initialize all sites to be blocked.
	// Repeat the following until the system percolates:
	// Choose a site uniformly at random among all blocked sites.
	// Open the site.
	// The fraction of sites that are opened when the system percolates provides an
	// estimate of the percolation threshold.

	// all methods except constructo should take constant time plus a constant
	// number
	// of calls to the union–find methods

	/**
	 * Opens a given site if it is not open already. When a site is opened, union()
	 * is called 0, 1, 2, 3, or 4 times.
	 *
	 * @param row
	 * @param col
	 */
	public void open(int row, int col) {
		validateIndices(row, col);

		if (isOpen(row, col))
			return;

		int current = mapTo1DIndex(row, col);

		if (row == 1)
			weightedQuickUnionUF.union(current, this.virtualTop);
		else if (row == this.size)
			weightedQuickUnionUF.union(current, this.virtualBottom);

		connectTopNode(row, col);
		connectBottomNode(row, col);
		connectRightNode(row, col);
		connectLeftNode(row, col);
	}

	/**
	 * Returns true if a given site is open.
	 *
	 * @param row
	 * @param col
	 */
	public boolean isOpen(int row, int col) {
		validateIndices(row, col);

		return this.grid[row - 1][col - 1]; // TODO: optional? == true
	}

	/**
	 * Returns true if a given site in the grid is full. A full site is an open site
	 * that can be connected to an open site in the top row via a chain of
	 * neighboring (left, right, up, down) open sites
	 *
	 * @param row
	 * @param col
	 */
	public boolean isFull(int row, int col) {
		validateIndices(row, col);

		return weightedQuickUnionUF.connected(mapTo1DIndex(row, col), this.virtualTop);
		// A full site is an open site that can be connected to an
		// open site in the top row via a chain of neighboring (left, right, up, down)
		// open sites
	}

	/**
	 * Returns the number of open sites in the grid
	 * 
	 */
	public int numberOfOpenSites() {
		return 0;
	}

	/**
	 * Returns true if the system percolates. A system percolates if there is a full
	 * site in the bottom row. In other words, a system percolates if we fill all
	 * open sites connected to the top row and that process fills some open site on
	 * the bottom row
	 * 
	 */
	public boolean percolates() {
		return this.weightedQuickUnionUF.connected(this.virtualTop, this.virtualBottom);
	}

	/**
	 * Uniquely maps a 2-dimensional set of coordinates to a 1-dimensional one
	 *
	 * @param row
	 * @param col
	 */
	private int mapTo1DIndex(int row, int col) {
		return row * this.size + col;
	}

	/**
	 * Throws an IndexOutOfBoundsException if indices are invalid
	 *
	 * @param i
	 * @param j
	 */
	private void validateIndices(int i, int j) {
		if (i <= 0 || i > this.size)
			throw new IndexOutOfBoundsException("Index i out of bounds");
		else if (j <= 0 || j > this.size)
			throw new IndexOutOfBoundsException("Index j out of bounds");
	}

	private void connectTopNode(int row, int col) {
		if (row > 1 && isOpen(row - 1, col))
			return;

		this.weightedQuickUnionUF.union(mapTo1DIndex(row - 1, col), mapTo1DIndex(row, col));
	}

	private void connectBottomNode(int row, int col) {
		if (row < this.size && isOpen(row + 1, col))
			return;

		this.weightedQuickUnionUF.union(mapTo1DIndex(row + 1, col), mapTo1DIndex(row, col));
	}

	private void connectRightNode(int row, int col) {
		if (col < this.size && isOpen(row, col + 1))
			return;

		this.weightedQuickUnionUF.union(mapTo1DIndex(row, col + 1), mapTo1DIndex(row, col));
	}

	private void connectLeftNode(int row, int col) {
		if (col > 1 && isOpen(row, col - 1))
			return;

		this.weightedQuickUnionUF.union(mapTo1DIndex(row, col - 1), mapTo1DIndex(row, col));
	}
}
