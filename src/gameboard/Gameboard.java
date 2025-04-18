package gameboard;

public class Gameboard {
    private char[][] board;
    private int length;
    private int width;
    private Cell playerCords = new Cell(0,1);

    public Gameboard(char[][] board, int length, int width)
    {
        this.board = board;
        this.length = length;
        this.width = width;
        this.board[playerCords.getCordX()][playerCords.getCordY()] = 'P';
    }

    public void movePlayer(int newCordX, int newCordY)
    {
        if (isCordOutOfBounds(newCordX,newCordY) || isCellWall(newCordX,newCordY))
        {
            System.out.println("The player cannot move there");
            return;
        }

        if (isMazeExit(newCordX,newCordY))
        {
            System.out.println("Maze cleared");
        }

        if (isCellMonster(newCordX, newCordY))
        {
            System.out.println("Monster encounter");
        }

        if (isCellTreasure(newCordX, newCordY))
        {
            System.out.println("Treasure encounter");
        }

        board[playerCords.getCordX()][playerCords.getCordY()] = '.';
        board[newCordX][newCordY] = 'P';

        playerCords.setCords(newCordX,newCordY);
    }

    private boolean isCellWall(int x, int y)
    {
        return board[x][y] == '#';
    }

    private boolean isCordOutOfBounds(int x, int y)
    {
        return x < 0 || y < 0 || x > length-1 || y > width-1;
    }

    public boolean isMazeExit(int x, int y)
    {
        return x == length-1 && y == width-2;
    }

    private boolean isCellMonster(int x, int y)
    {
        return board[x][y] == 'M';
    }

    private boolean isCellTreasure(int x, int y)
    {
        return board[x][y] == 'T';
    }

    public Cell getPlayerCords()
    {
        return playerCords;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<length;i++)
        {
            for (int j=0;j<width;j++)
            {
                builder.append(board[i][j]).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
