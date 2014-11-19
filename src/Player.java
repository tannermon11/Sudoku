import java.io.File;
import java.io.IOException;
import java.net.URL;

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
public class Player {

	private String username, password, secretQuestion, securityAnswer;
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

	public boolean register() throws ParserConfigurationException, TransformerException, SAXException, IOException {
		if (validate()) {
			return false;
		}
		String username = this.username;
		String pwd = this.password;
		String question = this.secretQuestion;
		String answer = this.securityAnswer;

		addUser(username, pwd, question, answer);

		return true;
	}

	public boolean validate() throws SAXException, IOException, ParserConfigurationException {
		URL url = getClass().getResource(USER_FILE);
		if (url != null) {
			File f = new File(url.getPath());
			if (f.exists()) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				Document doc = dbf.newDocumentBuilder().parse(url.getPath());

				NodeList elemNodeList = doc.getElementsByTagName("user");

				for (int j = 0; j < elemNodeList.getLength(); j++) {
					Node user = elemNodeList.item(j);
					Node usrname = user.getFirstChild();
					if (usrname.getFirstChild().getNodeValue().equals(this.username)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void getSecurityQA() throws SAXException, IOException, ParserConfigurationException {
		URL url = getClass().getResource(USER_FILE);
		if (url != null) {
			File f = new File(url.getPath());
			if (f.exists()) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				Document doc = dbf.newDocumentBuilder().parse(url.getPath());

				NodeList elemNodeList = doc.getElementsByTagName("user");

				for (int j = 0; j < elemNodeList.getLength(); j++) {
					Node user = elemNodeList.item(j);
					Node usrname = user.getFirstChild();
					if (usrname.getFirstChild().getNodeValue().equals(this.username)) {
						NodeList children = user.getChildNodes();
						for (int i = 0; i < children.getLength(); i++) {
							Node node = children.item(i);
							if(node.getFirstChild().getNodeName().equals("question")) {
								this.setSecretQuestion(node.getFirstChild().getNodeValue());
							}
							if(node.getFirstChild().getNodeName().equals("answer")) {
								this.setSecurityAnswer(node.getFirstChild().getNodeValue());
							}
						}
					}
				}
			}
		}
	}

	public void addUser(String username, String pwd, String question, String answer)
			throws ParserConfigurationException, TransformerException, IOException, SAXException {
		URL url = getClass().getResource(USER_FILE);
		File f = new File(url.getPath());

		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document document = null;
		Element root = null;

		if(f.exists()) {
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

		// create the xml file
		// transform the DOM Object to an XML File
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(f);

		transformer.transform(domSource, streamResult);
	}
}