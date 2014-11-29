
public class SavedGame {
	
	public String score;
	public String time;
	public String[][] savedGame; // here the 2D array of puzzle will be saved, Please change the type accroding to your logic
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String[][] getSavedGame() {
		return savedGame;
	}
	public void setSavedGame(String[][] savedGame) {
		this.savedGame = savedGame;
	}

}
