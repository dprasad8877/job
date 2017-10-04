package middleware.papi.adsonui.request;
/**
 * Purpose : 
 * This class holds all the UI screen information such as screen id ,
 * screen name , and opportunities corresponding to it
 *
 *Input : 1)screenId
 *		  2)screenName
 *
 *Output : None
 */
import java.util.ArrayList;


public class ScreenDetails
{
	private int screenId;
	//private String screenName;
	private ArrayList opportunitiesList;

	public ScreenDetails(int screenId)
	{
		super();
		this.screenId = screenId;
		//this.screenName = screenName;
		opportunitiesList = new ArrayList();
	}

	public int getScreenId()
	{
		return screenId;
	}

	public void setScreenId(int screenId)
	{
		this.screenId = screenId;
	}

	/*public String getScreenName()
	{
		return screenName;
	}*/

	/*public void setScreenName(String screenName)
	{
		this.screenName = screenName;
	}*/

	public ArrayList getOpportunities()
	{
		return opportunitiesList;
	}

	public void setOpportunity(ArrayList opportunity)
	{
		this.opportunitiesList = opportunity;
	}

	/**
	 * 
	 * Method to add opportunity related data corresponding to an UI screen
	 * 
	 * @param opportunityId
	 * @param width
	 * @param height
	 * @param supportingShapes
	 */
	public void addOpportunityDetails(int opportunityId, int width, int height,
	        String supportingShapes)
	{
		if (0 != opportunityId && 0 != width && 0 != height)
		{
			OpportunityDetails opportunityDetails = new OpportunityDetails(opportunityId, width,
			        height, supportingShapes);
			if (null != opportunitiesList)
			{
				opportunitiesList.add(opportunityDetails);
			}
		}
	}
}
