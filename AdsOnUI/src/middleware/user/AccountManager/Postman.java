package middleware.user.AccountManager;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import Util.Debug.Log;
import middleware.user.AccountManager.UserAccountsManager;
import middleware.user.AccountManager.UserDetails;
import middleware.user.AccountManager.UserDetailsConstants;

public class Postman
{
	private int numberOfReAttempts = 0;
	private String responseFromServer = "";

	// only for single user by passing userobj as parameter.
	public String sendUserDetails(UserDetails userobj)
	{
		String xml = createXml(userobj, numberOfReAttempts);
		CommunicationModule comm = new CommunicationModule(xml);
		String serverResponse = comm.sendRequestAndReceiveResponseFromServer();
		if (serverResponse == null && numberOfReAttempts < 3)
		{
			numberOfReAttempts++;
			Log.display("During exception called " + numberOfReAttempts + " times");
			sendUserDetails(userobj);
			responseFromServer = null;
		}
		else if (serverResponse != null)
		{
			Log.display("Server Response ==>" + serverResponse);
			responseFromServer = parse(serverResponse);
		}
		return responseFromServer;
	}

	public String sendAllUserDetails()
	{
		String xml = createXml(numberOfReAttempts);
		CommunicationModule comm = new CommunicationModule(xml);
		String serverResponse = comm.sendRequestAndReceiveResponseFromServer();
		if (serverResponse == null && numberOfReAttempts < 3)
		{
			numberOfReAttempts++;
			Log.display("During exception called " + numberOfReAttempts + " times");
			sendAllUserDetails();
			responseFromServer = null;
		}
		else if (serverResponse != null)
		{
			Log.display("Server Response ==>" + serverResponse);
			responseFromServer = parse(serverResponse);
		}
		return responseFromServer;
	}

	// Creates Xml for provided userObject
	private String createXml(UserDetails userobj, int numberOfReAttempts)
	{
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("<SubscriberProfileRequest>" + "<Header>UD,NoOfUsers</Header>"
		        + "<NoOfUsers>" + UserAccountsManager.getInstance().getListOfUsers().size()
		        + "</NoOfUsers>" + "<SubscriberId>S1000001</SubscriberId>");
		stringbuffer.append("<User>");
		stringbuffer.append("<UserId>U" + userobj.getUserId() + "</UserId>");
		if (userobj.getGender() == UserDetailsConstants.MALE)
		{
			stringbuffer.append(xmlNodeCreation("Gender", "Male"));
		}
		else
		{
			stringbuffer.append(xmlNodeCreation("Gender", "Female"));
		}
		stringbuffer.append(xmlNodeCreation("DOB", userobj.getDob()));
		if (userobj.isMarried())
		{
			stringbuffer.append(xmlNodeCreation("MaritalStatus", "Single"));
		}
		else
		{
			stringbuffer.append(xmlNodeCreation("MaritalStatus", "Married"));
		}
		if (userobj.isEmployed())
		{
			stringbuffer.append(xmlNodeCreation("EmployementStatus", "Employed"));
		}
		else
		{
			stringbuffer.append(xmlNodeCreation("EmployementStatus", "Unemployed"));
		}
		stringbuffer.append(xmlNodeCreation("Income", userobj.getIncome()));
		stringbuffer.append(xmlNodeCreation("Languages", userobj.getLanguagesKnown()));
		stringbuffer.append(xmlNodeCreation("EmailId", userobj.getEmailId()));
		stringbuffer.append("</User>");
		stringbuffer.append("<Location>" + userobj.getCity() + "</Location>");
		stringbuffer.append("<Reattempt>" + numberOfReAttempts + "</Reattempt>");
		stringbuffer.append("</SubscriberProfileRequest>");
		return stringbuffer.toString();
	}

	// // At BootUp time
	public String createXml(int numberOfReAttempts)
	{
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("<SubscriberProfileRequest>" + "<Header>UD,NoOfUsers</Header>"
		        + "<NoOfUsers>" + UserAccountsManager.getInstance().getListOfUsers().size()
		        + "</NoOfUsers>" + "<SubscriberId>S1000001</SubscriberId>");
		ArrayList userList = UserAccountsManager.getInstance().getListOfUsers();
		for (int i = 0; i < userList.size(); i++)
		{
			stringbuffer.append("<User>");
			stringbuffer.append("<UserId>U" + ((UserDetails) userList.get(i)).getUserId()
			        + "</UserId>");
			// stringbuffer.append(xmlNodeCreation("UserId",
			// userList
			// .get(i).getUserId()));
			if (((UserDetails) userList.get(i)).getGender() == UserDetailsConstants.MALE)
			{
				stringbuffer.append(xmlNodeCreation("Gender", "Male"));
			}
			else
			{
				stringbuffer.append(xmlNodeCreation("Gender", "Female"));
			}
			stringbuffer.append(xmlNodeCreation("DOB", ((UserDetails) userList.get(i)).getDob()));
			if (((UserDetails) userList.get(i)).isMarried())
			{
				stringbuffer.append(xmlNodeCreation("MaritalStatus", "Single"));
			}
			else
			{
				stringbuffer.append(xmlNodeCreation("MaritalStatus", "Married"));
			}
			if (((UserDetails) userList.get(i)).isEmployed())
			{
				stringbuffer.append(xmlNodeCreation("EmployementStatus", "Employed"));
			}
			else
			{
				stringbuffer.append(xmlNodeCreation("EmployementStatus", "Unemployed"));
			}
			stringbuffer.append(xmlNodeCreation("Income",
			        ((UserDetails) userList.get(i)).getIncome()));
			stringbuffer.append(xmlNodeCreation("Languages",
			        ((UserDetails) userList.get(i)).getLanguagesKnown()));
			stringbuffer.append(xmlNodeCreation("EmailId",
			        ((UserDetails) userList.get(i)).getEmailId()));
			stringbuffer.append("</User>");
		}
		stringbuffer.append("<Location>" + ((UserDetails) userList.get(0)).getCity()
		        + "</Location>");
		stringbuffer.append("<Reattempt>" + numberOfReAttempts + "</Reattempt>");
		stringbuffer.append("</SubscriberProfileRequest>");
		return stringbuffer.toString();
	}

	private String xmlNodeCreation(String tagName, String tagValue)
	{
		return "<" + tagName + ">" + tagValue + "</" + tagName + ">";
	}

	// private String xmlNodeCreation(String tagName, int tagValue) {
	// return "<" + tagName + ">" + tagValue + "</" + tagName + ">";
	// }
	private String parse(String xml)
	{
		String parseResult = "";
		if (xml == null)
		{
			throw new IllegalArgumentException("Parameter (xml) contains null");
		}
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("Status");
			for (int i = 0; i < nodes.getLength();)
			{
				Element element = (Element) nodes.item(i);
				NodeList acknowledgement = element.getElementsByTagName("Acknowledgement");
				Element line = (Element) acknowledgement.item(0);
				parseResult += textnode(line);
				parseResult += "\n";
				NodeList message = element.getElementsByTagName("Message");
				line = (Element) message.item(0);
				parseResult += textnode(line);
				parseResult += "\n";
				return parseResult;
			}
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private String textnode(Element elemChildNode)
	{
		Node elemText = elemChildNode.getFirstChild();
		String elemChildNodeName = elemChildNode.getNodeName();
		String value = elemChildNodeName + ":" + elemText.getNodeValue();
		return value;
	}
}
