import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int size;
  private int openSites;

  /**
   * Connections to top and bottom of grid. System percolates iff virtual top site
   * is connected to virtual bottom site.
   * 
   */
  private final int virtualTop;
  private final int virtualBottom;

  /**
   * Grid of sites. Site value is false if closed; true if open.
   * 
   */
  private final boolean[][] grid;

  private final WeightedQuickUnionUF weightedQuickUnionUF;

  /**
   * Creates a percolation system modeled by an n-by-n grid with all sites
   * blocked.
   *
   * @param n grid size
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Input n must be greater than 0");
    }

    this.size = n;
    this.openSites = 2;
    this.virtualTop = n * n;
    this.virtualBottom = n * n + 1;

    grid = new boolean[n + 1][n + 1];

    weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
  }

  /**
   * Opens a given site if it is not open already. When a site is opened, union()
   * is called 0, 1, 2, 3, or 4 times.
   *
   * @param row grid coordinate
   * @param col grid coordinate
   */
  public void open(int row, int col) {
    validateIndices(row, col);

    if (isOpen(row, col)) {
      return;
    }

    if (row == 1) {
      connectVirtualTopNode(row, col);
    } else if (row == this.size) {
      connectVirtualBottomNode(row, col);
    }

    connectTopNode(row, col);
    connectBottomNode(row, col);
    connectRightNode(row, col);
    connectLeftNode(row, col);
  }

  /**
   * Returns true if a given site is open.
   *
   * @param row grid coordinate
   * @param col grid coordinate
   * 
   * @return boolean
   */
  public boolean isOpen(int row, int col) {
    validateIndices(row, col);

    return this.grid[row - 1][col - 1];
  }

  /**
   * Returns true if a given site in the grid is full. A full site is an open site
   * that can be connected to an open site in the top row via a chain of
   * neighboring (left, right, up, down) open sites
   *
   * @param row grid coordinate
   * @param col grid coordinate
   * 
   * @return boolean
   */
  public boolean isFull(int row, int col) {
    validateIndices(row, col);

    return weightedQuickUnionUF.connected(mapTo1DIndex(row, col), this.virtualTop);
  }

  /**
   * Returns the number of open sites in the grid.
   * 
   * @return int
   */
  public int numberOfOpenSites() {
    return this.openSites;
  }

  /**
   * Returns true if the system percolates. A system percolates if there is a full
   * site in the bottom row. In other words, a system percolates if we fill all
   * open sites connected to the top row and that process fills some open site on
   * the bottom row
   * 
   * @return boolean
   */
  public boolean percolates() {
    return this.weightedQuickUnionUF.connected(this.virtualTop, this.virtualBottom);
  }

  /**
   * Uniquely maps a 2-dimensional set of coordinates to a 1-dimensional one.
   *
   * @param row grid coordinate
   * @param col grid coordinate
   * 
   * @return int
   */
  private int mapTo1DIndex(int row, int col) {
    return (row * this.size) + col;
  }

  /**
   * Throws an IndexOutOfBoundsException if indices are invalid.
   *
   * @param i grid coordinate
   * @param j grid coordinate
   */
  private void validateIndices(int i, int j) {
    if (i <= 0 || i > this.size) {
      throw new IndexOutOfBoundsException("Index i out of bounds");
    } else if (j <= 0 || j > this.size) {
      throw new IndexOutOfBoundsException("Index j out of bounds");
    }
  }

  private void connectVirtualTopNode(int row, int col) {
    weightedQuickUnionUF.union(this.virtualTop, mapTo1DIndex(row, col));

    ++this.openSites;
  }

  private void connectVirtualBottomNode(int row, int col) {
    weightedQuickUnionUF.union(this.virtualBottom, mapTo1DIndex(row, col));

    ++this.openSites;
  }

  private void connectTopNode(int row, int col) {
    if (row > 1 && isOpen(row - 1, col)) {
      return;
    }

    this.weightedQuickUnionUF.union(mapTo1DIndex(row, col), mapTo1DIndex(row - 1, col));

    ++this.openSites;
  }

  private void connectBottomNode(int row, int col) {
    if (row < this.size && isOpen(row + 1, col)) {
      return;
    }

    this.weightedQuickUnionUF.union(mapTo1DIndex(row, col), mapTo1DIndex(row + 1, col));

    ++this.openSites;
  }

  private void connectRightNode(int row, int col) {
    if (col < this.size && isOpen(row, col + 1)) {
      return;
    }

    this.weightedQuickUnionUF.union(mapTo1DIndex(row, col), mapTo1DIndex(row, col + 1));

    ++this.openSites;
  }

  private void connectLeftNode(int row, int col) {
    if (col > 1 && isOpen(row, col - 1)) {
      return;
    }

    this.weightedQuickUnionUF.union(mapTo1DIndex(row, col), mapTo1DIndex(row, col - 1));

    ++this.openSites;
  }
}
