package middleware.papi;

import middleware.papi.adsonui.AdsOnUIManager;
import middleware.papi.adsonui.util.AdsOnUILog;

public class Papi
{
	public static void registerForAnAdvertisement(int screenId, int oppurtunityID,
			AdvertisementListener adListener) throws IllegalArgumentException
			{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "Papi registerForAnAdvertisement entry");
		if (screenId == PapiConstants.WATCH_TV)
		{
		}
		else
		{
			AdsOnUIManager.getInstance().getAnAsset(screenId, oppurtunityID, adListener);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "Papi registerForAnAdvertisement entry");
			}

	public static void notifyPapiAdvertisemtHasBeenDisplayed(int NumberOfSecondsDisplayed,
			Advertisement TheAdvertismentObjectBeingDisplayed) throws IllegalArgumentException
			{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"Papi notifyPapiAdvertisemtHasBeenDisplayed entry");
		if (TheAdvertismentObjectBeingDisplayed != null
				&& TheAdvertismentObjectBeingDisplayed.getScreenId() == PapiConstants.WATCH_TV)
		{
		}
		else
		{
			AdsOnUIManager.getInstance().notifyAdHasBeenDisplayed(NumberOfSecondsDisplayed,
					TheAdvertismentObjectBeingDisplayed, false);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"Papi notifyPapiAdvertisemtHasBeenDisplayed exit");
			}

	public static void deregisterAdvertisementListener(int screenId, int oppurtunityId,
			AdvertisementListener AdListener) throws IllegalArgumentException
			{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"Papi deregisterAdvertisementListener entry");
		if (screenId == PapiConstants.WATCH_TV)
		{
		}
		else
		{
			AdsOnUIManager.getInstance().deregisterAdvertisementListener(screenId, oppurtunityId,
					AdListener);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"Papi deregisterAdvertisementListener entry");
			}

	public static void notifyAdCompanionHasBeenRequested(int NumberOfSecondsDisplayed,
			Advertisement advertisement) throws IllegalArgumentException
			{
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"Papi notifyAdCompanionHasBeenRequested entry");
		if (advertisement != null && advertisement.getScreenId() == PapiConstants.WATCH_TV)
		{
		}
		else
		{
			AdsOnUIManager.getInstance().notifyAdHasBeenDisplayed(NumberOfSecondsDisplayed,
					advertisement, true);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"Papi notifyAdCompanionHasBeenRequested exit");
			}
}
