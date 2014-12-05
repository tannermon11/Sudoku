import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class LoadSave {

	public void load() throws UnsupportedEncodingException, FileNotFoundException {
		PlayMenu.Accuracy = 0;
		int min = 0, sec = 0, score = 0;
		BufferedReader br = null;
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = URLDecoder.decode(path, "UTF-8");
		InputStream url = new FileInputStream(new File(decodedPath + "\\" + MainMenu.player.getUsername() + ".txt"));
		try {
			br = new BufferedReader(new InputStreamReader(url, "UTF-8"));
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

	public OutputStream copyStream(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024]; // Adjust if you want
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output;
	}

	public void save() throws IOException {

		BufferedWriter bw = null;
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = URLDecoder.decode(path, "UTF-8");
		FileWriter fw = new FileWriter(decodedPath + "\\" + MainMenu.player.getUsername() + ".txt");
		try {
			bw = new BufferedWriter(fw);
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
