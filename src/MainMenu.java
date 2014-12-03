import javax.swing.*;
import javax.swing.border.BevelBorder;
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
public class MainMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JFrame subFrame;
	public static JPanel subPanel;
	public static JTextArea test;
	public static Player player = new Player();
	public static boolean modeColor = false;

	public MainMenu() {
		subPanel = new JPanel();
		subPanel.setLayout(null);
		subFrame = new JFrame();
		JButton login = new JButton("Login");
		JButton register = new JButton("Register");
		JButton guest = new JButton("Guest");
		login.setBounds(150, 150, 200, 40); // x,y,width,height
		register.setBounds(150, 200, 200, 40);
		guest.setBounds(150, 250, 200, 40);
		subPanel.add(login);
		subPanel.add(register);
		subPanel.add(guest);
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
				MainMenu.player.setUsername(null);
				MainMenu.player.setScore("0");
				subFrame.dispose();
				new DashBoardMenu();
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
		loginPanel.setLayout(null);
		JButton loginButton = new JButton("Login");
		JButton forgotPwd = new JButton("Forgot Password");
		JButton goToMainScreen = new JButton("Back to Main Screen");
		final JTextField nameInput = new JTextField(7);
		final JPasswordField passwordInput = new JPasswordField(7);
		JLabel name = new JLabel("Username: ");
		JLabel pass = new JLabel("Password: ");
		final JLabel loginFailed = new JLabel("Login failed. Please check your username or password");
		loginFailed.setVisible(false);

		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setUsername(nameInput.getText());
				player.setPassword(passwordInput.getText());
				try {
					player.setInitialScore(player.getScoreOfPlayer());
				} catch (SAXException | IOException | ParserConfigurationException e2) {
					e2.printStackTrace();
				}
				Score.score = 0;
				System.out.println("Username: " + player.getUsername());
				System.out.println("Password: " + player.getPassword());
				try {
					if (player.validate(false)) {
						profile();
						login.dispose();
					} else {
						loginFailed.setVisible(true);
						loginFailed.setBounds(100, 200, 350, 40);
				    	goToMainScreen.setBounds(150, 250, 200, 40);
					}
				} catch (SAXException | IOException | ParserConfigurationException e1) {
					e1.printStackTrace();
				}

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
				new MainMenu();
			}
		});
		name.setBounds(150, 50, 80, 40);
		nameInput.setBounds(230, 50, 150, 30);
    	pass.setBounds(150, 100, 80, 40);
    	passwordInput.setBounds(230, 100, 150, 30);
    	loginButton.setBounds(100, 150, 150, 30);
    	forgotPwd.setBounds(250, 150, 150, 30);
    	goToMainScreen.setBounds(150, 200, 200, 40);
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

	public static void profile() {
		final JFrame profile = new JFrame(player.getUsername() + "'s Profile");
		JPanel buttons;
		JButton dashboard = new JButton("Go to dashboard");
		JButton settings = new JButton("Profile settings");
		JButton logout = new JButton("Log out");

		dashboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				profile.dispose();
				System.out.println(player.getUsername());
				new DashBoardMenu();
			}
		});

		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				profile.dispose();
				settings();
			}
		});

		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				profile.dispose();
				try {
					player.saveScore();
					player.setScore("0");
					Score.score = 0;
				} catch (SAXException | IOException | ParserConfigurationException | TransformerException e1) {
					e1.printStackTrace();
				}
				new MainMenu();
			}
		});

		buttons = new JPanel();
		buttons.setLayout(null);
		dashboard.setBounds(150, 150, 200, 40); // x,y,width,height
		settings.setBounds(150, 200, 200, 40);
		logout.setBounds(150, 250, 200, 40);

		buttons.add(dashboard);
		buttons.add(settings);
		buttons.add(logout);

		profile.add(buttons);
		profile.setLayout(new GridLayout(1, 2));
		profile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		profile.setVisible(true);
		profile.setSize(500, 500);
	}

	public static void settings() {
		final JFrame settings = new JFrame("Profile Settings");
		JPanel settingPanel = new JPanel();
		JButton save = new JButton("Save settings");
		JButton goToMainScreen = new JButton("Back to Main Screen");
		JLabel savedSettingMessage = new JLabel("Background color changed");
		@SuppressWarnings("unchecked")
		final JComboBox color = new JComboBox(new String[] { "White background / Black lines",
				"Black background / White lines" });

		color.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(color.getSelectedItem().toString());
				if (color.getSelectedItem().toString().equalsIgnoreCase("White background / Black lines"))
					modeColor = false;
				if (color.getSelectedItem().toString().equalsIgnoreCase("Black background / White lines"))
					modeColor = true;
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savedSettingMessage.setVisible(true);
				Timer t = new Timer(2000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						savedSettingMessage.setVisible(false);
					}
				});
				t.start();
			}
		});

		goToMainScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.dispose();
				profile();
			}
		});

		settingPanel.setLayout(new FlowLayout());
		settingPanel.add(color);
		settingPanel.add(save);
		settingPanel.add(savedSettingMessage);
		settingPanel.add(goToMainScreen);
		savedSettingMessage.setVisible(false);
		settings.add(settingPanel);

		settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settings.setVisible(true);
		settings.setSize(500, 500);
	}

	public void register() {
		final JFrame register = new JFrame();
		JPanel registerPanel = new JPanel();
		registerPanel.setLayout(null);
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
		final JLabel usernameNA = new JLabel("Sorry! Username not available");
		final JLabel pwdMismatch = new JLabel("Sorry! Passwords do not match");

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setUsername(nameInput.getText());
				player.setPassword(passwordInput.getText());
				player.setSecretQuestion((String) securityQuestions.getSelectedItem());
				player.setSecurityAnswer(securityAnswer.getText());
				try {
					if (confirmPassword.getText().equals(passwordInput.getText())) {
						if (player.register()) {
							profile();
							register.dispose();
						} else {
							pwdMismatch.setVisible(false);
							usernameNA.setVisible(true);
							usernameNA.setBounds(120, 70, 200, 30);
						}
					} else {
						usernameNA.setVisible(false);
						pwdMismatch.setVisible(true);
						pwdMismatch.setBounds(120, 180, 200, 30);
					}
				} catch (ParserConfigurationException | TransformerException | SAXException | IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("Username: " + player.getUsername());
				System.out.println("Password: " + player.getPassword());
			}
		});

		goToMainScreen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				register.dispose();
				new MainMenu();
			}
		});

		name.setBounds(120, 50, 80, 30);
		nameInput.setBounds(230, 50, 150, 30);
    	pass.setBounds(120, 100, 80, 30);
    	passwordInput.setBounds(230, 100, 150, 30);
    	confirm.setBounds(120, 150, 150, 30);
    	confirmPassword.setBounds(230, 150, 150, 30);
    	secQ.setBounds(120, 200, 150, 30);
    	securityQuestions.setBounds(230, 200, 240, 30);
    	secQA.setBounds(120, 250, 80, 30);
    	securityAnswer.setBounds(230, 250, 150, 30);
    	registerButton.setBounds(150, 300, 200, 30);
    	goToMainScreen.setBounds(150, 350, 200, 30);
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
		registerPanel.add(pwdMismatch);
		pwdMismatch.setVisible(false);
		registerPanel.add(usernameNA);
		usernameNA.setVisible(false);
		registerPanel.add(registerButton);
		registerPanel.add(goToMainScreen);
		register.add(registerPanel);
		register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		register.setVisible(true);
		register.setSize(500, 500);
	}

	public void forgotPwd() {
		final JFrame forgot = new JFrame();
		final JPanel forgotPwdPanel = new JPanel();
		forgotPwdPanel.setLayout(null);
		final JButton showPassword = new JButton("Show Password");
		JButton next = new JButton("Next");
		JButton goToMainScreen = new JButton("Back to Main Screen");
		final JTextField nameInput = new JTextField(7);
		final JTextField secAInput = new JTextField(15);
		JLabel name = new JLabel("Username: ");
		final JLabel usernameNA = new JLabel("Sorry! Username not found");
		final JLabel wrongAnswer = new JLabel("Sorry! Wrong Answer");
		final JLabel secQLabel = new JLabel();
		final JLabel pwd = new JLabel("Password: ");
		final JLabel password = new JLabel();

		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setUsername(nameInput.getText());
				try {
					if (player.validate(true)) {
						player = player.getSecurityQA();
						if (player != null) {
							usernameNA.setVisible(false);
							secQLabel.setText(player.getSecretQuestion());
							secQLabel.setBounds(40, 200, 210, 30); // x,y,width,height
							secAInput.setBounds(230, 200, 150, 30);
							showPassword.setBounds(150, 250, 150, 40);
							goToMainScreen.setBounds(150, 300, 150, 40);
							secQLabel.setVisible(true);
							secAInput.setVisible(true);
							showPassword.setVisible(true);
						}
					} else {
						usernameNA.setVisible(true);
						pwd.setVisible(false);
						password.setVisible(false);
						showPassword.setVisible(false);
						wrongAnswer.setVisible(false);
						secAInput.setVisible(false);
						secQLabel.setVisible(false);
						usernameNA.setBounds(150, 200, 200, 30); // x,y,width,height
						goToMainScreen.setBounds(150, 250, 150, 40);
					}
				} catch (SAXException | IOException | ParserConfigurationException e1) {
					e1.printStackTrace();
				}
			}
		});

		showPassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (secAInput.getText().equals(player.getSecurityAnswer())) {
					pwd.setVisible(true);
					pwd.setBounds(150, 300, 150, 40);
					password.setBounds(230, 300, 150, 40);
					goToMainScreen.setBounds(150, 350, 150, 40);
					wrongAnswer.setVisible(false);
					password.setText(player.getPassword());
					password.setVisible(true);
				} else {
					pwd.setVisible(false);
					password.setVisible(false);
					wrongAnswer.setVisible(true);
					wrongAnswer.setBounds(150, 300, 150, 40);
					goToMainScreen.setBounds(150, 350, 150, 40);
				}
			}
		});

		goToMainScreen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				forgot.dispose();
				new MainMenu();
			}
		});

		
		name.setBounds(150, 100, 150, 30); // x,y,width,height
		nameInput.setBounds(230, 100, 150, 30);
		next.setBounds(150, 150, 150, 40);
		goToMainScreen.setBounds(150, 200, 200, 40);
		
		forgotPwdPanel.add(name);
		forgotPwdPanel.add(nameInput);
		forgotPwdPanel.add(next);
		forgotPwdPanel.add(secQLabel);
		secQLabel.setVisible(false);
		forgotPwdPanel.add(secAInput);
		secAInput.setVisible(false);
		forgotPwdPanel.add(showPassword);
		showPassword.setVisible(false);
		forgotPwdPanel.add(pwd);
		pwd.setVisible(false);
		forgotPwdPanel.add(password);
		password.setVisible(false);
		forgotPwdPanel.add(usernameNA);
		usernameNA.setVisible(false);
		forgotPwdPanel.add(wrongAnswer);
		wrongAnswer.setVisible(false);
		forgotPwdPanel.add(goToMainScreen);
		forgot.add(forgotPwdPanel);

		forgot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		forgot.setVisible(true);
		forgot.setSize(500, 500);
	}
}