import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * User: Tanner Date: 11/3/2014 Time: 2:02 PM To change this template use File |
 * Settings | File Templates. Update by: Santhosh
 */
public class SubMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JFrame subFrame;
	public static JPanel subPanel;
	public static JTextArea test;
	public static Player player = new Player();

	public SubMenu() {
		subPanel = new JPanel();
		subFrame = new JFrame();
		/*
		 * test = new JTextArea();//Testing jtextarea, might need to switch from
		 * jtextfield test.setPreferredSize(new Dimension(75,50));
		 * test.setLineWrap(true);
		 */
		JButton login = new JButton("Login");
		JButton register = new JButton("Register");
		JButton guest = new JButton("Guest");
		subPanel.add(login);
		subPanel.add(register);
		subPanel.add(guest);
		// subPanel.add(test);
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

		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				subFrame.dispose();
				register();
			}
		});

		guest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subFrame.dispose();
                new Menu();
				/*
				 * SudokuGUI.myCardLayout mcl = sg.new myCardLayout();
				 * mcl.createAndShowGUI();
				 */
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
		JButton forgotPwd = new JButton("Forgot Password");
		JButton goToMainScreen = new JButton("Back to Main Screen");
		final JTextField nameInput = new JTextField(7);
		final JPasswordField passwordInput = new JPasswordField(7);
		JLabel name = new JLabel("Name: ");
		JLabel pass = new JLabel("Password: ");
		final JLabel loginFailed = new JLabel("Login failed. Please check your username or password");
		loginFailed.setVisible(false);

		loginButton.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setUsername(nameInput.getText());
                player.setPassword(passwordInput.getText());
                System.out.println("Username: " + player.getUsername());
                System.out.println("Password: " + player.getPassword());
                try {
                    if (player.validate()) {
                        profile();
                        login.dispose();
                    } else {
                        loginFailed.setVisible(true);
                        // Tanner : Show loginFailed message in loginPanel
                    }
                } catch (SAXException | IOException | ParserConfigurationException e1) {
                    e1.printStackTrace();
                }
                // new Menu();

            }
        });
		
		forgotPwd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                login.dispose();
                forgotPwd();
            }
        });

		goToMainScreen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                login.dispose();
                new SubMenu();
            }
        });

		loginPanel.add(name);
		loginPanel.add(nameInput);
		loginPanel.add(pass);
		loginPanel.add(passwordInput);
		loginPanel.add(loginButton);
		loginPanel.add(loginFailed);
		loginPanel.add(forgotPwd);
		loginPanel.add(goToMainScreen);
		login.add(loginPanel);

		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);
		login.setSize(500, 500);
	}

	public void profile() {
		final JFrame profile = new JFrame(player.getUsername() + "'s Profile");
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
				System.out.println(player.getUsername());
				new Menu();
			}
		});

		statistics = new JPanel();
		buttons = new JPanel();
		statistics.setLayout(new GridLayout(4, 1));
		buttons.setLayout(null);
		dashboard.setBounds(50, 175, 150, 40); // x,y,width,height
		settings.setBounds(50, 225, 150, 40);

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

	public void register() {
		final JFrame register = new JFrame();
		JPanel registerPanel = new JPanel();
		JButton registerButton = new JButton("Register");
		JButton goToMainScreen = new JButton("Back to Main Screen");
		final JTextField nameInput = new JTextField(7);
		final JTextField passwordInput = new JTextField(7);
		final JTextField confirmPassword = new JTextField(7);
		final JTextField securityAnswer = new JTextField(15);
		JLabel name = new JLabel("Username: ");
		JLabel pass = new JLabel("Password: ");
		JLabel confirm = new JLabel("Confirm Password:");
		JLabel secQ = new JLabel("Security Question:");
		final JComboBox<String> securityQuestions = new JComboBox<String>();
		securityQuestions.addItem("What is your first crush name?");
		securityQuestions.addItem("What is your first nick name?");
		securityQuestions.addItem("Where did you attend your first school?");
		securityQuestions.addItem("Which city you were born in?");
		final JLabel secQA = new JLabel("Security Answer");
		JLabel usernameNA = new JLabel("Sorry! Username not available");

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setUsername(nameInput.getText());
				player.setPassword(passwordInput.getText());
				player.setSecretQuestion((String) securityQuestions.getSelectedItem());
				player.setSecurityAnswer(secQA.getText());
				try {
					if (player.register()) {
						profile();
						register.dispose();
					} else {
						// Tanner: Show usernameNA
					}
				} catch (ParserConfigurationException | TransformerException | SAXException | IOException e1) {
					e1.printStackTrace();
				}

				System.out.println("Username: " + player.getUsername());
				System.out.println("Password: " + player.getPassword());
				// check for correct username and password
				// if incorrect, prompt to re-enter or register or forget
				// password

			}
		});

		goToMainScreen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				register.dispose();
				new SubMenu();
			}
		});

		registerPanel.add(name);
		registerPanel.add(nameInput);
		registerPanel.add(pass);
		registerPanel.add(passwordInput);
		registerPanel.add(confirm);
		registerPanel.add(confirmPassword);
		registerPanel.add(secQ);
		registerPanel.add(securityQuestions);
		registerPanel.add(secQA);
		registerPanel.add(securityAnswer);
		registerPanel.add(registerButton);
		registerPanel.add(goToMainScreen);
		register.add(registerPanel);
		register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		register.setVisible(true);
		register.setSize(1000, 1000);
	}
	
	public void forgotPwd() {
		final JFrame forgot = new JFrame();
		final JPanel forgotPwdPanel = new JPanel();
		final JButton showPassword = new JButton("Show Password");
		JButton next = new JButton("Next");
		JButton goToMainScreen = new JButton("Back to Main Screen");
		final JTextField nameInput = new JTextField(7);
		final JTextField secAInput = new JPasswordField(15);
		JLabel name = new JLabel("Username: ");
		JLabel usernameNA = new JLabel("Sorry! Username not found");

		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setUsername(nameInput.getText());
				try {
					if (player.validate()) {
						player.getSecurityQA();
						// Tanner: wanted to add these to the forgot Screen itself
						JLabel secQLabel = new JLabel(player.getSecretQuestion());
						forgotPwdPanel.add(secQLabel);
						forgotPwdPanel.add(secAInput);
						forgotPwdPanel.add(showPassword);
					} else {
						// Tanner : Show user not found message in panel
					}
				} catch (SAXException | IOException | ParserConfigurationException e1) {
					e1.printStackTrace();
				}
				// new Menu();

			}
		});
		
		showPassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JLabel pwd = new JLabel(player.getSecurityAnswer());
				forgotPwdPanel.add(pwd);
			}
		});

		goToMainScreen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                forgot.dispose();
                new SubMenu();
            }
        });

		forgotPwdPanel.add(name);
		forgotPwdPanel.add(nameInput);
		forgotPwdPanel.add(next);
		forgotPwdPanel.add(goToMainScreen);
		forgot.add(forgotPwdPanel);

		forgot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		forgot.setVisible(true);
		forgot.setSize(500, 500);
	}
}