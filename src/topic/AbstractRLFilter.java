package topic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import electrical.Complex;
import electrical.Electrical;
import graph.Coord;
import graph.GainGraphArea;
import graph.Graph;
import graph.PhaseGraphArea;
import graph.Serie;
import nonLinearScale.LogScale;
import widget.E12Scale;
import widget.EngineerNotationText;
import widget.data.FilterBehavior;
import widget.data.PropertiesPassFilter;

public abstract class AbstractRLFilter extends Composite implements SelectionListener {
	
	private final Composite schematicComposite;
	private final StackLayout stackLayout;	
	private final Label schematicNoLoadLabel;	
	private final Label schematiWithLoadLabel;
	
	private final LogScale fScale;
	private final E12Scale rScale;
	private final E12Scale lScale;
	private final E12Scale rlScale;
	
	private final EngineerNotationText xText;
	private final EngineerNotationText ztText;
	private final EngineerNotationText z2Text;	
	
	private final PropertiesPassFilter properties;

	private final FilterBehavior filterBehavior;
	
	protected Image schematicNoLoad;
	protected Image schematiWithLoad;
	
	protected double f = 1.00e3;
	protected double r = 68.0e0;
	protected double l = 10.0e-3;
	protected double rl = Double.POSITIVE_INFINITY;
	
	protected double fcNoLoad;
	protected double fcWithLoad;
	protected double xl;
	protected Complex z2;
	protected Complex zt;
	protected Complex a;
	protected double phi;
	protected double adb;
	
	List<Coord> adbCoordArray;
	List<Coord> phiCoordArray;
	
	private final Graph graph1;
	private final Graph graph2;
//	private final PolarGraph zGraph;

	public AbstractRLFilter(Composite parent, int style) {
		
		super(parent, style);
		
		this.setLayout(new GridLayout(2, false));
		
		// SCHEMATIC
		
		loadImage();
		
		stackLayout = new StackLayout();		
		
		schematicComposite = new Composite(this, SWT.NONE);
		schematicComposite.setLayout(stackLayout);
		schematicComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		
		schematicNoLoadLabel = new Label(schematicComposite, SWT.NONE);
		schematicNoLoadLabel.setImage(schematicNoLoad);
		
		schematiWithLoadLabel = new Label(schematicComposite, SWT.NONE);
		schematiWithLoadLabel.setImage(schematiWithLoad);
		
		
		// GRAPH
		
		TabFolder tabFolder = new TabFolder(this, SWT.TOP);
		tabFolder.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 6));
		
		// Tab 1
		
		TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
		tabItem1.setText("a_dB(f)");
		
		graph1 = new Graph(tabFolder, SWT.NONE);	
		graph1.setTitle("Gain en dB en fonction de la fréquence");
		graph1.setGraphArea(new GainGraphArea());
		
		tabItem1.setControl(graph1);
		
		// Tab 2
		
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		tabItem2.setText("\u03c6 :(f)");
		
		graph2 = new Graph(tabFolder, SWT.NONE);
		graph2.setTitle("Déphasage en fonction de la fréquence");
		graph2.setGraphArea(new PhaseGraphArea());
		
		tabItem2.setControl(graph2);
		
		// Tab 3
		
//		TabItem tabItem3 = new TabItem(tabFolder, SWT.NONE);
//		tabItem3.setText("Z");
//		
//		zGraph = new PolarGraph(tabFolder, SWT.NONE);
//		zGraph.setTitle("Impédances du circuit");
//		
//		tabItem3.setControl(zGraph);
		
		
		
		

		// VARIABLE
		
		Composite variableComposite = new Composite(this, SWT.NONE);
		
		variableComposite.setLayout(new FillLayout(SWT.VERTICAL));
		variableComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		rScale = new E12Scale(variableComposite, SWT.NONE);
		rScale.setLabel("R");
		rScale.setUnit(Electrical.UNIT_OHM);
		rScale.setCenterPowerOfTen(3);
		rScale.setDecadeSpan(3);
		rScale.setValue(r);
		rScale.addSelectionListener(this);
		
		lScale = new E12Scale(variableComposite, SWT.NONE);
		lScale.setLabel("L");
		lScale.setUnit(Electrical.UNIT_HENRY);
		lScale.setCenterPowerOfTen(-3);
		lScale.setDecadeSpan(3);
		lScale.setValue(l);
		lScale.addSelectionListener(this);
		
		rlScale = new E12Scale(variableComposite, SWT.NONE);
		rlScale.setLabel("Résistance de charge (Rl)");
		rlScale.setUnit(Electrical.UNIT_OHM);
		rlScale.setCenterPowerOfTen(3);
		rlScale.setDecadeSpan(3);
		rlScale.enableExtremeMax(Double.POSITIVE_INFINITY);
		rlScale.setValue(rl);
		rlScale.addSelectionListener(this);		
		
		properties = new PropertiesPassFilter(this, SWT.NONE);
		properties.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		fScale = new LogScale(this, SWT.NONE);
		fScale.setLabel("Fréquence");
		fScale.setUnit(Electrical.UNIT_HERTZ);
		fScale.setCenterPowerOfTen(3);
		fScale.setDecadeSpan(3);
		fScale.setValue(f);
		fScale.addSelectionListener(this);
		fScale.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		
		Label label;
		
		// RESULT
		
		Group resultGroup = new Group(this, SWT.NONE);
		
		resultGroup.setText("Résultats");
		resultGroup.setLayout(new GridLayout(3, true));
		resultGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		label = new Label(resultGroup, SWT.NONE);
		label.setText("Xl");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		label = new Label(resultGroup, SWT.NONE);
		label.setText("Z\u2082");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		label = new Label(resultGroup, SWT.NONE);
		label.setText("Zt");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false));
		
		xText = new EngineerNotationText(resultGroup, SWT.NONE);
		xText.setUnit(Electrical.UNIT_OHM);
		xText.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		
		z2Text = new EngineerNotationText(resultGroup, SWT.NONE);
		z2Text.setUnit(Electrical.UNIT_OHM);
		z2Text.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		
		ztText = new EngineerNotationText(resultGroup, SWT.NONE);
		ztText.setUnit(Electrical.UNIT_OHM);
		ztText.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		
		filterBehavior = new FilterBehavior(this, SWT.NONE);
		filterBehavior.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		updateSchematic();
		updateResult();
	}
	
	abstract protected void loadImage();
	
	abstract protected void recalculate(double f);
	
	private void updateSchematic(){
		
		if(rl == Double.POSITIVE_INFINITY){			
			stackLayout.topControl = schematicNoLoadLabel;
		}
		
		else{			
			stackLayout.topControl = schematiWithLoadLabel;
		}
		
		schematicComposite.layout();
	}
	
	private void updateResult(){
		
		sweep();
		
		properties.updateValues(fcNoLoad, fcWithLoad);
		
		List<Double> verticalBarList = new ArrayList<Double>();
		verticalBarList.add(f);
		verticalBarList.add(fcNoLoad);
		verticalBarList.add(fcWithLoad);
		
		List<Serie> serieList = new ArrayList<Serie>(1);
		
		serieList.add(new Serie("A_db", 1, adbCoordArray));
		
		graph1.setVerticalBarList(verticalBarList);
		graph1.setCoordArray(serieList);
		graph1.redraw();
		
		serieList = new ArrayList<Serie>(1);
		
		serieList.add(new Serie("Phi", 1, phiCoordArray));
		
		graph2.setVerticalBarList(verticalBarList);
		graph2.setCoordArray(serieList);
		graph2.redraw();
		
		List<Complex> phasorList = new ArrayList<>();
		phasorList.add(z2);
		phasorList.add(zt);
		
//		zGraph.setPhasorArray(phasorList);
//		zGraph.redraw();
		
		recalculate(f);
		
		xText.setValue(xl);
		ztText.setValue(zt);
		z2Text.setValue(z2);

		filterBehavior.updateValues(a.abs(), adb, phi);
	}
	
	private void sweep(){
		
		double f; //Variable de fréquence locale
		
		final int numberOfPoints = 12*6 + 1;
		
		adbCoordArray = new ArrayList<Coord>(numberOfPoints);
		phiCoordArray  = new ArrayList<Coord>(numberOfPoints);
		
		// On sweep la fréquence
		for(int i = 0; i <= numberOfPoints; i++){

			f = Math.pow(10., (double)i / 12.);
			
			recalculate(f);
			
			adbCoordArray.add(new Coord(f, adb));
			phiCoordArray.add(new Coord(f, phi));
		}
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
		
		// TIMER
		
		if(e.widget == fScale){
			
			f = fScale.getValue();
		}
		
		else if(e.widget == rScale){
			
			r = rScale.getValue();
		}
		
		else if(e.widget == lScale){
			
			l = lScale.getValue();
		}
		
		else if(e.widget == rlScale){
			
			rl = rlScale.getValue();
			updateSchematic();
		}		
		
		else{
			
			System.err.println("Widget non pris en charge par le listener");
		}
		
		updateResult();
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
		System.err.println("Oups, il fallait faire qqch");		
	}
}
