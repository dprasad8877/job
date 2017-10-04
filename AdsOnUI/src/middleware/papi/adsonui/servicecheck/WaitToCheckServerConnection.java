package middleware.papi.adsonui.servicecheck;
/**
 * Purpose : This class is used to wait for server 
 * connection.
 * 
 * Input  : None
 * 
 * Output : None
 * 
 */
import java.util.Timer;
import java.util.TimerTask;

import middleware.papi.adsonui.AdsOnUIManager;

public class WaitToCheckServerConnection extends Timer
{
	public WaitToCheckServerConnection()
	{
		// TODO Auto-generated constructor stub
	}

	public void startTimer(long delay, final int type)
	{
		schedule(new TimerTask() {
			public void run()
			{
				// TODO Auto-generated method stub
				AdsOnUIManager.getInstance().sendConnectionRequest(type);
			}
		}, delay);
	}
}
