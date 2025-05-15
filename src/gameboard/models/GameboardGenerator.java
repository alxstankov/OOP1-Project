package gameboard.models;

import java.util.*;

public class GameboardGenerator {
    private static int gameboardLength;
    private static int gameboardWidth;
    private static char[][] gameboard;

    public static char[][] generateBoard(int length, int width, int monsters, int treasure)
    {
        gameboardLength = length;
        gameboardWidth = width;

        gameboard = new char[gameboardLength][gameboardWidth];

        fillCells();

        List<Cell> visitedCells = new ArrayList<>();

        generatePaths(new Cell(1,1),visitedCells);

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

        for (int i = 0; i < treasure; i++) {
            int treasureCellIndex = specialCellSelector.nextInt(visitedCells.size()-1);
            Cell treasureCell = visitedCells.get(treasureCellIndex);
            gameboard[treasureCell.getCordY()][treasureCell.getCordX()] = 'T';
            visitedCells.remove(treasureCell);
        }

        return gameboard;
    }

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

    private static void generatePaths(Cell startCell,List<Cell> visitedCells)
    {
        gameboard[startCell.getCordY()][startCell.getCordX()] = '.';
        visitedCells.add(startCell);

        List<Cell> neighbours = checkNeighbours(startCell.getCordX(), startCell.getCordY());

        Collections.shuffle(neighbours);
        for (Cell neighbour : neighbours) {
            if (!visitedCells.contains(neighbour) && countPathNeighbours(neighbour.getCordX(),neighbour.getCordY())<=1) {
                generatePaths(neighbour,visitedCells);
            }
        }

    }

    private static List<Cell> checkNeighbours(int x, int y){

        List<Cell> neighbours = new ArrayList<>();

        if (x - 1 > 0) neighbours.add(new Cell(x - 1, y));
        if (y - 1 > 0) neighbours.add(new Cell(x, y - 1));
        if (x + 1 < gameboardWidth - 1) neighbours.add(new Cell(x + 1, y));
        if (y + 1 < gameboardLength - 1) neighbours.add(new Cell(x, y + 1));
        return neighbours;

    }

    private static int countPathNeighbours(int x, int y) {
        int count = 0;
        if (x > 0 && gameboard[y][x-1] == '.') count++;
        if (x < gameboardWidth - 1 && gameboard[y][x+1] == '.') count++;
        if (y > 0 && gameboard[y-1][x] == '.') count++;
        if (y < gameboardLength - 1 && gameboard[y+1][x] == '.') count++;
        return count;
    }
}
