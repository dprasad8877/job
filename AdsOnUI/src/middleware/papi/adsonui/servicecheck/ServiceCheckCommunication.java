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
package middleware.papi.adsonui.servicecheck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import middleware.papi.adsonui.glue.AdsOnUIGlue;

import org.safehaus.uuid.UUID;

/**
 * This class is responsible for Communication
 * 
 * @author NAGESH M H
 * 
 */
public class ServiceCheckCommunication
{
	/**
	 * variable===>"finalData" will have XML which has to be sent to the server
	 * 
	 * @author NAGESH M H
	 */
	private String finalData;
	private UUID uid;

	// private String xmlStr =new String("<Personnel><Employee
	// type=\"permanent\"><Name>NAGESH</Name><Id>10318</Id><Age>22</Age></Employee><Employee
	// type=\"contract\"><Name>Robin</Name><Id>3675</Id><Age>25</Age></Employee><Employee
	// type=\"permanent\"><Name>Sandeep</Name><Id>3676</Id><Age>28</Age></Employee></Personnel>");
	/**
	 * Constructor of communication module
	 * 
	 * @param finalData
	 * @param tempid
	 */
	public ServiceCheckCommunication(String finalData, UUID tempid)
	{
		this.finalData = finalData;
		this.uid = tempid;
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
		// CommunicationConstants commobj =
		// CommunicationConstants.getInstance();
		if (retString == null)
		{
			return false;
		}
		try
		{
			String data = URLEncoder.encode("request", "UTF-8") + "="
			        + URLEncoder.encode(finalData.toString(), "UTF-8") + "&"
			        + URLEncoder.encode("messageId", "UTF-8") + "="
			        + URLEncoder.encode(uid.toString(), "UTF-8");
			URL url = new URL(AdsOnUIGlue.getServiceCheckURl());
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			// ======Send data==================
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			// =================================
			// =======Get the response==========
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
			{
				// Process line...
				retString.append(line);
			}
			wr.close();
			rd.close();
			return true;
		}
		catch (Exception e)
		{
		}
		return false;
	}
}
