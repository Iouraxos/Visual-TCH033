package core;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Ressource {
	
	public final static int TYPE_LOW_PASS_RC = 0;
	public final static int TYPE_HIGH_PASS_RC =1; 
	public final static int TYPE_LOW_PASS_RL = 2; 
	public final static int TYPE_HIGH_PASS_RL = 3;
	public final static int TYPE_BAND_PASS_PAR = 4; 
	public final static int TYPE_BAND_STOP_PAR = 5; 
	
	private static Ressource instanceOf = null;
	
	private Display display;
	
	//private List<Image> imageList;
	private final String[] imageFileNameArray = {"rclp", "rchp", "rllp", "rlhp", "rlcbp", "rlcbs"};
	private Image[] imageArray;

	private Ressource() {
		
		display = new Display();
		
		//imageList = new ArrayList<Image>();
		
		imageArray = new Image[imageFileNameArray.length * 2];
	}
	
	public static Ressource getIntanceOf(){
		
		if(instanceOf == null){
			
			instanceOf = new Ressource();
		}
	
		return instanceOf;

	}
	
	public void load(){
		
		for(int i = 0; i < imageArray.length; i++){
			
			String completeFileName = "./" + imageFileNameArray[i / 2];
			
			// Tous les impairs
			if(i % 2 == 1){
				completeFileName += "wl";
			}
			
			completeFileName += ".png";
			
			System.out.println("Loading picture with filename : " + completeFileName);
			
			imageArray[i] = new Image(Display.getCurrent(), completeFileName);
		}
	}
	
	public void dispose(){
		
		display.dispose();
		
		for(Image image : imageArray){
			
			image.dispose();
		}
		
		System.out.println("Tous les ressources ont correctement été disposées");
	}
	
	// GETTERS
	
	public Display getDisplay() {		
		return display;
	}
	
	public Image getFilterImage(int type, boolean withLoad){
		
		if(type < TYPE_LOW_PASS_RC || type > TYPE_BAND_STOP_PAR){
			
			throw new IllegalArgumentException("L'index utilisée pour accèder l'image n'a pas rapport");
		}
		
		if(withLoad){			
			return imageArray[type * 2 + 1];
		}
		else{
			return imageArray[type * 2];
		}
	}
	
	public Image getImage(int imageIndex){
		
		if(imageIndex < 0 || imageIndex >= imageArray.length){
			
			throw new IllegalArgumentException("L'index utilisée pour accèder l'image n'a pas rapport");
		}
		
		return imageArray[imageIndex];
	}
}