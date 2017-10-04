package Application.cloud.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SettingsFileInterface
{
	private static SettingsFileInterface instance;
	private static Properties prop = new Properties();
	private static SettingData fromFile = new SettingData();
	
	//final private static String propertiesFilePath = "resources/Middleware/papi/tAdsServerConfiguration.properties";
	final private static String propertiesFilePath = "resources/config.properties";

	private SettingsFileInterface()
	{
	}

	public static SettingsFileInterface getInstance() throws FileNotFoundException, IOException
	{
		if (instance == null)
		{
			//InputStream input = null;
			//input = new FileInputStream("resources/config.properties");
			prop.load((new FileInputStream(propertiesFilePath)));
			//prop.load((new FileInputStream("resources/Middleware/papi/tAdsServerConfiguration.properties")));
			//prop.load(input);
			
			fromFile.poisURL = prop.getProperty("POIS_URL");
			fromFile.whoAMIURL = prop.getProperty("WHOIAM_URL");
			fromFile.subScriberID = prop.getProperty("SUBSCRIBER_ID");
			fromFile.periodicStatisticalEngineFrequency = prop
			        .getProperty("FREQUENCY_PERODIC_STAT_ENGINE");
			fromFile.BaseURL = prop.getProperty("BASE_URL");
			fromFile.PeriodicStatisticalEngineUrl = prop
			        .getProperty("PERIODIC_STATISTICAL_ENGINE_URL");
			fromFile.periodicStatisticalEngineFrequency = prop
			        .getProperty("FREQUENCY_PERODIC_STAT_ENGINE");
			fromFile.adsOnUIURL = prop.getProperty("ADS_ON_UI_URL");
			
			//System.out.println("<<<<<<<<<<<Testing>>>>>>>>>>>"+prop.getProperty("POIS_URL"));
			
			fromFile.poisURL = "/PoisAdt/placement";
			fromFile.whoAMIURL = "/Who_am_I/response";
			fromFile.subScriberID = "P1000000";
			fromFile.periodicStatisticalEngineFrequency = "60";
			fromFile.BaseURL = "http://localhost:9000";
			fromFile.PeriodicStatisticalEngineUrl = "/PeriodicStatisticalEngineData/periodicSERequest";
			fromFile.periodicStatisticalEngineFrequency = "60";
			fromFile.adsOnUIURL = "/AdsOnUI/adsOnUIReq";
			
			
			
			
			
			
			instance = new SettingsFileInterface();
		}
		return instance;
	}

	public String getPeriodicStatisticalEngineFrequency()
	{
		return fromFile.periodicStatisticalEngineFrequency;
	}

	public String getPeriodicStatisticalEngineUrl()
	{
		return fromFile.PeriodicStatisticalEngineUrl;
	}

	public String getBaseURL()
	{
		return fromFile.BaseURL;
	}

	public String getPoisURL()
	{
		return fromFile.poisURL;
	}

	public String getWhoAMIURL()
	{
		return fromFile.whoAMIURL;
	}

	public String getSubScriberID()
	{
		return fromFile.subScriberID;
	}

	public String getADSOnUIURL()
	{
		return fromFile.adsOnUIURL;
	}

	public void setPeriodicStatisticalEngineFrequency(String periodicStatisticalEngineFrequency)
	{
		fromFile.periodicStatisticalEngineFrequency = periodicStatisticalEngineFrequency;
	}

	public void setPeriodicStatisticalEngineUrl(String periodicStatisticalEngineUrl)
	{
		fromFile.PeriodicStatisticalEngineUrl = periodicStatisticalEngineUrl;
	}

	public void setBaseURL(String baseURL)
	{
		fromFile.BaseURL = baseURL;
	}

	public void setPoisURL(String poisURL)
	{
		fromFile.poisURL = poisURL;
	}

	public void setWhoAMIURL(String whoAMIURL)
	{
		fromFile.whoAMIURL = whoAMIURL;
	}

	public void setSubScriberID(String subScriberID)
	{
		fromFile.subScriberID = subScriberID;
	}

	public void setAdsOnUIURL(String URL)
	{
		fromFile.adsOnUIURL = URL;
	}

	public void saveSettingsToFile() throws IOException
	{
		prop.setProperty("BASE_URL", fromFile.BaseURL);
		prop.setProperty("POIS_URL", fromFile.poisURL);
		prop.setProperty("FREQUENCY_PERODIC_STAT_ENGINE",
		        fromFile.periodicStatisticalEngineFrequency);
		prop.setProperty("PERIODIC_STATISTICAL_ENGINE_URL", fromFile.PeriodicStatisticalEngineUrl);
		prop.setProperty("FREQUENCY_PERODIC_STAT_ENGINE",
		        fromFile.periodicStatisticalEngineFrequency);
		prop.setProperty("WHOIAM_URL", fromFile.whoAMIURL);
		prop.setProperty("SUBSCRIBER_ID", fromFile.subScriberID);
		prop.setProperty("ADS_ON_UI_URL", fromFile.adsOnUIURL);
		FileOutputStream propertyFileToBeSaved = new FileOutputStream(propertiesFilePath);
		prop.store(propertyFileToBeSaved, null);
		propertyFileToBeSaved.close();
	}
}

class SettingData
{
	public String periodicStatisticalEngineFrequency;
	public String PeriodicStatisticalEngineUrl;
	public String BaseURL;
	public String poisURL;
	public String whoAMIURL;
	public String subScriberID;
	public String adsOnUIURL;

	public String toString()
	{
		StringBuffer toString = new StringBuffer();
		toString.append("\nPOIS URL " + poisURL);
		toString.append("\nWHO AM I URL " + whoAMIURL);
		toString.append("\n BaseURL " + BaseURL);
		toString.append("\n PeriodicStatisticalEngineUrl " + PeriodicStatisticalEngineUrl);
		toString.append("\n periodicStatisticalEngineFrequency "
		        + periodicStatisticalEngineFrequency);
		toString.append("\n Subscriber ID " + subScriberID + "\n");
		return toString.toString();
	}
}
