package nonLinearScale;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Scale;

import widget.EngineerNotationText;

public abstract class AbstractNonLinearScale extends Composite implements SelectionListener {
	
	private Vector<SelectionListener> selectionListenerVector;
	
	private final Group group;
	protected final Scale scale;
	private final EngineerNotationText display;
	private final Button prevButton;
	private final Button nextButton;
	
	protected double value = 1.;
	
	protected int scaleMin = 0;
	protected int scaleMax = 1000;
	
	protected int centerPowerOfTen = 0;
	protected int decadeSpan = 3;
	
	protected boolean extremeMinEnabled = false;
	protected double extremeMinValue = 0.;
	protected boolean extremeMaxEnabled = false;
	protected double extremeMaxValue = Double.POSITIVE_INFINITY;

	public AbstractNonLinearScale(Composite parent, int style) {
		
		super(parent, style);
		
		selectionListenerVector = new Vector<SelectionListener>();
		
		this.setLayout(new FillLayout());
		
		group = new Group(this, SWT.NONE);		
		group.setLayout(new GridLayout(4, false));
		
		prevButton = new Button(group, SWT.ARROW | SWT.LEFT);
		prevButton.addSelectionListener(this);
		
		scale = new Scale(group, SWT.NONE);
		scale.setMinimum(scaleMin);
		scale.setMaximum(scaleMax);
		scale.addSelectionListener(this);
		scale.setLayoutData(new GridData(200, SWT.DEFAULT));
		
		nextButton = new Button(group, SWT.ARROW | SWT.RIGHT);
		nextButton.addSelectionListener(this);
		
		display = new EngineerNotationText(group, SWT.NONE);
		display.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}
	
	
	public void addSelectionListener(SelectionListener selectionListener){
		
		selectionListenerVector.add(selectionListener);
	}
	
	
	public void removeSelectionListener(SelectionListener selectionListener){
				
		selectionListenerVector.removeElement(selectionListener);
	}
	
	public void setLabel(String label) {
		group.setText(label);
	}


	public void setUnit(String unit) {
		display.setUnit(unit);
	}
	

	public double getValue() {
		return value;
	}
	
	public void enableExtremeMin(double extremeMinValue){
		this.extremeMinValue = extremeMinValue;
		extremeMinEnabled = true;
	}
	
	public void disableExtremeMin(){
		extremeMinEnabled = false;
	}
	
	public void enableExtremeMax(double extremeMaxValue){
		this.extremeMaxValue = extremeMaxValue;
		extremeMaxEnabled = true;
	}
	
	public void disableExtremeMax(){
		extremeMaxEnabled = false;
	}


	public void setValue(double value) {
		this.value = value;
		scale.setSelection(valueToScaleSelection(value));
		updateButtons(scale.getSelection());
		display.setValue(value);
	}


	public void setCenterPowerOfTen(int centerPowerOfTen) {
		this.centerPowerOfTen = centerPowerOfTen;
		value = scaleSelectionToValue(scale.getSelection());
		updateButtons(scale.getSelection());
		display.setValue(value);
	}


	public void setDecadeSpan(int decadeSpan) {
		this.decadeSpan = decadeSpan;
		value = scaleSelectionToValue(scale.getSelection());
		updateButtons(scale.getSelection());
		display.setValue(value);
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
		
		int scaleSelection = scale.getSelection();
		
		if(e.widget == prevButton){
			
			scaleSelection--;
			scale.setSelection(scaleSelection);
		}
		
		else if(e.widget == nextButton){
			
			scaleSelection++;
			scale.setSelection(scaleSelection);
		}
		
		updateButtons(scaleSelection);
		
		value = scaleSelectionToValue(scaleSelection);
		
		display.setValue(value);
		
		e.widget = this;
		
		for(int i = 0; i < selectionListenerVector.size(); i ++){
			
			selectionListenerVector.elementAt(i).widgetSelected(e);
		}
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
		System.err.println("Oups, il fallait faire qqch");
	}
	
	private void updateButtons(int scaleSelection){
		
		if(scaleSelection == scaleMin){
			prevButton.setEnabled(false);
			nextButton.setEnabled(true);
		}
		
		else if(scaleSelection == scaleMax){
			prevButton.setEnabled(true);
			nextButton.setEnabled(false);
		}
		
		else{
			prevButton.setEnabled(true);
			nextButton.setEnabled(true);
		}
	}
	
	abstract protected double scaleSelectionToValue(int scaleSelection);
	
	abstract protected int valueToScaleSelection(double value);
}
