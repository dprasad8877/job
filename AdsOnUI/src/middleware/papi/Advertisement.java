package middleware.papi;

/**
 * This class holds the generic advertisement info
 * 
 * that is advertisement id , type of advertisement
 * 
 * and screen id and opportunity id
 * 
 * @author Balaji Muralidhar
 * 
 */

public class Advertisement {
	

	public static final int IMAGE_AD_COMPANION = 0;
	
	public static final int IMAGE_VIDEO_COMPANION = 1;
	
	private String advertismentID;
	private int screenId;
	private int opportunityId;

	public Advertisement(int screenId, int opporunityId, String advertismentID)
	{
		super();
		this.advertismentID = advertismentID;
		this.screenId = screenId;
		this.opportunityId = opporunityId;
	}

	public String getAssetid()
	{
		return advertismentID;
	}

	public void setAdvertismentID(String advertismentID)
	{
		this.advertismentID = advertismentID;
	}

	public int getScreenId()
	{
		return screenId;
	}

	public void setScreenId(int screenId)
	{
		this.screenId = screenId;
	}

	public int getOpportunityId()
	{
		return opportunityId;
	}

	public void setOpportunityId(int opportunityId)
	{
		this.opportunityId = opportunityId;
	}
}
