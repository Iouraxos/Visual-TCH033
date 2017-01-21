package graph;

import java.util.ArrayList;
import java.util.List;

import electrical.Electrical;

public class LinearGraphArea extends AbstractGraphArea {

	public LinearGraphArea() {
		
	}
	

	public LinearGraphArea(double XAxisMin, double XAxisMax, double YAxisMin, double YAxisMax) {
		super(XAxisMin, XAxisMax, YAxisMin, YAxisMax);
	}

	@Override
	protected void recalculateAxisGraduationList() {
		
		xAxisGraduationList = linearGraduationList(xAxisMin, xAxisMax);
		yAxisGraduationList = linearGraduationList(yAxisMin, yAxisMax);
	}
	
	// Graduation logarithmique entre 1 et xAxisMAx
	private static List<Graduation> linearGraduationList(double axisMin, double axisMax){
		
		List<Graduation> graduationList = new ArrayList<Graduation>();
		
		//Calcul du span de l'axe
		double axisSpan = axisMax - axisMin;
		
		//System.out.println("axisSpan : " + axisSpan);
		
		//On trouve la puissance de 10 qui représente le span le plus proche possible
		double roundedPower = Math.round(Math.log10(axisSpan));
		
		double corseIncrement = Math.pow(10., roundedPower) / 10.;
		double fineIncrement = corseIncrement / 10.;
		
		//System.out.println("corseIncrement : " + corseIncrement);
		
		// Peu importe que le premier point soit pile ou pas, on le met dans laa liste
		double tempValue = axisMin;
		String tempLabel = Electrical.formatEngineer(tempValue, 3);
		graduationList.add(new Graduation(tempValue, tempLabel));
		
		//Si le premier point n'est pas pile
		if(!Electrical.isMultiple(tempValue, corseIncrement)){			
			tempValue = Math.ceil(tempValue / corseIncrement) * fineIncrement;
		}
		
		//System.out.println("corseIncrement : " + corseIncrement);
		
		while(tempValue <= axisMax){
			

			
			if(Electrical.isMultiple(tempValue, corseIncrement)){
				
				//System.out.println("tempValue : " + tempValue);
				//System.out.println(Math.round(tempValue / corseIncrement) / (tempValue / corseIncrement));
				//System.out.println(Math.abs(Math.round(tempValue / corseIncrement) - (tempValue / corseIncrement)) < 0.00001);
				
				tempLabel = Electrical.formatEngineer(tempValue, 3);
			}
			
			else{
				
				tempLabel = null;
			}
			
			graduationList.add(new Graduation(tempValue, tempLabel));
			
			tempValue += fineIncrement;
		}
		
		return graduationList;
	}
}
