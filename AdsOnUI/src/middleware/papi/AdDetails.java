package middleware.papi;

public class AdDetails
{
	private int screenId;
	private int opportunityId;
	private int width;
	private int height;
	private String screenName;

	public AdDetails(int screenId, int opportunityId, int width, int height, String screenName)
	{
		super();
		this.screenId = screenId;
		this.opportunityId = opportunityId;
		this.width = width;
		this.height = height;
		this.screenName=screenName;
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

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
