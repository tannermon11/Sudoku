import java.io.BufferedReader;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.*;
/**
 * User: Tanner
 * Date: 11/4/2014
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player
{
	String username, password, secretQuestion;
	try
	{
		File userFolder = new File("/UserInformation");
		FileReader fr = null;
		BufferedReader reader = null;
		File[] listOfProfiles = userFolder.listFiles();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document[] Profiles = dBuilder.parse(listOfProfiles);

	}
	ArrayList<String> listOfNames = new ArrayList<String>();

	for(int i = 0; i < listOfProfiles.length; i++)
	{
		if(listOfProfiles[i].isFile())
		{
			listOfName.add(listOfProfiles[i].getName());
		}
	}

	String getName(File User+".xml")
	{
		return eElement.getElementsBy
	}

    void CreateUser(String name, String code, String question)
    {
		if(
	}
}
