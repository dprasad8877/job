package middleware.papi.adsonui.request;
/**
 *Purpose : This is an interface used to obtain response from server 
 *
 *Input   : None
 *
 *Output  : None
 */
public interface CommunicationListener
{
	public void communicationDone(int type, int result, Object obj);
}
