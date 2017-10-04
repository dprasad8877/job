package middleware.papi.adsonui.constants;
/**
 *Purpose	: This class contains constants used by this AdsOnUi module 
 *
 *Input		: None
 *
 *Output	: None
 *
 */
public class AdsOnUIConstants
{
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static int THREE = 3;
	public static int FOUR = 4;
	public static final String NULL = null;
	public static final String adsOnUIFoldername = "AdsOnUI";
	
	
	/**
	 * Sequence type
	 */
	public static final String SUBSCRIBER_BASED = "Subscriber based";
	public static final String SCREEN_ONE = "ChannelList";
	public static final String SCREEN_TWO = "InfoBanner";
	public static final String SCREEN_THREE = "ProgramBanner";
	public static final boolean TEST = false;
	public static final boolean PC_CLIENT = true;
	public static final String sampleResponseXmlLocation = "sample.xml";
	public static final String sampleFileLocation = "AdsOnUI/AdsOnUIData.data";
	public static final String unAnsweredQuestionLocation = "AdsOnUI/Question.data";
	public static final String adtFileLocation = "AdsOnUI/AdtData.data";

	
	/**
	 * Request type
	 * 
	 */
	public static final int COMPLETE = 0;
	public static final int REPEAT = 1;
	public static final int ADT = 2;
	public static final int QUIZ = 3;
	public static final int FETCHING_AD_FIRST_TIME = 2;
	public static final int FETCHING_REPLACABLE_AD = 3;
	
	
	/**
	 * The width and height of ad container of each UI screen
	 */
	public static final int CHANNEL_LIST_WIDTH_PC_CLIENT = 400;
	public static final int CHANNEL_LIST_HEIGHT_PC_CLIENT = 900;
	public static final int INFO_BANNER_WIDTH_PC_CLIENT = 300;
	public static final int INFO_BANNER_HEIGHT_PC_CLIENT = 225;
	public static final int PROGRAM_BANNER_WIDTH_PC_CLIENT = 302;
	public static final int PROGRAM_BANNER_HEIGHT_PC_CLIENT = 167;

	
	/**
	 * Supporting shapes
	 */
	public static final String ELLIPSE = "Ellipse";
	public static final String RECTANGLE = "Rectangle";
	public static final String RECTANGLE_ELLIPSE = RECTANGLE + "," + ELLIPSE;
	
	
	/**
	 * Current UI Version
	 */
	public static String UI_VERSION = "Black Mamba 1.1";
	
	
	/**
	 * Test Subscriber Id
	 */
	public static final String TEST_SUBCRIBER_ID = "P1000000";
	
	
	/**
	 * Tag Names in AdsOnUIResponse
	 */
	public static final String AD_URL = "AdURL";
	public static final String AD_COMPANION_URL = "AdCompanionURL";
	public static final String PRIMARY_IMAGE_URL = "PrimaryAdImageURL";
	public static final String PRIMARY_VIDEO_URL = "PrimaryAdVideoURL";
	public static final String PRIMARY_AUDIO_URL = "PrimaryAdAudioURL";
	public static final String ADCOMPANION_IMAGE_URL = "AdCompanionImageURL";
	public static final String ADCOMPANION_VIDEO_URL = "AdCompanionVideoURL";
	public static final String ADCOMPANION_AUDIO_URL = "AdCompanionAudioURL";
	public static final String ADCOMPANION_LINK_URL = "AdCompanionLinkURL";
	public static final String PRIMARY_ASSET_TYPE = "PrimaryAssetType";
	public static final String ADCOMPANION_ASSET_TYPE = "AdCompanionAssetType";
	public static final String DISPLAY_TIME = "DisplayTime";
	public static final String ASSET_DISPLAY_MODE = "AssetDisplayMode";
	public static final String UI_SCREENS = "UIScreens";
	public static final String SCREEN = "Screen";
	public static final String SCREEN_NAME = "ScreenName";
	public static final String OPPORTUNITIES = "Opportunities";
	public static final String OPPORTUNITY = "Opportunity";
	public static final String OPPID = "OppId";
	public static final String SEQUENCE = "Sequence";
	public static final String ASSET = "Asset";
	public static final String STICK = "Stick";
	public static final String INFORMATION = "Information";
	public static final String SERVER_CONFLICT = "ServerQuestion";
	public static String TIME_INTERVAL = "TimeInterval";

	
	/**
	 * For handling primary and adcompanion type
	 */
	public static final int PICTURE = 0;
	public static final int VIDEO = 1;
	
	
	/**
	 * Service Check Delay changed to 60 secs
	 */
	public static final long SERVICE_CHECK_DELAY = 60 * 1000;
	public static final int SUCCESS = 1;
	public static final int FAILURE = -1;
	public static final int RETRY = 144;
	public static final int numberOfAttemptsInitialValue = 1;
	public static final int finalNumberOfAttempts = 3;
	
	
	/**
	 * UI Priority static
	 */
	public static final int CHANNEL_LIST_PRIORITY = 4;
	public static final int INFO_BANNER_PRIORITY = 2;
	public static final int[] UI_SLOTS = { 1, 1, 1, 1, 1, 1 };
	// public static final String UI_LOCATIONS[] =
	// {"AdsOnUI/ChannelList/","AdsOnUI/InfoBanner/","AdsOnUI/ProgramBanner/","AdsOnUI/GameFullScreen/","AdsOnUI/Channel
	// Lock/","AdsOnUI/EPG/"};
	public static final String UI_NAMES[] = {"ChannelList","InfoBanner","ProgramBanner","GameFullScreen","EPG Grid"};
	public static final int[] OPPIDS = {1,2,5,6,7};
	
	
	/**
	 * Channel List image location
	 */
//	public static final int CH_LIST_PATH = 0;
//	public static final String OPPORTUNITY1 = "Opportunity1";
//	public static final String OPPORTUNITY2 = "Opportunity2";
	public static final String CONNECTION_REFUSED = "Connection refused";
	public static final String VIEWED = "Viewed";
	public static final String INTERESTED = "Interested";
	public static final String quizAdtURL = "/ClientQuiz/QuizAnswer";
}
