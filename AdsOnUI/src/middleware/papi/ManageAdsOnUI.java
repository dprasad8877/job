package middleware.papi;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Util.Debug.Log;

public class ManageAdsOnUI extends JPanel {
	//instance variables
	ImageIcon pic;
	private Advertisement advvertisementObj;
	JPanel p;
	public  JLabel label;
	public JButton b;
	private boolean advertisementPresent = false;
	private Timer timerForAd;
	private long startTime;

	private ImageAdvertisement imageAdvertisement;
	private boolean deRegistered;

	private int adWidth;
	private int adHeight;
	private int uniqueOppId;
	private int screenId;
	static int temp=0;

	public AdvertisementListener advertisementListener = new AdvertisementListener() {

		@Override
		public void iGotAnAdvertisement(Advertisement TheAdvertisement) {
			if (TheAdvertisement != null) {
				advvertisementObj = TheAdvertisement;

				if (TheAdvertisement instanceof ImageAdvertisement) 
				{
					Log.display("Advertisement is ImageAdvertisement");
					displayImageAdvertisement((ImageAdvertisement) TheAdvertisement);
					//new TestDemo();
				} 
				else if (TheAdvertisement instanceof StickyAdvertisement) 
				{
					Log.display("Advertisement is StickyAdvertisement");
					//displayStickyAdvertisement((StickyAdvertisement) TheAdvertisement);

				}

			} else {
				setVisible(false);
			}

		}
	};


	public Component startAd(int pos_x, int pos_y,String screnName, int uniqueOppId )
	{
		//AdDetails adDetailsObj=PapiConstants.chList_Right_Vertical;
		AdDetails adDetailsObj=(AdDetails)PapiConstants.uoi_lookup1.get(uniqueOppId);
		this.adWidth=adDetailsObj.getWidth();
		this.adHeight=adDetailsObj.getHeight();    	
		this.screenId=adDetailsObj.getScreenId();
		this.uniqueOppId=uniqueOppId;

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		try{
			System.out.println(" start of : "+PapiConstants.uoi_lookup1.get(uniqueOppId).getScreenName().toString());
			
		}catch(Exception e){
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<exception>>>>>>>>>>>>>>>>>>>>");
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		


		Papi.registerForAnAdvertisement(screenId,uniqueOppId,advertisementListener);


		setLayout(new BorderLayout());
		this.setBounds(pos_x, pos_y, adWidth, adHeight);
		p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		label = new JLabel();

		label.setBounds(pos_x, pos_y, adWidth, adHeight);
		//label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		b=new JButton("switch screen");  
		//b.setBounds(0,0,40,30);  
		//this.add(b);
		label.setLayout( new GridBagLayout() );
		GridBagConstraints gridBagConstraints=new GridBagConstraints();
		//		
		//		gridBagConstraints.gridx = 0;
		//        gridBagConstraints.gridy = 0;
		//        gridBagConstraints.ipadx = 0;
		//        gridBagConstraints.ipady = 0;
		//        gridBagConstraints.anchor = java.awt.GridBagConstraints.NONE;
		//gridBagConstraints.insets = new java.awt.Insets(0, pos_y+160,pos_x+472,pos_y+ 0);
		//label.add(b, gridBagConstraints);
		this.add(label);
		//b.addMouseListener(mouseListener);
		return this;



	}


	public ManageAdsOnUI() {
		// TODO Auto-generated constructor stub
	}

/*public void register(){
	Papi.registerForAnAdvertisement(screenId,uniqueOppId,advertisementListener);
}*/
	protected void displayImageAdvertisement(ImageAdvertisement imageAdvertisement) 
	{

		if (null != imageAdvertisement) {
			this.imageAdvertisement = imageAdvertisement;

			advertisementPresent = true;

			Log.display("********************************************************");
			Log.display("ImageAdvertisement :"+imageAdvertisement.getAdCompanion().getPictureURL());
			Log.display("********************************************************");

			//		DataExchange.getInstance().put(DataExchange.AD_COMPANION_URL,
			//				imageAdvertisement.getAdCompanion());

			final int durationToDisplay = imageAdvertisement.getDuration();

			Log.display("Duration : " + durationToDisplay);

			Image imageToBeDisplayed = imageAdvertisement.getAssetImage();

			timerForAd = new Timer();

			imageToBeDisplayed =imageToBeDisplayed.getScaledInstance(adWidth, adHeight,Image.SCALE_DEFAULT);
			try{
			label.setIcon(new ImageIcon(imageToBeDisplayed ));
			}catch(NullPointerException npe){
				
			}

			if (!this.isVisible()) {
				this.setVisible(true);
			}

			label.revalidate();
			revalidate();
			repaint();

			startTime = System.currentTimeMillis();

			timerForAd.schedule(new TimerTask() {

				@Override
				public void run() 
				{
					Papi.notifyPapiAdvertisemtHasBeenDisplayed(
							durationToDisplay, advvertisementObj);
				}
			}, durationToDisplay * 1000);
		}
		else {
			Log.display("Advertisement object null");
			advertisementPresent = false;
			this.setVisible(false);
		}

	}	


	public void stopAd(boolean isScreenVisible ) {

		long endTime = System.currentTimeMillis();

		caneclAdTimer();

		int partiallyDisplayedTime = (int) ((endTime - startTime) / 1000);

		if (!deRegistered && advertisementPresent) {
			Papi.deregisterAdvertisementListener(screenId,uniqueOppId,advertisementListener);
			if (imageAdvertisement != null
					&& partiallyDisplayedTime <= imageAdvertisement
					.getDuration()) {
				if (isScreenVisible ) {
					// Log.display("Calling ad companion");
					Papi.notifyAdCompanionHasBeenRequested(
							partiallyDisplayedTime, imageAdvertisement);
				} else {
					Papi.notifyPapiAdvertisemtHasBeenDisplayed(
							partiallyDisplayedTime, imageAdvertisement);
				}
			}
		}
		deRegistered = true;
		advertisementPresent = false;

	}
	private void caneclAdTimer() {
		if (timerForAd != null) {
			timerForAd.cancel();
		}
	}

/*
	public  MouseListener mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent mouseEvent) {
			int modifiers = mouseEvent.getModifiers();
			if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {

				//label.remove(b);
				label.setVisible(false);
				b.setVisible(false);

				if(temp==0){
					stopAd(false);
					temp=1;
					try{

						//add(startAd(0,500,"epg",2));

					}catch(Exception e){

					}
				}
				else{
					stopAd(false);
					temp=0;
					try{

						//add(startAd(1110, 0,"chList_Right_Vertical",1));

					}catch(Exception e){

					}	
				}
			}
		}
	};
*/

}








