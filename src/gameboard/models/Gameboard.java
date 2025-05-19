package gameboard.models;

import events.interfaces.Event;
import events.models.BattleEvent;
import events.models.PlayerLevelUpEvent;
import events.models.TreasureEvent;
import game.models.Level;
import treasures.models.Treasure;
import treasures.models.TreasureListGenerator;

import java.io.Serializable;
import java.util.List;
/**
 *  The {@code Gameboard} class is responsible for tracking player, monster and treasure position.
 */
public class Gameboard implements Serializable {
    /**
     * Char representation of the game board.
     */
    private char[][] board;
    /**
     * Length of the game board.
     */
    private int length;
    /**
     * Width of the game board.
     */
    private int width;
    /**
     *  Numerical representation of the difficulty level.
     */
    private int level;
    /**
     * Current player coordinates.
     */
    private Cell playerCords = new Cell(1,0);
    /**
     * List of treasure for the game board
     */
    private List<Treasure> treasureList;

    /**
     * Constructs a {@code Gameboard} instance with the parameters from the level object.
     *
     * @param level Level object, holding level parameters, needed for game board creation
     */
    public Gameboard(Level level)
    {
        this.length = level.getLength();
        this.width = level.getWidth();
        this.board = GameboardGenerator.generateBoard(length,width,level.getMonsters(),level.getTreasures());
        this.level = level.getLevel();
        this.treasureList = TreasureListGenerator.generateTreasureList(level.getTreasures(), level.getLevel());
        this.board[playerCords.getCordY()][playerCords.getCordX()] = 'P';
    }

    /**
     * Moves the player on the game board.
     * If the player steps into an event, the event instance is created and returned.
     *
     * @param newCordX new X coordinate of the player
     * @param newCordY new Y coordinate of the player
     * @return Event instance of the encountered event
     */
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

    /**
     * Check if a cell on the game board is a wall.
     *
     * @param x X coordinates of the cell
     * @param y Y coordinates of the cell
     * @return Boolean if the cell is a wall
     */
    private boolean isCellWall(int x, int y)
    {
        return board[y][x] == '#';
    }

    /**
     * Check if a cell on the game board is out of bounds.
     *
     * @param x X coordinates of the cell
     * @param y Y coordinates of the cell
     * @return Boolean if the cell is out of bounds
     */
    private boolean isCordOutOfBounds(int x, int y)
    {
        return x < 0 || y < 0 || x > width-1 || y > length-1;
    }

    /**
     * Check if a cell on the game board is the maze exit.
     *
     * @param x X coordinates of the cell
     * @param y Y coordinates of the cell
     * @return Boolean if the cell is the maze exit
     */
    public boolean isMazeExit(int x, int y)
    {
        return x == width-2 && y == length-1;
    }

    /**
     * Check if a cell on the game board is a monster.
     *
     * @param x X coordinates of the cell
     * @param y Y coordinates of the cell
     * @return Boolean if the cell is a monster
     */
    private boolean isCellMonster(int x, int y)
    {
        return board[y][x] == 'M';
    }

    /**
     * Check if a cell on the game board is a monster.
     *
     * @param x X coordinates of the cell
     * @param y Y coordinates of the cell
     * @return Boolean if the cell is a treasure
     */
    private boolean isCellTreasure(int x, int y)
    {
        return board[y][x] == 'T';
    }

    /**
     * Gets the Current player coordinates.
     *
     * @return Cell instance of the current player coordinates
     */
    public Cell getPlayerCords()
    {
        return playerCords;
    }

    /**
     * Gets string representation of the game board.
     *
     * @return String representation of the game board
     */
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
