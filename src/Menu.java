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
    JButton play, load, login, register, highscore, manual, hints, pause, end;
    JLabel user, time;
    JComboBox difficultyBox;
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
        login = new JButton("Login");
        register = new JButton("Register");
        highscore = new JButton("Hall of Fame");
        manual = new JButton("How-To-Play");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //startTimer(0, 0);
                SudokuGUI sg = new SudokuGUI();
                frame.setVisible(false);
                /*SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
                mcl.createAndShowGUI();*/
            }
        });

        login.setBackground(Color.ORANGE);
        register.setBackground(Color.green);
        highscore.setBackground(new Color(126, 237, 235));
        bPanel.add(user);
        bPanel.add(difficultyBox);
        bPanel.add(play);
        bPanel.add(load);
        bPanel.add(login);
        bPanel.add(register);
        bPanel.add(highscore);
        bPanel.add(manual);
        frame.add(bPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(500, 500);
    }

    public void startTimer(int minutes, int seconds) {
        if(timer == null) {
            timer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    time.setText("Time: " + String.format("%02d:%02d", minutes_elapsed, seconds_elapsed));
                    seconds_elapsed++;
                    if(seconds_elapsed == 60) {
                        seconds_elapsed = 0;
                        minutes_elapsed++;
                    }
                }
            });
            timer.setDelay(1000);
            timer.setRepeats(true);
        }
        timer.start();
    }

    public void stopTimer() {
        if(timer != null)
            timer.stop();
    }
}