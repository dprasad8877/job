package middleware.papi;

import java.awt.Image;

public class StickyAdvertisement extends Advertisement
{
	private Image adImage;
	private int adCompanionType;
	private String adImageURL;
	private String adCompanionCommand;
	private int height;
	private int width;
	/**
	 * Below variables : displayPositionCode repeat; timeInterval is only valid
	 * in case of Ads on Watch TV ;
	 */
	private int displayPositionCode;
	private int repeat;
	private int timeInterval;

	
	


	public StickyAdvertisement(int screenId, int opporunityId,
			String advertismentID, Image adImage, int width , int height, int displayPositionCode,
			int adCompanionType, String adImageURL,
			String adCompanionCommand , int timeInterval , int repeat ) {
		super(screenId, opporunityId, advertismentID);
		this.displayPositionCode = displayPositionCode;
		this.adImage = adImage;
		this.width = width;
		this.height = height;
		this.adCompanionType = adCompanionType;
		this.adImageURL = adImageURL;
		this.adCompanionCommand = adCompanionCommand;
		this.timeInterval = timeInterval;
		this.repeat = repeat;
	}

	public Image getAssetImage()
	{
		return adImage;
	}

	public void setAssetImage(Image adImage)
	{
		this.adImage = adImage;
	}

	public int getAdCompanionType()
	{
		return adCompanionType;
	}

	public void setAdCompanionType(int adCompanionType)
	{
		this.adCompanionType = adCompanionType;
	}

	public String getAdCompURL()
	{
		return adImageURL;
	}

	public void setAdCompURL(String adImageURL)
	{
		this.adImageURL = adImageURL;
	}

	public String getAdCompanionCommand()
	{
		return adCompanionCommand;
	}

	public void setAdCompanionCommand(String adCompanionCommand)
	{
		this.adCompanionCommand = adCompanionCommand;
	}

	public int getDisplayPositionCode()
	{
		return displayPositionCode;
	}

	public void setDisplayPositionCode(int displayPositionCode)
	{
		this.displayPositionCode = displayPositionCode;
	}

	public int getRepeat()
	{
		return repeat;
	}

	public void setRepeat(int repeat)
	{
		this.repeat = repeat;
	}

	public int getTimeInterval()
	{
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval)
	{
		this.timeInterval = timeInterval;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}
}
