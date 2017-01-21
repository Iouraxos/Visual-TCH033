package widget.data;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import electrical.Electrical;
import widget.EngineerNotationText;

public class FilterBehavior extends Composite implements DisposeListener{
	
	private final EngineerNotationText aText;
	private final EngineerNotationText adbText;
	private final EngineerNotationText phiText;
	
	private final Font dataFont;

	public FilterBehavior(Composite parent, int style) {
		
		super(parent, style);

		this.setLayout(new FillLayout());
		
		Group group = new Group(this, SWT.NONE);
		
		group.setText("Comportement du filtre à la fréquence");
		group.setLayout(new GridLayout(3, true));
		
		dataFont = loadFont();
		
		Label label;
		
		label = new Label(group, SWT.NONE);
		label.setText("Gain");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		label = new Label(group, SWT.NONE);
		label.setText("Gain (dB)");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		label = new Label(group, SWT.NONE);
		label.setText("\u03c6");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		aText = new EngineerNotationText(group, SWT.NONE);
		aText.setUnit("");
		aText.setFont(dataFont);
		aText.setForceEngineerNotation(false);
		aText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		adbText = new EngineerNotationText(group, SWT.NONE);
		adbText.setUnit(Electrical.UNIT_DECIBEL);
		adbText.setFont(dataFont);
		adbText.setForceEngineerNotation(false);
		adbText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		phiText = new EngineerNotationText(group, SWT.NONE);
		phiText.setUnit(Electrical.UNIT_DEGREE);
		phiText.setFont(dataFont);
		phiText.setForceEngineerNotation(false);
		phiText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
	}
	
	public void updateValues(double a, double adb, double phi){
		
		aText.setValue(a);
		adbText.setValue(adb);
		phiText.setValue(phi);
	}
	
	private Font loadFont(){
		
		// Title font
		FontData[] fontDataArray = this.getDisplay().getSystemFont().getFontData();
		
		Font font = null;
		
		if(fontDataArray != null && fontDataArray.length > 0){
			
			fontDataArray[0].setHeight(16);
			
			font = new Font(this.getDisplay(), fontDataArray[0]);
		}
		else{
			
			System.err.println("Gros problème en essayant de loader la font");
		}
		
		return font;
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		dataFont.dispose();		
	}
}
