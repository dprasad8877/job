package middleware.papi.adsonui;

/**
 * Purpose :
 * Exception which is thrown when a UI is not found 
 * in the stored data 
 * 
 * @author Balaji Muralidhar
 * 
 * Input  : None
 * 
 * Output : None
 *
 */
public class UINotFoundException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UINotFoundException()
	{
		// TODO Auto-generated constructor stub
	}

	public UINotFoundException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}
}
