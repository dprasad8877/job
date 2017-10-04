package middleware.papi.adsonui.util;
/**
 * Purpose:
 * Class to write an object into file 
 * @author Balaji Muralidhar
 *
 *Input  : Location to where it should write
 *
 *Output : None
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Class to write and object into file
 * 
 * @author Balaji Muralidhar
 * 
 */
public class ObjectWriter
{
	private ObjectOutputStream objectOutputStream;
	private FileOutputStream fileOutputStream;
	private String location;

	public ObjectWriter(String location)
	{
		this.location = location;
	}

	/**
	 * Method to write an object into file
	 * 
	 * @param obj
	 */
	public void writeObject(Object obj)
	{
		try
		{
			fileOutputStream = new FileOutputStream(location);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			objectOutputStream.close();
			fileOutputStream.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
