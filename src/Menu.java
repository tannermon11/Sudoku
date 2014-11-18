import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * User: Tanner
 * Date: 10/30/2014
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */

public class Menu extends JFrame {
    JPanel bPanel, mPanel;
    static JFrame frame, mFrame;
    JButton play, load, highscore, manual, back;
    JLabel user;
    static JComboBox difficultyBox;
    Timer timer = null;
    int minutes_elapsed = 0, seconds_elapsed = 0;
    JTextArea mTextArea;
    File manualFile = new File("Manual.txt");
    FileReader fr = null;
    BufferedReader reader = null;

    public Menu() {
        frame = new JFrame();
        bPanel = new JPanel();
        difficultyBox = new JComboBox(new String[]{"Easy", "Medium", "Hard", "Evil"});
        difficultyBox.setSelectedIndex(0);

        user = new JLabel("User: " + SubMenu.player.username);
        play = new JButton("Play");
        load = new JButton("Load");
        highscore = new JButton("Hall of Fame");
        manual = new JButton("How-To-Play");

        difficultyBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(difficultyBox.getSelectedItem());
            }
        });

        play.addActionListener(new ActionListener()
        {
		            @Override
		            public void actionPerformed(ActionEvent e)
		            {
		                SudokuGUI.startTimer(0, 0);
		                frame.dispose();
		                new SudokuGUI();
		                System.out.println("Score play listener: " + String.valueOf(SudokuGUI.score));
		                /*SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
		                mcl.createAndShowGUI();*/
		            }
        });
        load.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                //SubMenu.player.loadGame();
            }
        });

        manual.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
				{
					mFrame = new JFrame();
					mTextArea = new JTextArea();
					mPanel = new JPanel();
					back = new JButton("Close");

					try
					{
						fr = new FileReader(manualFile);
						reader = new BufferedReader(fr);
						mTextArea.read(reader, "mTextArea");
						reader.close();
						mTextArea.requestFocus();
					}
					catch (IOException ex)
					{
						System.out.println("Your manual is missing.");
					}

					mPanel.add(mTextArea);
					mPanel.add(back);
					back.addActionListener(new ActionListener()
					{
								@Override
								public void actionPerformed(ActionEvent e)
								{
									mFrame.dispose();
								}
					});

					mFrame.add(mPanel);
					mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mFrame.setVisible(true);
					mFrame.setSize(500, 500);
				}
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
