package gameboard;

import java.util.*;

public class GameboardGenerator {
    private int length;
    private int width;
    private List<Cell> result;
    private Set<Cell> visitedCells = new HashSet<>();
    private char[][] gameboard;
    private Random specialCellSelector = new Random();

    public GameboardGenerator(int length, int width)
    {
        this.length = length;
        this.width = width;
        this.gameboard = new char[length+1][width+1];
    }

    public void generateBoard()
    {
        result = new ArrayList<>();
        for (int i=0; i<length;i++)
        {
            for (int j=0;j<width;j++)
            {
                    gameboard[i][j] ='#';
            }
        }
        generatePaths(new Cell(1,1));

        gameboard[0][1] = '.';

        if (gameboard[length-2][width-2] == '#')
        {
            gameboard[length - 2][width - 2] = '.';
        }
        gameboard[length - 1][width - 2] = '.';

        int numEndCells = ((result.size()-1)-(result.size()-1)/2);
        int dragonCellIndex = specialCellSelector.nextInt(numEndCells);
        Cell dragonCell = result.get(dragonCellIndex);
        gameboard[dragonCell.getCordX()][dragonCell.getCordY()] = 'M';

        visitedCells.remove(dragonCell);
        int treasureCellIndex = specialCellSelector.nextInt(visitedCells.size()-1);
        Cell treasureCell = (Cell) visitedCells.toArray()[treasureCellIndex];
        gameboard[treasureCell.getCordX()][treasureCell.getCordY()] = 'T';
    }

    private void generatePaths(Cell startCell)
    {
        gameboard[startCell.getCordX()][startCell.getCordY()] = '.';
        visitedCells.add(startCell);
        result.add(startCell);

        List<Cell> neighbours = checkNeighbours(startCell.getCordX(), startCell.getCordY());


        Collections.shuffle(neighbours);
        for (Cell neighbour : neighbours) {
            if (!visitedCells.contains(neighbour) && countPathNeighbours(neighbour.getCordX(),neighbour.getCordY())<=1) {
                generatePaths(neighbour);
            }
        }

    }

    public List<Cell> checkNeighbours(int x, int y){

        List<Cell> neighbours = new ArrayList<>();

        if (x - 1 > 0) neighbours.add(new Cell(x - 1, y));
        if (y - 1 > 0) neighbours.add(new Cell(x, y - 1));
        if (x + 1 < length - 1) neighbours.add(new Cell(x + 1, y));
        if (y + 1 < width - 1) neighbours.add(new Cell(x, y + 1));
        return neighbours;

    }

    private int countPathNeighbours(int x, int y) {
        int count = 0;
        if (x > 0 && gameboard[x - 1][y] == '.') count++;
        if (x < length - 1 && gameboard[x + 1][y] == '.') count++;
        if (y > 0 && gameboard[x][y - 1] == '.') count++;
        if (y < width - 1 && gameboard[x][y + 1] == '.') count++;
        return count;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<length;i++)
        {
            for (int j=0;j<width;j++)
            {
                builder.append(gameboard[i][j]).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
