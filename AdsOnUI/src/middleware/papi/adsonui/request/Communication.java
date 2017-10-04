/**
 *
 * @author NAGESH M H
 *
 * Purpose:This class is responsible for Communication with server.
 *
 * Input: XML data and unique Id.
 *
 * Output:Response from the Server(String)
 *
 */
package middleware.papi.adsonui.request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.constants.CommunicationConstants;
import middleware.papi.adsonui.glue.AdsOnUIGlue;
import middleware.papi.adsonui.util.AdsOnUILog;

import org.safehaus.uuid.UUID;

/**
 * This class is responsible for Communication
 * 
 * @author NAGESH M H
 * 
 */
public class Communication
{
	/**
	 * variable===>"finalData" will have XML which has to be sent to the server
	 * 
	 * @author NAGESH M H
	 */
	private String finalData;
	private UUID uid;
	private int requestType;

	/**
	 * Constructor of communication module
	 * 
	 * @param finalData
	 * @param tempid
	 */
	public Communication(String finalData, UUID tempid, int requestType)
	{
		if (CommunicationConstants.debugFlag)
		{
			System.out.println("communication module constructor");
		}
		this.finalData = finalData;
		this.uid = tempid;
		this.requestType = requestType;
	}

	public String getFinalData()
	{
		return finalData;
	}

	public void setFinalData(String finalData)
	{
		this.finalData = finalData;
	}

	/**
	 * @author NAGESH M H
	 * 
	 *         This function is responsible for sending data to the server. It
	 *         takes StringBuffer variable as a parameter(out parameter) out
	 *         parameter contains response given by the server if everything
	 *         works well else it contains null
	 * 
	 *         If (return value is true) { Data was sent and out parameter
	 *         contains response } else { Function failed and out parameter
	 *         contains null }
	 * @param retString
	 * @return boolean value
	 */
	public boolean sendAndFillResultIn(StringBuffer retString)
	{
		CommunicationConstants commobj = CommunicationConstants.getInstance();
		if (retString == null)
		{
			return false;
		}
		try
		{
			String data = URLEncoder.encode("httpRequest", "UTF-8") + "="
			        + URLEncoder.encode(finalData.toString(), "UTF-8") + "&"
			        + URLEncoder.encode("messageId", "UTF-8") + "="
			        + URLEncoder.encode(uid.toString(), "UTF-8");
			String urlString = null;
			if (requestType == AdsOnUIConstants.REPEAT || requestType == AdsOnUIConstants.COMPLETE)
			{
				urlString = AdsOnUIGlue.getAdsOnUIURL();
			}
			else if (requestType == AdsOnUIConstants.ADT)
			{
				urlString = AdsOnUIGlue.getAdsOnUIADTUrl();
			}
			else if (requestType == AdsOnUIConstants.QUIZ)
			{
				urlString = AdsOnUIGlue.getQuizAdtURL();
			}
			if (urlString == null)
			{
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "URL string is null");
				urlString = commobj.returnAdsOnUIRequestUrl();
			}
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "======Ads On UI url is===" + urlString
			        + requestType);
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			// ======Send data==================
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			// =======Get the response==========
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
			{
				retString.append(line);
			}
			wr.close();
			rd.close();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
