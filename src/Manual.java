import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Manual extends JFrame {
	public static JFrame mFrame;
	public static JTextArea mTextArea;
	public static JPanel mPanel;
	public static JButton back;
	
	
	public Manual() {
		InputStream url = this.getClass().getClassLoader().getResourceAsStream("Manual.txt");
	    BufferedReader reader = null;
		 mFrame = new JFrame();
         mTextArea = new JTextArea();
         mPanel = new JPanel(new BorderLayout());
         back = new JButton("Back to Dashboard");
         JScrollPane scrollPane = new JScrollPane(mTextArea);
			scrollPane.setViewportView(mTextArea);

         try {
             reader = new BufferedReader(new InputStreamReader(url, "UTF-8"));
             mTextArea.read(reader, "mTextArea");
             reader.close();
             mTextArea.requestFocus();
         } catch (IOException ex) {
             System.out.println("Your manual is missing.");
         }
			mTextArea.setLineWrap(true);
			mTextArea.setWrapStyleWord(true);
			mPanel.add(scrollPane, BorderLayout.CENTER);
			mPanel.add(back, BorderLayout.SOUTH);

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
