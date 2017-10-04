package middleware.papi.adsonui.glue;
/**
 * Purpose : This class contains all the information such as
 * 1)subscriberId
 * 2)ipAdress
 * 3)quizAdtURL
 * 4)adsOnUIURL
 * 5)serviceCheckURl
 * 6)setConfigLocation
 * 7)clientType
 * 	It contains getters and setters to get and set the above parameters
 * 
 * Input  : None
 * 
 * Output : None
 * 
 */
import java.io.FileNotFoundException;
import java.io.IOException;

import middleware.papi.adsonui.constants.AdsOnUIConstants;

import Application.cloud.config.SettingsFileInterface;

public class AdsOnUIGlue {
	
	private static String subscriberId;
	
	private static String ipAdress;
	
	private static String portNo;
	
	private static String adsOnUIURL;
	
	private static String adsOnUIADTUrl;
	
	private static String quizAdtURL;
	
	private static String serviceCheckURl;
	
	private static String setConfigLocation;
	
	private static String clientType;
	

	private static SettingsFileInterface settingsFileInterface;
	
	static 
	{
		System.out.println("Static init ");
		
		try {
			settingsFileInterface = SettingsFileInterface.getInstance();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		subscriberId = settingsFileInterface.getSubScriberID();
		
		adsOnUIURL = settingsFileInterface.getBaseURL()+settingsFileInterface.getADSOnUIURL();
		
		adsOnUIADTUrl = settingsFileInterface.getBaseURL()+"/AdsOnUIADT/adsOnUIADTReq";
		
		quizAdtURL = settingsFileInterface.getBaseURL()+AdsOnUIConstants.quizAdtURL;
		
		serviceCheckURl = settingsFileInterface.getBaseURL()+"/ServiceCheck/request";
		
		System.out.println("****ADS onUI URL"+adsOnUIURL);
		
		
	}


	public static String getAdsOnUIADTUrl() {
		return adsOnUIADTUrl;
	}

	public static void setAdsOnUIADTUrl(String adsOnUIADTUrl) {
		AdsOnUIGlue.adsOnUIADTUrl = adsOnUIADTUrl;
	}

	public static String getSubscriberId() {
		return subscriberId;
	}

	public static void setSubscriberId(String subscriberId) {
		AdsOnUIGlue.subscriberId = subscriberId;
	}

	public static String getIpAdress() {
		return ipAdress;
	}

	public static void setIpAdress(String ipAdress) {
		AdsOnUIGlue.ipAdress = ipAdress;
	}

	public static String getPortNo() {
		return portNo;
	}

	public static void setPortNo(String portNo) {
		AdsOnUIGlue.portNo = portNo;
	}

	public static String getAdsOnUIURL() {
		return adsOnUIURL;
	}

	public static void setAdsOnUIURL(String adsOnUIURL) {
		AdsOnUIGlue.adsOnUIURL = adsOnUIURL;
	}

	public static String getQuizAdtURL() {
		return quizAdtURL;
	}

	public static void setQuizAdtURL(String quizAdtURL) {
		AdsOnUIGlue.quizAdtURL = quizAdtURL;
	}

	public static String getServiceCheckURl() {
		return serviceCheckURl;
	}

	public static void setServiceCheckURl(String serviceCheckURl) {
		AdsOnUIGlue.serviceCheckURl = serviceCheckURl;
	}

	public static String getSetConfigLocation() {
		return setConfigLocation;
	}

	public static void setSetConfigLocation(String setConfigLocation) {
		AdsOnUIGlue.setConfigLocation = setConfigLocation;
	}
	
	public static String getClientType() {
		return clientType;
	}

	public static void setClientType(String clientType) {
		AdsOnUIGlue.clientType = clientType;
	}

	
	
	
	

}
