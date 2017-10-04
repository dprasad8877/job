package middleware.papi.adsonui.servicecheck;
/**
 * Purpose : This class is used to form xml form service 
 * check request and to parse the response.
 * 
 * Input : None
 * 
 * Output :None
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.glue.AdsOnUIGlue;

import org.safehaus.uuid.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ServiceCheckXMLPlacement
{
	private String connectionXML;
	private String serverXmlStr;
	private Document doc;
	private InputStream inputStreamOfServer;
	private static int numberOfattempts;
	static ServiceCheckXMLPlacement obj;
	private static UUID id;

	/**
	 * 
	 * @param tempData
	 * @return object of XMLPlacement return value can be null if something goes
	 *         wrong
	 */
	public static ServiceCheckXMLPlacement getInstance(UUID uid)
	{
		if (obj == null)
			obj = new ServiceCheckXMLPlacement();
		numberOfattempts = AdsOnUIConstants.numberOfAttemptsInitialValue;
		id = uid;
		return obj;
	}

	/**
	 * This function will initialize Document instance by parsing InputStream
	 * 
	 * @param temp
	 * @return SUCCESS OR FAILURE
	 */
	private int initOfXml(InputStream temp)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(temp);
			return AdsOnUIConstants.SUCCESS;
		}
		catch (Exception e)
		{
			return AdsOnUIConstants.FAILURE;
		}
	}

	/**
	 * This method will covert servertXmlStr to input stream
	 */
	private void convertServertXmlStrToInputStream()
	{
		if (serverXmlStr != null)
		{
			inputStreamOfServer = new ByteArrayInputStream(serverXmlStr.getBytes());
		}
		else
		{
			inputStreamOfServer = null;
		}
	}

	private String getTextFromElement(Element ele, String tagName)
	{
		String strValue = null;
		NodeList tempnl = ele.getElementsByTagName(tagName);
		if (tempnl != null && tempnl.getLength() > 0)
		{
			Element nameEle = (Element) tempnl.item(0);
			strValue = nameEle.getFirstChild().getNodeValue();
		}
		return strValue;
	}

	public String checkServerConnection()
	{
		// TODO Auto-generated method stub
		createConnectionXML();
		while (true)
		{
			ServiceCheckCommunication sendObj = new ServiceCheckCommunication(connectionXML, id);
			StringBuffer receivedXmlString = new StringBuffer();
			boolean retFlag = sendObj.sendAndFillResultIn(receivedXmlString);
			if (retFlag == true && receivedXmlString != null)
			{
				serverXmlStr = receivedXmlString.toString();
				convertServertXmlStrToInputStream();
				int result = initOfXml(inputStreamOfServer);
				if (result == AdsOnUIConstants.FAILURE)
				{
					return null;
				}
				String status = connectionResponse();
				return status;
			}
			else if (retFlag == false)
			{
				if (numberOfattempts >= AdsOnUIConstants.finalNumberOfAttempts)
				{
					break;
				}
				else
				{
					numberOfattempts++;
					createConnectionXML();
				}
			}
		}
		return null;
	}

	private String connectionResponse()
	{
		// TODO Auto-generated method stub
		Element root = doc.getDocumentElement();
		// fetch Unique-id from xml message
		// after fetching store it in PlacementResponseDataBase
		// String messageIdStr=root.getAttribute("messageId");
		String status = getTextFromElement(root, "Status");
		return status;
	}

	private void createConnectionXML()
	{
		// TODO Auto-generated method stub
		StringBuffer connBuffer = new StringBuffer();
		connBuffer.append("<ServiceCheckRequest messageId=\"" + id + "\" Version=\"1.0\">");
		connBuffer.append("<SubscriberId>" + AdsOnUIGlue.getSubscriberId() + "</SubscriberId>");
		connBuffer.append("</ServiceCheckRequest>");
		connectionXML = connBuffer.toString();
	}
}
