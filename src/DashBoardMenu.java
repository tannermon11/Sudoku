import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: Tanner Date: 10/30/2014 Time: 9:42 PM To change this template use File
 * | Settings | File Templates. Update by: Santhosh
 */

public class DashBoardMenu extends JFrame {
    public static JPanel bPanel, mPanel;
    public static JFrame frame, mFrame, scoreCardFrame;
    public JButton play, load, highscore, manual, back, goToMainScreen;
    public static JLabel user;
    public static JComboBox difficultyBox;
    public Timer timer = null;
    int minutes_elapsed = 0, seconds_elapsed = 0;
    public JTextArea mTextArea;
    

    public DashBoardMenu() {
        frame = new JFrame();
        bPanel = new JPanel();
		bPanel.setLayout(null);
		
        difficultyBox = new JComboBox(new String[]{"Easy", "Medium", "Hard", "Evil"});
        difficultyBox.setSelectedIndex(0);

        user = new JLabel("User: " + MainMenu.player.getUsername());
        play = new JButton("Play");
        load = new JButton("Load");
        highscore = new JButton("Hall of Fame");
        manual = new JButton("How-To-Play");
        back = new JButton("Go to Main Menu");
        goToMainScreen = new JButton("Back to Main Screen");

        difficultyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(difficultyBox.getSelectedItem());
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeTrack.startTimer(0,0);
                TimeTrack.setTime(0,0);
                Score.score = 3000;
                PlayMenu.Accuracy = 0;
                frame.dispose();
                new PlayMenu(true);
                System.out.println("Score play listener: " + String.valueOf(Score.score));
                /*
                 * SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
				 * mcl.createAndShowGUI();
				 */
            }
        });
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadSave.load();
            }
        });

        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                   new Manual();
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                MainMenu.profile();
            }
        });

        highscore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add to table
                try {
                    new HighScoreMenu();
                    frame.dispose();
                } catch (SAXException | IOException | ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
            }
        });

        goToMainScreen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
                new MainMenu();
            }
        });

        
        bPanel.add(user);
        bPanel.add(difficultyBox);
        bPanel.add(play);
        bPanel.add(load);
        bPanel.add(highscore);
        bPanel.add(manual);
        bPanel.add(back);
        bPanel.add(goToMainScreen);
        if (MainMenu.player.getUsername() == null) {
        	difficultyBox.setBounds(150, 100, 200, 40);
        	play.setBounds(150, 150, 200, 40);
        	highscore.setBounds(150, 200, 200, 40);
        	manual.setBounds(150, 250, 200, 40);
        	goToMainScreen.setBounds(150, 300, 200, 40);
            user.setVisible(false);
            load.setVisible(false);
            back.setVisible(false);
            goToMainScreen.setVisible(true);
        } else {
        	difficultyBox.setBounds(150, 50, 200, 40);
        	play.setBounds(150, 100, 200, 40);
        	load.setBounds(150, 150, 200, 40);
        	highscore.setBounds(150, 200, 200, 40);
        	manual.setBounds(150, 250, 200, 40);
        	back.setBounds(150, 300, 200, 40);
            goToMainScreen.setVisible(false);
        }
        frame.add(bPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		frame.setSize(500, 500);
	}

}
