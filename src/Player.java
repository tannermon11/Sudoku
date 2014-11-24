import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.w3c.dom.*;

/**
 * User: Tanner Date: 11/4/2014 Time: 11:30 AM To change this template use File
 * | Settings | File Templates. Updated by: Santhosh
 */
public class Player implements Comparator<Player> {

	private String username, password, secretQuestion, securityAnswer, score;
	private static final String USER_FILE = "Users.xml";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public boolean register() throws ParserConfigurationException, TransformerException, SAXException, IOException {
		if (validate(true)) {
			return false;
		}
		String username = this.username;
		String pwd = this.password;
		String question = this.secretQuestion;
		String answer = this.securityAnswer;
		String score = 0 + "";

		addUser(username, pwd, question, answer, score);

		return true;
	}

	public boolean validate(boolean checkOnlyForUsername) throws SAXException, IOException,
			ParserConfigurationException {
		URL url = getClass().getResource(USER_FILE);
		if (url != null) {
			File f = new File(url.getPath());
			if (f.exists()) {
				String username = null;
				String pwd = null;
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				Document doc = dbf.newDocumentBuilder().parse(url.getPath());
				List<Node> user = getUserNode(false, doc);
				if (user != null && !user.isEmpty()) {
					NodeList children = user.get(0).getChildNodes();
					for (int i = 0; i < children.getLength(); i++) {
						Node node = children.item(i);
						if (node.getNodeName().equals("username")) {
							username = node.getFirstChild().getNodeValue();
						}
						if (node.getNodeName().equals("password")) {
							pwd = node.getFirstChild().getNodeValue();
						}
					}
					if (!checkOnlyForUsername) {
						if (compareString(username, this.getUsername()) && compareString(pwd, this.getPassword())) {
							return true;
						}
					} else {
						if (compareString(username, this.getUsername())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public Player getSecurityQA() throws SAXException, IOException, ParserConfigurationException {
		URL url = getClass().getResource(USER_FILE);
		if (url != null) {
			File f = new File(url.getPath());
			if (f.exists()) {
				Player player = new Player();
				player.setUsername(this.username);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				Document doc = dbf.newDocumentBuilder().parse(url.getPath());
				List<Node> user = getUserNode(false, doc);
				if (user != null && !user.isEmpty()) {
					NodeList children = user.get(0).getChildNodes();
					for (int i = 0; i < children.getLength(); i++) {
						Node node = children.item(i);
						if (node.getNodeName().equals("question")) {
							player.setSecretQuestion(node.getFirstChild().getNodeValue());
						}
						if (node.getNodeName().equals("answer")) {
							player.setSecurityAnswer(node.getFirstChild().getNodeValue());
						}
						if (node.getNodeName().equals("password")) {
							player.setPassword(node.getFirstChild().getNodeValue());
						}
					}
				}
				return player;
			}
		}
		return null;
	}

	public void addUser(String username, String pwd, String question, String answer, String score)
			throws ParserConfigurationException, TransformerException, IOException, SAXException {
		URL url = getClass().getResource(USER_FILE);
		File f = new File(url.getPath());

		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document document = null;
		Element root = null;

		if (f.exists()) {
			document = documentBuilder.parse(f);
			root = document.getDocumentElement();
		} else {
			f.createNewFile();
			document = documentBuilder.newDocument();
			// root element
			root = document.createElement("users");
			document.appendChild(root);
		}

		// root element
		Element user = document.createElement("user");
		root.appendChild(user);

		// username element
		Element usernameElement = document.createElement("username");
		usernameElement.appendChild(document.createTextNode(username));
		user.appendChild(usernameElement);

		// password element
		Element passwordElement = document.createElement("password");
		passwordElement.appendChild(document.createTextNode(pwd));
		user.appendChild(passwordElement);

		// security question element
		Element secQElement = document.createElement("question");
		secQElement.appendChild(document.createTextNode(secretQuestion));
		user.appendChild(secQElement);

		// security answer elements
		Element secAElement = document.createElement("answer");
		secAElement.appendChild(document.createTextNode(answer));
		user.appendChild(secAElement);

		// score elements
		Element scoreElement = document.createElement("score");
		scoreElement.appendChild(document.createTextNode(score));
		user.appendChild(scoreElement);

		// create the xml file
		// transform the DOM Object to an XML File
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(f);

		transformer.transform(domSource, streamResult);
	}

	public List<Node> getUserNode(boolean all, Document doc) throws SAXException, IOException,
			ParserConfigurationException {

		NodeList elemNodeList = doc.getElementsByTagName("user");
		List<Node> users = new ArrayList<Node>();

		if (elemNodeList != null) {
			for (int j = 0; j < elemNodeList.getLength(); j++) {
				Node user = elemNodeList.item(j);
				Node usrname = user.getFirstChild();
				if (usrname != null && usrname.getFirstChild() != null
						&& usrname.getFirstChild().getNodeValue() != null
						&& usrname.getFirstChild().getNodeValue().equals(this.username) && !all) {
					users.add(user);
					return users;
				}
				if (all) {
					users.add(user);
				}
			}
		}
		return users;
	}

	public List<Player> getScoreCard() throws SAXException, IOException, ParserConfigurationException {
		URL url = getClass().getResource(USER_FILE);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = dbf.newDocumentBuilder().parse(url.getPath());
		List<Node> users = getUserNode(true, doc);
		List<Player> players = new ArrayList<Player>();
		String username = null;
		String score = null;
		for (Node user : users) {
			if (user != null) {
				Player player = new Player();
				NodeList children = user.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node node = children.item(i);
					if (node.getNodeName().equals("username")) {
						username = node.getFirstChild().getNodeValue();
						player.setUsername(username);
					}
					if (node.getNodeName().equals("score")) {
						score = node.getFirstChild().getNodeValue();
						player.setScore(score);
					}
				}
				players.add(player);
			}
		}
		Collections.sort(players, Collections.reverseOrder(new Player()));
		return players;
	}

	public boolean compareString(String str1, String str2) {
		if (str1 != null && str2 != null && !str1.trim().equals("") && !str2.trim().equals("")
				&& str1.trim().equals(str2.trim())) {
			return true;
		}
		return false;
	}

	@Override
	public int compare(Player player1, Player player2) {
		int score1 = Integer.parseInt(player1.getScore());
		int score2 = Integer.parseInt(player2.getScore());
		if (score1 > score2) {
			return 1;
		} else if (score1 == score2) {
			return 0;
		}
		return -1;
	}

	public void saveScore() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		URL url = getClass().getResource(USER_FILE);
		File f = new File(url.getPath());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = dbf.newDocumentBuilder().parse(url.getPath());

		List<Node> users = getUserNode(false, doc);
		if (users != null) {
			Node user = users.get(0);
			NodeList children = user.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				if (node.getNodeName().equals("score")) {
					String earlierScore = node.getFirstChild().getTextContent();
					int early = Integer.parseInt(earlierScore);
					if (this.getScore() != null) {
						int current = Integer.parseInt(this.getScore());
						int totalScore = current + early;
						node.getFirstChild().setTextContent(totalScore + "");
						node.getFirstChild().setNodeValue(totalScore + "");
						break;
					}
				}
			}
		}

		// save the result
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(doc);
		StreamResult streamResult = new StreamResult(f);
		transformer.transform(domSource, streamResult);
	}

	// things to do score set properly to db
	// score set properly after game
}