package graph;

import java.util.ArrayList;

import electrical.Electrical;

public class LogGraphArea extends AbstractGraphArea {

	public LogGraphArea() {
		
		setAxisLimits(1.00e0, 1.00e6, 0., 1.);
	}

	@Override
	protected void recalculateAxisGraduationList() {
		
		System.out.println("recalculateAxisGraduationList()");
		
		recalculateXAxisGraduationList();
		recalculateYAxisGraduationList();			
	}
	
	@Override
	public int xCoordToXPos(double xCoord){
		
		return (int)Math.round(Math.log10(xCoord) * (width / Math.log10(xAxisMax)));
	}
	
	// Graduation logarithmique entre 1 et xAxisMAx
	protected void recalculateXAxisGraduationList(){
		
		xAxisGraduationList = new ArrayList<Graduation>();
		
		int tempValue = 1;
		String tempLabel = null;
		int increment = 1;
		boolean newDecade = true;
		
		while(tempValue <= xAxisMax){
			
			if(newDecade){
				
				tempLabel = Electrical.formatEngineer(tempValue, 3);
				newDecade = false;
			}
			
			else{
				
				tempLabel = null;
			}
			
			xAxisGraduationList.add(new Graduation(tempValue, tempLabel));
			
			tempValue += increment;
			
			if(tempValue >= 10 * increment){
				
				increment *= 10;
				newDecade = true;
			}
		}
	}
	
	// Graduation linéaire avec un label une fois sur deux
	protected void recalculateYAxisGraduationList(){
		
		yAxisGraduationList = new ArrayList<Graduation>();
		
		int tempValue = (int)yAxisMin;
		String tempLabel = null;
		int increment = 1;
		
		while(tempValue <= yAxisMax){
			
			if(tempValue % 2 == 0){
				
				tempLabel = Electrical.formatEngineer(tempValue, 3);
			}
			
			else{
				
				tempLabel = null;
			}
			
			yAxisGraduationList.add(new Graduation(tempValue, tempLabel));
			
			tempValue += increment;
		}	
	}
}
