package middleware.papi.adsonui;
/**
*Purpose : 
* Class which manages the caching process of the assets 
* of a particular opportunity of a screen 
* 
* @author Balaji Muralidhar
*
*
*Input  : None 
*
*Output : None
*
*/
import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import middleware.papi.adsonui.util.AdsOnUILog;


public class CacheManager
{
	private static MediaTracker mediaTracker;
	private volatile static ArrayList cacheList;
	private volatile static int count = 0;
	static
	{
		cacheList = new ArrayList();
		mediaTracker = new MediaTracker(new Container());
	}

	/**
	 * Method used to cache an image and also store the
	 * 
	 * cached image along with the data passed as
	 * 
	 * parameters
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @param assetId
	 * @param location
	 * @return
	 */
	public static boolean cacheImage(String screenName, int opportunityId, String assetId,
	        URL location)
	{
		if (!checkIfImageIsCached( opportunityId, assetId))
		{
			Image image = Toolkit.getDefaultToolkit().getImage(location);
			mediaTracker.addImage(image, count);
			try
			{
				mediaTracker.waitForID(count);
			}
			catch (InterruptedException e)
			{
				return false;
			}
			if (AdsOnUILog.somethingWrong)
			{
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Download finshed" + location);
			}
			
			CacheData cacheData = new CacheData( opportunityId, assetId, image);
			cacheList.add(cacheData);
			if (AdsOnUILog.somethingWrong)
			{
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Count : " + count);
			}
			count++;
			return true;
		}
		return false;
	}

	/**
	 * Method used to cache an image and also store the
	 * 
	 * cached image along with the data passed as
	 * 
	 * parameters
	 * 
	 * @param screenName
	 * @param opportunityId
	 * @param assetId
	 * @param imageURL
	 * @return
	 */
	public static boolean cacheImage( int opportunityId, String assetId,
	        URL imageURL)
	{
		if (!checkIfImageIsCached( opportunityId, assetId))
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Caching " + imageURL);
			Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
			mediaTracker.addImage(image, count);
			try
			{
				mediaTracker.waitForID(count);
			}
			catch (InterruptedException e)
			{
				return false;
			}
			if (AdsOnUILog.somethingWrong)
			{
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Download finshed" + imageURL);
			}
			//System.out.println("$$$$$$$$$$$$$$$$$$$$$"+opportunityId);
			CacheData cacheData = new CacheData( opportunityId, assetId, image);
			cacheList.add(cacheData);
			if (AdsOnUILog.somethingWrong)
			{
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Count : " + count);
			}
			count++;
			return true;
		}
		return false;
	}

	public static boolean checkIfImageIsCached( int opportunityId, String assetId)
	{
		CacheData cacheData = getCacheData( opportunityId, assetId);
		if (null != cacheData)
		{
			return true;
		}
		return false;
	}

	/**
	 * Method which returns the {@link Image} object if it is
	 * 
	 * cached otherwise returns null
	 * 
	 * @param screeName
	 * @param opportunityId
	 * @param assetId
	 * @return
	 */
	public static Image getImage( int opportunityId, String assetId)
	{
		CacheData cacheData = getCacheData( opportunityId, assetId);
		if (null != cacheData)
		{
			return cacheData.getImage();
		}
		return null;
	}

	/**
	 * Method that flushes the cached image if it is cached returns true if
	 * flushed otherwise returns false
	 * 
	 * @param screeName
	 * @param opportunityId
	 * @param assetId
	 * @return boolean
	 */
	public static boolean removeImage( int opportunityId, String assetId)
	{
		CacheData cacheData = getCacheData( opportunityId, assetId);
		if (null != cacheData)
		{
			cacheData.getImage().flush();
			cacheList.remove(cacheData);
			return true;
		}
		return false;
	}

	/**
	 * Helper method to get the {@link CacheData} object based on the
	 * 
	 * passed parameters
	 * 
	 * @param screeName
	 * @param opportunityId
	 * @param assetId
	 * @return null if no entry is present based on passed parameters
	 */
	private static CacheData getCacheData( int opportunityId, String assetId)
	{
		if (null != cacheList)
		{
			Iterator cacheDataIterator = cacheList.iterator();
			while (cacheDataIterator.hasNext())
			{
				CacheData cacheData = (CacheData) cacheDataIterator.next();
				if (null != cacheData)
				{
					/*if (cacheData.getScreenName().equals(screeName))
					{*/
						if (cacheData.getOpportunityId() == opportunityId)
						{
							if (cacheData.getAssetId().equals(assetId))
							{
								return cacheData;
							}
						}
//					}
				}
			}
		}
		return null;
	}
}
