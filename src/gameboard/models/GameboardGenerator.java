package gameboard.models;

import java.io.Serializable;
import java.util.*;

public class GameboardGenerator implements Serializable {
    private int length;
    private int width;
    private char[][] gameboard;

    public char[][] generateBoard(int length, int width, int monsters, int treasure)
    {
        this.length = length;
        this.width = width;

        this.gameboard = new char[this.length][this.width];

        fillCells();

        List<Cell> visitedCells = new ArrayList<>();

        generatePaths(new Cell(1,1),visitedCells);

        gameboard[0][1] = '.';

        if (gameboard[this.length-2][this.width-2] == '#')
        {
            gameboard[this.length-2][this.width-2] = '.';
        }
        gameboard[this.length - 1][this.width - 2] = '.';

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

    private void fillCells()
    {
        for (int i=0; i<length;i++)
        {
            for (int j=0;j<width;j++)
            {
                gameboard[i][j] ='#';
            }
        }
    }

    private void generatePaths(Cell startCell,List<Cell> visitedCells)
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

    public List<Cell> checkNeighbours(int x, int y){

        List<Cell> neighbours = new ArrayList<>();

        if (x - 1 > 0) neighbours.add(new Cell(x - 1, y));
        if (y - 1 > 0) neighbours.add(new Cell(x, y - 1));
        if (x + 1 < width - 1) neighbours.add(new Cell(x + 1, y));
        if (y + 1 < length - 1) neighbours.add(new Cell(x, y + 1));
        return neighbours;

    }

    private int countPathNeighbours(int x, int y) {
        int count = 0;
        if (x > 0 && gameboard[y][x-1] == '.') count++;
        if (x < width - 1 && gameboard[y][x+1] == '.') count++;
        if (y > 0 && gameboard[y-1][x] == '.') count++;
        if (y < length - 1 && gameboard[y+1][x] == '.') count++;
        return count;
    }
}
