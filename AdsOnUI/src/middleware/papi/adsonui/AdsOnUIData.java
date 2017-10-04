/** Add comments to the file */
package middleware.papi.adsonui;
/**
 * 
 * Purpose :
 * This class holds the data which is got as response from the server 
 * 
 * <br/>1.The advertisement URL
 * <br/>2.The adCompanion URL
 * <br/>3.The display mode
 * <br/>4.The display duration of ad 
 * <br/>5.Each opportunity sequence of an individual screen 
 *
 *
 *Input : None 
 *
 *Output : None
 *
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import middleware.papi.adsonui.util.AdsOnUILog;

/**
 * This class holds the data which is got as response from the server
 * 
 * <br/>
 * 1.The advertisement URL <br/>
 * 2.The adCompanion URL <br/>
 * 3.The display mode <br/>
 * 4.The display duration of ad <br/>
 * 5.Each opportunity sequence of an individual screen
 * 
 * @author Balaji Muralidhar
 * 
 */
public class AdsOnUIData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3842831105514064784L;
	/**
	 * @since adsonui 3.0
	 */
	private String primaryAdImageURL;
	/**
	 * @since adsonui 3.0
	 */
	private String primaryAdVideoURL;
	/**
	 * @since adsonui 3.0
	 */
	private String primaryAdAudioURL;
	/**
	 * @since adsonui 3.0
	 */
	private String adCompanionImageURL;
	/**
	 * @since adsonui 3.0
	 */
	private String adCompanionVideoURL;
	/**
	 * @since adsonui 3.0
	 */
	private String adCompanionAudioURL;
	/**
	 * @since adsonui 3.0
	 */
	private String adCompanionLinkURL;
	private String displayMode;
	//private ArrayList uiScreenList;
	
	private ArrayList<Opportunity> uiOpportunityList;
	
	private Date createdDate;
	private boolean retain = false;
	private boolean serverBusy = false;
	private int delayToSendRequest = 0;
	private char COMMA = ',';

	public AdsOnUIData()
	{
		// TODO
		uiOpportunityList = new ArrayList<Opportunity>();
		//uiScreenList = new ArrayList();
	}
	
	
	
	public int getNoOfOpportunities() {
		return uiOpportunityList.size();
	}
	
	
	
	
/*	public void addScreenDetails(String name, int noOfOpportunities)
	{
		if (null != name && 0 != noOfOpportunities)
		{
			if (null == getUIScreen(name))
			{
				UIScreen uiScreen = new UIScreen(name, noOfOpportunities);
				uiScreenList.add(uiScreen);
			}
		}
	}*/

	/**
	 * Method to store the opportunity details of a particular UI screen
	 * obtained from server
	 * 
	 * <br/>
	 * Screen Name
	 * 
	 * <br/>
	 * Node Id Sequence
	 * 
	 * @param opportunityId
	 * @param name
	 * @param nodeIdSequence
	 */
	/** Where is this function called */
	public void addOpportunityDetails(int opportunityId, String nodeIdSequence,
	        boolean nonTargted, int displayDuration, int primaryAssetType, int adcompanionType)
	{
	/*	if (null != name && null != nodeIdSequence)
		{
			if (null != getUIScreen(name))
			{
				getUIScreen(name).addSequenceDetails(opportunityId, nodeIdSequence, nonTargted,
				        displayDuration, primaryAssetType, adcompanionType);
			}
		}*/
		if(	null != nodeIdSequence) {
			int i;

			for (i=0;i<uiOpportunityList.size();i++) {
				int oppId = uiOpportunityList.get(i).getOpportunityId();

				if (oppId == opportunityId) {
					uiOpportunityList.remove(i);
				}
			}
			Opportunity opportunity = new Opportunity();
			opportunity.setNodeIdSequence(nodeIdSequence);
			opportunity.setOpportunityId(opportunityId);
			opportunity.setDisplayDuration(displayDuration);
			uiOpportunityList.add(opportunity);

			for (i=0;i<uiOpportunityList.size();i++) {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,"uiOpportunity list Items " + i + " " + uiOpportunityList.get(i).getOpportunityId());
			}
		}
		
		
		
		
	}

	/**
	 * Method to update the display position pointer of an opportunity for a
	 * particular
	 * 
	 * UI screen
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @param positionPointer
	 */
	public void updateDisplayPositionPointer(int opportunityId,
	        int positionPointer)
	{
		/*if (screenName == null)
		{
			return;
		}
		UIScreen uiScreen = getUIScreen(screenName);
		if (null != uiScreen)
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "updateDisplayPositionPointer Screen Available For Updating");
			uiScreen.updateDisplayPositionPointer(opportunityId, positionPointer);
		}*/
		if(opportunityId == 0) {
			return;
		}

		Opportunity uiOpportunity = getUIOpportunity(opportunityId);

		if(null != uiOpportunity) {
			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,"Opportunity Available For Updating");
			uiOpportunity.updateDisplayPointerPosition();
		}
		/** above two functions could have been done under one function call */
	}

	
	
	/**
	 * Method to get the display position pointer of an opportunity for a
	 * particular
	 * 
	 * UI screen
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @param positionPointer
	 */
	public int getDisplayPositionPointer(int opportunityId)
	{
		if(opportunityId == 0) {
			return -1;
		}

		Opportunity uiOpportunity = getUIOpportunity(opportunityId);
		if (null != uiOpportunity)
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "getDisplayPositionPointer Screen Available For Updating");
			return uiOpportunity.getDisplayPositionPointer(opportunityId);
		}
		return -1;
	}

	/**
	 * Method to get the current or next sequence for an opportunity of an UI
	 * screen
	 * 
	 */
	/**
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @param noOfAssets
	 * @param next
	 * @return
	 */
	public String[] getCurrentOrNextSequence( int opportunityId, int noOfAssets,
	        boolean next)
	{
		/*if(opportunityId == 0) {
			return null;
		}

		Opportunity uiOpportunity = getUIOpportunity(opportunityId);
		if (null != uiOpportunity)
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "getCurrentOrNextSequence Screen Available");
			return uiOpportunity.getCurrentOrNextSequence(opportunityId, noOfAssets, next);
		}
		else
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "getCurrentOrNextSequence No Screen Available");
			return null;
		}
		*/
		
		
		if(null != uiOpportunityList) {
			for (int i = 0; i < uiOpportunityList.size(); i++) {
				Opportunity opportunity = uiOpportunityList.get(i);
				if(null != opportunity &&
						opportunity.getOpportunityId() == opportunityId) {
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,"Opportunity Available");
					return opportunity.getCurrentOrNextSequence(noOfAssets, next);
				}
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,"No Opportunity Available");
		
		return null;
	}

	/**
	 * 
	 * This method is to set the partially displayed asset so
	 * 
	 * that it can be saved to flash
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @param assetId
	 */
	public void setPartiallyDisplayedAsset( int opportunityId, String assetId)
	{
		if(opportunityId == 0) {
			return ;
		}
		Opportunity uiOpportunity = getUIOpportunity(opportunityId);
		if (null != uiOpportunity)
		{
			//AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, " Screen Available");
			uiOpportunity.setParitallyDisplayedAsset(opportunityId, assetId);
		}
	}

	/**
	 * Method to get the partially displayed asset
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @return
	 */
	public String getPartiallyDisplayedAsset( int opportunityId)
	{
		if(opportunityId == 0) {
			return null;
		}
		Opportunity uiOpportunity = getUIOpportunity(opportunityId);
		if (null != uiOpportunity)
		{
			//AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, " Screen Available");
			return uiOpportunity.getParitallyDisplayedAsset(opportunityId);
		}
		return null;
	}

	/**
	 * Method to set the remaining time the partially displayed asset has
	 * 
	 * to be shown again
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @param remainingDuration
	 */
	public void setRemainingDuration( int opportunityId, int remainingDuration)
	{
		if(opportunityId == 0) {
			return ;
		}
		Opportunity uiOpportunity = getUIOpportunity(opportunityId);
		if (null != uiOpportunity)
		{
			//AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, " Screen Available");
			uiOpportunity.setRemainingDuration(opportunityId, remainingDuration);
		}
	}

	/**
	 * Method to get the remaining duration of the partially displayed
	 * 
	 * asset
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @return
	 */
	public int getRemainingDuration( int opportunityId)
	{
		if(opportunityId == 0) {
			return 0;
		}
		Opportunity uiOpportunity = getUIOpportunity(opportunityId);
		if (null != uiOpportunity)
		{
			//AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, " Screen Available");
			return uiOpportunity.getRemainingDuration(opportunityId);
		}
		return 0;
	}

	/**
	 * 
	 * 
	 * Method to get the display duration of ad for each screen
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @return
	 * @since adsonui 2.0
	 */
	public int getDisplayDuration( int opportunityId)
	{
		if(opportunityId == 0) {
			return 0;
		}
		Opportunity uiOpportunity = getUIOpportunity(opportunityId);
		if (null != uiOpportunity)
		{
			//AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, " Screen Available");
			return uiOpportunity.getDisplayDurtion(opportunityId);
		}
		return 0;
	}

	/**
	 * Checks whether the specified opportunity of a screen is targeted or non
	 * targeted
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @return
	 */
	public boolean checkWhetherNonTargeted( int opportunityId)
	{					

		if(opportunityId == 0) {
			return false;
		}
		Opportunity uiOpportunity = getUIOpportunity(opportunityId);
		if (null != uiOpportunity)
		{
			//AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, " Screen Available");
			return uiOpportunity.checkWhetherNonTargeted(opportunityId);
			
		}
		return false;
	}

	/**
	 * Method to get the {@link UIScreen} object based on ScreenName
	 * 
	 * if Screen Name not found then it will return null
	 * 
	 * @param screenName
	 * @return
	 */
/*	public UIScreen getUIScreen(String screenName)
	{
		if (null != uiScreenList && uiScreenList.size() > 0)
		{
			for (int i = 0; i < uiScreenList.size(); i++)
			{
				UIScreen uiScreen = (UIScreen) uiScreenList.get(i);
				if (uiScreen != null && uiScreen.getName().equals(screenName))
				{
					return uiScreen;
				}
				else if (null != checkIndividualScreen(uiScreen, screenName))
				{
					return checkIndividualScreen(uiScreen, screenName);
				}
			}
		}
		return null;
	}*/

	/**
	 * Method to check whether the screen name and opportunity id
	 * 
	 * passed as parameter are valid or not
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @return
	 */
	public boolean checkForValidScreenAndOpportunityId(int opportunityId)
	{
		/*if (screenName != null)
		{
			UIScreen uiScreen = getUIScreen(screenName);
			if (uiScreen != null)
			{
				if (uiScreen.getOpportunity(opportunityId) != null)
				{
					return true;
				}
			}
		}
		return false;*/
		if (opportunityId != 0)
		{
			Opportunity uiOpportunity = getUIOpportunity(opportunityId);
			if (uiOpportunity != null)
			{
				if (uiOpportunity != null)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Helped method to check whether the screen name passed as a parameter
	 * 
	 * is present in combination of multiple screen names
	 * 
	 * @param uiScreen
	 * @param screenName
	 * @return
	 */
	private UIScreen checkIndividualScreen( UIScreen uiScreen,String screenName)
	{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "checkIndividualScreen entry");
		if (uiScreen != null && screenName != null)
		{
			if (uiScreen.getName().indexOf(COMMA) != -1)
			{
				StringTokenizer screenNameTokenizer = new StringTokenizer(uiScreen.getName(), ",");
				while (screenNameTokenizer.hasMoreElements())
				{
					String tempName = screenNameTokenizer.nextToken();
					if (tempName.equals(screenName))
					{
						return uiScreen;
					}
				}
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "checkIndividualScreen exit");
		return null;
	}

	/**
	 * Method to update partiallyDisplayedAsset and remaining duration
	 */
	/**
	 * Method to get the display mode Ex : Subscriber Based , User Based
	 * 
	 * @return String
	 */
	public String getDisplayMode()
	{
		return displayMode;
	}

	/**
	 * Method to set the display mode
	 * 
	 * @param displayMode
	 */
	public void setDisplayMode(String displayMode)
	{
		this.displayMode = displayMode;
	}

/*	public ArrayList getUiScreenList()
	{
		return uiScreenList;
	}

	public void setUiScreenList(ArrayList uiScreenList)
	{
		this.uiScreenList = uiScreenList;
	}*/

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public boolean isRetain()
	{
		return retain;
	}

	public void setRetain(boolean retain)
	{
		this.retain = retain;
	}

	public boolean isServerBusy()
	{
		return serverBusy;
	}

	public void setServerBusy(boolean serverBusy)
	{
		this.serverBusy = serverBusy;
	}

	public int getDelayToSendRequest()
	{
		return delayToSendRequest;
	}

	public void setDelayToSendRequest(int delayToSendRequest)
	{
		this.delayToSendRequest = delayToSendRequest;
	}

	public String getPrimaryAdImageURL()
	{
		return primaryAdImageURL;
	}

	public void setPrimaryAdImageURL(String primaryAdImageURL)
	{
		this.primaryAdImageURL = primaryAdImageURL;
	}

	public String getPrimaryAdVideoURL()
	{
		return primaryAdVideoURL;
	}

	public void setPrimaryAdVideoURL(String primaryAdVideoURL)
	{
		this.primaryAdVideoURL = primaryAdVideoURL;
	}

	public String getPrimaryAdAudioURL()
	{
		return primaryAdAudioURL;
	}

	public void setPrimaryAdAudioURL(String primaryAdAudioURL)
	{
		this.primaryAdAudioURL = primaryAdAudioURL;
	}

	public String getAdCompanionImageURL()
	{
		return adCompanionImageURL;
	}

	public void setAdCompanionImageURL(String adCompanionImageURL)
	{
		this.adCompanionImageURL = adCompanionImageURL;
	}

	public String getAdCompanionVideoURL()
	{
		return adCompanionVideoURL;
	}

	public void setAdCompanionVideoURL(String adCompanionVideoURL)
	{
		this.adCompanionVideoURL = adCompanionVideoURL;
	}

	public String getAdCompanionAudioURL()
	{
		return adCompanionAudioURL;
	}

	public void setAdCompanionAudioURL(String adCompanionAudioURL)
	{
		this.adCompanionAudioURL = adCompanionAudioURL;
	}

	public String getAdCompanionLinkURL()
	{
		return adCompanionLinkURL;
	}

	public void setAdCompanionLinkURL(String adCompanionLinkURL)
	{
		this.adCompanionLinkURL = adCompanionLinkURL;
	}
	/**
	 * Method to get the {@link Opportunity} object based on OpportunityId
	 * 
	 * if Opportunity Id not found then it will return null
	 * 
	 * @param Opportunity
	 * @return
	 */
	public Opportunity getUIOpportunity(int Opportunity) {
				if(null != uiOpportunityList && uiOpportunityList.size() > 0) {
			for (int i = 0; i < uiOpportunityList.size(); i++) {
				Opportunity uiOpportunity = uiOpportunityList.get(i);
				
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,"Checking with : " + uiOpportunity.getOpportunityId() + " against " + Opportunity);
				
				if(uiOpportunity != null &&
						uiOpportunity.getOpportunityId() == Opportunity) {
					return uiOpportunity;
				}
			}
		} else {
			AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,"UI opportunity List null");
		}
		return null;
	}
}
