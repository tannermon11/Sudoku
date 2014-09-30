import javax.swing.*;
import java.awt.*;


/**
 * User: Tanner
 * Date: 9/24/2014
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuGUI extends JFrame{
    JFrame frame;
    JPanel panel, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9;
    JTextField[][] textFields;
    JComboBox difficultyBox;

    public SudokuGUI() {
        frame = new JFrame("Sudoku");
        panel = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        panel6 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        panel9 = new JPanel();
        textFields = new JTextField[Sudoku.GRID_SIZE][Sudoku.GRID_SIZE];
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(9, 9)); //1,2 for method 2

        difficultyBox = new JComboBox(new String[]{ "Easy", "Medium", "Hard" });
        difficultyBox.setSelectedIndex(1);

        panel.setLayout(new GridLayout(3, 3));
        panel2.setLayout(new GridLayout(3, 3));
        panel3.setLayout(new GridLayout(3, 3));
        panel4.setLayout(new GridLayout(3, 3));
        panel5.setLayout(new GridLayout(3, 3));
        panel6.setLayout(new GridLayout(3, 3));
        panel7.setLayout(new GridLayout(3, 3));
        panel8.setLayout(new GridLayout(3, 3));
        panel9.setLayout(new GridLayout(3, 3));
        frame.add(panel);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);
        frame.add(panel7);
        frame.add(panel8);
        frame.add(panel9);
/*        for (int k=0; k<=8; k++) //Method 2
        {
            panel2 = new JPanel(new GridLayout(3,3));

            for(int i=0; i<=8; i++){
                panel2.add(new JTextField(2));
            }
            for(int i=0; i<=8; i++){
                panel.add(panel2);
            }
        }*/


        for (int x = 0; x < Sudoku.GRID_SIZE; x++) {
            for (int y = 0; y < Sudoku.GRID_SIZE; y++) {
                String position = Integer.toString(x) + "," + Integer.toString(y);
                textFields[x][y] = new JTextField(position, 2);
                //frame.add(textFields[x][y]);
                //Need to add border to 3x3 squares
                if(x<3 && y<3)
                    panel.add(textFields[x][y]);
                if(x<3 && y>=3 && y<6)
                    panel2.add(textFields[x][y]);
                if(x<3 && y>=6)
                    panel3.add(textFields[x][y]);

                if(x>=3 && x<6 && y<3)
                    panel4.add(textFields[x][y]);
                if(x>=3 && x<6 && y>=3 && y<6)
                    panel5.add(textFields[x][y]);
                if(x>=3 && x<6 && y>=6)
                    panel6.add(textFields[x][y]);

                if(x>=6 && y<3)
                    panel7.add(textFields[x][y]);
                if(x>=6 && y>=3 && y<6)
                    panel8.add(textFields[x][y]);
                if(x>=6 && y>=6)
                    panel9.add(textFields[x][y]);

            }
        }
        panel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.red));
        panel2.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.blue));
        panel3.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.green));

        //frame.add(panel); //Method 2
        frame.setVisible(true);
        frame.setSize(300, 300);
    }

    private boolean legalNumber(int n) {
        return n >= 1 && n <= 9;
    }
}
