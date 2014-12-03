import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class HighScoreMenu extends JFrame {
	public static JFrame scoreCardFrame;

	public HighScoreMenu() throws SAXException, IOException, ParserConfigurationException {
		scoreCardFrame = new JFrame();
		final JButton back = new JButton("Back to Dashboard");
		List<String> columns = new ArrayList<String>();
		List<String[]> values = new ArrayList<String[]>();
		columns.add("User");
		columns.add("Score");
		int totalScore = 0;

		Map<String, Player> playersMap = MainMenu.player.getScoreCard();

		if (playersMap.containsKey(MainMenu.player.getUsername())) {
			if (MainMenu.player.getSessionScore() != null) {
				int current = Integer.parseInt(MainMenu.player.getSessionScore());
				if (current > 0) {
					Player playerFromMap = playersMap.get(MainMenu.player.getUsername());
					Player player = MainMenu.player;
					int early = Integer.parseInt(playerFromMap.getScore());
					totalScore = current + early;
					player.setScore(totalScore + "");
					playersMap.remove(MainMenu.player.getUsername());
					playersMap.put(MainMenu.player.getUsername(), player);
				}
			}
		}

		List<Player> players = new ArrayList<Player>(playersMap.values());

		Collections.sort(players, Collections.reverseOrder(new Player()));

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
