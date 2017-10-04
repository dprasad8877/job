import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class MediaPlayer extends JPanel {

private EmbeddedMediaPlayerComponent mediaplayer;


private JPanel panel;
ProgramBanner programBanner=null;
JButton StopVideoButton;

public MediaPlayer() {



    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files (x86)\\VideoLAN\\VLC");
    
    
    mediaplayer = new EmbeddedMediaPlayerComponent();

    panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(mediaplayer, BorderLayout.CENTER);
    setLayout(new BorderLayout());
    add(mediaplayer, BorderLayout.CENTER);
    
  

}

public void play(final JFrame frame) {
    mediaplayer.getMediaPlayer().playMedia("resources/AboutMe.wmv");
    
    programBanner=new ProgramBanner();
    StopVideoButton = new JButton("stop Video");
    StopVideoButton.setBounds(ScreenResolution.width-100, 0, 100, 30);
	
	
    StopVideoButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//calling stopAd() API from manageAdsOnUI object using programBanner class 
			programBanner.manageAdsOnUI.stopAd(false);
			//making Program Banner and video invisible before navigating to new screen
			programBanner.setVisible(false);
			StopVideoButton.setVisible(false);
			mediaplayer.release();
			mediaplayer.setVisible(false);
			
			remove(mediaplayer);
			add(new AdvertisementHomeScreen(0, 20, 254, 501, 1,frame));
		
		}
	});

	JLayeredPane pane= frame.getLayeredPane();
	//JLayeredPane pane= new JLayeredPane();
	pane.add(programBanner, new Integer(0));
	pane.add(StopVideoButton, new Integer(1));
}

/*public static void main(String[] args) {
    MediaPlayer mediaplayer = new MediaPlayer();
    JFrame ourframe = new JFrame();
    ourframe.setContentPane(mediaplayer);
    ourframe.setSize(720, 560);
    ourframe.setVisible(true);
    mediaplayer.play();
    ourframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}*/
}