package core;

import java.util.Observable;


public class Model extends Observable {
	
	private static Model instanceOf = null;
	
	private Model(){
	

	}
	
	public static Model getIntanceOf(){
		
		if(instanceOf == null){
			
			instanceOf = new Model();
		}
	
		return instanceOf;

	}
}