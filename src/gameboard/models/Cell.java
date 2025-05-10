package gameboard.models;


public class Cell{
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
}
