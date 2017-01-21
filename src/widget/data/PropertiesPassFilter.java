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
	
	private final EngineerNotationText fcText;
	
	private final Font dataFont;

	public PropertiesPassFilter(Composite parent, int style) {
		
		super(parent, style);
		
		this.setLayout(new FillLayout());
		
		Group group = new Group(this, SWT.NONE);		
		group.setText("Propriétés du filtre");
		group.setLayout(new GridLayout());
		
		Composite composite;
		
		composite = new Composite(group, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		dataFont = loadFont();
		
		Label label;
		
		label = new Label(group, SWT.NONE);
		label.setText("Fc");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		fcText = new EngineerNotationText(group, SWT.NONE);
		fcText.setUnit(Electrical.UNIT_HERTZ);
		fcText.setFont(dataFont);
		fcText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		updateValues(0);
		
		this.addDisposeListener(this);
	}
	
	public void updateValues(double fc){
		
		fcText.setValue(fc);
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
