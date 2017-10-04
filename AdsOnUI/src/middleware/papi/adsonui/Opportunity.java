package middleware.papi.adsonui;
/**
 * Purpose : 
 * Class that holds the opportunity related information of 
 * a particular UI screen such as
 * 1)nodeIdSequence
 * 2)tempNodeIdSequence
 * 3)displayPointerPosition
 * 4)partiallyDisplayedAssetId
 * 5)remainingDuration
 * 6)opportunityId
 * 7)nonTargeted
 * 8)adCompanionType
 * 9)primaryAssetType
 * 10)displayDuration
 * 
 * @author Balaji Muralidhar
 *
 *Input  : None
 *
 *Output : None
 */
import java.io.Serializable;
import java.util.StringTokenizer;

import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.util.AdsOnUILog;

/**
 * Class that holds the opportunity related information of
 * 
 * a particular UI screen
 * 
 * @author Balaji Muralidhar
 * 
 */
public class Opportunity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nodeIdSequence;
	private String tempNodeIdSequence;
	private int displayPointerPosition;
	private String partiallyDisplayedAssetId;
	private int remainingDuration;
	private int opportunityId;
	private boolean nonTargeted;
	/**
	 * @since adsonui 2.0
	 */
	private int displayDuration;
	/**
	 * @since adsonui 3.0
	 */
	private int adCompanionType;
	/**
	 * @since adsonui 3.0
	 */
	private int primaryAssetType;

	public boolean isNonTargeted()
	{
		return nonTargeted;
	}

	public void setNonTargeted(boolean nonTargeted)
	{
		this.nonTargeted = nonTargeted;
	}

	private char COMMA = ',';

	private int nthOccurrence(String str, char c, int n)
	{
		int pos = str.indexOf(c, 0);
		while (n-- > 0 && pos != -1)
			pos = str.indexOf(c, pos + 1);
		return pos;
	}

	public int countOccurrences(String haystack, char character)
	{
		int count = 0;
		for (int i = 0; i < haystack.length(); i++)
		{
			if (haystack.charAt(i) == character)
			{
				count++;
			}
		}
		return count;
	}

	/**
	 * Method to get the opportunity id
	 * 
	 * @return opportunity id
	 */
	public int getOpportunityId()
	{
		return opportunityId;
	}

	/**
	 * Method to set the opportunity id
	 * 
	 * @param opportunityId
	 */
	public void setOpportunityId(int opportunityId)
	{
		this.opportunityId = opportunityId;
	}

	public String getNodeIdSequence()
	{
		return tempNodeIdSequence;
	}

	/**
	 * Method to set the node id sequence of that opportunity
	 * 
	 * @param nodeIdSequence
	 */
	public void setNodeIdSequence(String nodeIdSequence)
	{
		tempNodeIdSequence = nodeIdSequence;
		nodeIdSequence = nodeIdSequence.trim();
		this.nodeIdSequence = "," + nodeIdSequence + ",";
		handleLastAssetWithRepeatTagIfPresent();
	}

	/**
	 * Method to get the display pointer position
	 * 
	 * @return displayPointerPosition
	 */
	public int getDisplayPointerPosition()
	{
		return displayPointerPosition;
	}
	public void updateDisplayPointerPosition() {
		displayPointerPosition = displayPointerPosition + 1;
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE,"Display pointer position " + displayPointerPosition);
	}
	/**
	 * Method to set the display pointer position
	 * 
	 * @param displayPointerPosition
	 */
	public void setDisplayPointerPosition(int displayPointerPosition)
	{
		this.displayPointerPosition = displayPointerPosition;
	}
	/**
	 * Method to get the display pointer position
	 * 
	 * @param opportunityId
	 * @return
	 */
	public int getDisplayPositionPointer(int opportunityId)
	{
		
			return getDisplayPointerPosition();
		
	}
	
	public void setParitallyDisplayedAsset(int opportunityId, String assetId)
	{
		setPartiallyDisplayedAssetId(assetId);
		
	}
	
	
	public String getParitallyDisplayedAsset(int opportunityId)
	{
		return getPartiallyDisplayedAssetId();
			
	}
	
	
	
	public void setRemainingDuration(int opportunityId, int remainingDuration)
	{
		setRemainingDuration(remainingDuration);
	
	}
	public int getRemainingDuration(int opportunityId)
	{
					return getRemainingDuration();
			
	}
	
	public int getDisplayDurtion(int opportunityId)
	{
		
					return getDisplayDuration();
		
	}
	
	public boolean checkWhetherNonTargeted(int opportunityId)
	{
		
					return isNonTargeted();
			
	}

	/**
	 * Method to get the partially displayed asset
	 * 
	 * @return partiallyDisplayedAssetId or 0
	 */
	public String getPartiallyDisplayedAssetId()
	{
		return partiallyDisplayedAssetId;
	}

	/**
	 * Method to set the partially displayed asset id
	 * 
	 * @param partiallyDisplayedAssetId
	 */
	public void setPartiallyDisplayedAssetId(String partiallyDisplayedAssetId)
	{
		this.partiallyDisplayedAssetId = partiallyDisplayedAssetId;
	}

	/**
	 * Method to get the partially displayed assets remaining display duration
	 * 
	 * @return remainingDuration or 0
	 */
	public int getRemainingDuration()
	{
		return remainingDuration;
	}

	/**
	 * Method to set the partially displayed asset id
	 * 
	 * @param remainingDuration
	 */
	public void setRemainingDuration(int remainingDuration)
	{
		this.remainingDuration = remainingDuration;
	}

	/**
	 * 
	 * @param displayDuration
	 * @since adsonui 2.0
	 */
	public void setDisplayDuration(int displayDuration)
	{
		this.displayDuration = displayDuration;
	}

	/**
	 * @since adsonui 2.0
	 * @return
	 */
	public int getDisplayDuration()
	{
		return displayDuration;
	}

	public int getAdCompanionType()
	{
		return adCompanionType;
	}

	public void setAdCompanionType(int adCompanionType)
	{
		this.adCompanionType = adCompanionType;
	}

	public int getPrimaryAssetType()
	{
		return primaryAssetType;
	}

	public void setPrimaryAssetType(int primaryAssetType)
	{
		this.primaryAssetType = primaryAssetType;
	}

	/**
	 * Method to get the current or next sequence based on noOfAssets
	 * 
	 * passed and also if next is true then next sequence will be returned
	 * 
	 * else current sequence is returned
	 * 
	 * @param noOfAssets
	 * @param next
	 * @return (Current or Next Sequence) or null if no sequence found
	 */
	public String[] getCurrentOrNextSequence(int noOfAssets, boolean next)
	{
		int currentPosition = 0;
		int lastPosition = 0;
		int noOfOccuernceOfComma = countOccurrences(nodeIdSequence, COMMA);
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "getCurrentOrNextSequence entry");
		if (!next && displayPointerPosition <= noOfOccuernceOfComma)
		{
			/** function name should be specific */
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, nodeIdSequence + " "
			        + displayPointerPosition);
			currentPosition = nthOccurrence(nodeIdSequence, COMMA, displayPointerPosition);
			lastPosition = nthOccurrence(nodeIdSequence, COMMA, displayPointerPosition + noOfAssets);
			if (lastPosition == -1)
			{
				lastPosition = nodeIdSequence.lastIndexOf(COMMA);
				if (currentPosition == lastPosition)
				{
					lastPosition = -1;
				}
			}
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, currentPosition + " " + lastPosition);
		}
		else if ((displayPointerPosition + noOfAssets) <= noOfOccuernceOfComma)
		{
			currentPosition = nthOccurrence(nodeIdSequence, COMMA, displayPointerPosition
			        + noOfAssets);
			lastPosition = nthOccurrence(nodeIdSequence, COMMA, displayPointerPosition
			        + (AdsOnUIConstants.TWO * noOfAssets));
			if (AdsOnUILog.somethingWrong)
			{
				AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,"Display Position Pointer" + displayPointerPosition
				        + nodeIdSequence + currentPosition + " " + noOfAssets);
			}
			if (lastPosition == -1)
			{
				lastPosition = nodeIdSequence.lastIndexOf(COMMA);
				if (currentPosition == lastPosition)
				{
					lastPosition = -1;
				}
			}
		}
		else
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE,
			        "Returining null inside getCurrentOrNextSequence");
			return null;
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "getCurrentOrNextSequence exit");
		return tokenizeSequence(currentPosition, lastPosition, noOfAssets);
	}

	/**
	 * Helper method to tokenize the node id sequence
	 * 
	 * @param currentPosition
	 * @param lastPosition
	 * @param noOfAssets
	 * @return
	 */
	private String[] tokenizeSequence(int currentPosition, int lastPosition, int noOfAssets)
	{
		// TODO Auto-generated method stub
		String subSequence;
		int i = 0;
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "tokenizeSeqeunce entry");
		if (currentPosition != -1 && lastPosition != -1 && noOfAssets > 0)
		{
			subSequence = nodeIdSequence.substring(currentPosition + 1, lastPosition);
			if (null != subSequence)
			{
				StringTokenizer stringTokenizer = new StringTokenizer(subSequence, ",");
				String sequence[] = new String[stringTokenizer.countTokens()];
				while (stringTokenizer.hasMoreTokens())
				{
					sequence[i] = stringTokenizer.nextToken();
					i++;
				}
				return sequence;
			}
		}
		AdsOnUILog.displayLog(AdsOnUILog.ENTRY_EXIT_TYPE, "tokenizeSeqeunce exit");
		return null;
	}

	private void handleLastAssetWithRepeatTagIfPresent()
	{
		if (nodeIdSequence.indexOf('#') == -1)
		{
			return;
		}
		int count = countOccurrences(nodeIdSequence, ',') - 2;
		int pos = nthOccurrence(nodeIdSequence, ',', count);
		int lastPos = nodeIdSequence.lastIndexOf(',');
		String lastImage = nodeIdSequence.substring(pos + 1, lastPos);
		nodeIdSequence = nodeIdSequence.substring(0, pos) + ",";
		StringTokenizer stringTokenizer = new StringTokenizer(lastImage, "#");
		if (null != stringTokenizer)
		{
			int i = 0;
			String[] tokenizedArray = new String[stringTokenizer.countTokens()];
			if (tokenizedArray.length > 1)
			{
				while (stringTokenizer.hasMoreElements())
				{
					tokenizedArray[i] = stringTokenizer.nextToken();
					i++;
				}
				int repeatvalue = Integer.parseInt(tokenizedArray[1]);
				for (int j = 0; j < repeatvalue; j++)
				{
					nodeIdSequence = nodeIdSequence + tokenizedArray[0] + ",";
				}
			}
		}
	}
}
