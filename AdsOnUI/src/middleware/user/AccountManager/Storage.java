package middleware.user.AccountManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import Util.Debug.Log;


public class Storage
{
	public static final String filePath = "resources/Middleware/user_account/user_list";

	public static synchronized void write(ArrayList userList)
	{
		if (userList == null)
		{
			throw new IllegalArgumentException("Parameter is null");
		}
		// before writing , delete the contents if it exist
		File file = new File(filePath);
		if (file.exists() == true)
		{
			Log.display("file exists so i will delete old and write new");
			file.delete();
		}
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream oos = null;
		try
		{
			fileOutputStream = new FileOutputStream(filePath);
			oos = new ObjectOutputStream(fileOutputStream);
			Iterator it = userList.iterator();
			while (it.hasNext())
			{
				UserDetails tempData = (UserDetails) it.next();
				oos.writeObject(tempData);
			}
			oos.writeObject(null);
			oos.flush();
			fileOutputStream.flush();
		}
		catch (Exception e)
		{
			Log.display(" Exception occurred in write function  ..." + e.getMessage());
		}
		finally
		{
			if (fileOutputStream != null)
			{
				try
				{
					fileOutputStream.close();
				}
				catch (IOException e)
				{
					Log.display(" Exception occurred in closing fileoutputStream   ..."
					        + e.getMessage());
				}
			}
			if (oos != null)
			{
				try
				{
					oos.close();
				}
				catch (IOException e)
				{
					Log.display(" Exception occurred in closing ObjectOutput Stream   ..."
					        + e.getMessage());
				}
			}
		}
	}

	public static synchronized ArrayList readUserList()
	{
		File file = new File(filePath);
		if (file.exists() == false)
		{
			Log.display("Storage module ==> readUserList() ==> File doesn't exists.... so i will return null");
			return null;
		}
		FileInputStream fileInputStream = null;
		ObjectInputStream ois = null;
		ArrayList list = new ArrayList();
		try
		{
			fileInputStream = new FileInputStream(file);
			ois = new ObjectInputStream(fileInputStream);
			UserDetails tempData = null;
			while ((tempData = (UserDetails) ois.readObject()) != null)
			{
				list.add(tempData);
			}
			return list;
		}
		catch (Exception e)
		{
			Log.display(" Exception occurred in readUserList function..." + e.getMessage());
		}
		finally
		{
			Log.display(" Executing finally block in storage module\n");
			if (fileInputStream != null)
			{
				try
				{
					fileInputStream.close();
				}
				catch (IOException e)
				{
					Log.display(" Exception occurred in readUserList function(while closing fileInputStream)..."
					        + e.getMessage());
				}
			}
			if (ois != null)
			{
				try
				{
					ois.close();
				}
				catch (IOException e)
				{
					Log.display(" Exception occurred in readUserList function(while closing ObjectInputStream obj)..."
					        + e.getMessage());
				}
			}
		}
		return null;
	}
}
