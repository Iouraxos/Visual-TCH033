package core;

/**
 * @author iouri
 *
 */
public class Controller{

	private static Controller instanceOf = null;
	
	//The Big brothers
	private Model model = null;
	private View view = null;
	private Ressource ressource = null;
	
	public Controller() {
		
		model = Model.getIntanceOf();
		ressource = Ressource.getIntanceOf();		
	}
	
	public static Controller getIntanceOf(){
		
		if(instanceOf == null){
			
			instanceOf = new Controller();
		}
		
		return instanceOf;
	}
	
	public void run(){
		
		ressource.load();
		
		view = new View();
		view.start();
		
		//System.out.println("Stoping the ball");
	}

	//View action	
	public void viewActionDispose(){
		
		ressource.dispose();		
	}	


	//
	// MAIN
	//
	public static void main(String[] args) {

		Controller.getIntanceOf().run();
	}
}
