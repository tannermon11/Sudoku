import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
		File manualFile = new File("Manual.txt");
	    FileReader fr = null;
	    BufferedReader reader = null;
		 mFrame = new JFrame();
         mTextArea = new JTextArea();
         mPanel = new JPanel(new BorderLayout());
         back = new JButton("Back to Dashboard");
         JScrollPane scrollPane = new JScrollPane(mTextArea);
			scrollPane.setViewportView(mTextArea);

         try {
             fr = new FileReader(manualFile);
             reader = new BufferedReader(fr);
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
