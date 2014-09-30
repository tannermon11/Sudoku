import sun.plugin2.util.ColorUtil;

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
    JPanel panel, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, bPanel;
    JButton play, load, login, register, highscore, manual;
    JLabel user, timeLabel;
    JTextField[][] textFields;
    JComboBox difficultyBox;
    int rating, timeBonus, Accuracy, solveAids;
    int score;// = rating+timeBonus-Accuracy-solveAids;

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
        bPanel = new JPanel();
        timeLabel = new JLabel("Time: ");
        textFields = new JTextField[Sudoku.GRID_SIZE][Sudoku.GRID_SIZE];
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 3));

        panel.setLayout(new GridLayout(3, 3));
        panel2.setLayout(new GridLayout(3, 3));
        panel3.setLayout(new GridLayout(3, 3));
        panel4.setLayout(new GridLayout(3, 3));
        panel5.setLayout(new GridLayout(3, 3));
        panel6.setLayout(new GridLayout(3, 3));
        panel7.setLayout(new GridLayout(3, 3));
        panel8.setLayout(new GridLayout(3, 3));
        panel9.setLayout(new GridLayout(3, 3));
        bPanel.setLayout(new GridLayout(8,1));
        //frame.add(timeLabel);
        frame.add(panel);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);
        frame.add(panel7);
        frame.add(panel8);
        frame.add(panel9);
        frame.add(bPanel);


        for (int x = 0; x < Sudoku.GRID_SIZE; x++) {
            for (int y = 0; y < Sudoku.GRID_SIZE; y++) {
                String position = Integer.toString(x) + "," + Integer.toString(y);
                textFields[x][y] = new JTextField(position, 2);
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
        menu(); //Just seeing what it looks like currently - will move later

        panel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        panel2.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        panel3.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        panel4.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        panel5.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        panel6.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        panel7.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        panel8.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));
        panel9.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.black));

        frame.setVisible(true);
        frame.setSize(500, 500);
    }

    public void menu() {
        difficultyBox = new JComboBox(new String[]{"Easy", "Medium", "Hard", "Evil"});
        difficultyBox.setSelectedIndex(0);

        user = new JLabel("User: ");
        play = new JButton("Play");
        load = new JButton("Load");
        login = new JButton("Login");
        register = new JButton("Register");
        highscore = new JButton("Hall of Fame");
        manual = new JButton("How-To-Play");
        login.setBackground(Color.ORANGE);
        register.setBackground(Color.green);
        highscore.setBackground(new Color(126,237,235));
        bPanel.add(user);
        bPanel.add(difficultyBox);
        bPanel.add(play);
        bPanel.add(load);
        bPanel.add(login);
        bPanel.add(register);
        bPanel.add(highscore);
        bPanel.add(manual);
    }

    private boolean legalNumber(int n) {
        return n >= 1 && n <= 9;
    }
}
