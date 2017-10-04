package middleware.papi;

import java.awt.Image;

/**
 * This class extends the {@link Advertisement} object and holds the information 
 * 
 * related to asset of type image which is 
 * 
 * <br/>1.Image object of ad 
 * 
 * <br/>2.AdCompanion Type
 * 
 * <br/>3.Display duration of ad
 * 
 * <br/>4.AdCompanion URL
 * 
 * @author Balaji Muralidhar
 *
 */
public class ImageAdvertisement extends Advertisement{


	private Image adImage;
	
	private int adCompanionType;
	
	private int displayDuration;
	
	private AdCompanion adCompanionObject;
	
	private String adCompanionCommand ;

	private int height;
	private int width;
	
	
	/**Below variables : 
	 * displayPositionCode
	 * repeat;
	 * timeInterval
	 * is only valid in case of Ads on Watch TV 
	 * ;*/
	private int displayPositionCode;
	private int repeat;
	private int timeInterval;

	
	/**
	 * Temporary variable for getting ad companion URL
	 */
	
	private String adCompanionURL;


	public ImageAdvertisement(int screenId, int opporunityId,
			String advertismentID, Image adImage, int width , int height, int displayPositionCode,
			int adCompanionType, int displayDuration, AdCompanion adCompanionObject,
			String adCompanionCommand , int timeInterval , int repeat ) {
		super(screenId, opporunityId, advertismentID);
		this.displayPositionCode = displayPositionCode;
		this.adImage = adImage;
		
		this.width = width;
		this.height = height;
		
		this.adCompanionType = adCompanionType;
		this.displayDuration = displayDuration;
		this.adCompanionObject = adCompanionObject;
		this.adCompanionCommand = adCompanionCommand;
		
		this.timeInterval = timeInterval;
		this.repeat = repeat;
	}
	
	public ImageAdvertisement(int screenId, int opporunityId,
			String advertismentID, Image adImage, int width , int height, int displayPositionCode,
			int adCompanionType, int displayDuration, String adCompanionURL,
			String adCompanionCommand , int timeInterval , int repeat ) {
		super(screenId, opporunityId, advertismentID);
		this.displayPositionCode = displayPositionCode;
		this.adImage = adImage;
		
		this.width = width;
		this.height = height;
		
		this.adCompanionType = adCompanionType;
		this.displayDuration = displayDuration;
		this.adCompanionURL = adCompanionURL;
		this.adCompanionCommand = adCompanionCommand;
		
		this.timeInterval = timeInterval;
		this.repeat = repeat;
	}

	public Image getAssetImage() {
		return adImage;
	}

	public void setAssetImage(Image adImage) {
		this.adImage = adImage;
	}

	public int getAdCompanionType() {
		return adCompanionType;
	}

	public void setAdCompanionType(int adCompanionType) {
		this.adCompanionType = adCompanionType;
	}

	public int getDuration() {
		return displayDuration;
	}

	public void setDisplayDuration(int displayDuration) {
		this.displayDuration = displayDuration;
	}

	public AdCompanion getAdCompanion() {
		return adCompanionObject;
	}

	public void setAdCompURL(AdCompanion adCompanionObject) {
		this.adCompanionObject = adCompanionObject;
	}

	public String getAdCompanionCommand() {
		return adCompanionCommand;
	}

	public void setAdCompanionCommand(String adCompanionCommand) {
		this.adCompanionCommand = adCompanionCommand;
	}
	
	
	
	public int getDisplayPositionCode() {
		return displayPositionCode;
	}

	public void setDisplayPositionCode(int displayPositionCode) {
		this.displayPositionCode = displayPositionCode;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getAdCompanionURL() {
		return adCompanionURL;
	}

	public void setAdCompanionURL(String adCompanionURL) {
		this.adCompanionURL = adCompanionURL;
	}
	
	

}
