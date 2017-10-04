package middleware.papi.adsonui;
/**
 * Purpose : 
 * Class that extends {@link AssetData} which is used to 
 * save the cached image data based on opportunity id and screen name
 * @author Balaji Muralidhar
 *
 *Input : 	1) screenName
 *			2)opportunityId
 *			3)assetId
 *			4)image
 *
 *Output : None 
 */
import java.awt.Image;


public class CacheData extends AssetData
{
	private Image image;

	public CacheData( int opportunityId, String assetId, Image image)
	{
		super( opportunityId, assetId);
		this.image = image;
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}
}
