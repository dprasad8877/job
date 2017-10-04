package middleware.user.AccountManager;

import java.io.Serializable;
import java.util.ArrayList;

import middleware.sidatamanage.ChannelInformation;

public class UserDetails implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	private String dob;
	private int gender;
	private boolean married;
	private boolean employed;
	private String income;
	private String emailId;
	private String languagesKnown;
	private String country;
	private String state;
	private String city;
	// Stores favourite channels of user.
	private ArrayList listOfFavourites = new ArrayList();

	public UserDetails(int userId, String userName, String dob, int gender, boolean married,
	        boolean employed, String income, String emailId, String lang, String country,
	        String state, String city)
	{
		this.userId = userId;
		this.userName = userName;
		this.dob = dob;
		this.gender = gender;
		this.married = married;
		this.employed = employed;
		this.income = income;
		this.emailId = emailId;
		this.languagesKnown = (lang != null && lang.length() > 0) ? lang
		        : UserDetailsConstants.NOT_AVAILABALE;
		this.country = (country != null) ? country : UserDetailsConstants.NOT_AVAILABALE;
		this.state = (state != null) ? state : UserDetailsConstants.NOT_AVAILABALE;
		this.city = (city != null) ? city : UserDetailsConstants.NOT_AVAILABALE;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getDob()
	{
		return dob;
	}

	public void setDob(String dob)
	{
		this.dob = dob;
	}

	public int getGender()
	{
		return gender;
	}

	public void setGender(int gender)
	{
		this.gender = gender;
	}

	public boolean isMarried()
	{
		return married;
	}

	public void setMarried(boolean married)
	{
		this.married = married;
	}

	public boolean isEmployed()
	{
		return employed;
	}

	public void setEmployed(boolean employed)
	{
		this.employed = employed;
	}

	public String getIncome()
	{
		return income;
	}

	public void setIncome(String income)
	{
		this.income = income;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getLanguagesKnown()
	{
		return languagesKnown;
	}

	public void setLanguagesKnown(String languagesKnown)
	{
		this.languagesKnown = languagesKnown;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public ArrayList getListOfFavourites()
	{
		return listOfFavourites;
	}

	public void setListOfFavourites(ArrayList listOfFavourites)
	{
		this.listOfFavourites = listOfFavourites;
	}

	public int getUserId()
	{
		return userId;
	}

	/**
	 * 
	 * @param chInfo
	 * 
	 * 
	 */
	public void addToFavourites(ChannelInformation chInfo)
	{
		if (chInfo == null)
		{
			throw new IllegalArgumentException(
			        " Parameter (chInfo) of addToFavourites function is null");
		}
		if (listOfFavourites.contains(chInfo))
		{
			return;
		}
		listOfFavourites.add(chInfo);
	}

	/**
	 * 
	 * @param chInfo
	 * 
	 */
	public void removeFromFavorites(ChannelInformation chInfo)
	{
		if (chInfo == null)
		{
			throw new IllegalArgumentException(
			        " Parameter (chInfo) of removeFromFavorites function is null");
		}
		if (listOfFavourites.contains(chInfo))
		{
			listOfFavourites.remove(chInfo);
		}
		// else do nothing
	}

	public ArrayList getListOfAllFavorites()
	{
		return listOfFavourites;
		// Note: u can clone and give
	}

	/**
	 * This function is used to check whether the channel is favourite or not.
	 * 
	 * @param channelNumber
	 * @return If the passed ChannelInformation is present in favouritesList
	 *         then it returns true else it returns false.
	 */
	public boolean isFavorite(ChannelInformation chInfo)
	{
		if (chInfo == null)
		{
			throw new IllegalArgumentException(" Parameter (chInfo) of isFavorite function is null");
		}
		if (listOfFavourites.contains(chInfo))
		{
			return true;
		}
		// else return false
		return false;
	}

	public String toString()
	{
		String returnStr = " User Name : " + userName + "\n User Id : " + userId + "\n\n";
		return returnStr;
	}
}
