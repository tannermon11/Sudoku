import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * User: Tanner
 * Date: 11/7/2014
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuImporter {
    static String difficulty = "";
    int[][] grid = new int[9][9];
    int i;
    public SudokuImporter() {
        File file = new File("C:\\Users\\Tanner\\Documents\\Sudoku\\src\\test.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {// && !line.contains("Time:")) {
                String[] test = line.split(",");
                if (line.contains("Difficulty: ")) {
                    String[] diff = line.split(":");
                    difficulty = diff[1];
                } else {
                    if(line.contains(",")){
                    int row = Integer.parseInt(test[0]);
                    int column = Integer.parseInt(test[1]);
                    int number = Integer.parseInt(test[2]);
                    grid[column][row] = number;
                    System.out.println("Row: " + row + " Column: " + column + " Number: " + number +
                            " Difficulty: " + difficulty);}
                }

                i++;
                this.i = i;
                //System.out.println(Arrays.toString(test));
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
}
