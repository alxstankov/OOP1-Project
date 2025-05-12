package gameboard.models;


import java.io.Serializable;

public class Cell implements Comparable<Cell>, Serializable {
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

    public void setCords(int x, int y)
    {
        cordX = x;
        cordY = y;
    }


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
