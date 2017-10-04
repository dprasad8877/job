package middleware.papi.adsonui.request;
/**
 * Purpose : This class holds the opportunity data 
 * related to an UI screen 
 * 
 * Input :  1)opportunityId
 * 			2)width
 * 			3)height
 * 			4)supportingShapes
 * 
 * Output : None
 *
 */
public class OpportunityDetails
{
	private int opportunityId;
	private int width;
	private int height;
	private String supportingShapes;
	private String nodeIdSequence;

	public OpportunityDetails(int opportunityId, int width, int height, String supportingShapes)
	{
		super();
		this.opportunityId = opportunityId;
		this.width = width;
		this.height = height;
		this.supportingShapes = supportingShapes;
	}

	public String getNodeIdSequence()
	{
		return nodeIdSequence;
	}

	public void setNodeIdSequence(String sequence)
	{
		this.nodeIdSequence = sequence;
	}

	public OpportunityDetails(int opportunityId, String sequence)
	{
		super();
		this.opportunityId = opportunityId;
		this.nodeIdSequence = sequence;
	}

	public int getOpportunityId()
	{
		return opportunityId;
	}

	public void setOpportunityId(int opportunityId)
	{
		this.opportunityId = opportunityId;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public String getSupportingShapes()
	{
		return supportingShapes;
	}

	public void setSupportingShapes(String supportingShapes)
	{
		this.supportingShapes = supportingShapes;
	}
}
