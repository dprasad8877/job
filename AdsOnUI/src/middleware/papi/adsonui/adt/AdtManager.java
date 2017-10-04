package middleware.papi.adsonui.adt;
/**
 * Purpose : 
 * Class that is responsible for management 
 * of the adt data which is to be sent to 
 * server
 * 
 * Input	:	None
 * 
 * Output	:	None
 *
 */
import java.util.ArrayList;

import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.util.FlashManager;
import middleware.papi.adsonui.util.ObjectReader;
import middleware.papi.adsonui.util.ObjectWriter;


public class AdtManager
{
	private static ArrayList adtDataList;
	static
	{
		if (checkOldAdtData())
		{
			adtDataList = readTheUncomittedAdtList();
			deleteUncomittedData();
			if (adtDataList == null)
			{
				adtDataList = new ArrayList();
			}
		}
		else
		{
			adtDataList = new ArrayList();
		}
	}

	/**
	 * 
	 * Method to store the adt information with the
	 * 
	 * given parameters
	 * 
	 * @param feedBack
	 * @param assetId
	 * @param displayedTimeStamp
	 * @param displayDuration
	 */
	public static void addAdtInfo(String feedBack, String assetId, long displayedTimeStamp,
	        int displayDuration)
	{
		AdtData adtData = new AdtData(assetId, feedBack, displayedTimeStamp, displayDuration);
		adtDataList.add(adtData);
	}

	/**
	 * Method to read the uncomitted adt data if any
	 * 
	 * @return list of adt data
	 */
	private static ArrayList readTheUncomittedAdtList()
	{
		// TODO Auto-generated method stub
		ArrayList uncomittedList;
		try
		{
			ObjectReader objectReader = new ObjectReader(AdsOnUIConstants.adtFileLocation);
			uncomittedList = (ArrayList) objectReader.readObject();
			return uncomittedList;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Method to get the adt list
	 * 
	 * @return list of adt data
	 */
	public static ArrayList getAdtDataList()
	{
		return adtDataList;
	}

	/**
	 * Clears all the adt data stored
	 */
	public static void clearAdtData()
	{
		adtDataList.removeAll(adtDataList);
	}

	/**
	 * Method to save the uncomitted adt data to a
	 * 
	 * file
	 * 
	 * @return success on saving else false
	 */
	public static boolean saveAdtDataToFile()
	{
		if (adtDataList != null && adtDataList.size() > 0)
		{
			try
			{
				ObjectWriter objectWriter = new ObjectWriter(AdsOnUIConstants.adtFileLocation);
				objectWriter.writeObject(adtDataList);
				return true;
			}
			catch (Exception e)
			{
				return false;
			}
		}
		return false;
	}

	/**
	 * Method to delete uncomitted adt data once it is
	 * 
	 * successfully sent to server
	 * 
	 */
	public static void deleteUncomittedData()
	{
		FlashManager.removeFile(AdsOnUIConstants.adtFileLocation);
	}

	/**
	 * Method to check if uncomitted adt data exists
	 * 
	 * @return true if exists else false
	 */
	private static boolean checkOldAdtData()
	{
		return FlashManager.checkFileExists(AdsOnUIConstants.adtFileLocation);
	}
}
