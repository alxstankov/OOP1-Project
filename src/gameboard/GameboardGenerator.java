package gameboard;

import java.util.*;

public class GameboardGenerator {
    private int length;
    private int width;
    private char[][] gameboard;

    // Implements level params.
    public char[][] generateBoard(int length, int width)
    {
        setLength(length);
        setWidth(width);

        this.gameboard = new char[this.length+1][this.width+1];

        fillCells();

        List<Cell> result = new ArrayList<>();
        Set<Cell> visitedCells = new HashSet<>();

        generatePaths(new Cell(1,1),result,visitedCells);

        gameboard[0][1] = '.';

        if (gameboard[this.length-2][this.width-2] == '#')
        {
            gameboard[this.length-2][this.width-2] = '.';
        }
        gameboard[this.length - 1][this.width - 2] = '.';

        Random specialCellSelector = new Random();

        int numEndCells = ((result.size()-1)-(result.size()-1)/2);
        int dragonCellIndex = specialCellSelector.nextInt(numEndCells);
        Cell dragonCell = result.get(dragonCellIndex);
        gameboard[dragonCell.getCordY()][dragonCell.getCordX()] = 'M';

        visitedCells.remove(dragonCell);
        int treasureCellIndex = specialCellSelector.nextInt(visitedCells.size()-1);
        Cell treasureCell = (Cell) visitedCells.toArray()[treasureCellIndex];
        gameboard[treasureCell.getCordY()][treasureCell.getCordX()] = 'T';

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

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private void generatePaths(Cell startCell,List<Cell> result, Set<Cell> visitedCells)
    {
        gameboard[startCell.getCordY()][startCell.getCordX()] = '.';
        visitedCells.add(startCell);
        result.add(startCell);

        List<Cell> neighbours = checkNeighbours(startCell.getCordX(), startCell.getCordY());


        Collections.shuffle(neighbours);
        for (Cell neighbour : neighbours) {
            if (!visitedCells.contains(neighbour) && countPathNeighbours(neighbour.getCordX(),neighbour.getCordY())<=1) {
                generatePaths(neighbour,result,visitedCells);
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
