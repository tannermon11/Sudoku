public class Score {

	public static int score = 0;

	public static void setScoreLabel() {
		PlayMenu.rateLabel.setText("Score: " + String.valueOf(score));
	}

	public static void setScore() {
		PlayMenu.timeBonus = 3000 - (TimeTrack.minutes_elapsed * 60 + TimeTrack.seconds_elapsed);
		score = PlayMenu.rating + PlayMenu.timeBonus - PlayMenu.Accuracy - PlayMenu.solveAids;
		if (score < 0)
			score = 0;
		setScoreLabel();
		if (MainMenu.player.getUsername() != null) {
			MainMenu.player.setScore(score + "");

		}
	}

	public static void setPlayerScore() {
		if (MainMenu.player.getUsername() != null) {
			if (MainMenu.player.getInitialScore() != null) {
				if (MainMenu.player.getSessionScore() != null) {
					int current = Integer.parseInt(MainMenu.player.getSessionScore());
					MainMenu.player.setSessionScore(score + current + "");
				} else {
					MainMenu.player.setSessionScore(score + "");
				}
				MainMenu.player.setScore(score + "");
			}
		}
	}
}
