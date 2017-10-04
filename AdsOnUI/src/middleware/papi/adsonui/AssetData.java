package middleware.papi.adsonui;
/**
 * Class that holds the generic information of an asset such as
 * 1)screenName
 * 2)opportunityId
 * 3)assetId
 * 
 * 
 * @author Balaji Muralidhar
 *
 */
public class AssetData
{
	//protected String screenName;
	protected int opportunityId;
	protected String assetId;

	public AssetData()
	{
		// TODO Auto-generated constructor stub
	}

	public AssetData( int opportunityId, String assetId)
	{
		super();
		//this.screenName = screenName;
		this.opportunityId = opportunityId;
		this.assetId = assetId;
	}

/*	public String getScreenName()
	{
		return screenName;
	}

	public void setScreenName(String screenName)
	{
		this.screenName = screenName;
	}*/

	public int getOpportunityId()
	{
		return opportunityId;
	}

	public void setOpportunityId(int opportunityId)
	{
		this.opportunityId = opportunityId;
	}

	public String getAssetId()
	{
		return assetId;
	}

	public void setAssetId(String assetId)
	{
		this.assetId = assetId;
	}
}
