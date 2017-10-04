package middleware.papi.adsonui.util;
/**
 * purpose:
 * Class to read an object from file 
 * @author Balaji Muralidhar
 * 
 *Input : Location from where it should read
 *
 *Output : None
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class to read an object from file
 * 
 * @author Balaji Muralidhar
 * 
 */
public class ObjectReader
{
	private ObjectInputStream objectInputStream;
	private FileInputStream fileInputStream;
	private String location;

	public ObjectReader(String location)
	{
		this.location = location;
	}

	/**
	 * Method to read an object from file
	 * 
	 * @return {@link Object}
	 * 
	 */
	public Object readObject()
	{
		Object object;
		try
		{
			if (location == null)
			{
				return null;
			}
			File file = new File(location);
			if (!file.exists())
			{
				return null;
			}
			fileInputStream = new FileInputStream(location);
			objectInputStream = new ObjectInputStream(fileInputStream);
			object = objectInputStream.readObject();
			return object;
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		finally
		{
			try
			{
				if (objectInputStream != null && fileInputStream != null)
				{
					objectInputStream.close();
					fileInputStream.close();
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
