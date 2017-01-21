package nonLinearScale;

import org.eclipse.swt.widgets.Composite;

public class LogScale extends AbstractNonLinearScale {

	public LogScale(Composite parent, int style) {
		super(parent, style);
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
			
			double interval = (double)(scaleMax - scaleMin) / (double)(decadeSpan * 2);
			//System.out.println("interval :" + interval + group.getText());
			value = Math.pow(10., (double)scaleSelection / interval - decadeSpan + centerPowerOfTen);
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
