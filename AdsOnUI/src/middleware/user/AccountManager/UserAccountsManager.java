package middleware.user.AccountManager;

import java.util.ArrayList;

import Util.Debug.Log;


public class UserAccountsManager
{
	// stores favourite channels of all user.
	private ArrayList userList;
	private static UserAccountsManager myObj;
	private UserDetails currentUserDetailsObj;
	private static final int startUserId = 1;
	private int uniqueNumber;

	private UserAccountsManager()
	{
		// userList.add(new UserDetails(1, "Nagesh", "1989-09-07",
		// UserDetailsConstants.MALE, false, true, "less than 15000",
		// "abc@gmail.com","kannada,English","India","Karnataka","Bangalore"));
		//
		// userList.add(new UserDetails(2, "Sandeep", "1989-08-18",
		// UserDetailsConstants.MALE, false, true, "less than 15000",
		// "xyz@gmail.com","kannada,English","India","Karnataka","Bangalore"));
		//
		// userList.add(new UserDetails(3, "Pavithra", "1990-12-11",
		// UserDetailsConstants.FEMALE, false, true, "less than 15000",
		// "xyz@gmail.com","kannada,English","India","Karnataka","Bangalore"));
		//
		// userList.add(new UserDetails(4, "Kavyshree", "1990-08-18",
		// UserDetailsConstants.FEMALE, false, true, "less than 15000",
		// "xyz@gmail.com","kannada,English","India","Karnataka","Bangalore"));
		userList = Storage.readUserList();
		if (userList != null)
		{
			if (userList.size() <= 0)
			{
				userList.add(new UserDetails(startUserId, "General", "1989-09-07",
				        UserDetailsConstants.MALE, false, true, "less than 15000", "abc@gmail.com",
				        "kannada,English", "India", "Karnataka", "Bangalore"));
				uniqueNumber = startUserId + 1;
			}
			else
			{
				// find the unique number
				int max = ((UserDetails) userList.get(0)).getUserId();
				for (int i = 1; i < userList.size(); i++)
				{
					if (((UserDetails) userList.get(i)).getUserId() > max)
					{
						max = ((UserDetails) userList.get(i)).getUserId();
					}
				}
				uniqueNumber = max + 1;
			}
		}
		else
		{
			userList = new ArrayList();
			userList.add(new UserDetails(startUserId, "General", "1989-09-07",
			        UserDetailsConstants.MALE, false, true, "less than 15000", "abc@gmail.com",
			        "kannada,English", "India", "Karnataka", "Bangalore"));
			uniqueNumber = startUserId + 1;
		}
		currentUserDetailsObj = (UserDetails) userList.get(0);
	}

	public static UserAccountsManager getInstance()
	{
		if (myObj == null)
		{
			myObj = new UserAccountsManager();
		}
		return myObj;
	}

	public ArrayList getListOfUsers()
	{
		return userList;
	}

	public int getCurrentUserId()
	{
		return currentUserDetailsObj.getUserId();
	}

	/**
	 * 
	 * @return
	 * 
	 *         UserDetails object
	 * 
	 * @since version 1.0
	 * 
	 * @see UserDetails
	 */
	public UserDetails getCurrentuserDetails()
	{
		return currentUserDetailsObj;
	}

	/**
	 * 
	 * @param userID
	 * 
	 * <br/>
	 *            user id <br/>
	 * 
	 * @return
	 * 
	 * <br/>
	 *         UserDetails object or null<br/>
	 * 
	 * @since version 1.0
	 * 
	 * @see UserDetails
	 */
	public UserDetails getUserDetails(int userID)
	{
		for (int i = 0; i < userList.size(); i++)
		{
			if (((UserDetails) userList.get(i)).getUserId() == userID)
			{
				return (UserDetails) userList.get(i);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param userID
	 * 
	 *            if the user id is not valid then IllegalArgumentException will
	 *            be thrown
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @since version 1.0
	 */
	public void setCurrentuser(int userID)
	{
		for (int i = 0; i < userList.size(); i++)
		{
			if (((UserDetails) userList.get(i)).getUserId() == userID)
			{
				currentUserDetailsObj = (UserDetails) userList.get(i);
				return;
			}
		}
		throw new IllegalArgumentException("Invalid Used ID");
	}

	/**
	 * 
	 * @param userObj
	 * 
	 *            Duplicate entries are not allowed
	 * 
	 * @see UserDetails
	 * 
	 * @since version 1.0
	 */
	public void addUserObjectToList(UserDetails userObj)
	{
		if (userObj == null)
		{
			throw new IllegalArgumentException(
			        " Parameter (userObj) of addUserObjectToList func is null... plz check");
		}
		if (userList.contains(userObj))
		{
			// ignore
			return;
		}
		userList.add(userObj);
		uniqueNumber++;
	}

	public int getNextIdNumber()
	{
		Log.display(" user Id which i am going to return is ==> " + uniqueNumber);
		return uniqueNumber;
	}

	/**
	 * 
	 * @param userObj
	 * 
	 * @see UserDetails
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * <br/>
	 *             if the userObject (parameter) is same as current user <br/>
	 *             <i>Note: U can't delete current user</i><br/>
	 * 
	 * @since version 1.0
	 */
	public void removeUserObjectToList(UserDetails userObj)
	{
		if (userObj == null)
		{
			throw new IllegalArgumentException(
			        " Parameter (userObj) of removeUserObjectToList func is null... plz check");
		}
		if (userList.contains(userObj))
		{
			if (userObj.getUserId() == currentUserDetailsObj.getUserId())
			{
				throw new IllegalArgumentException("U can't delete current user");
			}
			else
			{
				userList.remove(userObj);
			}
		}
	}

	public void saveToStorage()
	{
		Storage.write(getListOfUsers());
	}
}
