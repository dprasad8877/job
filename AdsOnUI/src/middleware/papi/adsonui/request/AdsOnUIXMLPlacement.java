/**
 * Purpose : This class is responsible for sending
 *  and receiving  xml data(AdsOnUI) from the server 
 *  
 *  Input : 1)adsOnUIRequestData
 *  		2)requestType
 *  
 *  Output : None
 */

package middleware.papi.adsonui.request;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import middleware.papi.adsonui.AdsOnUIData;
import middleware.papi.adsonui.adt.AdtData;
import middleware.papi.adsonui.adt.AdtManager;
import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.glue.AdsOnUIGlue;
import middleware.papi.adsonui.util.AdsOnUILog;
import middleware.papi.serverconflict.Question;
import middleware.papi.serverconflict.QuizManager;
import middleware.user.AccountManager.UserAccountsManager;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Application.cloud.config.SettingsFileInterface;
import Util.Debug.Log;



/**
 * This class is responsible for AdsOnUI request and response
 * @author 
 *
 */
public class AdsOnUIXMLPlacement extends Thread{

//	static int OppId1=1, OppId2=2 , OppId3=5, OppId4=6, OppId5=7;   // unique opportunity Ids for PC Client
/*	public static int OppId1;  	

	public static int OppId2;

	public static int OppId3;

	public static int OppId4;

	public static int OppId5;*/
	//private static Thread serverRequestThread;
	//	private  Thread serverRequestThread;

	public static String oppids="";
	private Document doc;

	/**
	 * String used to hold the request XML sent to server by client
	 */
	private String clientXmlString;

	/**
	 * String used to hold the response XML from server
	 */
	private String serverXmlString;

	/**
	 * Unique Id which is sent in request to server
	 */
	private UUID uuId;


	private InputStream inputStreamOfServer;

	/**
	 * No of attempts the client has to try to connect to server if it fails each time  
	 */
	private int numberOfattempts;

	private StringBuffer tempBuffer;

	private AdsOnUIRequestData adsOnUIRequestData;

	private AdsOnUIData adsOnUIData ;

	private int requestType;

	private CommunicationListener communicationListener;

	private Question question;






	/**
	 * Constructor takes AdsOnUIRequestData object and 
	 * converts object to clientXmlStr
	 * 
	 * @author  
	 * @param PlacementRequestDataBase object
	 */
	public AdsOnUIXMLPlacement(AdsOnUIRequestData adsOnUIRequestData , int requestType) {

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_constructor : Enter");

		if(adsOnUIRequestData == null && 
				(requestType != AdsOnUIConstants.ADT && 
				requestType != AdsOnUIConstants.QUIZ))
		{
			System.out.println("adsOnUIRequestData is null");
			return;
		}

		if(requestType == AdsOnUIConstants.COMPLETE 
				|| requestType == AdsOnUIConstants.REPEAT)
		{

			this.adsOnUIRequestData=adsOnUIRequestData;
		}

		this.requestType = requestType;

		numberOfattempts=AdsOnUIConstants.numberOfAttemptsInitialValue;

		//generate a unique id for each message
		UUIDGenerator idGen=UUIDGenerator.getInstance();
		uuId=idGen.generateRandomBasedUUID();

		//convert adsOnUIRequestData object into xml_string
		if(requestType == AdsOnUIConstants.COMPLETE)
		{
			convertAdsOnUIRequestDataBaseObjectToClientXmlStr();
		}
		else if(requestType == AdsOnUIConstants.REPEAT)
		{
			convertAdsOnUIRepeatRequestDataBaseObjectToClientXmlStr();
		}
		else if(requestType == AdsOnUIConstants.ADT)
		{
			convertAdsOnUIADTObjectToClientXmlStr();
		}



		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_constructor : Exit");


	}


	/**
	 * Constructor takes AdsOnUIRequestData object and 
	 * converts object to clientXmlStr
	 * 
	 * @author  
	 * @param PlacementRequestDataBase object
	 */
	public AdsOnUIXMLPlacement(Question question , int requestType) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_constructor : Enter");

		if(question == null)
		{
			System.out.println("adsOnUIRequestData is null");
			return;
		}

		this.question = question;

		this.requestType = requestType;

		numberOfattempts=AdsOnUIConstants.numberOfAttemptsInitialValue;

		//generate a unique id for each message
		UUIDGenerator idGen=UUIDGenerator.getInstance();
		uuId=idGen.generateRandomBasedUUID();

		convertQuizObjectToClientXmlStr();


		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_constructor : Exit");
	}



	/**
	 * This function will initialize Document instance by parsing InputStream
	 * 
	 * @author 
	 * @param temp
	 * @return SUCCESS OR FAILURE
	 */
	private int initOfXml(InputStream temp)
	{	

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_initOfXml : Enter");


		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder db=dbf.newDocumentBuilder();
			doc=db.parse(temp);
			doc.getDocumentElement().normalize();
			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_initOfXml : Return sucess");
			return AdsOnUIConstants.SUCCESS;
		} 
		catch (Exception e) 
		{

			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_initOfXml : Return failure");
			return AdsOnUIConstants.FAILURE;
		} 
	}

	/**
	 * @author 
	 * This method will covert clientXmlStr to input stream
	 * 
	 */
	/*	private void convertClientXmlStrToInputStream()
	{
		if(clientXmlStr!=null)
		{
			new ByteArrayInputStream(clientXmlStr.getBytes());
		}
		else
		{
		}
	}*/

	/**
	 * @author 
	 * This method will covert servertXmlStr to input stream
	 */
	private void convertServertXmlStrToInputStream()
	{
		if(serverXmlString!=null)
		{
			inputStreamOfServer=new ByteArrayInputStream(serverXmlString.getBytes());
		}
		else
		{
			inputStreamOfServer=null;
		}
	}


	public void sendDataToServer(CommunicationListener communicationListener)
	{
		if(communicationListener == null)
		{
			return;
		}
		this.communicationListener = communicationListener; 

		this.start();

	}



	/**
	 * This function will send AdsOnUIRequest to server
	 * and returns AdsOnUIResponse 
	 * 
	 * if server fails then this function may return null
	 * 
	 * @author 
	 * @return PlacementResponseDataBase object
	 * 
	 */
	public void  sendToServer_and_getTheAdsOnUIResponseObj()
	{


		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_sendToServer_and_getThePlacementResponseObj : Enter");

		while(true)
		{
			System.out.println("Request : "+clientXmlString+" "+requestType);

			Communication sendObj=new Communication(clientXmlString,uuId,requestType);
			StringBuffer receivedXmlString=new StringBuffer();


			boolean retFlag=sendObj.sendAndFillResultIn(receivedXmlString);
			if(retFlag==true && receivedXmlString!=null)
			{	
				serverXmlString=receivedXmlString.toString();

				System.out.println("********");
				System.out.println("Server ads on ui response "+serverXmlString);
				System.out.println("********");

				convertServertXmlStrToInputStream();
				int result=initOfXml(inputStreamOfServer);

				if(result==AdsOnUIConstants.FAILURE)
				{
					System.out.println("Result is a failure");
					communicationListener.communicationDone(requestType,AdsOnUIConstants.ONE, null);
					break;
				}

				if(requestType != AdsOnUIConstants.ADT 
						&& requestType != AdsOnUIConstants.QUIZ)
				{
					AdsOnUIData tempAdsOnUIData=parseResponse();
					if(tempAdsOnUIData != null)
					{
						communicationListener.communicationDone(requestType,AdsOnUIConstants.ZERO, tempAdsOnUIData);
					}
				}
				else
				{
					communicationListener.communicationDone(requestType, AdsOnUIConstants.ZERO, null);
				}
				break;
			}
			else if(retFlag==false)
			{
				if(numberOfattempts>=AdsOnUIConstants.finalNumberOfAttempts)
				{
					communicationListener.communicationDone(requestType,AdsOnUIConstants.ONE, null);
					break;
				}
				else
				{
					numberOfattempts++;
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE," AdsOnUIXMLPlacement_sendToServer_and_getThePlacementResponseObj : attempt number" + numberOfattempts);
					if(requestType == AdsOnUIConstants.COMPLETE)
					{
						convertAdsOnUIRequestDataBaseObjectToClientXmlStr();
					}
					else if(requestType == AdsOnUIConstants.REPEAT)
					{
						convertAdsOnUIRepeatRequestDataBaseObjectToClientXmlStr();
					}
					else if(requestType == AdsOnUIConstants.ADT)
					{
						convertAdsOnUIADTObjectToClientXmlStr();
					}
					else if(requestType == AdsOnUIConstants.QUIZ)
					{
						convertQuizObjectToClientXmlStr();
					}

				}
			}
		}

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_sendToServer_and_getThePlacementResponseObj : Exit");


	}

	public AdsOnUIData testParsingOfSampleResponseData()
	{
		int result = 0;

		try {
			InputStream inputStream = new FileInputStream(AdsOnUIConstants.sampleResponseXmlLocation);

			result = initOfXml(inputStream);

			inputStream.close();



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(AdsOnUIConstants.FAILURE == result)
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Cannot parse response ERROR!!!");
			return null;
		}
		else
		{
			return parseResponse();
		}
	}


	/**
	 *  
	 *  
	 *  This function will convert AdsOnUIRequestDataBaseObject to client Xml string
	 *  
	 */

	private void convertAdsOnUIRequestDataBaseObjectToClientXmlStr()
	{

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_convertPlacementRequestDataBaseObjectToClientXmlStr : Enter");

		tempBuffer=new StringBuffer();
		
		

		if(null != tempBuffer && null != adsOnUIRequestData)
		{
			tempBuffer.append("<AdsOnUIRequest messageId=\""+uuId+"\" Version=\"1.0\">");
			tempBuffer.append("<SubscriberId>"+adsOnUIRequestData.getSubscriberId()+"</SubscriberId>");
			tempBuffer.append("<TimeZone>"+adsOnUIRequestData.getTimeZone()+"</TimeZone>");
			tempBuffer.append("<TimeStamp>"+adsOnUIRequestData.getTimeStamp()+"</TimeStamp>");
			tempBuffer.append("<UIVersion>"+adsOnUIRequestData.getUiVersion()+"</UIVersion>");


			tempBuffer.append("<UIScreens>");   // to be removed ??

			/*Iterate over ScreenDetails*/

			appendScreenDetails();
			tempBuffer.append("</UIScreens>");

			
			/*Append Opportunity id details*/
					//tempBuffer.append("<uniqueOppId>"+OppId1+","+OppId2+","+OppId3+","+OppId4+","+OppId5+"</uniqueOppId>");
					tempBuffer.append("<uniqueOppId>"+oppids+"</uniqueOppId>");
					
			tempBuffer.append("<Reattempt>"+numberOfattempts+"</Reattempt>");
			tempBuffer.append("</AdsOnUIRequest>");
			clientXmlString=tempBuffer.toString();
			//clientXmlString="<AdsOnUIRequest messageId='76540c30-dbfd-4b37-8427-65dee215820f' Version='1.0'><SubscriberId>P1000000</SubscriberId><TimeZone>India Standard Time</TimeZone><TimeStamp>1502433172455</TimeStamp><uniqueOppId>1,2,5,6,7</uniqueOppId><Reattempt>1</Reattempt></AdsOnUIRequest>";

		}

		AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "XML==>"+clientXmlString);


		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_convertPlacementRequestDataBaseObjectToClientXmlStr : Exit");

	}


	private void convertQuizObjectToClientXmlStr() {


		tempBuffer=new StringBuffer();

		if(null != tempBuffer && null != question)
		{
			tempBuffer.append("<ClientQuizRequest messageId=\""+uuId+"\" Version=\"1.0\">");
			tempBuffer.append("<SubscriberId>"+AdsOnUIGlue.getSubscriberId()+"</SubscriberId>");
			tempBuffer.append("<ClientAnswer>"+question.getAnswer()+"</ClientAnswer>");
			tempBuffer.append("<TimeTakenToAnswerQuestion>"+question.getTimeStamp()+"</TimeTakenToAnswerQuestion>");
			tempBuffer.append("<Reattempt>"+numberOfattempts+"</Reattempt>");
			tempBuffer.append("</ClientQuizRequest>");
			clientXmlString=tempBuffer.toString();

		}
	}


	private void convertAdsOnUIRepeatRequestDataBaseObjectToClientXmlStr() {

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_convertPlacementRepeatRequestDataBaseObjectToClientXmlStr : Enter");

		tempBuffer=new StringBuffer();

		if(null != tempBuffer && null != adsOnUIRequestData)
		{
			tempBuffer.append("<AdsOnUIRequest messageId=\""+uuId+"\" Version=\"1.0\">");
			tempBuffer.append("<SubscriberId>"+adsOnUIRequestData.getSubscriberId()+"</SubscriberId>");
			tempBuffer.append("<TimeZone>"+adsOnUIRequestData.getTimeZone()+"</TimeZone>");
			tempBuffer.append("<TimeStamp>"+adsOnUIRequestData.getTimeStamp()+"</TimeStamp>");
			//tempBuffer.append("<uniqueOppId>"+OppId1+","+OppId2+","+OppId3+","+OppId4+","+OppId5+"</uniqueOppId>");
			tempBuffer.append("<uniqueOppId>"+oppids+"</uniqueOppId>");
			
			tempBuffer.append("<Reattempt>"+numberOfattempts+"</Reattempt>");			
			tempBuffer.append("</AdsOnUIRequest>");
			clientXmlString=tempBuffer.toString();
			//clientXmlString="<AdsOnUIRequest messageId='76540c30-dbfd-4b37-8427-65dee215820f' Version='1.0'><SubscriberId>P1000000</SubscriberId><TimeZone>India Standard Time</TimeZone><TimeStamp>1502433172455</TimeStamp><uniqueOppId>1,2,5,6,7</uniqueOppId><Reattempt>1</Reattempt></AdsOnUIRequest>";
		}


		AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "XML!!==>"+clientXmlString);


		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_convertPlacementRepeatRequestDataBaseObjectToClientXmlStr : Exit");
	}





	private void convertAdsOnUIADTObjectToClientXmlStr() {
		// TODO Auto-generated method stub
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_convertAdsOnUIADTObjectToClientXmlStr : Enter");

		tempBuffer=new StringBuffer();

		if(AdsOnUIGlue.getSubscriberId() == null)
		{
			AdsOnUIGlue.setSubscriberId(AdsOnUIConstants.TEST_SUBCRIBER_ID);
		}

		if(null != tempBuffer)
		{
			tempBuffer.append("<AdsOnUIADTRequest messageId=\""+uuId+"\" Version=\"1.0\">");
			tempBuffer.append("<SubscriberId>"+AdsOnUIGlue.getSubscriberId()+"</SubscriberId>");
			tempBuffer.append("<UserId>"+"U"+UserAccountsManager.getInstance().getCurrentUserId()+"</UserId>");
			tempBuffer.append("<TimeZone>"+TimeZone.getDefault().getDisplayName()+"</TimeZone>");

			tempBuffer.append("<Assets>");

			appendAssetDetails();

			tempBuffer.append("</Assets>");
			tempBuffer.append("<Reattempt>"+numberOfattempts+"</Reattempt>");			
			tempBuffer.append("</AdsOnUIADTRequest>");
			clientXmlString=tempBuffer.toString();

			System.out.println("ADT request string : "+clientXmlString);


		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE," AdsOnUIXMLPlacement_convertAdsOnUIADTObjectToClientXmlStr : Exit");

	}


	private void appendAssetDetails() {
		// TODO Auto-generated method stub
		System.out.println("Appending asset details");
		if(AdtManager.getAdtDataList() != null)
		{
			Iterator<AdtData> adtIterator = AdtManager.getAdtDataList().iterator();

			while (adtIterator.hasNext()) {

				AdtData adtData = adtIterator.next();

				if(adtData != null)
				{
					tempBuffer.append("<Asset>");
					tempBuffer.append("<AssetId>"+adtData.getAssetId()+"</AssetId>");
					tempBuffer.append("<UserFeedback>"+adtData.getUserFeedback()+"</UserFeedback>");
					tempBuffer.append("<DisplayedTimestamp>"+adtData.getDisplayedTimeStamp()+"</DisplayedTimestamp>");
					tempBuffer.append("<DisplayedDuration>"+adtData.getDisplayDuration()+"</DisplayedDuration>");
					tempBuffer.append("</Asset>");
				}

			}



		}


	}



	private void appendScreenDetails() {

		ArrayList<ScreenDetails> screenDetailsList = AdsOnUIRequestData.getScreenDetailsList();

		if(null != screenDetailsList )

		{
			Iterator<ScreenDetails> screenDetailsIterator = screenDetailsList.iterator();

			while (screenDetailsIterator.hasNext()) {

				ScreenDetails screenDetails = (ScreenDetails) screenDetailsIterator.next();

				if(null != screenDetails)
				{
					tempBuffer.append("<Screen Id=\""+screenDetails.getScreenId()+"\""+">");
					//tempBuffer.append("<ScreenName>"+screenDetails.getScreenName()+"</ScreenName>");

					tempBuffer.append("<Opportunities>");

					appendOpportunityDetails(screenDetails.getOpportunities());

					tempBuffer.append("</Opportunities>");
					tempBuffer.append("</Screen>");

				}
			}

		}


	}
	
	
	private void appendOpportunityDetails(ArrayList<OpportunityDetails> opportunities) {


		if(null != opportunities)
		{
			Iterator<OpportunityDetails> opportunityIterator = opportunities.iterator();

			while (opportunityIterator.hasNext()) {

				OpportunityDetails opportunity = (OpportunityDetails) opportunityIterator.next();


				if(null != opportunity)
				{
					tempBuffer.append("<Opportunity Id=\""+opportunity.getOpportunityId()+"\""+">");
					tempBuffer.append("<Width>"+opportunity.getWidth()+"</Width>");
					tempBuffer.append("<Height>"+opportunity.getHeight()+"</Height>");
					tempBuffer.append("<SupportingShapes>"+opportunity.getSupportingShapes()+"</SupportingShapes>");
					tempBuffer.append("</Opportunity>");

				}

			}

		}

	}
	/**
	 * 
	 * @param tempData
	 * @return object of AdsOnUIXMLPlacement
	 *	return value can be null if something goes wrong
	 */
	//	public static AdsOnUIXMLPlacement createInstance(AdsOnUIRequestData adsOnUIRequestData,int type)
	//	{
	//
	//		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,"AdsOnUIXMLPlacement_getInstance : Enter");
	//
	//		AdsOnUIXMLPlacement obj=new AdsOnUIXMLPlacement(adsOnUIRequestData,type);
	//
	//		serverRequestThread  = new Thread(obj);
	//
	//		return obj;
	//	}

	//===================HELPER_METHODS============================
	/**
	 * Function_name==>getTextFromElement
	 * Description==> This function returns the 
	 * 				  string value associated with element.
	 * @author 
	 * @param ele
	 * @param tagName
	 * @return strValue
	 */
	private String getTextFromElement(Element ele,String tagName)
	{
		String strValue=null;

		NodeList tempnl=ele.getElementsByTagName(tagName);
		if(tempnl!=null&& tempnl.getLength()>0)
		{
			Element nameEle=(Element) tempnl.item(AdsOnUIConstants.ZERO);

			if(nameEle.getFirstChild() != null)
			{
				strValue=nameEle.getFirstChild().getNodeValue();
			}

		}
		return strValue;
	}



	/**
	 * Function_name==> getIntgerFromElement
	 * <br/>Description==> This function returns the 
	 * 				  integer value associated with element.
	 * @author 
	 * @param ele
	 * @param tagName
	 * @return intValue
	 */
	private int getIntgerFromElement(Element ele,String tagName)
	{
		return Integer.parseInt(getTextFromElement(ele,tagName));
	}
	//=====================================================================


	private AdsOnUIData parseResponse()
	{	

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseResponse entry");

		adsOnUIData = new AdsOnUIData();
		
		Element root =doc.getDocumentElement();

		if(doc.getElementsByTagName("Action").getLength() > 0 )
		{
			adsOnUIData.setRetain(true);
			return adsOnUIData;
		}
		else if(doc.getElementsByTagName(AdsOnUIConstants.TIME_INTERVAL).getLength() > 0)
		{
			adsOnUIData.setServerBusy(true);
			adsOnUIData.setDelayToSendRequest(getDelayToSendRequest(root));
			
			return adsOnUIData;
		}
		else
		{

			
			adsOnUIData.setRetain(false);



			/**
			 * Fetching all the primary asset URL's
			 */
			adsOnUIData.setPrimaryAdImageURL(getTextFromElement(root, AdsOnUIConstants.PRIMARY_IMAGE_URL));
			
			adsOnUIData.setPrimaryAdVideoURL(getTextFromElement(root, AdsOnUIConstants.PRIMARY_VIDEO_URL));
			
			adsOnUIData.setPrimaryAdAudioURL(getTextFromElement(root, AdsOnUIConstants.PRIMARY_AUDIO_URL));
			
			/**
			 * Fetching all the adcompanion asset URL's
			 */
			adsOnUIData.setAdCompanionImageURL(getTextFromElement(root, AdsOnUIConstants.ADCOMPANION_IMAGE_URL));
			
			adsOnUIData.setAdCompanionVideoURL(getTextFromElement(root, AdsOnUIConstants.ADCOMPANION_VIDEO_URL));
			
			adsOnUIData.setAdCompanionAudioURL(getTextFromElement(root, AdsOnUIConstants.ADCOMPANION_AUDIO_URL));
			
			adsOnUIData.setAdCompanionLinkURL(getTextFromElement(root, AdsOnUIConstants.ADCOMPANION_LINK_URL));
			
			
			adsOnUIData.setDisplayMode(getTextFromElement(root, AdsOnUIConstants.ASSET_DISPLAY_MODE));

			adsOnUIData.setCreatedDate(new Date(System.currentTimeMillis()));

			//parseScreenDetails();
			parseOpportunityDetails();  
			
			checkForServerConflict(root);

			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseResponse exit");

			return adsOnUIData;



		}

	}


	

	private int getDelayToSendRequest(Element root) {
		
		return getIntgerFromElement(root, AdsOnUIConstants.TIME_INTERVAL);
	
	}


	private void checkForServerConflict(Element root) 
	{

		NodeList serverConflictNodeList = root.getElementsByTagName(AdsOnUIConstants.SERVER_CONFLICT); 

		if(serverConflictNodeList != null)
		{
			Node serverConflictNode = serverConflictNodeList.item(AdsOnUIConstants.ZERO);

			if(serverConflictNode != null)
			{
				String conflictQuestion = 
						serverConflictNode.getTextContent();

				if(AdsOnUILog.somethingWrong)
				{
					System.out.println("*************************");
					System.out.println("Server question is : "+conflictQuestion);
					System.out.println("*************************");
				}
				if(conflictQuestion != null)
				{
					QuizManager.getInstance().
					setQuestion(new Question(conflictQuestion, AdsOnUIConstants.NULL));
				}
			}
		}
	}


	private void parseScreenDetails() {
		
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseScreenDetails entry");

//		Node uiScreensNode = doc.getElementsByTagName(AdsOnUIConstants.UI_SCREENS).item(AdsOnUIConstants.ZERO);
//
//		if(uiScreensNode.getNodeType() == Node.ELEMENT_NODE)
//		{
//			Element uiScreenElement = (Element) uiScreensNode;
//
//			if(!checkForSequenceNotReadySituation(uiScreenElement))
//			{
//				parseEachScreen(uiScreenElement);
//			}
//		}
//		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseScreenDetails exit");

		NodeList nodes=doc.getElementsByTagName("Status");
		
		Element element = (Element) nodes.item(AdsOnUIConstants.ZERO);

			if(getIntgerFromElement(element, "Acknowledgement")==0)
			{
				Node uiScreensNode = doc.getElementsByTagName(AdsOnUIConstants.UI_SCREENS).item(AdsOnUIConstants.ZERO);
				if(uiScreensNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element uiScreenElement = (Element) uiScreensNode;
				
					if(!checkForSequenceNotReadySituation(uiScreenElement))
					{
						parseEachScreen(uiScreenElement);
					}
				}
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseScreenDetails exit");
			}
			else if(getIntgerFromElement(element, "Acknowledgement")==1000)
			{
//				System.out.println("Message obtained from server is :"+getTextFromElement(element, "Message"));
				
				
				Log.display("***************************************");
				Log.display("Message : "+getTextFromElement(element, "Message"));
				Log.display("***************************************");
				
				//For invalid subscriber_id ,set subscriber id to emptyString 
				try {
					SettingsFileInterface.getInstance().setSubScriberID("");
					SettingsFileInterface.getInstance().saveSettingsToFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
			else 
			{
				System.out.println("Message obtained from server is :"+getTextFromElement(element, "Message"));
			}
	}
	
	
	
private void parseOpportunityDetails() {
		
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseOpportunityDetails entry");

//		Node uiScreensNode = doc.getElementsByTagName(AdsOnUIConstants.UI_SCREENS).item(AdsOnUIConstants.ZERO);
//
//		if(uiScreensNode.getNodeType() == Node.ELEMENT_NODE)
//		{
//			Element uiScreenElement = (Element) uiScreensNode;
//
//			if(!checkForSequenceNotReadySituation(uiScreenElement))
//			{
//				parseEachScreen(uiScreenElement);
//			}
//		}
//		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseScreenDetails exit");

		NodeList nodes=doc.getElementsByTagName("Status");
		
		Element element = (Element) nodes.item(AdsOnUIConstants.ZERO);

			if(getIntgerFromElement(element, "Acknowledgement")==0)
			{
				Node uiOpportunitiesNode = doc.getElementsByTagName(AdsOnUIConstants.OPPORTUNITIES).item(AdsOnUIConstants.ZERO);
				if(uiOpportunitiesNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element uiOpportunityElement = (Element) uiOpportunitiesNode;
				
					if(!checkForSequenceNotReadySituation(uiOpportunityElement))
					{
						parseEachOpportunity(uiOpportunityElement);
					}
				}
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseOpportunityDetails exit");
			}
			else if(getIntgerFromElement(element, "Acknowledgement")==1000)
			{
//				System.out.println("Message obtained from server is :"+getTextFromElement(element, "Message"));
				
				
				Log.display("***************************************");
				Log.display("Message : "+getTextFromElement(element, "Message"));
				Log.display("***************************************");
				
				//For invalid subscriber_id ,set subscriber id to emptyString 
				try {
					SettingsFileInterface.getInstance().setSubScriberID("");
					SettingsFileInterface.getInstance().saveSettingsToFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
			else 
			{
				System.out.println("Message obtained from server is :"+getTextFromElement(element, "Message"));
			}
	}
	
	
	
	
	private boolean checkForSequenceNotReadySituation(Element uiScreenElement) {

		if(getTextFromElement(uiScreenElement, AdsOnUIConstants.INFORMATION)
				!= null)
		{
			System.out.println("Server sent information tag");
			adsOnUIData = null;
			return true;
		}
		return false;
	}



	private void parseEachScreen(Element uiScreenElement) {

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseEachScreen entry");

		NodeList screenList = uiScreenElement.getElementsByTagName(AdsOnUIConstants.SCREEN);

		System.out.println("Screen list size : "+screenList.getLength());


		for (int i = 0; i < screenList.getLength(); i++) {


			Node screenNode = screenList.item(i);

			if(screenNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element screenElement = (Element) screenNode;

				String screenName = getTextFromElement(screenElement, AdsOnUIConstants.SCREEN_NAME);

				Node opportunitiesNode = screenElement.getElementsByTagName(AdsOnUIConstants.OPPORTUNITIES).item(AdsOnUIConstants.ZERO);

				if(opportunitiesNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element opportunitiesElement = (Element) opportunitiesNode;
					parseEachOpportunity(screenName,opportunitiesElement);
				}


			}

		}

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseEachScreen exit");


	}
	private void parseEachOpportunity(String screenName,
			Element opportunitiesElement) {

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseEachOpportunity entry");

		String sequence;

		boolean isNonTargeted = false;


		NodeList opportunityList = opportunitiesElement.getElementsByTagName(AdsOnUIConstants.OPPORTUNITY);

		for (int i = 0; i < opportunityList.getLength(); i++) {

			Node opportunityNode = opportunityList.item(i);



			if(opportunityNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element opportunityElement = (Element) opportunityNode;


				int opportunityId = getOpportunityId(opportunityElement);
				
				//NEW
				int displayDuration = 0;
				
				int adCompanionType = -1;
				
				int primaryAssetType = -1;

				if((sequence = getTextFromElement(opportunityElement, AdsOnUIConstants.ASSET))
						!= null)
				{
					isNonTargeted = true;
				}
				else if((sequence = getTextFromElement(opportunityElement, AdsOnUIConstants.SEQUENCE)
						) != null)
				{
					isNonTargeted = false;
					
					//NEW
					displayDuration = getIntgerFromElement(opportunityElement, AdsOnUIConstants.DISPLAY_TIME);
					
				}
				
				primaryAssetType = getIntgerFromElement(opportunityElement, AdsOnUIConstants.PRIMARY_ASSET_TYPE);

				adCompanionType = getIntgerFromElement(opportunityElement, AdsOnUIConstants.ADCOMPANION_ASSET_TYPE);

				System.out.println("Sequence : "+sequence+" "+opportunityId+" "+isNonTargeted+"Display Duration : "+displayDuration);


				//adsOnUIData.addScreenDetails(screenName, opportunityList.getLength());
				if(sequence != null && primaryAssetType != -1
						&& adCompanionType != -1)
				{
					System.out.println("Adding sequence for "+screenName+"Non Targeted"+isNonTargeted+"Sequence"+sequence);
					adsOnUIData.addOpportunityDetails(opportunityId, sequence,isNonTargeted,displayDuration,
							primaryAssetType,adCompanionType);
				}
				else
				{
					System.out.println("Sequence is null");
				}
			}
			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseEachOpportunity exit");

		}


	}

	
	private void parseEachOpportunity(Element opportunitiesElement) {

		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseEachOpportunity entry");

		String sequence;

		boolean isNonTargeted = false;


		NodeList opportunityList = opportunitiesElement.getElementsByTagName(AdsOnUIConstants.OPPID);

		for (int i = 0; i < opportunityList.getLength(); i++) {

			Node opportunityNode = opportunityList.item(i);



			if(opportunityNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element opportunityElement = (Element) opportunityNode;


				int opportunityId = getOpportunityId(opportunityElement);
				
				//NEW
				int displayDuration = 0;
				
				int adCompanionType = -1;
				
				int primaryAssetType = -1;

				if((sequence = getTextFromElement(opportunityElement, AdsOnUIConstants.ASSET))
						!= null)
				{
					isNonTargeted = true;
				}
				else if((sequence = getTextFromElement(opportunityElement, AdsOnUIConstants.SEQUENCE)
						) != null)
				{
					isNonTargeted = false;
					
					//NEW
					displayDuration = getIntgerFromElement(opportunityElement, AdsOnUIConstants.DISPLAY_TIME);
					
				}
				
				primaryAssetType = getIntgerFromElement(opportunityElement, AdsOnUIConstants.PRIMARY_ASSET_TYPE);

				adCompanionType = getIntgerFromElement(opportunityElement, AdsOnUIConstants.ADCOMPANION_ASSET_TYPE);

				System.out.println("Sequence : "+sequence+" "+opportunityId+" "+isNonTargeted+"Display Duration : "+displayDuration);

String screenName="EPG"; //diwakar
				//adsOnUIData.addScreenDetails(screenName, opportunityList.getLength());
				if(sequence != null && primaryAssetType != -1
						&& adCompanionType != -1)
				{
					System.out.println("Adding sequence for "+screenName+" Non Targeted"+isNonTargeted+"Sequence"+sequence);
					adsOnUIData.addOpportunityDetails(opportunityId, sequence,isNonTargeted,displayDuration,
							primaryAssetType,adCompanionType);
				}
				else
				{
					System.out.println("Sequence is null");
				}
			}
			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "parseEachOpportunity exit");

		}


	}

		
	private int getOpportunityId(Element opportunityElement)
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "getOpportunityId entry");

		if (opportunityElement.hasAttributes()) {

			// get attributes names and values
			NamedNodeMap nodeMap = opportunityElement.getAttributes();

			for (int j = 0; j < nodeMap.getLength(); j++) {

				Node node = nodeMap.item(j);

				if( node != null &&
						!node.getNodeValue().equals("") )
				{
					AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "getOpportunityId exit");
					if(node.getNodeName().equals("Id"))
					{
						return Integer.parseInt(node.getNodeValue());
					}
				}

			}

		}	
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "getOpportunityId exit");

		return -1;

	}

	private boolean getAdType(Element opportunityElement)
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "getAdType entry");

		if (opportunityElement.hasAttributes()) {

			// get attributes names and values
			NamedNodeMap nodeMap = opportunityElement.getAttributes();

			for (int j = 0; j < nodeMap.getLength(); j++) {

				Node node = nodeMap.item(j);

				if( node != null &&
						!node.getNodeValue().equals("") )
				{
					AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "getAdType exit");

					if(node.getNodeName().equals(AdsOnUIConstants.STICK))
					{
						if(node.getNodeValue().equals("true"))
						{
							return true;
						}
					}


				}

			}

		}	
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "getAdType exit");

		return false;

	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		sendToServer_and_getTheAdsOnUIResponseObj();
	}




}
