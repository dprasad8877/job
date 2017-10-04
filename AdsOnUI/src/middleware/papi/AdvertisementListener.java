package middleware.papi;

/**
 * Its an interface that an application will implement so that
 * 
 * it can receive {@link Advertisement} when registered for it
 * 
 * @author Balaji Muralidhar
 * 
 */
public interface AdvertisementListener
{
	/**
	 * This is the method which will be called by the middleware
	 * 
	 * when an application registers for an advertisement with the middleware
	 * 
	 * the {@link Advertisement} object is sent as a parameter to it which
	 * 
	 * contains the asset information
	 * 
	 * @param TheAdvertisement
	 */
	public void iGotAnAdvertisement(Advertisement TheAdvertisement);
}
