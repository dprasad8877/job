package middleware.papi.serverconflict;
/**
 * Purpose :
 * Class which manages the server conflict situation which is 
 * if there is a question sent by server to resolve conflict it 
 * is notified back to any application which registers for this situation 
 * 
 * @author Balaji Muralidhar
 *
 *Input : None
 *
 *Output :None
 *
 */
import java.util.Iterator;
import java.util.Vector;

import middleware.papi.IGotQuestion;
import middleware.papi.adsonui.constants.AdsOnUIConstants;
import middleware.papi.adsonui.request.AdsOnUIXMLPlacement;
import middleware.papi.adsonui.request.CommunicationListener;
import middleware.papi.adsonui.util.AdsOnUILog;
import middleware.papi.adsonui.util.FlashManager;
import middleware.papi.adsonui.util.ObjectReader;
import middleware.papi.adsonui.util.ObjectWriter;


public class QuizManager implements CommunicationListener {


	private static QuizManager serverConflictManager;

	private Question question;


	private int minimumTime = 2;

	private Vector iGotQuestionVector;

	private QuizManager()
	{
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("QuizManager const entry");
		}
		
		
		readAnsweredOrUnAnsweredQuestionIfExists();

		iGotQuestionVector = new Vector();
		
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("QuizManager const exit");
		}
		
	}


	private void readAnsweredOrUnAnsweredQuestionIfExists() {


		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("readAnsweredOrUnAnsweredQuestionIfExists entry");
		}
		
		ObjectReader objectReader = 
				new ObjectReader(AdsOnUIConstants.unAnsweredQuestionLocation);

		if(objectReader != null)
		{
			Question tempQuestion = (Question) objectReader.readObject();

			if(tempQuestion != null)
			{
				if(tempQuestion.getAnswer() != null)
				{
					if(AdsOnUILog.somethingWrong)
					{
						System.out.println("***********");
						System.out.println("Answer : "+tempQuestion.getAnswer());
						System.out.println("***********");
					}
					
					AdsOnUIXMLPlacement adsOnUIXMLPlacement = new AdsOnUIXMLPlacement
							(tempQuestion, AdsOnUIConstants.QUIZ);

					adsOnUIXMLPlacement.sendDataToServer(this);

				}
				else
				{
					question = tempQuestion;
				}
			}
		}	
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("readAnsweredOrUnAnsweredQuestionIfExists exit");
		}
		
	}




	public static synchronized QuizManager getInstance()
	{
		if(serverConflictManager == null)
		{
			serverConflictManager = new QuizManager();
		}

		return serverConflictManager;

	}


	public synchronized Question getQuestion() {
		return question;
	}


	public synchronized void setQuestion(Question question) {

		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("QuizManager setQuestion entry");
		}
		
		if(this.question == null)

		{
			this.question = question;

			saveQuestion();

			notifyListeners();
		}
		
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("QuizManager setQuestion exit");
		}
		

	}


	private void saveQuestion() {

		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("QuizManager saveQuestion entry");
		}
		
		if(question != null)
		{
			FlashManager.createFolder(AdsOnUIConstants.adsOnUIFoldername);

			FlashManager.removeFile(AdsOnUIConstants.unAnsweredQuestionLocation);
			
			ObjectWriter objectWriter = new ObjectWriter(AdsOnUIConstants.unAnsweredQuestionLocation);

			objectWriter.writeObject(question);
		}

		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("QuizManager saveQuestion exit");
		}

	}

	public void deleteAnsweredOrUnansweredQuestion()
	{
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("QuizManager deleteAnsweredOrUnansweredQuestion  entry");
		}
		FlashManager.removeFile(AdsOnUIConstants.unAnsweredQuestionLocation);
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("QuizManager deleteAnsweredOrUnansweredQuestion  entry");
		}
	}


	private void notifyListeners() {

		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("notifyListeners entry");
		}
		
		if(question != null)
		{

			Iterator iGotQuestionIterator =
					iGotQuestionVector.iterator();

			while (iGotQuestionIterator.hasNext()) {

				IGotQuestion iGotQuestion2 = (IGotQuestion) iGotQuestionIterator.next();

				iGotQuestion2.notifyServerQuestionRecieved(question.getQuestion());
				
				if(AdsOnUILog.somethingWrong)
				{
					System.out.println("Inside notify while");
				}
				

			}

		}
		
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("notifyListeners exit");
		}
		

	}


	public void registerForQuiz(IGotQuestion iGotQuestion)
	{
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("registerForQuiz entry");
		}
		
		if(iGotQuestion == null)
		{
			throw new IllegalArgumentException("Listener is null");
		}

		if(	!iGotQuestionVector.contains(iGotQuestion))
		{
			iGotQuestionVector.add(iGotQuestion);
		}

		if(question != null && question.getAnswer() == null)
		{
			notifyListeners();
		}

		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("registerForQuiz exit");
		}
		
		
	}






	public void deRegisterForQuiz(IGotQuestion iGotQuestion)
	{
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("deRegisterForQuiz entry");
		}
		
		
		if(iGotQuestion == null)
		{
			throw new IllegalArgumentException("Listener is null");
		}

		if(iGotQuestionVector != null && 
				iGotQuestionVector.contains(iGotQuestion))
		{
			iGotQuestionVector.remove(iGotQuestion);
		}
		
		if(AdsOnUILog.somethingWrong)
		{
			System.out.println("deRegisterForQuiz exit");
		}
		


	}

	public void answerToTheQuestion(int timeViwerTookToAnswerTheQuestion , 
			String answer)
	{

		if(timeViwerTookToAnswerTheQuestion < minimumTime)
		{
			return;
		}

		if(question != null && answer != null)
		{
			question.setAnswer(answer);
			question.setTimeStamp(timeViwerTookToAnswerTheQuestion);

			saveQuestion();

			AdsOnUIXMLPlacement adsOnUIXMLPlacement = 
					new AdsOnUIXMLPlacement(question, AdsOnUIConstants.QUIZ);

			adsOnUIXMLPlacement.sendDataToServer(this);

		}




	}


	@Override
	public void communicationDone(int type, int result, Object obj) {
		// TODO Auto-generated method stub

		if(type == AdsOnUIConstants.QUIZ
				&& result == AdsOnUIConstants.ZERO)
		{
			if(AdsOnUILog.somethingWrong)
			{
				System.out.println("Communication done");
			}
			deleteAnsweredOrUnansweredQuestion();
			question = null;
		}

	}






}
