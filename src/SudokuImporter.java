import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * User: Tanner
 * Date: 11/7/2014
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuImporter {
    static int[][] grid = new int[9][9];
    static int[][] solutionGrid = new int[9][9];
    int row, column, number, puzzleNum;
    Random rand = new Random();

    public SudokuImporter(boolean load) {
    	InputStream[] puzzles = new InputStream[4];
    	InputStream[] solutions = new InputStream[4];
        String[] txtFiles = {"easy.txt", "medium.txt", "hard.txt", "evil.txt"};
        String[] solutionTxtFiles = {"easySol.txt", "mediumSol.txt", "hardSol.txt", "evilSol.txt"};
        InputStream url;
        for (int i = 0; i < puzzles.length; i++) {
            url = this.getClass().getClassLoader().getResourceAsStream(txtFiles[i]);
            if (url != null)
                puzzles[i] = url;
            url = this.getClass().getClassLoader().getResourceAsStream(solutionTxtFiles[i]);
            if (url != null)
                solutions[i] = url;
        }
        puzzleNum = rand.nextInt(3);
        int difficulty = 0;
        BufferedReader reader = null;
        int count = 0;
        System.out.println(puzzleNum);
        if (DashBoardMenu.difficultyBox.getSelectedItem().toString().contains("Easy"))
            difficulty = 0;
        if (DashBoardMenu.difficultyBox.getSelectedItem().toString().contains("Medium"))
            difficulty = 1;
        if (DashBoardMenu.difficultyBox.getSelectedItem().toString().contains("Hard"))
            difficulty = 2;
        if (DashBoardMenu.difficultyBox.getSelectedItem().toString().contains("Evil"))
            difficulty = 3;

        try {
            reader = new BufferedReader(new InputStreamReader(puzzles[difficulty], "UTF-8"));
            String line;
            if (load)
            while ((line = reader.readLine()) != null) {
                String[] test = line.split(",");
                if(count != puzzleNum)
                {
                    if(line.contains("/"))
                    {
                        count++;
                    }
                }
                else {
                    if(line.contains("/"))
                        break;
                    if (line.contains(",")) {
                        row = Integer.parseInt(test[0]);
                        column = Integer.parseInt(test[1]);
                        number = Integer.parseInt(test[2]);
                        grid[column][row] = number;
                        System.out.println("Row: " + row + " Column: " + column + " Number: " + number +
                                " Difficulty: " + difficulty);
                    }
                }
            }
            reader = new BufferedReader(new InputStreamReader(solutions[difficulty], "UTF-8"));
            count = 0;
            if (load)
            while ((line = reader.readLine()) != null) {
                String[] test = line.split(",");
                if (count != puzzleNum)
                {
                    if(line.contains("/"))
                    {
                        count ++;
                    }
                }
                else {
                    if (line.contains("/")) {
                        break;
                    }
                    else if (line.contains(",")) {
                        row = Integer.parseInt(test[0]);
                        column = Integer.parseInt(test[1]);
                        number = Integer.parseInt(test[2]);
                        solutionGrid[column][row] = number;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int[] getRow(int row) {
        int[] arr = new int[9];
        for (int i = 0; i < 9; i++) {
            arr[i] = grid[i][row];
        }
        return arr;
    }

    public int[] getColumn(int column) {
        return grid[column];
    }

    public int getNumber(int x, int y) {
        return grid[x][y];
    }

    public String getSolution(int x, int y) {
        return String.valueOf(solutionGrid[x][y]);
    }
}
