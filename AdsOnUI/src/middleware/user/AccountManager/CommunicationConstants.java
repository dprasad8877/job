package middleware.user.AccountManager;

import Application.cloud.config.SettingsFileInterface;

public class CommunicationConstants
{
	public static final int TIME_OUT_PERIOD = 1000;
	public static String urlPath = "NA";
	static
	{
		try
		{
			urlPath = SettingsFileInterface.getInstance().getBaseURL()
			        + "/SubscriberProfile/response?httpRequest=";
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
