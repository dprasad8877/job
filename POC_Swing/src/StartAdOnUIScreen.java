import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import middleware.papi.ManageAdsOnUI;
import middleware.papi.adsonui.AdsOnUIManager;



public class StartAdOnUIScreen extends JFrame {
	
	static int[] oppIdsArray = { 1, 2, 5, 6, 7 };
	
	public static void main(String[] args) {
		AdsOnUIManager. enableAdsOnUI(oppIdsArray);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Map();

	}

	

	private static void Map() {


		JFrame frame = new JFrame("Testing");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		uoiScreenobj=new AdvertisementForUIScreen();
	
//		frame.add(uoiScreenobj.startAd(1110, 0,"chList_Right_Vertical",1));
		frame.add(new AdvertisementHomeScreen(0, 20, 254, 501, 1,frame));
		frame.pack();
		frame.setSize(ScreenResolution.width, ScreenResolution.height);

		frame.setVisible(true);

	}
}






 class AdvertisementHomeScreen extends JPanel{
    ImageIcon pic;
    static ManageAdsOnUI uoiScreenobj=null;
    static ManageAdsOnUI uoiScreenobj1=null;

    public AdvertisementHomeScreen(int pos_x, int pos_y, int width, int height, int uniqueOppId, final JFrame frame)
    {

        uoiScreenobj=new ManageAdsOnUI(); 	
        pic = new ImageIcon("resources/AS100.jpg");

        this.setLayout(null); //set your frame layout to null for abs. positioning
        setBounds(pos_x,pos_y,width,height);
        JPanel  yourPanel = new JPanel(); // create your JPanel
        
        yourPanel.setLayout(null); // set the layout null for this JPanel !

        JLabel label = new JLabel(pic);
        label.setBounds(pos_x,pos_y,width,height);
        label.setBorder(BorderFactory.createLineBorder(Color.yellow));
        
        final JButton EPGButton=new JButton("EPG Grid");  
        EPGButton.setBounds(550,20,150,30); 
        
         final JButton channelListButton=new JButton("ChannelList");  
        channelListButton.setBounds(550,20,150,30); 
        
        final JButton mixAdBannerButton=new JButton("Mixed Ad banner screen");  
        mixAdBannerButton.setBounds(710,20,150,30);
        
		final JButton PlayVideo = new JButton("play video");
		PlayVideo.setBounds(610, 60, 150, 30);

        EPGButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	add(channelListButton);
            	remove(EPGButton);
            	
            	uoiScreenobj.label.setVisible(false);
				uoiScreenobj.b.setVisible(false);
            	uoiScreenobj.stopAd(false);
            	
            	try{
    				uoiScreenobj1.label.setVisible(false);
    				uoiScreenobj1.b.setVisible(false);
                	uoiScreenobj1.stopAd(false);
    				}catch(Exception e1){
    					
    				}
            	
            	uoiScreenobj=new ManageAdsOnUI();
            	add(uoiScreenobj.startAd(0,500,"epg",2));
              System.out.println("Channel List");
              return;
            }          
         });
         
        channelListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	add(EPGButton);
            	remove(channelListButton);
            	uoiScreenobj.label.setVisible(false);
				uoiScreenobj.b.setVisible(false);
				uoiScreenobj.stopAd(false);
				try{
				uoiScreenobj1.label.setVisible(false);
				uoiScreenobj1.b.setVisible(false);
            	uoiScreenobj1.stopAd(false);
				}catch(Exception e1){
					
				}
				uoiScreenobj=new ManageAdsOnUI();
            	add(uoiScreenobj.startAd(1110, 0,"chList_Right_Vertical",1));
       	System.out.println("EPG");
       	return;
            }
         });
	
        mixAdBannerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	uoiScreenobj.label.setVisible(false);
				uoiScreenobj.b.setVisible(false);
				uoiScreenobj.stopAd(false);
				
				uoiScreenobj1=new ManageAdsOnUI();
				uoiScreenobj=new ManageAdsOnUI();
            	add(uoiScreenobj.startAd(1110, 0,"chList_Right_Vertical",1));
            	add(uoiScreenobj1.startAd(0, 0,"TEST",5));
       	System.out.println("EPG");
       	return;
            }
         });
        
    	PlayVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uoiScreenobj.label.setVisible(false);
				uoiScreenobj.b.setVisible(false);
				uoiScreenobj.stopAd(false);
				
				MediaPlayer mediaplayer = new MediaPlayer();
				frame.setContentPane(mediaplayer);
				mediaplayer.play(frame);
			}
		});
        
        
		add(uoiScreenobj.startAd(1110, 0,"chList_Right_Vertical",1));
        label.setOpaque(true);
       // this.add(label);
        this.add(EPGButton);
        this.add(mixAdBannerButton);
        this.add(PlayVideo);
    }


}
