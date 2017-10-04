package middleware.papi.adsonui.request;
/**
 * Purpose :
 * This class holds the data which is required to send 
 * AdsOnUIRequest to the server
 * 
 * @author Balaji Muralidhar
 *
 *Input		:	1)subscriberId
 *				2)timeZone
 *				3)timeStamp
 *
 *Output	:	None
 */
import java.util.ArrayList;
import java.util.TimeZone;

import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.glue.AdsOnUIGlue;
import middleware.papi.adsonui.util.AdsOnUILog;

/**
 * This class holds the data which is required to send
 * 
 * AdsOnUIRequest to the server
 * 
 * @author Balaji Muralidhar
 * 
 */
public class AdsOnUIRequestData
{
	private String subscriberId;
	private String timeZone;
	private long timeStamp;
	private String uiVersion;
	private static ArrayList screenDetailsList;
	static
	{
		screenDetailsList = new ArrayList();
	}

	public AdsOnUIRequestData()
	{
		// TODO Auto-generated constructor stub
	}

	public AdsOnUIRequestData(String subscriberId, String timeZone, long timeStamp, String uiVersion)
	{
		super();
		this.subscriberId = subscriberId;
		this.timeZone = timeZone;
		this.timeStamp = timeStamp;
		this.uiVersion = uiVersion;
	}

	public AdsOnUIRequestData(String subscriberId, String timeZone, long timeStamp)
	{
		super();
		this.subscriberId = subscriberId;
		this.timeZone = timeZone;
		this.timeStamp = timeStamp;
	}

	public static AdsOnUIRequestData getAdsOnUIRequestData()
	{
		fillInScreenDetails();
		if (AdsOnUIConstants.TEST)
		{
			return new AdsOnUIRequestData(AdsOnUIConstants.TEST_SUBCRIBER_ID, TimeZone.getDefault()
			        .getID(), System.currentTimeMillis(), AdsOnUIConstants.UI_VERSION);
		}
		else
		{
			if (AdsOnUIGlue.getSubscriberId() == null)
			{
				AdsOnUIGlue.setSubscriberId(AdsOnUIConstants.TEST_SUBCRIBER_ID);
			}
			return new AdsOnUIRequestData(AdsOnUIGlue.getSubscriberId(), TimeZone.getDefault()
			        .getDisplayName(), System.currentTimeMillis(), AdsOnUIConstants.UI_VERSION);
		}
	}

	public static AdsOnUIRequestData getAdsonUIRepeatRequestData()
	{
		if (AdsOnUIConstants.TEST)
		{
			return new AdsOnUIRequestData(AdsOnUIConstants.TEST_SUBCRIBER_ID, TimeZone.getDefault()
			        .getID(), System.currentTimeMillis());
		}
		else
		{
			if (AdsOnUIGlue.getSubscriberId() == null)
			{
				AdsOnUIGlue.setSubscriberId(AdsOnUIConstants.TEST_SUBCRIBER_ID);
			}
			return new AdsOnUIRequestData(AdsOnUIGlue.getSubscriberId(), TimeZone.getDefault()
			        .getDisplayName(), System.currentTimeMillis());
			/**
			 * API to fetch Subscriber id in PC Client
			 */
		}
	}

	private static void fillInScreenDetails()
	{
		// TODO Auto-generated method stub
		if (AdsOnUIConstants.PC_CLIENT)
		{
			/**
			 * For channel list
			 */
			ScreenDetails screenDetails = new ScreenDetails(AdsOnUIConstants.ONE
			        );
			screenDetails.addOpportunityDetails(AdsOnUIConstants.ONE,
			        AdsOnUIConstants.CHANNEL_LIST_WIDTH_PC_CLIENT,
			        AdsOnUIConstants.CHANNEL_LIST_HEIGHT_PC_CLIENT,
			        AdsOnUIConstants.RECTANGLE_ELLIPSE);
			addUIScreen(screenDetails);
			/**
			 * For info banner
			 */
			screenDetails = new ScreenDetails(AdsOnUIConstants.TWO);
			screenDetails.addOpportunityDetails(AdsOnUIConstants.ONE,
			        AdsOnUIConstants.INFO_BANNER_WIDTH_PC_CLIENT,
			        AdsOnUIConstants.INFO_BANNER_HEIGHT_PC_CLIENT,
			        AdsOnUIConstants.RECTANGLE_ELLIPSE);
			addUIScreen(screenDetails);
			/**
			 * For Progam Banner
			 */
			screenDetails = new ScreenDetails(AdsOnUIConstants.THREE);
			screenDetails.addOpportunityDetails(AdsOnUIConstants.ONE,
			        AdsOnUIConstants.PROGRAM_BANNER_WIDTH_PC_CLIENT,
			        AdsOnUIConstants.PROGRAM_BANNER_HEIGHT_PC_CLIENT,
			        AdsOnUIConstants.RECTANGLE_ELLIPSE);
			addUIScreen(screenDetails);
		}
	}

	public static void addUIScreen(ScreenDetails screenDetails)
	{
		// TODO Auto-generated method stub
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "Inside addUIScreen");
		if (null != screenDetailsList && null != screenDetails)
		{
			screenDetailsList.add(screenDetails);
		}
	}

	public String getSubscriberId()
	{
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId)
	{
		this.subscriberId = subscriberId;
	}

	public String getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(String timeZone)
	{
		this.timeZone = timeZone;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public String getUiVersion()
	{
		return uiVersion;
	}

	public void setUiVersion(String uiVersion)
	{
		this.uiVersion = uiVersion;
	}

	public static ArrayList getScreenDetailsList()
	{
		return screenDetailsList;
	}

	public static void setScreenDetailsList(ArrayList screenDetailsList)
	{
		AdsOnUIRequestData.screenDetailsList = screenDetailsList;
	}
}
