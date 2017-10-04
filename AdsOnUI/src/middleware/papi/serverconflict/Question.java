package middleware.papi.serverconflict;
/**
 * Purpose : This class is used obtain quiz question and answer
 * 
 * Input : Question and answer
 * 
 * Output : None
 * 
 */
import java.io.Serializable;

public class Question implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String question;
	private String answer;
	private int timeStamp;

	public Question(String question, String answer)
	{
		super();
		this.question = question;
		this.answer = answer;
	}

	public int getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}
