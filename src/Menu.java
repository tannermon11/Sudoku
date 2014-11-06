import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Tanner
 * Date: 10/30/2014
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */

public class Menu extends JFrame {
    JPanel bPanel;
    static JFrame frame;
    JButton play, load, highscore, manual;
    JLabel user;
    static JComboBox difficultyBox;
    Timer timer = null;
    int minutes_elapsed = 0, seconds_elapsed = 0;

    public Menu() {
        frame = new JFrame();
        bPanel = new JPanel();
        difficultyBox = new JComboBox(new String[]{"Easy", "Medium", "Hard", "Evil"});
        difficultyBox.setSelectedIndex(0);


        user = new JLabel("User: ");
        play = new JButton("Play");
        load = new JButton("Load");
        highscore = new JButton("Hall of Fame");
        manual = new JButton("How-To-Play");

        difficultyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(difficultyBox.getSelectedItem());
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SudokuGUI.startTimer(0, 0);
                frame.dispose();
                new SudokuGUI();
                System.out.println("Score play listener: " + String.valueOf(SudokuGUI.score));
                /*SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
                mcl.createAndShowGUI();*/
            }
        });


        highscore.setBackground(new Color(126, 237, 235));
        bPanel.add(user);
        bPanel.add(difficultyBox);
        bPanel.add(play);
        bPanel.add(load);
        bPanel.add(highscore);
        bPanel.add(manual);
        frame.add(bPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(500, 500);
    }
}