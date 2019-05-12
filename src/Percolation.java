// use WeightedQuickUnionUF.java

// TODO: move us to GitHub issues
// TODO: add JavaDoc

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

public class Percolation {

	// what is stored: grid size, open sites, which sites are connected to other
	// sites

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {

	}

	// open site (row, col) if it is not open already
	public void open() {
		// first, validate the indices of the site received

		// second, mark the site as open

		// third, perform some sequence of WeightedQuickUnionUF operations that links
		// the
		// site in question to its open neighbors.
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		return true;
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {		
		return false;
	}

	// number of open sites
	public int numberOfOpenSites() {
		return 0;
	}

	// does the system percolate?
	public boolean percolates() {
		return true;
	}
	
	// test client (optional)
	public static void main(String[] args) {
	}

	// uniquely maps 2D coordinates to 1D coordinates
	private int xyTo1D(int x, int y) {
		return 0;
	}

	// throw an exception if invalid
	private boolean validateIndices(int x, int y) {
		return true;
	}
}
