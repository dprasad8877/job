package middleware.papi.adsonui.adt;
/**
 * Purpose :
 * Class which holds the adt data which is sent to server such as
 * 1)assetId 
 * 2)userFeedback
 * 3)displayedTimeStamp
 * 4)displayDuration
 * 
 *Input   : 	1)assetId 
 *				2)userFeedback
 *				3)displayedTimeStamp
 *				4)displayDuration
 *
 *Output  : None
 */
import java.io.Serializable;

/**
 * 
 * <i>Class which holds the adt data
 * 
 * which is sent to server</i>
 * 
 * @author Balaji Muralidhar
 * 
 */
public class AdtData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String assetId;
	private String userFeedback;
	private long displayedTimeStamp;
	private int displayDuration;

	public AdtData(String assetId, String userFeedback, long displayedTimeStamp, int displayDuration)
	{
		super();
		this.assetId = assetId;
		this.userFeedback = userFeedback;
		this.displayedTimeStamp = displayedTimeStamp;
		this.displayDuration = displayDuration;
	}

	public String getAssetId()
	{
		return assetId;
	}

	public void setAssetId(String assetId)
	{
		this.assetId = assetId;
	}

	public String getUserFeedback()
	{
		return userFeedback;
	}

	public void setUserFeedback(String userFeedback)
	{
		this.userFeedback = userFeedback;
	}

	public long getDisplayedTimeStamp()
	{
		return displayedTimeStamp;
	}

	public void setDisplayedTimeStamp(long displayedTimeStamp)
	{
		this.displayedTimeStamp = displayedTimeStamp;
	}

	public int getDisplayDuration()
	{
		return displayDuration;
	}

	public void setDisplayDuration(int displayDuration)
	{
		this.displayDuration = displayDuration;
	}
}
