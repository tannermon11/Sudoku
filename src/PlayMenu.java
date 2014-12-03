import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.Paths.get;

/**
 * User: Tanner Date: 9/24/2014 Time: 7:40 PM To change this template use File |
 * Settings | File Templates.
 */
public class PlayMenu extends JFrame {
	public static JFrame frame;
	public JPanel[] panel = new JPanel[9];
	public JPanel bPanel, tPanel, pausePanel;
	public JButton hints, pause, end, notes, save;
	public static JLabel user, difficultyLabel, time, rateLabel;
	public static JTextField[][] textFields;
	public static int rating, timeBonus, Accuracy, solveAids;
	public static boolean pauseEnabled, notesEnabled, hintsEnabled;
	public static SudokuImporter si;

	public PlayMenu(boolean load) {
		si = new SudokuImporter(load);
		String difficulty = DashBoardMenu.difficultyBox.getSelectedItem().toString();
		frame = new JFrame("Sudoku");
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
		}
		bPanel = new JPanel();
		tPanel = new JPanel();
		pausePanel = new JPanel();
		hints = new JButton("Hints");
		pause = new JButton("Pause");
		end = new JButton("End");
		notes = new JButton("Notes");
		save = new JButton("Save");
		time = new JLabel();
		rateLabel = new JLabel();
		user = new JLabel("User: " + MainMenu.player.getUsername());
		difficultyLabel = new JLabel("Difficulty: " + difficulty);
		time.setForeground(Color.blue);
		textFields = new JTextField[Sudoku.GRID_SIZE][Sudoku.GRID_SIZE];
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(4, 3));

		for (JPanel aPanel : panel) {
			aPanel.setLayout(new GridLayout(3, 3));
		}
		bPanel.setLayout(new GridLayout(5, 1));
		tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.PAGE_AXIS));

		for (JPanel aPanel : panel) {
			frame.add(aPanel);
		}
		tPanel.add(time);
		tPanel.add(rateLabel);
		tPanel.add(user);
		if (MainMenu.player.getUsername() == null) {
			user.setVisible(false);
		}
		tPanel.add(difficultyLabel);
		frame.add(bPanel);
		frame.add(tPanel); // Try to add first then messes up grid
		bPanel.add(hints);
		bPanel.add(pause);
		bPanel.add(end);
		bPanel.add(notes);

		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!pauseEnabled) {
					TimeTrack.stopTimer();
					pauseEnabled = true;
					for (JPanel aPanel : panel)
						aPanel.setVisible(false);
					pause.setText("Un-pause");
				} else {
					TimeTrack.startTimer(TimeTrack.minutes_elapsed, TimeTrack.seconds_elapsed);
					pauseEnabled = false;
					for (JPanel aPanel : panel)
						aPanel.setVisible(true);
					pause.setText("Pause");
				}
			}
		});

		end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int x = 0; x < Sudoku.GRID_SIZE; x++) {
					for (int y = 0; y < Sudoku.GRID_SIZE; y++) {
						if (textFields[x][y].getText().length() == 0)
							Accuracy += 60;
						SudokuImporter.grid[x][y] = 0;
					}
				}
				Score.setScore();
				Score.setPlayerScore();
				Score.setScoreLabel();
				System.out.println("Score in end listener: " + Score.score);
				TimeTrack.setTime(0, 0);
				TimeTrack.timer.stop();
				displaySolution();
				frame.dispose();
				try {
					Files.delete(get(MainMenu.player.getUsername() + ".txt"));
				} catch (IOException e1) {
				}
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TimeTrack.timer.stop();
				LoadSave.save();
			}
		});

		notes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!notesEnabled) {
					notesEnabled = true;
				} else {
					notesEnabled = false;
				}
				System.out.println(notesEnabled);
			}
		});

		hints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HintSystem.useHints();
			}
		});

		switch (difficulty) {
		case "Easy":
			rating = 10;
			break;
		case "Medium":
			rating = 20;
			break;
		case "Hard":
			rating = 30;
			break;
		case "Evil":
			rating = 40;
			break;
		}
		System.out.println(rating);

		for (int x = 0; x < Sudoku.GRID_SIZE; x++) {
			for (int y = 0; y < Sudoku.GRID_SIZE; y++) {
				// String position = Integer.toString(x) + "," +
				// Integer.toString(y);
				// textFields[x][y] = new JTextField(position, 2);
				textFields[x][y] = new JTextField();
				int num;
				if (si.getNumber(y, x) != 0) {
					if (si.getNumber(y, x) > 0) {
						textFields[x][y].setEditable(false);
						textFields[x][y].setForeground(Color.BLUE); // Initial
																	// numbers
																	// on board
						num = si.getNumber(y, x);
					} else {
						num = -si.getNumber(y, x);
					}
					textFields[x][y].setText(String.valueOf(num));

				}
				((AbstractDocument) textFields[x][y].getDocument()).setDocumentFilter(new MyDocumentFilter(x, y));
				textFields[x][y].setHorizontalAlignment(SwingConstants.CENTER);

				if (x < 3 && y < 3)
					panel[0].add(textFields[x][y]);
				if (x < 3 && y >= 3 && y < 6)
					panel[1].add(textFields[x][y]);
				if (x < 3 && y >= 6)
					panel[2].add(textFields[x][y]);

				if (x >= 3 && x < 6 && y < 3)
					panel[3].add(textFields[x][y]);
				if (x >= 3 && x < 6 && y >= 3 && y < 6)
					panel[4].add(textFields[x][y]);
				if (x >= 3 && x < 6 && y >= 6)
					panel[5].add(textFields[x][y]);

				if (x >= 6 && y < 3)
					panel[6].add(textFields[x][y]);
				if (x >= 6 && y >= 3 && y < 6)
					panel[7].add(textFields[x][y]);
				if (x >= 6 && y >= 6)
					panel[8].add(textFields[x][y]);

				if (MainMenu.modeColor) {
					textFields[x][y].setBackground(Color.black);
					if (String.valueOf(si.getNumber(y, x)).equalsIgnoreCase("0")) {
						textFields[x][y].setForeground(Color.CYAN);
					}
				}
			}
		}

		for (JPanel aPanel : panel) {
			if (MainMenu.modeColor)
				aPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));
			else
				aPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
		}
		
		if(MainMenu.player.getUsername()!=null) {
			bPanel.add(save);
		}

		frame.setVisible(true);
		frame.setSize(500, 500);
	}

	public void displaySolution() {
		final JFrame solution = new JFrame();
		JPanel solutionPanel = new JPanel();
		JPanel gameOver = new JPanel();
		JPanel buttonPanel = new JPanel();
		JButton dashboard = new JButton("Go to dashboard");

		solutionPanel.setLayout(new GridLayout(9, 9));
		solution.setLayout(new GridLayout(1, 2));
		gameOver.setLayout(new GridLayout(5, 1));
		buttonPanel.setLayout(new FlowLayout());
		dashboard.setSize(new Dimension(20, 15));

		gameOver.add(user);
		gameOver.add(difficultyLabel);
		gameOver.add(time);
		gameOver.add(rateLabel);
		buttonPanel.add(dashboard);

		dashboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solution.dispose();
				new DashBoardMenu();
			}
		});

		for (int x = 0; x < Sudoku.GRID_SIZE; x++) {
			for (int y = 0; y < Sudoku.GRID_SIZE; y++) {
				boolean empty = textFields[x][y].getText().length() == 0;
				boolean preset = !textFields[x][y].isEditable();
				JTextField new_field = new JTextField(String.valueOf(si.getSolution(y, x)));
				if (empty) {
					new_field.setFont(new Font("SansSerif", Font.PLAIN, 10));
				}
				if (preset) {
					new_field.setFont(new Font("SansSerif", Font.BOLD, 15));
				}
				new_field.setEditable(false);
				new_field.setHorizontalAlignment(JTextField.CENTER);
				solutionPanel.add(new_field);
			}
		}

		gameOver.add(buttonPanel);
		solution.add(gameOver);
		solution.add(solutionPanel);

		solution.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		solution.setVisible(true);
		solution.setSize(500, 500);
	}

	class MyDocumentFilter extends DocumentFilter {
		int x, y;

		public MyDocumentFilter(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void insertString(DocumentFilter.FilterBypass fp, int offset, String string, AttributeSet aset)
				throws BadLocationException {
			int len = string.length();
			boolean isValidInteger = true;
			System.out.println(fp.getDocument().getLength());
			for (int i = 0; i < len; i++) {
				if (notesEnabled) {
					if (!Character.isDigit(string.charAt(i)) || string.contains(String.valueOf(0))
							|| fp.getDocument().getLength() >= 9) {
						isValidInteger = false;
						break;
					}
				} else if (!notesEnabled) {
					if (!Character.isDigit(string.charAt(i)) || string.contains(String.valueOf(0))
							|| fp.getDocument().getLength() >= 1) {
						isValidInteger = false;
						break;
					}
				}
			}
			if (isValidInteger)
				super.insertString(fp, offset, string, aset);
			else
				Toolkit.getDefaultToolkit().beep();
		}

		@Override
		public void replace(DocumentFilter.FilterBypass fp, int offset, int length, String string, AttributeSet aset)
				throws BadLocationException {
			int len = string.length();
			boolean isValidInteger = true;
			System.out.println(fp.getDocument().getLength());

			for (int i = 0; i < len; i++) {
				if (notesEnabled) {
					if (!Character.isDigit(string.charAt(i)) || string.contains(String.valueOf(0))
							|| fp.getDocument().getLength() >= 9) {
						isValidInteger = false;
						break;
					}
				} else if (!notesEnabled) {
					if (!Character.isDigit(string.charAt(i)) || string.contains(String.valueOf(0))
							|| fp.getDocument().getLength() >= 1) {
						isValidInteger = false;
						break;
					}
				}
			}
			if (isValidInteger) {
				super.replace(fp, offset, length, string, aset);
				checkAccuracy(string, this.x, this.y);
			} else
				Toolkit.getDefaultToolkit().beep();
		}

		public void checkAccuracy(String num, int x, int y) {
			if (!si.getSolution(y, x).equals(num) && !notesEnabled)
				Accuracy += 30;
			System.out.println(x + ":" + y);
		}
	}
}