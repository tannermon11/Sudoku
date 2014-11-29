import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Tanner Date: 10/30/2014 Time: 9:42 PM To change this template use File
 * | Settings | File Templates. Update by: Santhosh
 */

public class DashBoardMenu extends JFrame {
	public static JPanel bPanel, mPanel;
	public static JFrame frame, mFrame, scoreCardFrame;
	public JButton play, load, highscore, manual, back, goToMainScreen, challenge, challengeAFriend;
	public static JLabel user;
	public static JComboBox difficultyBox;
	public Timer timer = null;
	int minutes_elapsed = 0, seconds_elapsed = 0;
	public JTextArea mTextArea;
	public File manualFile = new File("Manual.txt");
	public FileReader fr = null;
	public BufferedReader reader = null;

	public DashBoardMenu() {
		frame = new JFrame();
		bPanel = new JPanel();
		difficultyBox = new JComboBox(new String[] { "Easy", "Medium", "Hard", "Evil" });
		difficultyBox.setSelectedIndex(0);

		user = new JLabel("User: " + UserLoginRegisterMenu.player.getUsername());
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
				PlayMenu.startTimer(0, 0);
				frame.dispose();
				new PlayMenu();
				System.out.println("Score play listener: " + String.valueOf(PlayMenu.score));
				/*
				 * SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
				 * mcl.createAndShowGUI();
				 */
			}
		});
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
//				UserLoginRegisterMenu.player.loadGame();
			}
		});

		manual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				{
					mFrame = new JFrame();
					mTextArea = new JTextArea();
					mPanel = new JPanel();
					back = new JButton("Back to Dashboard");

					try {
						fr = new FileReader(manualFile);
						reader = new BufferedReader(fr);
						mTextArea.read(reader, "mTextArea");
						reader.close();
						mTextArea.requestFocus();
					} catch (IOException ex) {
						System.out.println("Your manual is missing.");
					}

					mPanel.add(mTextArea);
					mPanel.add(back);
					back.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
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

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				UserLoginRegisterMenu.profile();
			}
		});

		highscore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// add to table
				try {
					List<Player> players = UserLoginRegisterMenu.player.getScoreCard();
					scoreBoard(players);
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
				new UserLoginRegisterMenu();
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
		if (UserLoginRegisterMenu.player.getUsername() == null) {
			user.setVisible(false);
			load.setVisible(false);
			back.setVisible(false);
		} else {
			goToMainScreen.setVisible(false);
		}
		frame.add(bPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(500, 500);
	}

	public void scoreBoard(List<Player> players) {
		scoreCardFrame = new JFrame();
		final JButton back = new JButton("Back to Dashboard");
		List<String> columns = new ArrayList<String>();
		List<String[]> values = new ArrayList<String[]>();
		columns.add("User");
		columns.add("Score");

		if (players != null) {
			Iterator<Player> playerIter = players.iterator();
			while (playerIter.hasNext()) {
				Player user = playerIter.next();
				values.add(new String[] { user.getUsername(), user.getScore() });
			}
		}

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreCardFrame.dispose();
				new DashBoardMenu();
			}
		});

		TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		JTable table = new JTable(tableModel);

		scoreCardFrame.setLayout(new BorderLayout());
		scoreCardFrame.add(new JScrollPane(table), BorderLayout.CENTER);
		scoreCardFrame.add(table.getTableHeader(), BorderLayout.NORTH);
		scoreCardFrame.add(back, BorderLayout.SOUTH);
		scoreCardFrame.setVisible(true);
		scoreCardFrame.setSize(500, 500);
	}
}
