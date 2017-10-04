import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import middleware.papi.ManageAdsOnUI;

public class ProgramBanner extends JPanel {
	private JPanel advertisementPanel = new JPanel();

	private ImageIcon advertisementImageIcon;

	private Image advertisementImage;

	private Image resizedImage;

	private JLabel resizedImageLabel;

	private JButton button = new JButton("\u25C4");
	ManageAdsOnUI manageAdsOnUI=null;

	public ProgramBanner() {
		this.setLayout(null);
		//setting the  size of panel
		this.setSize(ProgramBannerConstants.pgWidth,ProgramBannerConstants.pgHeight);
		//setting the Background color
		this.setBorder(BorderFactory.createLineBorder(Color.gray));
		//setting the Border line color
		this.setBackground(Color.blue);
		//setting position and size of the program Banner panel
		this.setBounds(ProgramBannerConstants.pg_XPos,
				ProgramBannerConstants.pg_YPos, ProgramBannerConstants.pgWidth,
				ProgramBannerConstants.pgHeight);
		this.setOpaque(true);
		//	  GradientBackground backgroundGradient = new GradientBackground(
		//	  ProgramBannerConstants.pgWidth, ProgramBannerConstants.pgHeight,
		//	  GradientBackground.PB_INFO_BANNER_GRADIENT);
		//	  this.add(backgroundGradient); this.setVisible(false);

		// =======================================================
		/*advertisementPanel.setBounds(
				ProgramBannerConstants.advertisementPanel_XPos,
				ProgramBannerConstants.advertisementPanel_YPos,
				ProgramBannerConstants.advertisementPanel_Width,
				ProgramBannerConstants.advertisementPanel_Height);*/

		/*  // if(!PapiConstants.isAdsonUINessecary) // { 
	  advertisementImageIcon = new ImageIcon( "resources/AS115.jpg", "NO IMAGE"); advertisementImage = advertisementImageIcon.getImage(); 
	  resizedImage =advertisementImage.getScaledInstance(ProgramBannerConstants.
			  advertisementPanel_Width, ProgramBannerConstants.advertisementPanel_Height, Image.SCALE_SMOOTH);
	  resizedImageLabel = new JLabel(new ImageIcon(resizedImage));
	  advertisementPanel.add(resizedImageLabel); 
	  advertisementPanel.setOpaque(false); 
	 // add(advertisementPanel);
		 */	  
		manageAdsOnUI=new ManageAdsOnUI();
		//Adding the advertisementPanel to Program Banner panel and starting ad
		add(manageAdsOnUI.startAd(ProgramBannerConstants.advertisementPanel_XPos,ProgramBannerConstants.advertisementPanel_YPos, "Program Banner", 6) );
		//add(button);
		// =====setting Z-order ==========================
		//setComponentZOrder(backgroundGradient, 6); //
		//setComponentZOrder(advertisementPanel, 0);
		/*JButton button1 = new JButton("Stop Ad");
		button1.setBounds(550-100, 0, 100, 30);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageAdsOnUI.stopAd(false);
				manageAdsOnUI.label.setVisible(false);
			
			}
		});*/
		//add(button1);

	}

	

}
