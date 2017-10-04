
/**
 * Purpose : This class is used to obtain the width and height of the screen
 * 
 * Input   : None
 * 
 * Output  : None
 * 
 */
import java.awt.Dimension;
import java.awt.Toolkit;


public class ScreenResolution {

	private static Dimension screenSize;

	static
	{
		screenSize=Toolkit.getDefaultToolkit().getScreenSize();

	}

	public static final int width=screenSize.width;

	public static final int height=screenSize.height;


}
