package widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import electrical.Complex;
import electrical.Electrical;

public class EngineerNotationText extends Composite {
	
	private final Label text;
	
	private double realValue = 0.;
	private Complex complexValue;
	private boolean useComplexValue = false;
	
	private String unit = "";
	private boolean forceEngineerNotation = true;

	public EngineerNotationText(Composite parent, int style) {
		
		super(parent, style);
		
		this.setLayout(new FillLayout());
		
		text = new Label(this, SWT.NONE);
		text.setText("0,00");
	}

	public void setValue(double value) {
		this.realValue = value;
		useComplexValue = false;
		text.setText(convertValueToString() + unit);
	}
	
	public void setValue(Complex value) {
		this.complexValue = value;
		useComplexValue = true;
		text.setText(convertValueToString() + unit);
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
		text.setText(convertValueToString() + unit);
	}
	
	public void setForceEngineerNotation(boolean forceEngineerNotation) {
		this.forceEngineerNotation = forceEngineerNotation;
	}	

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {

		if(wHint < 160){
			
			if(useComplexValue){
				wHint = 160;	//110
			}
			else{
				wHint = 90;		//52
			}
		}
		return super.computeSize(wHint, hHint, changed);
	}
	
	@Override
	public void setFont(Font font) {
		text.setFont(font);
	}

	private String convertValueToString(){
		
		String valueAsString;
		
		if(useComplexValue){
			
			valueAsString = Electrical.formatEngineer(complexValue, 3);
		}
		
		else{
		
			if(forceEngineerNotation){
				
				valueAsString = Electrical.formatEngineer(realValue, 3);
			}
			
			else{
				
				valueAsString = Electrical.formatPrecision(realValue, 3);
			}
		}
		
		return valueAsString;
	}
}
