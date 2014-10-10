import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * User: Tanner
 * Date: 9/24/2014
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuGUI extends JFrame {
    JFrame frame;
    JPanel panel, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, bPanel, tPanel;
    Container pane;
    JButton play, load, login, register, highscore, manual, hints, pause, end;
    JLabel user, time;
    JTextField[][] textFields;
    JComboBox difficultyBox;
    Timer timer = null;
    int rating, timeBonus, Accuracy, solveAids;
    int score;// = rating+timeBonus-Accuracy-solveAids;
    int minutes_elapsed = 0, seconds_elapsed = 0;

    public SudokuGUI() {
        frame = new JFrame("Sudoku");
        panel = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        panel6 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        panel9 = new JPanel();
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

        panel.setLayout(new GridLayout(3, 3));
        panel2.setLayout(new GridLayout(3, 3));
        panel3.setLayout(new GridLayout(3, 3));
        panel4.setLayout(new GridLayout(3, 3));
        panel5.setLayout(new GridLayout(3, 3));
        panel6.setLayout(new GridLayout(3, 3));
        panel7.setLayout(new GridLayout(3, 3));
        panel8.setLayout(new GridLayout(3, 3));
        panel9.setLayout(new GridLayout(3, 3));
        bPanel.setLayout(new GridLayout(8, 1));
        tPanel.setLayout(new BorderLayout());

        tPanel.add(time, BorderLayout.NORTH);
        frame.add(panel);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);
        frame.add(panel7);
        frame.add(panel8);
        frame.add(panel9);
        frame.add(bPanel);
        frame.add(tPanel); //Try to add first then messes up grid


        for (int x = 0; x < Sudoku.GRID_SIZE; x++) {
            for (int y = 0; y < Sudoku.GRID_SIZE; y++) {
                String position = Integer.toString(x) + "," + Integer.toString(y);
                textFields[x][y] = new JTextField(position, 2);
                if (x < 3 && y < 3)
                    panel.add(textFields[x][y]);
                if (x < 3 && y >= 3 && y < 6)
                    panel2.add(textFields[x][y]);
                if (x < 3 && y >= 6)
                    panel3.add(textFields[x][y]);

                if (x >= 3 && x < 6 && y < 3)
                    panel4.add(textFields[x][y]);
                if (x >= 3 && x < 6 && y >= 3 && y < 6)
                    panel5.add(textFields[x][y]);
                if (x >= 3 && x < 6 && y >= 6)
                    panel6.add(textFields[x][y]);

                if (x >= 6 && y < 3)
                    panel7.add(textFields[x][y]);
                if (x >= 6 && y >= 3 && y < 6)
                    panel8.add(textFields[x][y]);
                if (x >= 6 && y >= 6)
                    panel9.add(textFields[x][y]);


                InputVerifier fieldVerifier = new InputVerifier() {
                    @Override
                    public boolean verify(JComponent input) {
                        JTextField temp = (JTextField)input;
                        try {
                            //int number = Integer.parseInt(temp.getText());
                            if(temp.getText().trim().length() > 1)
                                JOptionPane.showMessageDialog(null, "Only 1 number is allowed");
                            return (temp.getText().trim().length() == 1);
                        }
                        catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Only numbers are allowed");
                        }
                        return false;
                    }
                };
            textFields[x][y].setInputVerifier(fieldVerifier);

            }
        }
        menu(); //Just seeing what it looks like currently - will move later

        panel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panel2.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panel3.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panel4.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panel5.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panel6.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panel7.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panel8.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        panel9.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));

        frame.setVisible(true);
        frame.setSize(500, 500);
    }

    private boolean legalNumber(int n) {
        return n >= 1 && n <= 9;
    }

    public void menu() {
        difficultyBox = new JComboBox(new String[]{"Easy", "Medium", "Hard", "Evil"});
        difficultyBox.setSelectedIndex(0);

        user = new JLabel("User: ");
        play = new JButton("Play");
        load = new JButton("Load");
        login = new JButton("Login");
        register = new JButton("Register");
        highscore = new JButton("Hall of Fame");
        manual = new JButton("How-To-Play");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer(0,0);
            }
        });

        login.setBackground(Color.ORANGE);
        register.setBackground(Color.green);
        highscore.setBackground(new Color(126, 237, 235));
        bPanel.add(user);
        bPanel.add(difficultyBox);
        bPanel.add(play);
        bPanel.add(load);
        bPanel.add(login);
        bPanel.add(register);
        bPanel.add(highscore);
        bPanel.add(manual);

    }

    public void startTimer(int minutes, int seconds) {
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

    public void stopTimer() {
        if(timer != null)
            timer.stop();
    }

    public class myCardLayout {
        JPanel cardHolder;
        JPanel card1, card2, buttonPane;
        public CardLayout cardLayout = new CardLayout();
        String tPanel = "Card with testing & 2";
        String bPanel = "Card with testing3";
        JButton bShowOne = new JButton(new showOne());
        JButton bShowTwo = new JButton(new showTwo());

        public void addComponentToPane (Container pane){
            buttonPane = new JPanel();
            buttonPane.add(bShowOne);
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

        }

        private class showOne extends AbstractAction {
            public showOne() { super("Show One"); }
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
        }

        /*public void itemStateChanged(ItemEvent e) {
            CardLayout cl = (CardLayout) (cardHolder.getLayout());
            cl.show(cardHolder, (String) e.getItem());
        }*/

        public void createAndShowGUI() {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            myCardLayout myLayout = new myCardLayout();
            myLayout.addComponentToPane(frame.getContentPane());

            frame.pack();
            frame.setVisible(true);
        }
    }

}