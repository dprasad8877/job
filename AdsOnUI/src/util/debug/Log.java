package Util.Debug;

public class Log
{
	public static void display(String message)
	{
		if (DebugConstants.debugFlag)
		{
			System.out.println("\n " + message + " \n");
		}
	}
}
