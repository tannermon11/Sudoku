import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

/**
 * User: Tanner
 * Date: 11/7/2014
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuImporter {
    static String difficulty = "";
    int[][] grid = new int[9][9];
    int[][] solutionGrid = new int[9][9];
    int row, column, number;
    public SudokuImporter() {
        Random rand = new Random();
        int num = rand.nextInt(3);
    	URL url = getClass().getResource("test.txt");
        File file = new File(url.getPath());
        url = getClass().getResource("solution.txt");
        File solution = new File(url.getPath());
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {// && !line.contains("Time:")) {
                String[] test = line.split(",");
                if (line.contains("Difficulty: ")) {
                    String[] diff = line.split(":");
                    difficulty = diff[1];
                } else {
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
            reader = new BufferedReader(new FileReader(solution));
            while ((line = reader.readLine()) != null) {// && !line.contains("Time:")) {
                String[] test = line.split(",");
                if (line.contains("Difficulty: ")) {
                    String[] diff = line.split(":");
                    difficulty = diff[1];
                } else {
                    if (line.contains(",")) {
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
        int [] arr = new int[9];
        for(int i =0; i<9;i++){
            arr[i]=grid[i][row];
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
