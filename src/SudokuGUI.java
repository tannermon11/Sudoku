import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;


/**
 * User: Tanner
 * Date: 9/24/2014
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuGUI extends JFrame {
    static JFrame frame;
    JPanel[] panel = new JPanel[9];
    JPanel bPanel, tPanel;
    JButton highscore, manual, hints, pause, end;
    static JLabel user;
    static JLabel time;
    JTextField[][] textFields;
    static Timer timer = null;
    int rating, timeBonus, Accuracy, solveAids;
    int score;// = rating+timeBonus-Accuracy-solveAids;
    static int minutes_elapsed = 0, seconds_elapsed = 0;
    boolean pauseEnabled;

    public SudokuGUI() {
        frame = new JFrame("Sudoku");
        panel[0] = new JPanel();
        panel[1] = new JPanel();
        panel[2] = new JPanel();
        panel[3] = new JPanel();
        panel[4] = new JPanel();
        panel[5] = new JPanel();
        panel[6] = new JPanel();
        panel[7] = new JPanel();
        panel[8] = new JPanel();
        bPanel = new JPanel();
        tPanel = new JPanel();
        hints = new JButton("Hints");
        pause = new JButton("Pause");
        end = new JButton("End");
        time = new JLabel();
        time.setForeground(Color.blue);
        textFields = new JTextField[Sudoku.GRID_SIZE][Sudoku.GRID_SIZE];
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 3));

        for(int i=0; i<panel.length; i++) {
            panel[i].setLayout(new GridLayout(3, 3));
        }
        bPanel.setLayout(new GridLayout(8, 1));
        tPanel.setLayout(new BorderLayout());

        for(int i=0; i<panel.length; i++) {
            frame.add(panel[i]);
        }
        tPanel.add(time, BorderLayout.NORTH);
        frame.add(bPanel);
        frame.add(tPanel); //Try to add first then messes up grid
        bPanel.add(hints);
        bPanel.add(pause);
        bPanel.add(end);

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pauseEnabled) {
                    stopTimer();
                    pauseEnabled = true;
                } else {
                    startTimer(minutes_elapsed, seconds_elapsed);
                    pauseEnabled = false;
                }
            }
        });

        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minutes_elapsed = 0; seconds_elapsed  = 0;
                Menu.frame.setVisible(true);
                frame.dispose();
                /*SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
                mcl.createAndShowGUI();*/
            }
        });

        for (int x = 0; x < Sudoku.GRID_SIZE; x++) {
            for (int y = 0; y < Sudoku.GRID_SIZE; y++) {
                String position = Integer.toString(x) + "," + Integer.toString(y);
                //textFields[x][y] = new JTextField(position, 2);
                textFields[x][y] = new JTextField();
                //textFields[x][y].setText("\u00B2 :: \u2074");
                ((AbstractDocument)textFields[x][y].getDocument()).setDocumentFilter(
                        new MyDocumentFilter());
                textFields[x][y].setHorizontalAlignment(JTextField.CENTER);
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


                InputVerifier fieldVerifier = new InputVerifier() {
                    @Override
                    public boolean verify(JComponent input) {
                        JTextField temp = (JTextField)input;
                        try {
                            //int number = Integer.parseInt(temp.getText());
                            if(temp.getText().trim().length() > 1)
                                JOptionPane.showMessageDialog(frame, "Only 1 number is allowed","", JOptionPane.WARNING_MESSAGE);
                            return (temp.getText().trim().length() == 1);
                        }
                        catch (NumberFormatException e) { //Not working so using DocumentFilter
                            JOptionPane.showMessageDialog(null, "Only numbers are allowed");
                        }
                        return false;
                    }
                };
            textFields[x][y].setInputVerifier(fieldVerifier);

            }
        }
        //Just seeing what it looks like currently - will move later

        for(int i=0; i<panel.length; i++) {
            panel[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        }

        frame.setVisible(true);
        frame.setSize(500, 500);
    }

    private boolean legalNumber(int n) {
        return n >= 1 && n <= 9;
    }


    public static void startTimer(int minutes, int seconds) {
        if(timer == null) {
            timer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    time.setText("Time: " + String.format("%02d:%02d", minutes_elapsed, seconds_elapsed));
                    seconds_elapsed++;
                    if(seconds_elapsed == 60) {
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
        if(timer != null)
            timer.stop();
    }

    public class myCardLayout {
        JPanel cardHolder;
        JPanel card1, card2, buttonPane;
        public CardLayout cardLayout = new CardLayout();
        String tPanel = "Card with testing & 2";
        String bPanel = "Card with testing3";
        /*JButton bShowOne = new JButton(new showOne());
        JButton bShowTwo = new JButton(new showTwo());*/

        /*public void addComponentToPane (Container pane){
            buttonPane = new JPanel();
       /*     buttonPane.add(bShowOne);
            buttonPane.add(bShowTwo);

            card1 = new JPanel();
            JButton test = new JButton("testing");
            JButton test2 = new JButton("testing2");
            card1.add(test);
            card1.add(test2);
            card2 = new JPanel();
            JButton test3 = new JButton("Go Back");
            card2.add(test3);

            cardHolder = new JPanel(cardLayout);
            cardHolder.add(card1, tPanel);
            cardHolder.add(card2, bPanel);
            pane.add(buttonPane, BorderLayout.PAGE_START);
            pane.add(cardHolder, BorderLayout.CENTER);

        }*/

        /*private class showOne extends AbstractAction {
            public showOne() { super("Show hugraigeasg"); }
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardHolder, tPanel);
                buttonPane.remove(bShowTwo);
                buttonPane.revalidate();
                buttonPane.repaint();
            }
        }

        private class showTwo extends AbstractAction {
            public showTwo() { super("Show Two"); }
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardHolder, bPanel);
                buttonPane.remove(bShowOne);
                buttonPane.revalidate();
                buttonPane.repaint();
            }
        }*/

        /*public void itemStateChanged(ItemEvent e) {
            CardLayout cl = (CardLayout) (cardHolder.getLayout());
            cl.show(cardHolder, (String) e.getItem());
        }*/

        public void createAndShowGUI() {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //myCardLayout myLayout = new myCardLayout();
            //myLayout.addComponentToPane(frame.getContentPane());

            frame.pack();
            frame.setVisible(true);
        }
    }

    class MyDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(DocumentFilter.FilterBypass fp, int offset,
                                 String string, AttributeSet aset)
                throws BadLocationException {
            int len = string.length();
            boolean isValidInteger = true;

            for (int i = 0; i < len; i++) {
                if (!Character.isDigit(string.charAt(i))) {
                    isValidInteger = false;
                    break;
                }
            }
            if (isValidInteger)
                super.insertString(fp, offset, string, aset);
            else
                Toolkit.getDefaultToolkit().beep();
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fp, int offset
                , int length, String string, AttributeSet aset)
                throws BadLocationException
        {
            int len = string.length();
            boolean isValidInteger = true;

            for (int i = 0; i < len; i++)
            {
                if (!Character.isDigit(string.charAt(i)))
                {
                    isValidInteger = false;
                    break;
                }
            }
            if (isValidInteger)
                super.replace(fp, offset, length, string, aset);
            else
                Toolkit.getDefaultToolkit().beep();
        }
    }
}