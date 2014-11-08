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
        JPasswordField passwordInput = new JPasswordField(7);
        JLabel name = new JLabel("Name: ");
        JLabel pass = new JLabel("Password: ");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.username = nameInput.getText(); //pass.getText();
                System.out.print("Username: " + Player.username);
                login.dispose();
                new Menu();
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
}