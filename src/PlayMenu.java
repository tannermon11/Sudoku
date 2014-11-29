import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.*;

/**
 * User: Tanner Date: 9/24/2014 Time: 7:40 PM To change this template use File |
 * Settings | File Templates.
 */
public class PlayMenu extends JFrame {
	static JFrame frame;
	JPanel[] panel = new JPanel[9];
	JPanel bPanel, tPanel, pausePanel;
	JButton highscore, manual, hints, pause, end, notes;
	static JLabel user, difficultyLabel, time, rateLabel;
	JTextField[][] textFields;
	static Timer timer = null;
	static int rating, timeBonus, Accuracy, solveAids;
	static int score;
	static int minutes_elapsed = 0, seconds_elapsed = 0;
	boolean pauseEnabled, notesEnabled, hintsEnabled;
	SudokuImporter si = new SudokuImporter();

	public PlayMenu() {
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
		time = new JLabel();
		rateLabel = new JLabel();
		user = new JLabel("User: " + UserLoginRegisterMenu.player.getUsername());
		difficultyLabel = new JLabel("Difficulty: " + difficulty);
		time.setForeground(Color.blue);
		textFields = new JTextField[Sudoku.GRID_SIZE][Sudoku.GRID_SIZE];
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(4, 3));

		for (JPanel aPanel : panel) {
			aPanel.setLayout(new GridLayout(3, 3));
		}
		bPanel.setLayout(new GridLayout(8, 1));
		tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.PAGE_AXIS));

		for (JPanel aPanel : panel) {
			frame.add(aPanel);
		}
		tPanel.add(time);
		tPanel.add(rateLabel);
		tPanel.add(user);
		if (UserLoginRegisterMenu.player.getUsername() == null) {
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
					stopTimer();
					pauseEnabled = true;
					for (JPanel aPanel : panel)
						aPanel.setVisible(false);
					pause.setText("Un-pause");
				} else {
					startTimer(minutes_elapsed, seconds_elapsed);
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
						if(textFields[x][y].getText().length() == 0)
							Accuracy += 60;
					}
				}
				setScore();
				System.out.println("Score in end listener: " + score);
				minutes_elapsed = 0;
				seconds_elapsed = 0;
				timer.stop();
				DashBoardMenu.frame.setVisible(true);
				displaySolution();
				frame.dispose();
			}
		});

		notes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!notesEnabled) {
					notesEnabled = true;
				}
				else {
					notesEnabled = false;
				}
				System.out.println(notesEnabled);
			}
		});

		hints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hintsEnabled = false;
				if (rating <= 20) {
					for (int x = 0; x < 9; x++) {
						for (int y = 0; y < 9; y++) {
							final int finalX = x;
							final int finalY = y;
							textFields[x][y].addFocusListener(new FocusListener() {
								@Override
								public void focusGained(FocusEvent e) {
									System.out.println(finalX + " : " + finalY);
									if (!hintsEnabled) {
										solveAids += 15;
										textFields[finalX][finalY].setText(si.getSolution(finalY, finalX));
										hintsEnabled = true;
									}
								}

								@Override
								public void focusLost(FocusEvent e) {
									System.out.println("fL: " + finalX + " : " + finalY);
								}
							});
						}
					}
				} else {
					for (int x = 0; x < 9; x++) {
						for (int y = 0; y < 9; y++) {
							final int finalX = x;
							final int finalY = y;
							textFields[x][y].addFocusListener(new FocusListener() {
								@Override
								public void focusGained(FocusEvent e) {
									System.out.println(finalX + " : " + finalY);
									if (!hintsEnabled) {
										solveAids += 15;
										if (!textFields[finalX][finalY].getText().equals(si.getSolution(finalY, finalX)))
											textFields[finalX][finalY].setForeground(Color.red);
										hintsEnabled = true;
									}
								}

								@Override
								public void focusLost(FocusEvent e) {
									textFields[finalX][finalY].setForeground(Color.blue);
									System.out.println("fL: " + finalX + " : " + finalY);
								}
							});
						}
					}
				}
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
				if (!String.valueOf(si.getNumber(y, x)).equalsIgnoreCase("0")) {
					textFields[x][y].setText(String.valueOf(si.getNumber(y, x)));
					textFields[x][y].setEditable(false);
					textFields[x][y].setForeground(Color.BLUE); //Initial numbers on board
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

				if(UserLoginRegisterMenu.modeColor) {
					textFields[x][y].setBackground(Color.black);
					if(String.valueOf(si.getNumber(y, x)).equalsIgnoreCase("0")) {
						textFields[x][y].setForeground(Color.CYAN);
					}
				}
			}
		}

		for (JPanel aPanel : panel) {
			if(UserLoginRegisterMenu.modeColor)
				aPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white));
			else
				aPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
		}

		frame.setVisible(true);
		frame.setSize(500, 500);
	}

	private boolean legalNumber(int n) {
		return n >= 1 && n <= 9;
	}

	public static void setScore() {
		timeBonus = 3000 - (minutes_elapsed * 60 + seconds_elapsed);
		score = rating + timeBonus - Accuracy - solveAids;
		if(score < 0) score = 0;
		rateLabel.setText("Score: " + String.valueOf(score));
		if (UserLoginRegisterMenu.player.getUsername() != null) {
			String totalScore = UserLoginRegisterMenu.player.getScore();
			if (totalScore != null) {
				UserLoginRegisterMenu.player.setScore(score + Integer.parseInt(totalScore) + ""); // add
				// up
				// all
				// scores
			} else {
				UserLoginRegisterMenu.player.setScore(score + "");
			}
		}
	}

	public static void startTimer(int minutes, int seconds) {
		if (timer == null) {
			timer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					time.setText("Time: " + String.format("%02d:%02d", minutes_elapsed, seconds_elapsed));
					setScore();
					seconds_elapsed++;
					if (seconds_elapsed == 60) {
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

	public static void stopTimer() {
		if (timer != null)
			timer.stop();
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