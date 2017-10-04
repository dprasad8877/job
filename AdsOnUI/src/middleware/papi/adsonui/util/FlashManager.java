package middleware.papi.adsonui.util;

import java.io.File;
import java.io.IOException;

/**
 * Purpose:Used to save the sequence and display pointer in HDD
 * 
 * @author Balaji Muralidhar
 * 
 */
public class FlashManager implements Runnable
{
	// private static String URL;
	// private static String destination;
	/**
	 * 
	 * This method checks the number of free slots available
	 * 
	 * for an opportunity of an UI and if available returns true
	 * 
	 * if not false
	 * 
	 * @param location
	 * @param maxslots
	 * @return
	 */
	/*
	 * public static boolean checkFreeSlots(String location,int maxslots) {
	 * if(location == null || maxslots == 0) { return false; } File file = new
	 * File(location);
	 * 
	 * 
	 * if(file.exists()) {
	 * 
	 * File file1[] = file.listFiles();
	 * 
	 * if(file1.length < maxslots) { return true; } else { return false; } }
	 * return false; }
	 */
	/**
	 * 
	 * This method creates a folder with filename as the name if the given
	 * 
	 * location exists else it creates the complete folder heirarchy and returns
	 * 
	 * the location
	 * 
	 * @param location
	 * @param fileName
	 * @return
	 */
//	public static String createFolder(String location, String fileName)
//	{
//		File file = new File(location);
//		if (file.exists())
//		{
//			file = new File(location + fileName);
//			if (!file.exists())
//			{
//				file.mkdir();
//			}
//			return location + fileName + "/";
//		}
//		else
//		{
//			file = new File(location + fileName);
//			file.mkdirs();
//			return location + fileName + "/";
//		}
//	}

	/**
	 * This method removes all the files present in the location
	 * 
	 * if location is valid
	 * 
	 * @param location
	 * @return
	 */
//	public static boolean removeFiles(String location)
//	{
//		File file = new File(location);
//		if (file.exists())
//		{
//			try
//			{
//				delete(file);
//			}
//			catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return true;
//		}
//		return false;
//	}

	/**
	 * Check whether the asset is present or not
	 * 
	 * @param location
	 * @param assetId
	 * @return
	 */
	/*
	 * public static boolean checkForAsset(String location ,String assetId) {
	 * File file = new File(location);
	 * 
	 * if(file.exists()) { File file1[] = file.listFiles();
	 * 
	 * for (int i = 0; i < file1.length; i++) {
	 * if(file1[i].getName().equals(assetId)) { return true; } } return false; }
	 * 
	 * return false; }
	 */
	/**
	 * Method which returns the contents of a particular location
	 * 
	 * (Usable only For AdsOnUI)
	 * 
	 * @param absoluteLocation
	 * @return
	 */
//	public static String[] getFlashContents(String absoluteLocation)
//	{
//		File file = new File(absoluteLocation);
//		if (file.exists())
//		{
//			File[] file1 = file.listFiles();
//			String[] assetIds = new String[file1.length];
//			for (int i = 0; i < file1.length; i++)
//			{
//				assetIds[i] = file1[i].getName();
//			}
//			return assetIds;
//		}
//		return null;
//	}

	/**
	 * Helper methods to remove files
	 * 
	 * recursively
	 * 
	 * @param file
	 * @throws IOException
	 */
//	private static void delete(File file) throws IOException
//	{
//		if (file.isDirectory())
//		{
//			// directory is empty, then delete it
//			if (file.list().length == 0)
//			{
//				file.delete();
//			}
//			else
//			{
//				// list all the directory contents
//				String files[] = file.list();
//				// for (String temp : files) {
//				// //construct the file structure
//				// File fileDelete = new File(file, temp);
//				//
//				// //recursive delete
//				// delete(fileDelete);
//				// }
//				for (int i = 0; i < files.length; i++)
//				{
//					File fileDelete = new File(file, files[i]);
//					// recursive delete
//					delete(fileDelete);
//				}
//				// check the directory again, if empty then delete it
//				if (file.list().length == 0)
//				{
//					file.delete();
//				}
//			}
//		}
//		else
//		{
//			// if file, then delete it
//			file.delete();
//		}
//	}

	/**
	 * Used for caching image
	 * 
	 * @param assetId
	 * @param screeName
	 * @return
	 */
//	public static String getAbsoluteLocationOfFile(String assetId, String screeName)
//	{
//		File file = new File(screeName);
//		if (file.exists())
//		{
//			File[] files = file.listFiles();
//			for (int i = 0; i < files.length; i++)
//			{
//				if (files[i].isDirectory())
//				{
//					File internalFiles[] = files[i].listFiles();
//					for (int j = 0; j < internalFiles.length; j++)
//					{
//						if (internalFiles[j].isFile())
//						{
//							if (internalFiles[j].getName().equals(assetId))
//							{
//								return internalFiles[j].getAbsolutePath();
//							}
//						}
//					}
//				}
//			}
//		}
//		else
//		{
//		}
//		return null;
//	}

	/**
	 * Method to check whether a particular file exists or not
	 * 
	 * @param location
	 * @return true if file present else false
	 */
	public static boolean checkFileExists(String location)
	{
		File file = new File(location);
		if (file.exists())
		{
			return true;
		}
		return false;
	}

	/**
	 * Method to create a folder
	 * 
	 * @param location
	 * @return true if folder created else false
	 */
	public static boolean createFolder(String location)
	{
		File file = new File(location);
		if (!file.exists())
		{
			file.mkdir();
			return true;
		}
		return false;
	}

	/**
	 * Method to remove an image from a particular location (Usable only for
	 * AdsOnUI)
	 * 
	 * @param assetId
	 * @param screenName
	 * @return true if image removed else false
	 */
//	public static boolean removeImage(String assetId, String screenName)
//	{
//		if (assetId != null && screenName != null)
//		{
//			String absLocation = getAbsoluteLocationOfFile(assetId, screenName);
//			if (absLocation != null)
//			{
//				if (removeFile(absLocation))
//				{
//					return true;
//				}
//				return false;
//			}
//		}
//		return false;
//	}

	/**
	 * Method to remove a file from particular location
	 * 
	 * @param location
	 * @return true if file removed else false
	 */
	public static boolean removeFile(String location)
	{
		File file = new File(location);
		if (file.exists())
		{
			file.delete();
		}
		return false;
	}

	public void run()
	{
		// TODO Auto-generated method stub
		// saveImageToFlash(URL, destination);
	}
}
