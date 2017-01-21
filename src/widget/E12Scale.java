package widget;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import nonLinearScale.AbstractNonLinearScale;

public class E12Scale extends AbstractNonLinearScale implements SelectionListener {
	
	private final int[] e12Array = {10, 12, 15, 18, 22, 27, 33, 39, 47, 56, 68, 82}; 

	public E12Scale(Composite parent, int style) {		
		super(parent, style);
	}

	@Override
	public void setDecadeSpan(int decadeSpan) {
		
		super.setDecadeSpan(decadeSpan);

		scaleMax = 12 * decadeSpan * 2;
		scale.setMaximum(scaleMax);
	}
	
	@Override
	protected double scaleSelectionToValue(int scaleSelection){
		
		double value;		
		
		if(extremeMinEnabled && scaleSelection == scaleMin){
			value = extremeMinValue;
		}
		
		else if(extremeMaxEnabled && scaleSelection == scaleMax){
			value = extremeMaxValue;
		}
		
		else{
			
			int powerOfTen = scaleSelection / 12;
			
			double multiplier = Math.pow(10., powerOfTen - 1 - decadeSpan + centerPowerOfTen);
			
			value = e12Array[scaleSelection % 12] * multiplier;
		}
		
		return value;
	}
	
	@Override
	protected int valueToScaleSelection(double value){
		
		int scaleSelection;
		
		if(extremeMinEnabled && value <= extremeMinValue){
			scaleSelection = scaleMin;
		}
		
		else if(extremeMaxEnabled && value >= extremeMaxValue){
			scaleSelection = scaleMax;
		}
		
		else{
		
			double interval = (double)(scaleMax - scaleMin) / (double)(decadeSpan * 2);
			//System.out.println("interval :" + interval + group.getText());
			scaleSelection = (int)((Math.log10(value) + decadeSpan - centerPowerOfTen) * interval);
		}
		
		return scaleSelection;
	}
}
