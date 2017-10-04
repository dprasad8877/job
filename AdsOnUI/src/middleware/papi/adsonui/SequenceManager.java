package middleware.papi.adsonui;
/**
 * Purpose : 
 * This class is responsible for management of asset sequence
 * for each opportunity of a screen and also provides an asset for a
 * particular opportunity of a screen when requested by the application
 * It also manages the caching of assets and removing them when not
 * required
 * 
 * @author Balaji Muralidhar
 * 
 * Input  : None
 * 
 * Output : None
 * 
 */
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import middleware.papi.AdCompanion;
import middleware.papi.Advertisement;
import middleware.papi.AdvertisementListener;
import middleware.papi.ImageAdvertisement;
import middleware.papi.PapiConstants;
import middleware.papi.StickyAdvertisement;
import middleware.papi.adsonui.adt.AdtManager;
import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.request.AdsOnUIRequestData;
import middleware.papi.adsonui.request.AdsOnUIXMLPlacement;
import middleware.papi.adsonui.request.CommunicationListener;
import middleware.papi.adsonui.util.AdsOnUILog;
import Util.Debug.Log;


public class SequenceManager implements CommunicationListener {
	private static SequenceManager sequenceManager;
	private ArrayList screenDataList;
	private AdsOnUIData adsOnUIData;
	private boolean imageStillNotCached;
	private long displayedTime;

	private SequenceManager() {
		screenDataList = new ArrayList();
	}

	protected static SequenceManager getInstance() {
		if (null == sequenceManager) {
			sequenceManager = new SequenceManager();
		}
		return sequenceManager;
	}

	/**
	 * Method that is called when an application requests for an
	 * 
	 * advertisement and notifies back the application who has
	 * 
	 * registered to get the ad with the {@link Advertisement} object
	 * 
	 * 
	 * @param screenId
	 * @param oppurtunityID
	 * @param adListener
	 */
	public void fetchAnAsset(int screenId, int oppurtunityID,
			AdvertisementListener adListener) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager fetchAnAsset entry");
		adsOnUIData = AdsOnUIManager.getInstance().getAdsOnUIData();
		ScreenAndOppData screenAndOppData = getScreenAndOppData(
				 oppurtunityID);
		if (null != adListener) {
			if (null != screenAndOppData) {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
						"SequenceManager screenOppData Already exists");
				if (screenAndOppData.getAdvertisementListener() == null) {
					screenAndOppData.setAdvertisementListener(adListener);
					screenAndOppData.setRegistered(true);
				} else {
					if (!screenAndOppData.getAdvertisementListener().equals(
							adListener)) {
						screenAndOppData.setAdvertisementListener(adListener);
						screenAndOppData.setRegistered(true);
					}
				}
				fillImageAdvertismentDetailsAndSend(screenAndOppData, screenId);
			} else {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
						"SequenceManager screenOppData doesnt exist");
				createScreenOppData(screenId, oppurtunityID, adListener);
			}
		} else {
			if (screenAndOppData != null && screenAndOppData.isRegistered()) {
				throw new IllegalArgumentException(
						"Advertisement Listener is null");
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager fetAnAsset exit");
	}

	/**
	 * Helper method that sends if any partially displayed asset is
	 * 
	 * present to the requesting application and also caches the
	 * 
	 * next asset to be given
	 * 
	 * @param screenAndOppData
	 * @param screenId
	 * @return
	 */
	private boolean sendPartiallyDisplayedAssetIfAny(
			ScreenAndOppData screenAndOppData, int screenId) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager.sendPartiallyDisplayedAssetIfAny entry");
		if (null != screenAndOppData) {
			if (adsOnUIData != null) {
				String assetId = adsOnUIData.getPartiallyDisplayedAsset(
						
						screenAndOppData.getOpportunityId());
				int remDuration = adsOnUIData.getRemainingDuration(
						
						screenAndOppData.getOpportunityId());
				if (assetId != null && remDuration > 0) {
					screenAndOppData.setPartiallyDisplayedAssetId(assetId);
					screenAndOppData.setRemainingTime(remDuration);
					adsOnUIData.setPartiallyDisplayedAsset(
							
							screenAndOppData.getOpportunityId(),
							AdsOnUIConstants.NULL);
					adsOnUIData.setRemainingDuration(
							
							screenAndOppData.getOpportunityId(),
							AdsOnUIConstants.ZERO);
					if (!screenAndOppData.isNonTargeted()) {
						cacheTheNextAdvertisement(screenAndOppData, screenId,
								false);
					}
				}
			}
			if (screenAndOppData.getPartiallyDisplayedAssetId() != null
					&& screenAndOppData.getRemainingTime() != 0) {
				Image image = CacheManager.getImage(						
						screenAndOppData.getOpportunityId(),
						screenAndOppData.getPartiallyDisplayedAssetId());

				if (null != image) {
					String adCompURL = null;
					if (screenAndOppData.getAdCompanionType() == AdsOnUIConstants.PICTURE)
					{
//						adCompURL = adsOnUIData.getAdCompanionImageURL()
//								+ screenAndOppData
//										.getPartiallyDisplayedAssetId();
						//edit
						adCompURL = adsOnUIData.getAdCompanionImageURL();
						if (AdsOnUILog.somethingWrong) {
							AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
									"Givin image URL :" + adCompURL);
						}

						AdCompanion adCompanion = new AdCompanion(
								AdsOnUIConstants.PICTURE);
						adCompanion.setPictureURL(adCompURL
								+ String.valueOf(screenAndOppData
										.getPartiallyDisplayedAssetId()));

						ImageAdvertisement imageAdvertisement = new ImageAdvertisement(
								screenId, screenAndOppData.getOpportunityId(),
								String.valueOf(screenAndOppData
										.getPartiallyDisplayedAssetId()),
								image, AdsOnUIConstants.ZERO,
								AdsOnUIConstants.ZERO, AdsOnUIConstants.ZERO,
								PapiConstants.IMAGE_AD,
								screenAndOppData.getRemainingTime(),
								adCompanion, null, AdsOnUIConstants.ZERO,
								AdsOnUIConstants.ZERO);

						screenAndOppData.getAdvertisementListener()
								.iGotAnAdvertisement(imageAdvertisement);
						AdsOnUILog
								.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
										"SequenceManager.sendPartiallyDisplayedAssetIfAny exit");
						return true;
					} else if (screenAndOppData.getAdCompanionType() == AdsOnUIConstants.VIDEO) {
						adCompURL = adsOnUIData.getAdCompanionVideoURL()
								+ screenAndOppData
										.getPartiallyDisplayedAssetId();
						if (AdsOnUILog.somethingWrong) {
							AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
									"Givin video URL :" + adCompURL);
						}

						// edit
						AdCompanion adCompanion = new AdCompanion(
								AdsOnUIConstants.VIDEO);
						adCompanion.setVideoURL(adCompURL
								+ String.valueOf(screenAndOppData
										.getPartiallyDisplayedAssetId()));

						ImageAdvertisement imageAdvertisement = new ImageAdvertisement(
								screenId, screenAndOppData.getOpportunityId(),
								String.valueOf(screenAndOppData
										.getPartiallyDisplayedAssetId()),
								image, AdsOnUIConstants.ZERO,
								AdsOnUIConstants.ZERO, AdsOnUIConstants.ZERO,
								PapiConstants.VIDEO_AD,
								screenAndOppData.getRemainingTime(),
								adCompanion, null, AdsOnUIConstants.ZERO,
								AdsOnUIConstants.ZERO);

						screenAndOppData.getAdvertisementListener()
								.iGotAnAdvertisement(imageAdvertisement);
						AdsOnUILog
								.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
										"SequenceManager.sendPartiallyDisplayedAssetIfAny exit");
						return true;
					}
				}
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager.sendPartiallyDisplayedAssetIfAny exit");
		return false;
	}

	/**
	 * If the application if requesting ad for the first time then
	 * 
	 * a {@link ScreenAndOppData} object is newly created
	 * 
	 * @param screenId
	 * @param oppurtunityID
	 * @param adListener
	 */
	private void createScreenOppData(int screenId, int oppurtunityID,
			AdvertisementListener adListener) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager createScreenOppData entry");
		if (adsOnUIData != null) {
			String screenName = AdsOnUIConstants.UI_NAMES[screenId - 1];
			String[] currentSequence = adsOnUIData.getCurrentOrNextSequence(
					 oppurtunityID,
					AdsOnUIConstants.UI_SLOTS[screenId - 1], false);
			//Opportunity opportunity = getOpportunity( oppurtunityID);
			Opportunity opportunity =adsOnUIData.getUIOpportunity(oppurtunityID);
			if (currentSequence == null || currentSequence.length <= 0
					|| opportunity == null) {
				adListener.iGotAnAdvertisement(null);
				AdsOnUILog
						.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
								"SequenceManager createScreenOppData exit without sequence");
				return;
			}
			if (AdsOnUILog.somethingWrong) {
				for (int i = 0; i < currentSequence.length; i++) {
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
							"Current Sequence : " + currentSequence[i]);
				}
			}
			String[] nextSequence = adsOnUIData.getCurrentOrNextSequence(
					 oppurtunityID,
					AdsOnUIConstants.UI_SLOTS[screenId - 1], true);
			if (AdsOnUILog.somethingWrong && nextSequence != null) {
				for (int i = 0; i < nextSequence.length; i++) {
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
							"Next Sequence : " + nextSequence[i]);
				}
			}
			int displayPostionPointer = adsOnUIData.getDisplayPositionPointer(
					 oppurtunityID);
			ScreenAndOppData screenAndOppData = new ScreenAndOppData(
					 oppurtunityID, AdsOnUIConstants.NULL,
					currentSequence, nextSequence, AdsOnUIConstants.ZERO,
					adListener, true);
			screenAndOppData.setDisplayPostionPointer(displayPostionPointer);
			screenAndOppData.setRegistered(true);
			screenAndOppData.setNonTargeted(adsOnUIData
					.checkWhetherNonTargeted( oppurtunityID));
			screenAndOppData.setPrimaryAssetType(opportunity
					.getPrimaryAssetType());
			screenAndOppData.setAdCompanionType(opportunity
					.getAdCompanionType());
			screenDataList.add(screenAndOppData);
			resetReplacementFlags(screenAndOppData);
			fillImageAdvertismentDetailsAndSend(screenAndOppData, screenId);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager createScreenOppData exit");
	}

	/**
	 * Resets the replacement flags of the current sequence
	 * 
	 * @param screenAndOppData
	 */
	private void resetReplacementFlags(ScreenAndOppData screenAndOppData) {
		if (screenAndOppData.getCurrentSequence() != null) {
			boolean[] replacable = new boolean[screenAndOppData
					.getCurrentSequence().length];
//			screenAndOppData.setReplacable(replacable);
		}
	}

	/**
	 * Helper method that fills the {@link Advertisement}
	 * 
	 * object and sends it to the application by calling iGotAndAdvertisement of
	 * 
	 * the requesting application
	 * 
	 * 
	 * 
	 * @param screenAndOppData
	 * @param screenId
	 */
	private void fillImageAdvertismentDetailsAndSend(
			ScreenAndOppData screenAndOppData, int screenId) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager.fillImageAdvertismentDetailsAndSend entry");
		if (screenAndOppData.getPrimaryAssetType() == AdsOnUIConstants.PICTURE) {
			if (sendPartiallyDisplayedAssetIfAny(screenAndOppData, screenId)) {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
						"Sending partially displayed Asset");
				return;
			}
			String currentAssetId = null;
			AdsOnUILog
					.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
							"SequenceManager fillImageAdvertismentDetailsAndSend entry");
			if (screenAndOppData.isNonTargeted()) {
				currentAssetId = screenAndOppData.getCurrentSequence()[AdsOnUIConstants.ZERO];
			} else {
				if (screenAndOppData.getCurrentSequence() != null
						&& screenAndOppData.getCurrentSequence().length > 0) {
					currentAssetId = screenAndOppData.getCurrentSequence()[screenAndOppData
							.getDisplayPointer()];
				}
			}
			if (imageStillNotCached) {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
						"Image still not cached");
				imageStillNotCached = false;
				cacheTheNextAdvertisement(screenAndOppData, screenId, true);
			} else {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
						"imageStillNotCached : " + imageStillNotCached);
			}
			Image image = CacheManager.getImage(
					
					screenAndOppData.getOpportunityId(), currentAssetId);
			if (AdsOnUILog.somethingWrong) {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "ScreenID : "
						+ screenId+ " OpportunityId: "
						+ screenAndOppData.getOpportunityId()
						+ " currentAssetId: " + currentAssetId + " image"
						+ image);
			}
			int displayDuration = 0;
			String adCompanionURL = null;
			if (null != adsOnUIData) {
				displayDuration = adsOnUIData.getDisplayDuration(
						
						screenAndOppData.getOpportunityId());
			} else {
				AdsOnUILog
						.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
								"SequenceManager fillImageAdvertismentDetailsAndSend adOnUIData null");
			}
			if (image != null) {
				/**
				 * New Change send sticky advertisement if non targted
				 */
				if (!screenAndOppData.isNonTargeted()) {
					Log.display("*************%%%%%%%%%%%%%%%%%%%%%%%%%%%*********************");
					Log.display("Screen is targeted.....");
					Log.display("*************%%%%%%%%%%%%%%%%%%%%%%%%%%%*********************");

					if (displayDuration != 0) {
						AdsOnUILog
								.displayLog(AdsOnUILog.LOGIC_TYPE,
										"Sequencemanager creating ImageAdvertisement Object");
						if (screenAndOppData.getAdCompanionType() == AdsOnUIConstants.PICTURE) {
							adCompanionURL = adsOnUIData
									.getAdCompanionImageURL();
							if (AdsOnUILog.somethingWrong) {
								AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
										"Givin image URL :" + adCompanionURL);
							}

							// edit
							AdCompanion adCompanion = new AdCompanion(
									AdsOnUIConstants.PICTURE);
							adCompanion.setPictureURL(adCompanionURL
									+ String.valueOf(currentAssetId));

							Log.display("*************%%%%%%%%%%%%%%%%%%%%%%%%%%%*********************");
							Log.display("AdCompanionType is picture.....");
							Log.display("*************%%%%%%%%%%%%%%%%%%%%%%%%%%%*********************");

							ImageAdvertisement imageAdvertisement = new ImageAdvertisement(
									screenId,
									screenAndOppData.getOpportunityId(),
									String.valueOf(currentAssetId), image,
									AdsOnUIConstants.ZERO,
									AdsOnUIConstants.ZERO,
									AdsOnUIConstants.ZERO,
									PapiConstants.IMAGE_AD, displayDuration,
									adCompanion, null, AdsOnUIConstants.ZERO,
									AdsOnUIConstants.ZERO);
							screenAndOppData.getAdvertisementListener()
									.iGotAnAdvertisement(imageAdvertisement);
						} else if (screenAndOppData.getAdCompanionType() == AdsOnUIConstants.VIDEO) {
							adCompanionURL = adsOnUIData
									.getAdCompanionVideoURL();
							if (AdsOnUILog.somethingWrong) {
								AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
										"Givin video URL :" + adCompanionURL);
							}

							// edit
							AdCompanion adCompanion = new AdCompanion(
									AdsOnUIConstants.VIDEO);
							adCompanion.setVideoURL(adCompanionURL
									+ String.valueOf(currentAssetId));

							Log.display("*************%%%%%%%%%%%%%%%%%%%%%%%%%%%*********************");
							Log.display("AdCompanionType is video.....");
							Log.display("*************%%%%%%%%%%%%%%%%%%%%%%%%%%%*********************");

							ImageAdvertisement imageAdvertisement = new ImageAdvertisement(
									screenId,
									screenAndOppData.getOpportunityId(),
									String.valueOf(currentAssetId), image,
									AdsOnUIConstants.ZERO,
									AdsOnUIConstants.ZERO,
									AdsOnUIConstants.ZERO,
									PapiConstants.VIDEO_AD, displayDuration,
									adCompanion, null, AdsOnUIConstants.ZERO,
									AdsOnUIConstants.ZERO);

							screenAndOppData.getAdvertisementListener()
									.iGotAnAdvertisement(imageAdvertisement);
						}
						displayedTime = System.currentTimeMillis();
						cacheTheNextAdvertisement(screenAndOppData, screenId,
								false);
					} else {
						AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
								"Data Object is null");
						screenAndOppData.getAdvertisementListener()
								.iGotAnAdvertisement(null);
					}
				} else {
					StickyAdvertisement stickyAdvertisement = new StickyAdvertisement(
							screenId, screenAndOppData.getOpportunityId(),
							String.valueOf(currentAssetId), image,
							AdsOnUIConstants.ZERO, AdsOnUIConstants.ZERO,
							AdsOnUIConstants.ZERO, PapiConstants.IMAGE_AD,
							adCompanionURL + String.valueOf(currentAssetId),
							null, AdsOnUIConstants.ZERO, AdsOnUIConstants.ZERO);
					displayedTime = System.currentTimeMillis();
					screenAndOppData.getAdvertisementListener()
							.iGotAnAdvertisement(stickyAdvertisement);
				}
			} else {
				screenAndOppData.getAdvertisementListener()
						.iGotAnAdvertisement(null);
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Image is null");
			}
		}
		
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager fillImageAdvertismentDetailsAndSend exit");

	}

	/**
	 * Helper method to cache the next asset to be given
	 * 
	 * @param screenAndOppData
	 * @param screenId
	 * 
	 */
	private void cacheTheNextAdvertisement(ScreenAndOppData screenAndOppData,
			int screenId, boolean current) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"cacheTheNextAdvertisement entry");
		String assetId = null;
		if (screenAndOppData.getCurrentSequence() != null
				&& screenAndOppData.getCurrentSequence().length > 0) {
			if (screenAndOppData.getDisplayPointer() < screenAndOppData
					.getCurrentSequence().length - 1) {
				if (!current) {
					assetId = screenAndOppData.getCurrentSequence()[screenAndOppData
							.getDisplayPointer() + 1];
				} else {
					assetId = screenAndOppData.getCurrentSequence()[screenAndOppData
							.getDisplayPointer()];
				}
			} else {
				if (screenAndOppData.getNextSequence() != null
						&& screenAndOppData.getNextSequence().length > 0) {
					assetId = screenAndOppData.getNextSequence()[AdsOnUIConstants.ZERO];
				}
			}
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
					"Caching next sequence advertisement" + assetId);

			if (assetId != null) {
				adsOnUIData = AdsOnUIManager.getInstance().getAdsOnUIData();
				URL imageUrl = null;
				try {
					imageUrl = new URL(adsOnUIData.getPrimaryAdImageURL()
							+ assetId);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				if (null != imageUrl) {
					CacheManager.cacheImage(
							screenAndOppData.getOpportunityId(), assetId,
							imageUrl);
				} else {
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
							"Image still not cached " + imageUrl);
					imageStillNotCached = true;
				}
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"cacheTheNextAdvertisement exit");
	}

	/**
	 * Helper method to get the {@link ScreenAndOppData} based on
	 * 
	 * passed parameters
	 * 
	 * 
	 * @param screeName
	 * @param oppurtunityId
	 * @return {@link ScreenAndOppData} or null
	 */
	private ScreenAndOppData getScreenAndOppData(
			int oppurtunityId) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager.getScreenAndOppData entry");
		if (null != screenDataList) {
			Iterator screenOppDataIterator = screenDataList.iterator();
			while (screenOppDataIterator.hasNext()) {
				ScreenAndOppData sOppData = (ScreenAndOppData) screenOppDataIterator
						.next();
				if (null != sOppData) {
//					if (sOppData.getScreenName().equals(screeName)) {
						if (sOppData.getOpportunityId() == oppurtunityId) {
							AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
									"SequenceManager.getScreenAndOppData exit");
							return sOppData;
						}
//					}
				}
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager.getScreenAndOppData exit");
		return null;
	}

	/**
	 * This method will be called when application notifies that
	 * 
	 * an advertisement has been displayed
	 * 
	 * This method starts the updation process so that next asset can be given
	 * to
	 * 
	 * the application
	 * 
	 * @param numberOfSecondsDisplayed
	 * @param theAdvertismentObjectBeingDisplayed
	 * @param adCompanionRequested
	 */
	public void notifyAdvertismentDisplayed(int numberOfSecondsDisplayed,
			Advertisement theAdvertismentObjectBeingDisplayed,
			boolean adCompanionRequested) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager.notifyAdvertismentDisplayed entry");
		String screenName = AdsOnUIConstants.UI_NAMES[theAdvertismentObjectBeingDisplayed
				.getScreenId() - 1];
		if (null != screenName) {
			ScreenAndOppData screenAndOppData = getScreenAndOppData(
					theAdvertismentObjectBeingDisplayed.getOpportunityId());
			if (screenAndOppData == null) {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
						"screenAndOppData is null");
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
						"SequenceManager.notifyAdvertismentDisplayed exit ");
				return;
			} else if (screenAndOppData.isNonTargeted()) {
				saveADTData(theAdvertismentObjectBeingDisplayed.getAssetid(),
						numberOfSecondsDisplayed, AdsOnUIConstants.VIEWED);
				sendADTDatatoServer();
				AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
						"SequenceManager.notifyAdvertismentDisplayed exit");
				return;
			} else if (!screenAndOppData.isRegistered()
					&& !adCompanionRequested) {
				sendADTDatatoServer();
			}
			if (null != screenAndOppData) {
				updateTheNextAssetTobeGiven(
						theAdvertismentObjectBeingDisplayed.getAssetid(),
						numberOfSecondsDisplayed, screenAndOppData,
						theAdvertismentObjectBeingDisplayed.getScreenId(),
						adCompanionRequested);
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager.notifyAdvertismentDisplayed exit");
	}

	/**
	 * This method updates the display pointer of the sequence
	 * 
	 * if the noOfSecondsDisplayed is equal to display time
	 * 
	 * otherwise no update is done and the asset which is displayed
	 * 
	 * will be stored as partially displayed and will be sent back to the
	 * application
	 * 
	 * 
	 * @param assetId
	 * @param noOfSecondsImageWasDisplayed
	 * @param screenAndOppData
	 * @param screenId
	 * @param adCompanionRequested
	 */
	private void updateTheNextAssetTobeGiven(String assetId,
			int noOfSecondsImageWasDisplayed,
			ScreenAndOppData screenAndOppData, int screenId,
			boolean adCompanionRequested) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager updateTheNextAssetTobeGiven entry");
		if (null != adsOnUIData && assetId != null) {
			int displayDuration = adsOnUIData.getDisplayDuration(
					
					screenAndOppData.getOpportunityId());
			/* Check If Partially Displayed asset is there */
			if (screenAndOppData.getPartiallyDisplayedAssetId() != null
					&& screenAndOppData.getRemainingTime() > 0) {
				if (noOfSecondsImageWasDisplayed == screenAndOppData
						.getRemainingTime() || adCompanionRequested) {
					screenAndOppData
							.setPartiallyDisplayedAssetId(AdsOnUIConstants.NULL);
					screenAndOppData.setRemainingTime(AdsOnUIConstants.ZERO);
					if (adCompanionRequested) {
						AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
								"User interested in ad");
						saveADTData(assetId, displayDuration,
								AdsOnUIConstants.INTERESTED);
					} else {
						AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
								"User viewed ad");
						saveADTData(assetId, displayDuration,
								AdsOnUIConstants.VIEWED);
					}
					updatePointerAndSequence(screenAndOppData, assetId,
							screenId);
				} else {
					screenAndOppData.setRemainingTime(screenAndOppData
							.getRemainingTime() - noOfSecondsImageWasDisplayed);
					adsOnUIData.setRemainingDuration(
							
							screenAndOppData.getOpportunityId(),
							screenAndOppData.getRemainingTime()
									- noOfSecondsImageWasDisplayed);
				}
			}
			/* Check if displayed time is equal to total display time */
			else if (displayDuration == noOfSecondsImageWasDisplayed
					|| adCompanionRequested) {
				if (adCompanionRequested) {
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
							"User interested in ad");
					saveADTData(assetId, displayDuration,
							AdsOnUIConstants.INTERESTED);
				} else {
					AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
							"User viewed ad");
					saveADTData(assetId, displayDuration,
							AdsOnUIConstants.VIEWED);
				}
				updatePointerAndSequence(screenAndOppData, assetId, screenId);
			}
			/* Store the partially displayed asset */
			else {
				screenAndOppData.setPartiallyDisplayedAssetId(assetId);
				screenAndOppData.setRemainingTime(displayDuration
						- noOfSecondsImageWasDisplayed);
				adsOnUIData.setPartiallyDisplayedAsset(
						
						screenAndOppData.getOpportunityId(),
						screenAndOppData.getPartiallyDisplayedAssetId());
				adsOnUIData.setRemainingDuration(
						
						screenAndOppData.getOpportunityId(),
						screenAndOppData.getRemainingTime());
			}
			if (!adCompanionRequested && !screenAndOppData.isNonTargeted()) {
				fetchAnAsset(screenId, screenAndOppData.getOpportunityId(),
						screenAndOppData.getAdvertisementListener());
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager updateTheNextAssetTobeGiven exit");
	}

	/**
	 * This method updates the current and next sequence
	 * 
	 * this will be called when all the assets in the current sequence
	 * 
	 * have been displayed completely
	 * 
	 * @param screenAndOppData
	 * @param screenId
	 */
	private void updateTheCurrentAndNextSequence(
			ScreenAndOppData screenAndOppData, int screenId) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"updateTheCurrentAndNextSequence entry");
		if (screenAndOppData.getNextSequence() != null
				&& screenAndOppData.getNextSequence().length > 0) {
			screenAndOppData.setCurrentSequence(screenAndOppData
					.getNextSequence());
			resetReplacementFlags(screenAndOppData);
			if (AdsOnUILog.somethingWrong) {
				for (int i = 0; i < screenAndOppData.getCurrentSequence().length; i++) {
					AdsOnUILog.displayLog(
							AdsOnUILog.LOGIC_TYPE,
							"Current Sequence : "
									+ screenAndOppData.getCurrentSequence()[i]);
				}
			}
			String[] nextSequence = adsOnUIData.getCurrentOrNextSequence(
					
					screenAndOppData.getOpportunityId(),
					AdsOnUIConstants.UI_SLOTS[screenId - 1], true);
			if (nextSequence != null && nextSequence.length > 0) {
				if (AdsOnUILog.somethingWrong) {
					for (int i = 0; i < nextSequence.length; i++) {
						AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
								"Next Sequence : " + nextSequence[i]);
					}
				}
				screenAndOppData.setNextSequence(nextSequence);
			} else {
				screenAndOppData.setNextSequence(null);
			}
		} else {
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Next sequence null");
			screenAndOppData.setCurrentSequence(null);
			screenAndOppData.setNextSequence(null);
			/**
			 * Send adt to server once all assets are displayed
			 */
			sendADTDatatoServer();
			AdsOnUIManager.getInstance().writeAdsOnUIDataToFlash();
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"updateTheCurrentAndNextSequence exit");
	}

	/**
	 * This method is called when application requests for
	 * 
	 * deregistration when it is quitting so that sending of ads
	 * 
	 * can be stopped
	 * 
	 * This method makes the {@link AdvertisementListener} for the
	 * 
	 * particular oppId , screenId and also updates the flash contents
	 * 
	 * @param screenId
	 * @param oppurtunityId
	 * @param adListener
	 */
	public void deregisterAdvertisementListener(int screenId,
			int oppurtunityId, AdvertisementListener adListener) {
		String screenName = AdsOnUIConstants.UI_NAMES[screenId - 1];
		if (null != screenName) {
			ScreenAndOppData screenAndOppData = getScreenAndOppData(
					oppurtunityId);
			if (null != screenAndOppData
					&& null != screenAndOppData.getAdvertisementListener()) {
				if (screenAndOppData.getAdvertisementListener().equals(
						adListener)) {
					screenAndOppData.setAdvertisementListener(null);
					screenAndOppData.setRegistered(false);
					AdsOnUIManager.getInstance().writeAdsOnUIDataToFlash();
				}
			}
		}
	}

	/**
	 * Helper method that updates the display position pointer , display pointer
	 * and starts the replacement process
	 * 
	 * and also starts caching process of next asset to be given
	 * 
	 * @param screenAndOppData
	 * @param assetId
	 * @param screenId
	 */
	private void updatePointerAndSequence(ScreenAndOppData screenAndOppData,
			String assetId, int screenId) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager updatePointerAndSequence entry");
		if (!screenAndOppData.isNonTargeted()) {
			screenAndOppData.setDisplayPostionPointer(screenAndOppData
					.getDisplayPostionPointer() + 1);
			adsOnUIData.updateDisplayPositionPointer(
					
					screenAndOppData.getOpportunityId(),
					screenAndOppData.getDisplayPostionPointer());
		}
		if (screenAndOppData.getCurrentSequence() != null
				&& screenAndOppData.getCurrentSequence().length > 0
				&& !screenAndOppData.isNonTargeted()) {
			if (imageStillNotCached) {
				imageStillNotCached = false;
				cacheTheNextAdvertisement(screenAndOppData, screenId, false);
			}
			if (screenAndOppData.getDisplayPointer() < screenAndOppData
					.getCurrentSequence().length - 1) {
				screenAndOppData.setDisplayPointer(screenAndOppData
						.getDisplayPointer() + 1);
				if (!screenAndOppData.getCurrentSequence()[screenAndOppData
						.getDisplayPointer()].equals(assetId)) {
					CacheManager.removeImage(
							screenAndOppData.getOpportunityId(), assetId);
				}
			} else {
				screenAndOppData.setDisplayPointer(AdsOnUIConstants.ZERO);
				updateTheCurrentAndNextSequence(screenAndOppData, screenId);
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager updatePointerAndSequence exit");
	}

	private void sendADTDatatoServer() {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager sendADTDatatoServer entry");
		if (AdtManager.getAdtDataList() != null
				&& AdtManager.getAdtDataList().size() > 0) {
			AdsOnUIRequestData adsOnUIRequestData = new AdsOnUIRequestData();
			AdsOnUIXMLPlacement adsOnUIXMLPlacement = new AdsOnUIXMLPlacement(
					adsOnUIRequestData, AdsOnUIConstants.ADT);
			adsOnUIXMLPlacement.sendDataToServer(this);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager sendADTDatatoServer exit");
	}

	private void saveADTData(String assetId, int displayDuration,
			String userFeedBack) {
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager saveADTData entry");
		if (assetId != null && displayDuration != 0) {
			AdtManager.addAdtInfo(userFeedBack, assetId, displayedTime,
					displayDuration);
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,
				"SequenceManager saveADTData exit");
	}

	public void communicationDone(int type, int result, Object obj) {
		if (type == AdsOnUIConstants.ADT) {
			if (result == AdsOnUIConstants.ZERO) {
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
						"Sucess sending adt");
				AdtManager.clearAdtData();
			} else {
				AdtManager.saveAdtDataToFile();
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
						"Failure sending adt");
			}
		}
	}

	private Opportunity getOpportunity( int opportunityId) {
		//UIScreen uiScreen = adsOnUIData.getUIScreen(screenName);
		Opportunity uiOpportunity = adsOnUIData.getUIOpportunity(opportunityId);
		if (uiOpportunity != null) {
			return uiOpportunity;
		}
		return null;
	}
}
