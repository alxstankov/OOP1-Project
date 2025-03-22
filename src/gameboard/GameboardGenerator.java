package gameboard;

import java.util.ArrayList;
import java.util.List;

public class GameboardGenerator {
    private int length;
    private int width;
    private List<List<String>> gameboard;

    public GameboardGenerator(int length, int width)
    {
        this.length = length;
        this.width = width;
    }

    public void generateBoard()
    {
        gameboard = new ArrayList<>();
        for (int i=0; i<length;i++)
        {
            List<String> row = new ArrayList<>();
            for (int j=0;j<width;j++)
            {
                if(((i == 0) && (j == 0)) || ((i == width-1) && (j == length-1)) )
                {
                    row.add("..");
                }
                else
                {
                    row.add("#");
                }
            }
            gameboard.add(row);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<length;i++)
        {
            List<String> row = gameboard.get(i);
            for (int j=0;j<width;j++)
            {
                builder.append(row.get(j)).append(" ");
                if (row.get(j).equals("#"))
                {
                    builder.append(" ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
