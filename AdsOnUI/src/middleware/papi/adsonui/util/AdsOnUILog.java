package middleware.papi.adsonui.util;

/**
 * Class to display logs
 * 
 * @author Balaji Muralidhar
 * 
 */
public class AdsOnUILog
{
	private static boolean enableLog = true;
	private static boolean entryExitLog = true;
	private static boolean logicLog = true;
	public static int ENTRY_EXIT_TYPE = 0;
	public static int LOGIC_TYPE = 1;
	public static final boolean somethingWrong = true;

	/**
	 * 
	 * @param type
	 * @param msg
	 */
	public static void displayLog(int type, String msg)
	{
		if (enableLog)
		{
			if (type == ENTRY_EXIT_TYPE && entryExitLog)
			{
				System.out.println("\n" + msg);
			}
			else if (type == LOGIC_TYPE && logicLog)
			{
				System.out.println("\n" + msg);
			}
		}
	}
}
