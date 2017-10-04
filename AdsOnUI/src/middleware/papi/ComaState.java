package middleware.papi;

public class ComaState
{
	public static void goToComa()
	{
		Config.papiEnable = true;
	}

	public static void recoverFromComa()
	{
		Config.papiEnable = false;
	}

	public static boolean isInComaState()
	{
		return false;
	}
}
