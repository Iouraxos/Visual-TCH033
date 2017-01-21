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

public class PropertiesRLC extends Composite implements DisposeListener{
	
	private final EngineerNotationText f1Text;
	private final EngineerNotationText frText;
	private final EngineerNotationText f2Text;
	private final EngineerNotationText bwText;
	private final EngineerNotationText qText;
	
	private final Font dataFont;

	public PropertiesRLC(Composite parent, int style) {
		
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
		
		label = new Label(composite, SWT.NONE);
		label.setText("f1");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		label = new Label(composite, SWT.NONE);
		label.setText("fr");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		label = new Label(composite, SWT.NONE);
		label.setText("f2");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		f1Text = new EngineerNotationText(composite, SWT.NONE);
		f1Text.setUnit(Electrical.UNIT_HERTZ);
		f1Text.setFont(dataFont);
		f1Text.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
				
		frText = new EngineerNotationText(composite, SWT.NONE);
		frText.setUnit(Electrical.UNIT_HERTZ);
		frText.setFont(dataFont);
		frText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		f2Text = new EngineerNotationText(composite, SWT.NONE);
		f2Text.setUnit(Electrical.UNIT_HERTZ);
		f2Text.setFont(dataFont);
		f2Text.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
	    // Create a horizontal separator
	    Label separator = new Label(group, SWT.HORIZONTAL | SWT.SEPARATOR);
	    separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    
		composite = new Composite(group, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		label = new Label(composite, SWT.NONE);
		label.setText("Bande passante (BP)");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		label = new Label(composite, SWT.NONE);
		label.setText("Facteur de qualité (Q)");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
				
		bwText = new EngineerNotationText(composite, SWT.NONE);
		bwText.setUnit(Electrical.UNIT_HERTZ);
		bwText.setFont(dataFont);
		bwText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		qText = new EngineerNotationText(composite, SWT.NONE);
		qText.setUnit("");
		qText.setFont(dataFont);
		qText.setForceEngineerNotation(false);
		qText.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		updateValues(0, 0, 0, 0, 0);
		
		this.addDisposeListener(this);
	}
	
	public void updateValues(double f1, double fr, double f2, double bw, double q){
		
		f1Text.setValue(f1);
		frText.setValue(fr);
		f2Text.setValue(f2);
		bwText.setValue(bw);
		qText.setValue(q);
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
