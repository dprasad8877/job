

public class ProgramBannerConstants {
	
	public static final int pgWidth = 550;

	public static final int pgHeight = 160;

	public static final int spaceBetweenFrameAndUI = 80;

	public static final int pg_XPos = (ScreenResolution.width - pgWidth) / 2;

	public static final int pg_YPos = (ScreenResolution.height - pgHeight - ProgramBannerConstants.spaceBetweenFrameAndUI);

	public static final int advertisementPanel_XPos = 20;

	public static final int advertisementPanel_YPos = 5;

	public static final int advertisementPanel_Width = 250;

	public static final int advertisementPanel_Height = 145;

	public static final int ChannelNameNumberXPos = 10 + advertisementPanel_Width + 40;

	public static final int ChannelNameNumberYPos = 23;

	public static final int channelNameNumber_Width = 200;

	public static final int channelNameNumber_Height =40 ;

	public static final int leftArrowButtonXPos = ChannelNameNumberXPos - 20;

	public static final int leftArrowButtonYPos = 55;

	public static final int leftArrowButton_Width = 50;

	public static final int leftArrowButton_Height = 50;





	public static final int eventNameXPos = ChannelNameNumberXPos
			+ leftArrowButton_Width - 20;

	public static final int eventNameYPos = leftArrowButtonYPos;

	public static final int eventName_Width = 150;

	public static final int eventName_Height = 44;


	public static final int startEndTimeXPos = ChannelNameNumberXPos + 20;

	public static final int startEndTimeYPos = 75;

	public static final int startEndTime_Width = 250;

	public static final int startEndTime_Height = 60;


	public static final int rightArrowButtonXPos = eventNameXPos
			+ eventName_Width - 10;

	public static final int rightArrowButtonYPos = leftArrowButtonYPos;

	public static final int rightArrowButton_Width = 50;

	public static final int rightArrowButton_Height = 50;


	public static final int gap=10;

	public static final int infoButtonWidth = 50;

	public static final int infoButtonHeight = 40;

	public static final int infoButtonXPos = pgWidth - gap - infoButtonWidth;

	public static final int infoButtonYPos = pgHeight-infoButtonHeight;


	
	public static final int userName_Width=80;
	
	public static final int userName_Height = 25;
	
	public static final int userNameYPos = 0;

	public static final int userNameXPos = ChannelNameNumberXPos;


	// for placing favImage

	public static final int favImage_Width = 25;

	public static final int favImage_XPos = ProgramBannerConstants.pgWidth
			- (favImage_Width + 5);

	public static final int favImage_YPos = 5;

	public static final int favImage_Height = 25;

	public static final int programBannerVanishTimer = 5000;
}
