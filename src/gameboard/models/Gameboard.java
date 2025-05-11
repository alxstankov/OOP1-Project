package gameboard.models;

import events.interfaces.Event;
import events.models.BattleEvent;
import events.models.PlayerLevelUpEvent;
import events.models.TreasureEvent;
import game.models.LevelProcessor;
import treasures.models.Treasure;
import treasures.models.TreasureListGenerator;

import java.util.List;

public class Gameboard {
    private char[][] board;
    private int length;
    private int width;
    private int level;
    private Cell playerCords = new Cell(1,0);
    private List<Treasure> treasureList;

    public Gameboard(char[][] board, LevelProcessor level)
    {
        this.board = board;
        this.length = level.getLength();
        this.width = level.getWidth();
        this.level = level.getLevel();
        this.treasureList = TreasureListGenerator.generateTreasureList(level.getTreasures(), level.getLevel());
        this.board[playerCords.getCordY()][playerCords.getCordX()] = 'P';
    }

    public Event movePlayer(int newCordX, int newCordY)
    {
        Event event = null;
        if (isCordOutOfBounds(newCordX,newCordY) || isCellWall(newCordX,newCordY))
        {
            System.out.println("The player cannot move there");
            return event;
        }

        if (isMazeExit(newCordX,newCordY))
        {
            event = new PlayerLevelUpEvent();
        }

        if (isCellMonster(newCordX, newCordY))
        {
            event = new BattleEvent(level);
        }

        if (isCellTreasure(newCordX, newCordY))
        {
            System.out.println("Treasure encounter");
            event = new TreasureEvent(treasureList.removeFirst());
        }

        board[playerCords.getCordY()][playerCords.getCordX()] = '.';
        board[newCordY][newCordX] = 'P';

        playerCords.setCords(newCordX,newCordY);

        return event;
    }

    private boolean isCellWall(int x, int y)
    {
        return board[y][x] == '#';
    }

    private boolean isCordOutOfBounds(int x, int y)
    {
        return x < 0 || y < 0 || x > width-1 || y > length-1;
    }

    public boolean isMazeExit(int x, int y)
    {
        return x == width-2 && y == length-1;
    }

    private boolean isCellMonster(int x, int y)
    {
        return board[y][x] == 'M';
    }

    private boolean isCellTreasure(int x, int y)
    {
        return board[y][x] == 'T';
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
