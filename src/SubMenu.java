import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Tanner
 * Date: 11/3/2014
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubMenu extends JFrame { //login, register, guest
    JFrame subFrame;
    JPanel subPanel;
    JTextArea test;
    Player player = new Player();
    public SubMenu() {
        subPanel = new JPanel();
        subFrame = new JFrame();
        /*test = new JTextArea();//Testing jtextarea, might need to switch from jtextfield
        test.setPreferredSize(new Dimension(75,50));
        test.setLineWrap(true);*/
        JButton login = new JButton("Login");
        JButton register = new JButton("Register");
        JButton guest = new JButton("Guest");
        subPanel.add(login);
        subPanel.add(register);
        subPanel.add(guest);
        //subPanel.add(test);
        subFrame.add(subPanel);
        login.setBackground(Color.ORANGE);
        register.setBackground(Color.green);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subFrame.dispose();
                login();
            }
        });

        guest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subFrame.dispose();
                new Menu();
                /*SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
                mcl.createAndShowGUI();*/
            }
        });

        subFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        subFrame.setVisible(true);
        subFrame.setSize(500, 500);
    }

    public void login() {
        final JFrame login = new JFrame();
        JPanel loginPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        final JTextField nameInput = new JTextField(7);
        final JPasswordField passwordInput = new JPasswordField(7);
        JLabel name = new JLabel("Name: ");
        JLabel pass = new JLabel("Password: ");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.username = nameInput.getText();
                player.password = passwordInput.getText(); //getPassword()?
                System.out.println("Username: " + player.username);
                System.out.println("Password: " + player.password);
                //check for correct username and password
                //if incorrect, prompt to re-enter or register or forget password
                profile();
                login.dispose();
                //new Menu();

            }
        });

        loginPanel.add(name);
        loginPanel.add(nameInput);
        loginPanel.add(pass);
        loginPanel.add(passwordInput);
        loginPanel.add(loginButton);
        login.add(loginPanel);

        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setVisible(true);
        login.setSize(500, 500);
    }

    public void profile() {
        final JFrame profile = new JFrame(player.username + "'s Profile");
        JPanel statistics, buttons;
        JLabel statsLabel = new JLabel("Statistics");
        JLabel games = new JLabel("Games Completed: ");
        JLabel highscore = new JLabel("Highest score: ");
        JLabel bestTime = new JLabel("Best time: ");
        JButton dashboard = new JButton("Go to dashboard");
        JButton settings = new JButton("Profile settings");

        dashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profile.dispose();
                new Menu();
            }
        });

        statistics = new JPanel();
        buttons = new JPanel();
        statistics.setLayout(new GridLayout(4, 1));
        buttons.setLayout(null);
        dashboard.setBounds(50,175,150,40); //x,y,width,height
        settings.setBounds(50,225,150,40);

        statistics.add(statsLabel);
        statistics.add(games);
        statistics.add(highscore);
        statistics.add(bestTime);
        buttons.add(dashboard);
        buttons.add(settings);

        profile.add(statistics);
        profile.add(buttons);
        profile.setLayout(new GridLayout(1, 2));
        profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profile.setVisible(true);
        profile.setSize(500, 500);
    }
}