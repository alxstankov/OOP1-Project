package gameboard.models;


import java.io.Serializable;

/**
 * The {@code Cell} class is responsible for holding game board coordinates.
 */
public class Cell implements Comparable<Cell>, Serializable {
    /**
     * Coordinates on X axis.
     */
    private int cordX;
    /**
     * Coordinates on Y axis.
     */
    private int cordY;

    /**
     * Constructs a {@code Code} instance with X and Y coordinates.
     */
    public Cell(int x, int y)
    {
        this.cordX = x;
        this.cordY = y;
    }

    /**
     * Gets X coordinates.
     *
     * @return X coordinates
     */
    public int getCordX() {
        return cordX;
    }

    /**
     * Gets Y coordinates.
     *
     * @return Y coordinates
     */
    public int getCordY() {
        return cordY;
    }

    /**
     * Sets new X and Y coordinates
     *
     * @param x New X coordinates
     * @param y New Y coordinates
     */
    public void setCords(int x, int y)
    {
        cordX = x;
        cordY = y;
    }

    /**
     * Compares current and given cell, based on their coordinates.
     * First it compares their Y axis coordinates.
     * If they are equal, X axis coordinates are compared.
     *
     * @param o Cell instance, that is going to be compared to the current Cell instance
     * @return Difference between the two compared cells
     */
    @Override
    public int compareTo(Cell o) {
            int comparison = this.cordY - o.cordY;
            if (comparison == 0)
            {
                comparison = this.cordX - o.cordX;
            }
            return comparison;
    }
}
