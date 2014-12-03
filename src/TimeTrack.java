import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class TimeTrack {
	public static Timer timer = null;
	public static int minutes_elapsed = 0, seconds_elapsed = 0;

	public static void stopTimer() {
		if (timer != null)
			timer.stop();
	}

	public static void startTimer(int minutes, int seconds) {
		if (timer == null) {
			timer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PlayMenu.time.setText("Time: " + String.format("%02d:%02d", minutes_elapsed, seconds_elapsed));
					Score.setScore();
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

	public static void setTime(int min, int sec) {
		 minutes_elapsed = min;
         seconds_elapsed = sec;
	}
}
