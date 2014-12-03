import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LoadSave {

	public static void load() {
		PlayMenu.Accuracy = 0;
		int min = 0, sec = 0, score = 0;
		BufferedReader br = null;
		File file = new File(MainMenu.player.getUsername() + ".txt");
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				String[] test = line.split(",");
				if (line.contains("Time:")) {
					String[] timing = line.split(" ");
					min = Integer.parseInt(timing[1]);
					sec = Integer.parseInt(timing[2]);
				} else if (line.contains("Score:")) {
					String[] scoring = line.split(" ");
					score = Integer.parseInt(scoring[1]);
				} else {
					int row = Integer.parseInt(test[0]);
					int column = Integer.parseInt(test[1]);
					if (test.length == 3) {
						int number = Integer.parseInt(test[2]);
						SudokuImporter.grid[column][row] = number;
					}
				}
			}
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
		DashBoardMenu.frame.dispose();
		new PlayMenu(false);
		TimeTrack.setTime(min, sec);
		TimeTrack.startTimer(min, sec);
		Score.score = score;
	}

	public static void save() {

		File file = new File(MainMenu.player.getUsername() + ".txt");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("Time: " + TimeTrack.minutes_elapsed + " " + TimeTrack.seconds_elapsed + "\n");
			Score.setScore();
			bw.write("Score: " + Score.score + "\n");
			for (int x = 0; x < 9; x++)
				for (int y = 0; y < 9; y++) {
					System.out.println(x + "," + y + "," + PlayMenu.textFields[x][y].getText());
					String val = !PlayMenu.textFields[x][y].isEditable()
							|| PlayMenu.textFields[x][y].getText().length() == 0 ? PlayMenu.textFields[x][y].getText()
							: '-' + PlayMenu.textFields[x][y].getText();
					bw.write(x + "," + y + "," + val + "\n");
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
		for (int x = 0; x < Sudoku.GRID_SIZE; x++) {
			for (int y = 0; y < Sudoku.GRID_SIZE; y++) {
				SudokuImporter.grid[x][y] = 0;
			}
		}
		PlayMenu.frame.dispose();
		new DashBoardMenu();
	}
}
