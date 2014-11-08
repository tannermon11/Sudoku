import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: Tanner
 * Date: 11/7/2014
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuImporter {
    public SudokuImporter() {
        File file = new File("C:\\Users\\Tanner\\Documents\\Sudoku\\src\\Player.java");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null && !line.contains("Time:")) {
                if(line.contains("Difficulty: "))
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
