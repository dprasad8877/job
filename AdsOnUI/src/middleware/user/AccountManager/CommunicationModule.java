package middleware.user.AccountManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import Util.Debug.Log;


public class CommunicationModule
{
	private String xml;

	public CommunicationModule(String xml)
	{
		this.xml = xml;
	}

	public String sendRequestAndReceiveResponseFromServer()
	{
		String response = "";
		URLConnection openConnection = null;
		BufferedReader br = null;
		OutputStreamWriter outPutStream = null;
		try
		{
			xml = URLEncoder.encode(xml, "UTF-8");
			URL url = new URL(CommunicationConstants.urlPath + xml);
			openConnection = url.openConnection();
			// openConnection
			// .setConnectTimeout(CommunicationConstants.TIME_OUT_PERIOD);
			openConnection.setDoOutput(true);
			openConnection.setDoInput(true);
			// To send
			// outPutStream = new OutputStreamWriter(
			// openConnection.getOutputStream());
			// outPutStream.write(data);
			// outPutStream.flush();
			// To obtain the response
			br = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null)
			{
				response += line;
			}
			Log.display("returned correct response when there is no exception ....");
			return response;
		}
		catch (NoRouteToHostException e)
		{
			response = null;
		}
		catch (IOException e)
		{
			response = null;
		}
		catch (Exception e)
		{
			response = null;
		}
		finally
		{
			Log.display(" I am in finally block");
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (outPutStream != null)
			{
				try
				{
					outPutStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return response;
	}
}
