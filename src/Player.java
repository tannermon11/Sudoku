mport java.io.IOException;
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
		ListofPlayers = new ArrayList<String>();
		Document document;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder db = dbFactory.newDocumentBuilder();

			document = db.parse(xml);
			Element e = document.getDocumentElement();

			username = getTextValue(username, e, "username");
			if (username != null)
			{
				if(!username.isEmpty())
					{ListofPlayers.add(username);}
			}
            return true;
		}
        catch (ParserConfigurationException pce)
        {System.out.println(pce.getMessage());}
        catch (SAXException se)
        {System.out.println(se.getMessage());}
        catch (IOException ioe)
        {System.err.println(ioe.getMessage());}

        return false;
	}

	public void saveUser(String xmlFile)
	{
		Document document;
		Element element = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			document = db.newDocument();

			Element root = document.createElement("User Information");

			element = document.createElement("Username");
			element.appendChild(document.createElement(username));
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

                tr.transform(new DOMSource(document),new StreamResult(new FileOutputStream(xmlFile)));
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
