package middleware.papi;

import java.util.HashMap;
import java.util.Map;

public class PapiConstants
{
	public static final int ADS_ON_WATCHTV = 0;
	public static final int ADS_ON_UI = 1;
	public static final int IMAGE_AD = 0;
	public static final int VIDEO_AD = 1;
	public static final int WATCH_TV = 0;
	public static final int CHANNEL_LIST = 1;
	public static final int GUIDE = 2;
	public static final int INFO_BANNER = 3;
	public static final int PROGRAM_BANNER = 4;
	
	
	
	public static final AdDetails chList_Right_Vertical = new AdDetails(1, 1, 254, 501,"chList_Right_Vertical");
	public static final AdDetails infoBanner = new AdDetails(2, 5, 480, 243, "infoBanner");
	public static final AdDetails programBanner = new AdDetails(3, 6, 250, 145, "programBanner");
	public static final AdDetails game = new AdDetails(4, 7, 1920, 1080, "game");
	public static final AdDetails  EPG= new AdDetails(5, 2, 1360, 280, "EPG");
	
	//public static final AdDetails channelLock = new AdDetails(6, 11, 417, 540);
	public static final AdDetails[] AD_DETAILS = { chList_Right_Vertical, infoBanner, programBanner, game, EPG };
	public static final int ADS_ON_VIDEO = 1;
	
	
	
	//Mapping of Banner pet name and Unique Opp ID
	public static final Map<Integer,AdDetails> uoi_lookup1=new HashMap<Integer, AdDetails>(){{
		put(1,chList_Right_Vertical);
		put(2,EPG);
		put(5,infoBanner);
		put(6,programBanner);
		put(7,game);
	}};
}
