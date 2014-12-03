import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;


public class HintSystem extends JFrame {

	
	public HintSystem() {
	}
	
	public static void useHints() {
		 PlayMenu.hintsEnabled = false;
         if (PlayMenu.rating <= 20) {
             for (int x = 0; x < 9; x++) {
                 for (int y = 0; y < 9; y++) {
                     final int finalX = x;
                     final int finalY = y;
                     PlayMenu.textFields[x][y].addFocusListener(new FocusListener() {
                         @Override
                         public void focusGained(FocusEvent e) {
                             System.out.println(finalX + " : " + finalY);
                             if (!PlayMenu.hintsEnabled) {
                            	 PlayMenu.solveAids += 15;
                            	 PlayMenu.textFields[finalX][finalY].setText(PlayMenu.si.getSolution(finalY, finalX));
                            	 PlayMenu.hintsEnabled = true;
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
                     PlayMenu.textFields[x][y].addFocusListener(new FocusListener() {
                         @Override
                         public void focusGained(FocusEvent e) {
                             System.out.println(finalX + " : " + finalY);
                             if (!PlayMenu.hintsEnabled) {
                            	 PlayMenu.solveAids += 15;
                                 if (!PlayMenu.textFields[finalX][finalY].getText().equals(PlayMenu.si.getSolution(finalY, finalX)))
                                	 PlayMenu.textFields[finalX][finalY].setForeground(Color.red);
                                 PlayMenu.hintsEnabled = true;
                             }
                         }

                         @Override
                         public void focusLost(FocusEvent e) {
                        	 PlayMenu.textFields[finalX][finalY].setForeground(Color.blue);
                             System.out.println("fL: " + finalX + " : " + finalY);
                         }
                     });
                 }
             }
         }
     }
}
