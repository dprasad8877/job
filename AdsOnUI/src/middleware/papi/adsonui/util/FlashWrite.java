/**
 *@Purpose:
 Copies file from URL and saves it locally in flash.	
 *@Input:
 Source URL: from where the file needs to be copied from
 *@Output:
 Destination File : The File to which the contents of URL Copied to.

 *@Author: Sandeep N.
 */
package middleware.papi.adsonui.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FlashWrite
{
	public static void SaveImage(URL SourceURL, String DestinationFile) throws IOException
	{
		/*
		 * TODO: Check the validity of Destination File.
		 */
		// Destination File: OK
		File OutPutFile = new File(DestinationFile);
		InputStream input;
		URLConnection urlConnection = SourceURL.openConnection();
		input = urlConnection.getInputStream();
		OutputStream out = new FileOutputStream(OutPutFile);
		byte[] buffer = new byte[1024];
		int len;
		while ((len = input.read(buffer)) > 0)
		{
			out.write(buffer, 0, len);
		}
		input.close();
		out.close();
	}
}
