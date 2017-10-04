package middleware.papi.adsonui;
/**
 * Purpose :
 * Class that holds the data related to the opportunity of a
 * particular screen for which the application has requested for 
 * an advertisement
 * 
 * @author Balaji Muralidhar
 * 
 * Input  : None
 * 
 * Output : None
 *
 */
import middleware.papi.AdvertisementListener;


public class ScreenAndOppData extends AssetData
{
	private String[] currentSequence;
	private String[] nextSequence;
	private int displayPointer;
	private String partiallyDisplayedAssetId;
	private int remainingTime;
	private String toBeReplaced;
	private int displayPostionPointer;
	private boolean[] replacable;
	private boolean nonTargeted;
	private int primaryAssetType;
	private int adCompanionType;

	public boolean isNonTargeted()
	{
		return nonTargeted;
	}

	public void setNonTargeted(boolean nonTargeted)
	{
		this.nonTargeted = nonTargeted;
	}

//	public boolean[] getReplacable()
//	{
//		return replacable;
//	}

//	public void setReplacable(boolean[] replacable)
//	{
//		this.replacable = replacable;
//	}

	private boolean registered;

	public boolean isRegistered()
	{
		return registered;
	}

	public void setRegistered(boolean isRegistered)
	{
		this.registered = isRegistered;
	}

	public int getDisplayPostionPointer()
	{
		return displayPostionPointer;
	}

	public void setDisplayPostionPointer(int displayPostionPointer)
	{
		this.displayPostionPointer = displayPostionPointer;
	}

	private AdvertisementListener advertisementListener;

	public ScreenAndOppData( int opportunityId, String assetId,
	        String[] currentSequence, String[] nextSequence, int displayPositionPointer,
	        AdvertisementListener advertisementListener, boolean registered)
	{
		super( opportunityId, assetId);
		this.registered = registered;
		this.currentSequence = currentSequence;
		this.nextSequence = nextSequence;
		this.displayPointer = displayPositionPointer;
		this.advertisementListener = advertisementListener;
	}

	/**
	 * Method to get the {@link AdvertisementListener} if an application has
	 * registered
	 * 
	 * for and ad if not returns null
	 * 
	 * @return {@link AdvertisementListener} or null
	 */
	public AdvertisementListener getAdvertisementListener()
	{
		return advertisementListener;
	}

	public void setAdvertisementListener(AdvertisementListener advertisementListener)
	{
		this.advertisementListener = advertisementListener;
	}

	/**
	 * Method to get the current sequence of an opportunity of an
	 * 
	 * UI screen
	 * 
	 * 
	 * @return
	 */
	public String[] getCurrentSequence()
	{
		return currentSequence;
	}

	public void setCurrentSequence(String[] currentSequence)
	{
		this.currentSequence = currentSequence;
	}

	/**
	 * Method to get the next sequence of an opportunity of an
	 * 
	 * UI screen
	 * 
	 * @return
	 */
	public String[] getNextSequence()
	{
		return nextSequence;
	}

	public void setNextSequence(String[] nextSequence)
	{
		this.nextSequence = nextSequence;
	}

	/**
	 * Method to get the internal display pointer
	 * 
	 * for the current sequence
	 * 
	 * @return displayPointer
	 */
	public int getDisplayPointer()
	{
		return displayPointer;
	}

	public void setDisplayPointer(int displayPointer)
	{
		this.displayPointer = displayPointer;
	}

	/**
	 * Method to get the partially displayed asset id
	 * 
	 * @return partiallyDisplayedAssetId or 0
	 */
	public String getPartiallyDisplayedAssetId()
	{
		return partiallyDisplayedAssetId;
	}

	public void setPartiallyDisplayedAssetId(String partiallyDisplayedAssetId)
	{
		this.partiallyDisplayedAssetId = partiallyDisplayedAssetId;
	}

	/**
	 * Method to get the remaining display duration of the
	 * 
	 * partially displayed asset
	 * 
	 * @return remainingTime or 0
	 */
	public int getRemainingTime()
	{
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime)
	{
		this.remainingTime = remainingTime;
	}

	public String getToBeReplaced()
	{
		return toBeReplaced;
	}

	public void setToBeReplaced(String toBeReplaced)
	{
		this.toBeReplaced = toBeReplaced;
	}

	public int getPrimaryAssetType()
	{
		return primaryAssetType;
	}

	public void setPrimaryAssetType(int primaryAssetType)
	{
		this.primaryAssetType = primaryAssetType;
	}

	public int getAdCompanionType()
	{
		return adCompanionType;
	}

	public void setAdCompanionType(int adCompanionType)
	{
		this.adCompanionType = adCompanionType;
	}
}
