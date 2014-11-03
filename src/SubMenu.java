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
    public SubMenu() {
        subPanel = new JPanel();
        subFrame = new JFrame();
        JButton login = new JButton("Login");
        JButton register = new JButton("Register");
        JButton guest = new JButton("Guest");
        subPanel.add(login);
        subPanel.add(register);
        subPanel.add(guest);
        subFrame.add(subPanel);
        login.setBackground(Color.ORANGE);
        register.setBackground(Color.green);

        guest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subFrame.dispose();
                Menu mn = new Menu();
                /*SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
                mcl.createAndShowGUI();*/
            }
        });

        subFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        subFrame.setVisible(true);
        subFrame.setSize(500, 500);
    }
}