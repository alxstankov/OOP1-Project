package gameboard.models;

import java.util.*;
/**
 *  The {@code GameboardGenerator} class is responsible generating game boards.
 */
public class GameboardGenerator {
    /**
     * Length of the game board.
     */
    private static int gameboardLength;
    /**
     * Width of the game board.
     */
    private static int gameboardWidth;
    /**
     * Char representation of the game board.
     */
    private static char[][] gameboard;

    /**
     * List of visited cells during game board generation.
     */
    private static List<Cell> visitedCells;

    /**
     * Generates a char matrix game board with the given parameters.
     *
     *  @param length Length of game board
     *  @param width Width of game board
     *  @param monsters Number of monsters on the game board
     *  @param treasures Number of treasures on the game board
     *  @return Char matrix of the game board
     */
    public static char[][] generateBoard(int length, int width, int monsters, int treasures)
    {
        gameboardLength = length;
        gameboardWidth = width;

        gameboard = new char[gameboardLength][gameboardWidth];

        fillCells();

        visitedCells = new ArrayList<>();

        generatePaths(new Cell(1,1));

        gameboard[0][1] = '.';

        if (gameboard[gameboardLength -2][gameboardWidth -2] == '#')
        {
            gameboard[gameboardLength -2][gameboardWidth -2] = '.';
        }
        gameboard[gameboardLength - 1][gameboardWidth - 2] = '.';

        Collections.sort(visitedCells);
        Random specialCellSelector = new Random();

        for (int i = 0; i < monsters; i++) {
            int numEndCells = ((visitedCells.size() - 1) - (visitedCells.size() - 1) / 2);
            int dragonCellIndex = specialCellSelector.nextInt((visitedCells.size() - 1)-numEndCells+1)+numEndCells;
            Cell dragonCell = visitedCells.get(dragonCellIndex);
            gameboard[dragonCell.getCordY()][dragonCell.getCordX()] = 'M';
            visitedCells.remove(dragonCell);
        }

        for (int i = 0; i < treasures; i++) {
            int treasureCellIndex = specialCellSelector.nextInt(visitedCells.size()-1);
            Cell treasureCell = visitedCells.get(treasureCellIndex);
            gameboard[treasureCell.getCordY()][treasureCell.getCordX()] = 'T';
            visitedCells.remove(treasureCell);
        }

        return gameboard;
    }

    /**
     * Fills all the cells of the newly created game board with the wall character.
     */
    private static void fillCells()
    {
        for (int i = 0; i< gameboardLength; i++)
        {
            for (int j = 0; j< gameboardWidth; j++)
            {
                gameboard[i][j] ='#';
            }
        }
    }

    /**
     * Using recursive method, the method generate random paths,that the player can move on.
     * This method calls itself, until there are no unvisited cells with less than 2 neighbouring cells with movable paths.
     *
     * @param startCell The cell from witch the path generation starts
     */
    private static void generatePaths(Cell startCell)
    {
        gameboard[startCell.getCordY()][startCell.getCordX()] = '.';
        visitedCells.add(startCell);

        List<Cell> neighbours = checkNeighbours(startCell.getCordX(), startCell.getCordY());

        Collections.shuffle(neighbours);
        for (Cell neighbour : neighbours) {
            if (!visitedCells.contains(neighbour) && countPathNeighbours(neighbour.getCordX(),neighbour.getCordY())<2) {
                generatePaths(neighbour);
            }
        }

    }

    /**
     * Checks all neighbouring cells, that are not out of bounds
     *
     * @param x X coordinates of the cell for which we are looking around for other cells
     * @param y Y coordinates of the cell for which we are looking around for other cells
     *
     * @return List of all neighbouring cells, that are in bounds of the game board
     */
    private static List<Cell> checkNeighbours(int x, int y){

        List<Cell> neighbours = new ArrayList<>();

        if (x - 1 > 0) neighbours.add(new Cell(x - 1, y));
        if (y - 1 > 0) neighbours.add(new Cell(x, y - 1));
        if (x + 1 < gameboardWidth - 1) neighbours.add(new Cell(x + 1, y));
        if (y + 1 < gameboardLength - 1) neighbours.add(new Cell(x, y + 1));
        return neighbours;

    }
    /**
     * Counts how many neighbouring cells are marked as movable.
     *
     * @param x X coordinates of the cell for which we are looking around for other cells
     * @param y Y coordinates of the cell for which we are looking around for other cells
     *
     * @return Number of neighbouring cells are marked as movable
     */
    private static int countPathNeighbours(int x, int y) {
        int count = 0;
        if (x > 0 && gameboard[y][x-1] == '.') count++;
        if (x < gameboardWidth - 1 && gameboard[y][x+1] == '.') count++;
        if (y > 0 && gameboard[y-1][x] == '.') count++;
        if (y < gameboardLength - 1 && gameboard[y+1][x] == '.') count++;
        return count;
    }
}
