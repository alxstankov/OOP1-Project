package gameboard;

import java.util.Objects;

public class Cell {
    private int cordX;
    private int cordY;

    public Cell(int x, int y)
    {
        this.cordX = x;
        this.cordY = y;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell cell)) return false;
        return cordX == cell.cordX && cordY == cell.cordY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cordX, cordY);
    }
}
