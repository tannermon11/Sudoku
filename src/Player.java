import java.io.BufferedReader;
import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.util.*;

/**
 * User: Tanner
 * Date: 11/4/2014
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player
{
	String username = null;
	String password = null;
	String secretQuestion = null;
	String secretAnswer = null;
	ArrayList<String> ListofPlayers;

	public boolean createPlayer(String xml)
	{
		List = new ArrayList<String>();
		Document document;
		DocumentBuilder
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder db = dbFactory.newDoumentBuilder();

			document = db.parse(xml)
			Element doc = document.getDocumentElement();

			username = getTextValue(username, doc, "username");
			if (username != null)
			{
				if(!username.isEmpty())
					{username.add(username);}
			}
		}
	}

	public void saveUser(String xmlFile)
	{
		Document document;
		Element element = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder db = dbFactory.newDoumentBuilder();
			document = db.newDocument();

			Element root = document.createElement("User Information");

			element = document.createElement("Username");
			element.appendChild(dom.createElement(username));
			root.appendChild(element);


			document.appendChild(root);
			try
			{
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

				tr.transform(new DOMSource(document)), new StreamResults(new FileOutputStream(xmlFile)));
			}
			catch(TransformerException te)
			{
				System.out.println(te.getMessage());
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
		}
		catch (ParserConfigurationException pce)
		{
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
    	}
	}

	public void setTextValue(String information, Element doc, String tag)
	{
		Node node = getNode(tag,

		NodeList nodeList = node.getElementsByTagName(tag);
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			Node info = child
		}

	}

	public String getTextValue(String information, Element doc, String tag)
	{
		String info = information;
		NodeList nodeList = doc.getElementsByTagName(tag);

		if (nodeList.getLength() > 0 && nodeList.item(0).hasChildNodes())
		{
			info = nodeList.item(0).getFirstChild().getNodeValues();
		}

		return info;
	}
}
