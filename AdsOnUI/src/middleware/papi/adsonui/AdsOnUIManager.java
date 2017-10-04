package middleware.papi.adsonui;
/**
 * Purpose :
 * This class is responsible for sending the AdsOnUIRequest to server 
 * <br/> If the server responds with a new sequence then this class will 
 * fill the {@link AdsOnUIData} object and keep it in flash 
 * 
 * <br/>If the server responds with retain then the stored {@link AdsOnUIData} 
 * object is read from flash 
 * 
 * @author Balaji Muralidhar
 * 
 * Input : None 
 * 
 * Output : None
 *
 */
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import middleware.papi.Advertisement;
import middleware.papi.AdvertisementListener;
import middleware.papi.PapiConstants;
import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.request.AdsOnUIRequestData;
import middleware.papi.adsonui.request.AdsOnUIXMLPlacement;
import middleware.papi.adsonui.request.CommunicationListener;
import middleware.papi.adsonui.servicecheck.ServiceCheckXMLPlacement;
import middleware.papi.adsonui.servicecheck.WaitToCheckServerConnection;
import middleware.papi.adsonui.util.AdsOnUILog;
import middleware.papi.adsonui.util.FlashManager;
import middleware.papi.adsonui.util.ObjectReader;
import middleware.papi.adsonui.util.ObjectWriter;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;


public class AdsOnUIManager implements Runnable, CommunicationListener
{
	private static AdsOnUIManager adsOnUIManager;
	private AdsOnUIData adsOnUIData;
	private WaitToCheckServerConnection waitToCheckServerConnection;
	private int[] oppIdsArray=null;
	private AdsOnUIManager()
	{
	}

	/**
	 * Method that returns the singleton instance of the class
	 * {@link AdsOnUIManager}
	 * 
	 * @return {@link AdsOnUIManager}
	 */
	public static synchronized AdsOnUIManager getInstance()
	{

		if (adsOnUIManager == null)
		{
			adsOnUIManager = new AdsOnUIManager();
		}
		return adsOnUIManager;
	}

	public static void  enableAdsOnUI(int[] oppIdsArray)
	{
		AdsOnUIManager.getInstance(). startAdsOnUI(oppIdsArray);
	}
	
	/**
	 * Method which starts the AdsOnUI process
	 */
	
	public void  startAdsOnUI(int[] oppIdsArray)
	{
		this.oppIdsArray=oppIdsArray;
//		AdsOnUIXMLPlacement.OppId1=oppIdsArray[0];
//		AdsOnUIXMLPlacement.OppId2=oppIdsArray[1];
//		AdsOnUIXMLPlacement.OppId3=oppIdsArray[2];
//		AdsOnUIXMLPlacement.OppId4=oppIdsArray[3];
//		AdsOnUIXMLPlacement.OppId5=oppIdsArray[4];
		
		String oppIdsString="";
		for(int i=0;i<oppIdsArray.length;i++){
			oppIdsString+=oppIdsArray[i]+",";
		}
		oppIdsString=oppIdsString.substring(0, oppIdsString.length()-1);
		
		AdsOnUIXMLPlacement.oppids=	oppIdsString;
		
		new Thread(adsOnUIManager).start();
	}

	/**
	 * This method starts the AdsOnUI process
	 * 
	 * <br/>
	 * 1.Checks whether complete request is sent if not sends the complete
	 * AdsOnUIRequest <br/>
	 * 2.If complete request is already sent then repeat request is sent
	 * 
	 */
	private void adsOnUIProcessStart()
	{
		//edit
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "adsOnUIProcessStart entry");
		sendAdsOnUIRepeatRequest();
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "adsOnUIProcessStart exit");
	}

	/**
	 * This method will first checks whether {@link AdsOnUIData} exists if yes
	 * returns true if not returns false
	 * 
	 * @return
	 */
	private boolean readAdsOnUIDataFromFlash()
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "readAdsOnUIDataFromFlash entry");
		File file = new File(AdsOnUIConstants.sampleFileLocation);
		if (!file.exists())
		{
			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
			        "readAdsOnUIDataFromFlash returning false");
			return false;
		}
		else
		{
			ObjectReader objectReader = new ObjectReader(AdsOnUIConstants.sampleFileLocation);
			adsOnUIData = (AdsOnUIData) objectReader.readObject();
			if (adsOnUIData == null)
			{
				return false;
			}
			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
			        "readAdsOnUIDataFromFlash returning true");
			return true;
		}
	}

	/**
	 * This method will send the complete screen details to the server <br/>
	 * This method will be called only one time that is at the time of first
	 * bootup
	 */
	private void sendAdsOnUICompleteRequest()
	{
		AdsOnUIXMLPlacement adsOnUIXMLPlacement;
		if (AdsOnUIConstants.TEST)
		{
		}
		else
		{
			adsOnUIXMLPlacement = new AdsOnUIXMLPlacement(
			        AdsOnUIRequestData.getAdsOnUIRequestData(), AdsOnUIConstants.COMPLETE);
			adsOnUIXMLPlacement.sendDataToServer(this);
		}
	}

	/**
	 * This method sends only the repeat AdsOnUI request to
	 * 
	 * server
	 */
	private void sendAdsOnUIRepeatRequest()
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "sendAdsOnUIRepeatRequest entry");
		AdsOnUIXMLPlacement adsOnUIXMLPlacement;
		if (AdsOnUIConstants.TEST)
		{
		}
		else
		{
			adsOnUIXMLPlacement = new AdsOnUIXMLPlacement(
			        AdsOnUIRequestData.getAdsonUIRepeatRequestData(), AdsOnUIConstants.REPEAT);
			adsOnUIXMLPlacement.sendDataToServer(this);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "sendAdsOnUIRepeatRequest exit");
	}

	/**
	 * Writes the {@link AdsOnUIData} object to flash
	 */
	public void writeAdsOnUIDataToFlash()
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "writeAdsOnUIDataToFlash entry");
		FlashManager.createFolder(AdsOnUIConstants.adsOnUIFoldername);
		if (null != adsOnUIData)
		{
			if (AdsOnUIConstants.PC_CLIENT)
			{
				ObjectWriter objectWriter = new ObjectWriter(AdsOnUIConstants.sampleFileLocation);
				objectWriter.writeObject(adsOnUIData);
			}
			else
			{
				// Write to flash location of STB
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "writeAdsOnUIDataToFlash exit");
	}

	/**
	 * Method to get the {@link AdsOnUIData} object
	 * 
	 * @return {@link AdsOnUIData}
	 */
	protected synchronized AdsOnUIData getAdsOnUIData()
	{
		return adsOnUIData;
	}

	/**
	 * This function is called when the service check delay expires so that
	 * 
	 * once again request can be sent to server
	 * 
	 * @param type
	 */
	public void sendConnectionRequest(int type)
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager sendConnectionRequest entry");
		UUIDGenerator idGen = UUIDGenerator.getInstance();
		UUID id = idGen.generateRandomBasedUUID();
		ServiceCheckXMLPlacement serviceCheckObj = ServiceCheckXMLPlacement.getInstance(id);
		String status = serviceCheckObj.checkServerConnection();
		if (status != null)
		{
			if (status.equalsIgnoreCase("success"))
			{
				// send asset request to server once the connection is
				// established
				if (type == AdsOnUIConstants.COMPLETE)
				{
					sendAdsOnUICompleteRequest();
				}
				else if (type == AdsOnUIConstants.REPEAT)
				{
					sendAdsOnUIRepeatRequest();
				}
				else if (type == AdsOnUIConstants.FETCHING_AD_FIRST_TIME)
				{
					// saveFirstSequenceOfEachUIToFlash();
				}
			}
			else
			{
				// start the timer to check the connection again
				waitToCheckServerConnection = new WaitToCheckServerConnection();
				waitToCheckServerConnection.startTimer(AdsOnUIConstants.SERVICE_CHECK_DELAY, type);
			}
		}
		else
		{
			waitToCheckServerConnection = new WaitToCheckServerConnection();
			waitToCheckServerConnection.startTimer(AdsOnUIConstants.SERVICE_CHECK_DELAY, type);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager sendConnectionRequest exit");
	}

	/**
	 * Method to cache the first asset of each opportunity of a all screens
	 * 
	 * @param imageURL
	 * @param screenName
	 * @param oppurtunityId
	 * @param assetId
	 */
	private void cacheFirstAssetOfEachSequence(URL imageURL,  int oppurtunityId,
	        String assetId)
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager cacheFirstAssetOfEachSequence entry");
		//boolean result = CacheManager.cacheImage(screenName, oppurtunityId, assetId, imageURL);
		boolean result = CacheManager.cacheImage( oppurtunityId, assetId, imageURL);
		
		if (AdsOnUILog.somethingWrong)
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Image Cached or Not : " + result + " "
			        + "screenName" + " " + oppurtunityId + " " + assetId);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager cacheFirstAssetOfEachSequence exit");
	}

	public void run()
	{
		adsOnUIProcessStart();
	}

	/**
	 * Method that is internally called when an application requests for an
	 * 
	 * advertisement and which in-turn hands over the control to
	 * {@link SequenceManager}
	 * 
	 * if the screen id and opportunity id are valid
	 * 
	 * @param screenId
	 * @param oppurtunityID
	 * @param adListener
	 */
	public void getAnAsset(final int screenId, final int oppurtunityID,
	        final AdvertisementListener adListener)
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "AdsOnUIManager getAnAsset entry");
		Thread thread = new Thread() {
			public void run()
			{
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				        "AdsOnUIManager getAnAsset thread entry");
				if (!checkForValidScreenId(screenId))
				{
					throw new IllegalArgumentException("Screen Id is not Correct");
				}
				else if (!checkForValidOpportunityId(screenId, oppurtunityID))
				{
					throw new IllegalArgumentException("Opportunity Id is not Correct");
				}
				if (adsOnUIData != null)
				{
					SequenceManager.getInstance().fetchAnAsset(screenId, oppurtunityID, adListener);
				}
				else
				{
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "AdsOnUI Data is Null");
				}
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				        "AdsOnUIManager getAnAsset thread exit");
			}
		};
		thread.start();
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "AdsOnUIManager getAnAsset exit");
	}

	/**
	 * Helper method which checks whether the opportunity id is valid
	 * 
	 * or not
	 * 
	 * @param screenId
	 * @param oppurtunityID
	 * @return boolean
	 */
	private boolean checkForValidOpportunityId(int screenId, int oppurtunityID)
	{
		for (int i = 0; i < PapiConstants.AD_DETAILS.length; i++)
		{
			if (PapiConstants.AD_DETAILS[i].getScreenId() == screenId)
			{
				if (PapiConstants.AD_DETAILS[i].getOpportunityId() == oppurtunityID)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Helper method which checks whether the screen id is valid
	 * 
	 * or not
	 * 
	 * @param screenId
	 * @return
	 */
	private boolean checkForValidScreenId(int screenId)
	{
		for (int i = 0; i < PapiConstants.AD_DETAILS.length; i++)
		{
			if (PapiConstants.AD_DETAILS[i].getScreenId() == screenId)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that is internally called when an application notifies that
	 * 
	 * an advertisement has been displayed and which in-turn hands over the
	 * control to {@link SequenceManager}
	 * 
	 * if the screen id and opportunity id are valid
	 * 
	 * @param numberOfSecondsDisplayed
	 * @param theAdvertismentObjectBeingDisplayed
	 * @param adCompanionRequested
	 */
	public void notifyAdHasBeenDisplayed(final int numberOfSecondsDisplayed,
	        final Advertisement theAdvertismentObjectBeingDisplayed,
	        final boolean adCompanionRequested)
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager notifyAdHasBeenDisplayed entry");
		Thread thread = new Thread() {
			public void run()
			{
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				        "AdsOnUIManager notifyAdHasBeenDisplayed run thread entry");
				if (theAdvertismentObjectBeingDisplayed != null)
				{
					if (!checkForValidScreenId(theAdvertismentObjectBeingDisplayed.getScreenId()))
					{
						throw new IllegalArgumentException("Screen Id is not Correct");
					}
					else if (!checkForValidOpportunityId(
					        theAdvertismentObjectBeingDisplayed.getScreenId(),
					        theAdvertismentObjectBeingDisplayed.getOpportunityId()))
					{
						throw new IllegalArgumentException("Opportunity Id is not Correct");
					}
					if (adsOnUIData != null)
					{
						SequenceManager.getInstance().notifyAdvertismentDisplayed(
						        numberOfSecondsDisplayed, theAdvertismentObjectBeingDisplayed,
						        adCompanionRequested);
					}
				}
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				        "AdsOnUIManager notifyAdHasBeenDisplayed run thread exit");
			}
		};
		thread.start();
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager notifyAdHasBeenDisplayed exit");
	}

/*	*//**
	 * Helper method used to cache first asset of each opportunity of all the
	 * screens
	 *//*
	private void cacheFirstAssetOfEachUI()
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager cacheFirstAssetOfEachUI entry");
		for (int i = 0; i < AdsOnUIConstants.UI_NAMES.length; i++)
		{
			if (null != adsOnUIData)
			{
				String screenName = AdsOnUIConstants.UI_NAMES[i];
				UIScreen uiScreen = adsOnUIData.getUIScreen(screenName);
				if (uiScreen != null)
				{
					int opportunities = uiScreen.getNoOfOpportunities();
					if (opportunities > 0)
					{
						for (int j = 0; j < opportunities; j++)
						{
							String[] curSequence = adsOnUIData.getCurrentOrNextSequence(screenName,
							        j + 1, AdsOnUIConstants.UI_SLOTS[i], false);
							if (curSequence != null && curSequence.length > 0)
							{
								if (AdsOnUILog.somethingWrong)
								{
									AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, screenName + " "
									        + curSequence[AdsOnUIConstants.ZERO]);
								}
								adsOnUIData = AdsOnUIManager.getInstance().getAdsOnUIData();
								URL imageUrl = null;
								try
								{
									imageUrl = new URL(adsOnUIData.getPrimaryAdImageURL()
									        + curSequence[AdsOnUIConstants.ZERO]);
								}
								catch (MalformedURLException e)
								{
									e.printStackTrace();
								}
								if (imageUrl != null)
								{
									cacheFirstAssetOfEachSequence(imageUrl,  j + 1,
									        curSequence[AdsOnUIConstants.ZERO]);
								}
							}
						}
					}
				}
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager cacheFirstAssetOfEachUI exit");
	}*/

	
	/**
	 * Helper method used to cache first asset of each opportunity of all the
	 * screens
	 * Diwakar
	 */
	private void cacheFirstAssetOfEachUI()
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager cacheFirstAssetOfEachUI entry");
		for (int i = 0; i < AdsOnUIConstants.OPPIDS.length; i++)
		{
			if (null != adsOnUIData)
			{
				int oppid = AdsOnUIConstants.OPPIDS[i];
				
						String[] curSequence = adsOnUIData.getCurrentOrNextSequence(oppid, AdsOnUIConstants.UI_SLOTS[i], false);
							if (curSequence != null && curSequence.length > 0)
							{
								if (AdsOnUILog.somethingWrong)
								{
									AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE," screenName "+ " "
									        + curSequence[AdsOnUIConstants.ZERO]);
								}
								adsOnUIData = AdsOnUIManager.getInstance().getAdsOnUIData();
								URL imageUrl = null;
								try
								{
									imageUrl = new URL(adsOnUIData.getPrimaryAdImageURL()
									        + curSequence[AdsOnUIConstants.ZERO]);
								}
								catch (MalformedURLException e)
								{
									e.printStackTrace();
								}
								if (imageUrl != null)
								{
									cacheFirstAssetOfEachSequence(imageUrl, oppid,
									        curSequence[AdsOnUIConstants.ZERO]);
								}
							}
						
					
				
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager cacheFirstAssetOfEachUI exit");
	}
	
	/**
	 * Method that is internally called when an application requests for
	 * de-registration
	 * 
	 * so that providing ads to it can be stopped and which in-turn hands over
	 * the control to {@link SequenceManager}
	 * 
	 * if the screen id and opportunity id are valid
	 * 
	 * @param screenId
	 * @param oppurtunityId
	 * @param adListener
	 */
	public void deregisterAdvertisementListener(int screenId, int oppurtunityId,
	        AdvertisementListener adListener)
	{
		if (!checkForValidScreenId(screenId))
		{
			throw new IllegalArgumentException("Screen Id is not Correct");
		}
		else if (!checkForValidOpportunityId(screenId, oppurtunityId))
		{
			throw new IllegalArgumentException("Opportunity Id is not Correct");
		}
		SequenceManager.getInstance().deregisterAdvertisementListener(screenId, oppurtunityId,
		        adListener);
	}

	public void communicationDone(int type, int result, Object obj)
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager.communicationDone() entry");
		if (obj != null && obj instanceof AdsOnUIData)
		{
			if (type == AdsOnUIConstants.COMPLETE)
			{
				adsOnUIData = (AdsOnUIData) obj;
				writeAdsOnUIDataToFlash();
			}
			else if (type == AdsOnUIConstants.REPEAT)
			{
				if (AdsOnUILog.somethingWrong)
				{
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Recieved repeat response");
				}
				AdsOnUIData tempData = (AdsOnUIData) obj;
				if (!tempData.isRetain() && !tempData.isServerBusy())
				{
					if (AdsOnUILog.somethingWrong)
					{
						AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "New Response");
					}
					adsOnUIData = tempData;
					writeAdsOnUIDataToFlash();
					readAdsOnUIDataFromFlash();
					cacheFirstAssetOfEachUI();
				}
				else if (tempData.isServerBusy())
				{
					if (AdsOnUILog.somethingWrong)
					{
						AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Server is busy");
					}
					if (tempData.getDelayToSendRequest() != 0)
					{
						if (AdsOnUILog.somethingWrong)
						{
							AdsOnUILog.displayLog(
							        AdsOnUILog.LOGIC_TYPE,
							        "Server is busy sending request after : "
							                + tempData.getDelayToSendRequest() + " seconds");
						}
						scheduleTimerToSendNextRequest(tempData.getDelayToSendRequest());
					}
				}
				else
				{
					if (AdsOnUILog.somethingWrong)
					{
						AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Retain Response ");
					}
					readAdsOnUIDataFromFlash();
					cacheFirstAssetOfEachUI();
				}
			}
		}
		else
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Object is " + obj);
			waitToCheckServerConnection = new WaitToCheckServerConnection();
			waitToCheckServerConnection.startTimer(AdsOnUIConstants.SERVICE_CHECK_DELAY, type);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
		        "AdsOnUIManager.communicationDone() entry");
	}

	private void scheduleTimerToSendNextRequest(int delayToSendRequest)
	{
		Timer timerForSendingNextRequest = new Timer();
		timerForSendingNextRequest.schedule(new TimerTask() {
			public void run()
			{
				 startAdsOnUI(oppIdsArray);
				
			}
		}, delayToSendRequest * 1000);
	}
}
