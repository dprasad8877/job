package middleware.papi.adsonui;
/**
 * Class that holds the screen related information such as
 * 1)name
 * 2)opportunityList
 * 3)noOfOpportunities
 * 
 * @author Balaji Muralidhar
 *
 *
 *Input	 :	1)name==> name of the UI screen
 *			2)noOfOpportunities
 *
 *Output : None
 */
import java.io.Serializable;
import java.util.ArrayList;

import middleware.papi.adsonui.util.AdsOnUILog;

/**
 * Class that holds the screen related information
 * 
 * @author Balaji Muralidhar
 * 
 */
public class UIScreen implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList opportunityList;
	private int noOfOpportunities;

	public UIScreen(String name, int noOfOpportunities)
	{
		this.name = name;
		this.noOfOpportunities = noOfOpportunities;
		opportunityList = new ArrayList();
	}

	/**
	 * Method to get the no of opportunities present in a particular screen
	 * 
	 * @return noOfOpportunities
	 */
	public int getNoOfOpportunities()
	{
		return noOfOpportunities;
	}

	public void setNoOfOpportunities(int noOfOpportunities)
	{
		this.noOfOpportunities = noOfOpportunities;
	}

	/**
	 * Method to get the screen name
	 * 
	 * @return screen name
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public ArrayList getOpportunityList()
	{
		return opportunityList;
	}

	public void setOpportunityList(ArrayList opportunityList)
	{
		this.opportunityList = opportunityList;
	}

	/**
	 * Method to add opportunity details related to a
	 * 
	 * particular UI screen
	 * 
	 * @param opportunityId
	 * @param nodeIdSequence
	 */
	public void addSequenceDetails(int opportunityId, String nodeIdSequence, boolean nonTargeted,
	        int displayDuration, int primaryAssetType, int adCompanionType)
	{
		if (getOpportunity(opportunityId) == null)
		{
			Opportunity opportunity = new Opportunity();
			opportunity.setNodeIdSequence(nodeIdSequence);
			opportunity.setOpportunityId(opportunityId);
			opportunity.setNonTargeted(nonTargeted);
			// NEW
			opportunity.setDisplayDuration(displayDuration);
			opportunity.setPrimaryAssetType(primaryAssetType);
			opportunity.setAdCompanionType(adCompanionType);
			opportunityList.add(opportunity);
		}
	}

	/**
	 * Method to update the display pointer position of
	 * 
	 * a particular sequence
	 * 
	 * @param opportunityId
	 * @param positionPointer
	 */
	public void updateDisplayPositionPointer(int opportunityId, int positionPointer)
	{
		Opportunity opportunity;
		if ((opportunity = getOpportunity(opportunityId)) != null)
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Opportunity Available for updating");
			/** how does it work in case of multiple opportunities */
			opportunity.setDisplayPointerPosition(positionPointer);
		}
		else
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Opportunity Not Available for updating");
		}
	}

	/**
	 * Method to get the display pointer position
	 * 
	 * @param opportunityId
	 * @return
	 */
	public int getDisplayPositionPointer(int opportunityId)
	{
		Opportunity opportunity;
		if ((opportunity = getOpportunity(opportunityId)) != null)
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Opportunity Available for updating");
			/** how does it work in case of multiple opportunities */
			return opportunity.getDisplayPointerPosition();
		}
		else
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Opportunity Not Available for updating");
			return -1;
		}
	}

	/**
	 * Helper Method to get current sequence and next sequence
	 */
	public String[] getCurrentOrNextSequence(int opportunityId, int noOfAssets, boolean next)
	{
		Opportunity opportunity;
		if ((opportunity = getOpportunity(opportunityId)) != null)
		{
			AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "Opportunity Available");
			return opportunity.getCurrentOrNextSequence(noOfAssets, next);
		}
		AdsOnUILog.displayLog(AdsOnUILog.LOGIC_TYPE, "No Opportunity Available");
		return null;
	}

	public Opportunity getOpportunity(int opportunityId)
	{
		if (null != opportunityList)
		{
			for (int i = 0; i < opportunityList.size(); i++)
			{
				Opportunity opportunity = (Opportunity) opportunityList.get(i);
				if (null != opportunity && opportunity.getOpportunityId() == opportunityId)
				{
					return opportunity;
				}
			}
		}
		return null;
	}

	public void setParitallyDisplayedAsset(int opportunityId, String assetId)
	{
		// TODO Auto-generated method stub
		if (null != opportunityList && assetId != null)
		{
			for (int i = 0; i < opportunityList.size(); i++)
			{
				Opportunity opportunity = (Opportunity) opportunityList.get(i);
				if (null != opportunity && opportunity.getOpportunityId() == opportunityId)
				{
					opportunity.setPartiallyDisplayedAssetId(assetId);
				}
			}
		}
	}

	public String getParitallyDisplayedAsset(int opportunityId)
	{
		// TODO Auto-generated method stub
		if (null != opportunityList)
		{
			for (int i = 0; i < opportunityList.size(); i++)
			{
				Opportunity opportunity = (Opportunity) opportunityList.get(i);
				if (null != opportunity && opportunity.getOpportunityId() == opportunityId)
				{
					return opportunity.getPartiallyDisplayedAssetId();
				}
			}
		}
		return null;
	}

	public int getRemainingDuration(int opportunityId)
	{
		if (null != opportunityList)
		{
			for (int i = 0; i < opportunityList.size(); i++)
			{
				Opportunity opportunity = (Opportunity) opportunityList.get(i);
				if (null != opportunity && opportunity.getOpportunityId() == opportunityId)
				{
					return opportunity.getRemainingDuration();
				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * @param opportunityId
	 * @return
	 * @since adsonui 2.0
	 */
	public int getDisplayDurtion(int opportunityId)
	{
		if (null != opportunityList)
		{
			for (int i = 0; i < opportunityList.size(); i++)
			{
				Opportunity opportunity = (Opportunity) opportunityList.get(i);
				if (null != opportunity && opportunity.getOpportunityId() == opportunityId)
				{
					return opportunity.getDisplayDuration();
				}
			}
		}
		return 0;
	}

	public void setRemainingDuration(int opportunityId, int remainingDuration)
	{
		// TODO Auto-generated method stub
		if (null != opportunityList && remainingDuration >= 0)
		{
			for (int i = 0; i < opportunityList.size(); i++)
			{
				Opportunity opportunity = (Opportunity) opportunityList.get(i);
				if (null != opportunity && opportunity.getOpportunityId() == opportunityId)
				{
					opportunity.setRemainingDuration(remainingDuration);
				}
			}
		}
	}

	/**
	 * Checks whether the specified opportunity is targeted or non targeted
	 * 
	 * @param opportunityId
	 * @return
	 */
	public boolean checkWhetherNonTargeted(int opportunityId)
	{
		if (null != opportunityList)
		{
			for (int i = 0; i < opportunityList.size(); i++)
			{
				Opportunity opportunity = (Opportunity) opportunityList.get(i);
				if (null != opportunity && opportunity.getOpportunityId() == opportunityId)
				{
					return opportunity.isNonTargeted();
				}
			}
		}
		return false;
	}
}
