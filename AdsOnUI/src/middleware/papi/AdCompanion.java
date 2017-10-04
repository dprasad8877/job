package middleware.papi;


/**
 * This is the AD Companion object thrown to the Application Layer to display 
 * Ad Companion
 * 
 * This object represents all the required information for AD Companion Application
 * to Show Adcompanion
 * 
 *  
 * */
public class AdCompanion {

	final public static int TYPE_PICTURE = 0;
	final public static int TYPE_VIDEO = 1;
	final public static int TYPE_URL = 2;
	final public static int TYPE_PICTURE_VIDEO = 3;
	final public static int TYPE_PICTURE_VIDEO_URL = 4;

	// There is picture , video and URL = 3
	final private static int NUMBER_OF_ADCOMPANION_COMPONENTS = 3;

	private String[] adCompanionURLS;
	private int adCompanionType;

	final static String[] typesOfAdCompanion = 
		{"TYPE_PICTURE",
		"TYPE_VIDEO",
		"TYPE_URL",
		"TYPE_PICTURE_VIDEO",
		"TYPE_PICTURE_VIDEO_URL"
	};
	
	public AdCompanion(int adCompanionType) {

		this.adCompanionType = adCompanionType;
		this.adCompanionURLS = new String[NUMBER_OF_ADCOMPANION_COMPONENTS];

	}

	public void setVideoURL(String videoURL) {

		adCompanionURLS[TYPE_VIDEO] = videoURL;
	}

	public void setPictureURL(String pictureURL) {
		adCompanionURLS[TYPE_PICTURE] = pictureURL;
	}

	public void setAdCompanionURL(String adcompURL) {
		adCompanionURLS[TYPE_URL] = adcompURL;
	}
	
	public int getAdCompanionType () {
		return adCompanionType;
	}
	
	
	/**
	 * 
	 * Say you name this object as PICTURE VIDEO and URL
	 * 
	 * and you have set only PICTURE and you are trying to 
	 * get the ad companion image!
	 * now what happens, the object is not yet completely filled
	 * i.e. we have to still fill VIDEO, URL
	 * and then return 
	 * 
	 * */
	private void validateAllFieldsAreFilled( int typeRequested ) throws IllegalArgumentException {
		
		String throwingMessage = " The Adcompanion Object is not yet filled ";
		String errorMessageForWrongRequest = "Illegal request for this type of Ad Companion " ;
		
		
		switch ( adCompanionType ) {
		
			case  TYPE_PICTURE:
				if ( adCompanionURLS[ TYPE_PICTURE ] == null ) {
					throw new IllegalArgumentException( throwingMessage );
				}
				
//				if(  typeRequested != )
				
				if( typeRequested != TYPE_PICTURE ) {
					throw new IllegalArgumentException(errorMessageForWrongRequest);
				}
			break;
			
			case TYPE_VIDEO:
				if ( adCompanionURLS[ TYPE_VIDEO ] == null ) {
					throw new IllegalArgumentException( throwingMessage );
				}
				
				if( typeRequested != TYPE_VIDEO ) {
					throw new IllegalArgumentException(errorMessageForWrongRequest);
				}

			break;
			
			case TYPE_URL:
				if ( adCompanionURLS[ TYPE_URL ] == null ) {
					throw new IllegalArgumentException( throwingMessage );
				}
				
				if( typeRequested != TYPE_URL ) {
					throw new IllegalArgumentException(errorMessageForWrongRequest);
				}
				
			break;
			case TYPE_PICTURE_VIDEO:
				if ( adCompanionURLS[ TYPE_PICTURE ] == null || 
						 adCompanionURLS[ TYPE_VIDEO ] == null) {
					throw new IllegalArgumentException( throwingMessage );
				}
				
				
				
				if( typeRequested != TYPE_PICTURE ||  typeRequested != TYPE_VIDEO) {
					throw new IllegalArgumentException(errorMessageForWrongRequest);
				}
				
			break;
		
			case TYPE_PICTURE_VIDEO_URL:
				if ( adCompanionURLS[ TYPE_PICTURE ] == null || 
						 adCompanionURLS[ TYPE_VIDEO ] == null ||
						 	adCompanionURLS[ TYPE_PICTURE_VIDEO_URL ] == null ) {
					throw new IllegalArgumentException( throwingMessage );
				}
				
				if( typeRequested != TYPE_PICTURE ||  typeRequested != TYPE_VIDEO ||typeRequested != TYPE_URL ) {
					throw new IllegalArgumentException(errorMessageForWrongRequest);
				}

			break;
		}
		
		
	}

	public String getVideoURL ( ) {
		validateAllFieldsAreFilled( TYPE_VIDEO );

		return adCompanionURLS[ TYPE_VIDEO ];
	}
	
	public String getAdcompanionURL () {
		return adCompanionURLS [ TYPE_URL ] ; 
	}
	
	public String getPictureURL () {
		return adCompanionURLS [ TYPE_PICTURE ] ; 
	}
	
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		
		toString.append("AD Companion Type: " + typesOfAdCompanion[adCompanionType] + "\n" );
		if( adCompanionURLS [ TYPE_PICTURE ] != null   ) {
			toString.append("Picture URL: "+ adCompanionURLS[TYPE_PICTURE] + "\n");
		}
		if( adCompanionURLS [ TYPE_VIDEO ] != null   ) {
			toString.append("Video URL: "+ adCompanionURLS[TYPE_VIDEO] + "\n");
		}
		
		if( adCompanionURLS [ TYPE_URL ] != null   ) {
			toString.append("Ad Image URL: "+ adCompanionURLS[TYPE_URL] + "\n");
		}
		return toString.toString();
	}	
}
