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

public class PropertiesPassFilter extends Composite implements DisposeListener{
	
	private final EngineerNotationText fcNoLoadText;
	private final EngineerNotationText fcWithLoadText;
	
	private final Font dataFont;

	public PropertiesPassFilter(Composite parent, int style) {
		
		super(parent, style);
		
		this.setLayout(new FillLayout());
		
		Group group = new Group(this, SWT.NONE);		
		group.setText("Propriétés du filtre");
		group.setLayout(new GridLayout());
		
		Composite composite;
		
		composite = new Composite(group, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		dataFont = loadFont();
		
		Label label;
		
		label = new Label(composite, SWT.NONE);
		label.setText("Fc sans charge");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		label = new Label(composite, SWT.NONE);
		label.setText("Fc avec charge");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		fcNoLoadText = new EngineerNotationText(composite, SWT.NONE);
		fcNoLoadText.setUnit(Electrical.UNIT_HERTZ);
		fcNoLoadText.setFont(dataFont);
		fcNoLoadText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		fcWithLoadText = new EngineerNotationText(composite, SWT.NONE);
		fcWithLoadText.setUnit(Electrical.UNIT_HERTZ);
		fcWithLoadText.setFont(dataFont);
		fcWithLoadText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		updateValues(0, 0);
		
		this.addDisposeListener(this);
	}
	
	public void updateValues(double fcNoLoad, double fcWithLoad){
		
		fcNoLoadText.setValue(fcNoLoad);
		fcWithLoadText.setValue(fcWithLoad);
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
