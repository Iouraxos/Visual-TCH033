package topic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import electrical.Complex;
import electrical.Electrical;
import graph.Coord;
import graph.GainGraphArea;
import graph.Graph;
import graph.LinearGraphArea;
import graph.LogGraphArea;
import graph.Serie;
import nonLinearScale.LogScale;
import widget.E12Scale;

public class IntegratorRC extends Composite implements SelectionListener {
	
	private Label schematicLabel;
	
	private final Graph graph1;
	
	private final LogScale fScale;
	private final Scale dutyScale;
	private final E12Scale rScale;
	private final E12Scale cScale;
	
	protected double f = 1.00e3;
	private double duty;
	protected double r = 1000.;
	protected double c = 2.20e-6;

	public IntegratorRC(Composite parent, int style) {
		
		super(parent, style);
		
		this.setLayout(new GridLayout(2, false));
		
		schematicLabel = new Label(this, SWT.NONE);
		schematicLabel.setImage(null);
		schematicLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		
		// GRAPH
		
		TabFolder tabFolder = new TabFolder(this, SWT.TOP);
		tabFolder.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 6));
		
		// Tab 1
		
		TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
		tabItem1.setText("Vout(t)");
		
		graph1 = new Graph(tabFolder, SWT.NONE);	
		graph1.setTitle("Évolution de la tension de sortie en fonction du temps");
		graph1.setGraphArea(new LinearGraphArea(-0.1, 1.1, -6., 6.));
		
		tabItem1.setControl(graph1);
		
		// VARIABLE
		
		Composite variableComposite = new Composite(this, SWT.NONE);
		
		variableComposite.setLayout(new FillLayout(SWT.VERTICAL));
		variableComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		fScale = new LogScale(this, SWT.NONE);
		fScale.setLabel("Fréquence");
		fScale.setUnit(Electrical.UNIT_HERTZ);
		fScale.setCenterPowerOfTen(3);
		fScale.setDecadeSpan(3);
		fScale.setValue(f);
		fScale.addSelectionListener(this);
		fScale.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		dutyScale = new Scale(variableComposite, SWT.NONE);
		dutyScale.setMinimum(1);
		dutyScale.setMaximum(100);
		dutyScale.addSelectionListener(this);
		
		rScale = new E12Scale(variableComposite, SWT.NONE);
		rScale.setLabel("R");
		rScale.setUnit(Electrical.UNIT_OHM);
		rScale.setCenterPowerOfTen(3);
		rScale.setDecadeSpan(3);
		rScale.setValue(r);
		rScale.addSelectionListener(this);
		
		cScale = new E12Scale(variableComposite, SWT.NONE);
		cScale.setLabel("C");
		cScale.setUnit(Electrical.UNIT_FARAD);
		cScale.setCenterPowerOfTen(-6);
		cScale.setDecadeSpan(6);
		cScale.setValue(c);
		cScale.addSelectionListener(this);
		
		updateResult();
	}
	
	private void updateResult(){
		
		System.out.println("yo");
		
		List<Serie> vList = scan();
		
//		List<Double> verticalBarList = new ArrayList<Double>();
//		verticalBarList.add(f);
//		verticalBarList.add(f1);
//		verticalBarList.add(f2);
//		verticalBarList.add(fr);
		
//		graph1.setVerticalBarList(verticalBarList);
		graph1.setCoordArray(vList);
		graph1.redraw();
//		
//		graph2.setVerticalBarList(verticalBarList);
//		graph2.setCoordArray(phiCoordArray);
//		graph2.redraw();
//		
//		List<Complex> phasorList = new ArrayList<>();
//		phasorList.add(z2);
//		phasorList.add(zt);
//		
////		zGraph.setPhasorArray(phasorList);
////		zGraph.redraw();
//		
//		recalculate(f);
//
//		xcText.setValue(xc);
//		xlText.setValue(xl);
//		ztText.setValue(zt);
//		z2Text.setValue(z2);
//		
//		filterBehavior.updateValues(a.abs(), adb, phi);
	}
	
	private List<Serie> scan(){
		
		double tMin = 0;
		double tMax = 1;
		int nbPoints = 200;
		
		//double period = 50;
		
		double tIncrement = (tMax-tMin) / (double)nbPoints;
		
		List<Coord> vinList = new ArrayList<>(nbPoints);
		List<Coord> vcList = new ArrayList<>(nbPoints);
		
		double vc = 0;
		double vsMax = 0;
		double vsMin = 0;
		
		for(int i = 0; i < nbPoints; i++){
			
			double t = tMin + tIncrement * (double)i;
			double vin = 0.;
			
			if(t >= 0. && t < .25){
				
				vin = 5.;
			}
			else if(t >= .25 && t < .50){
				
				vin = 0;
			}
			else if(t >= .50 && t < .75){
				
				vin = 5;
			}
			else if(t >= .75){
				
				vin = 0.;
			}
			
			if(vin > vsMax){
				vsMax = vin;
			}
			else if(vin < vsMin){
				vsMin = vin;
			}
			
			double vr = vin - vc;
			double ir = vr / r;
			vc = vc + (ir * tIncrement) / c;
			
			// garde fou
			if(vc > vsMax){				
				vc = vsMax;
			}
			else if(vc < vsMin){
				vc = vsMin;
			}
			
			//System.out.println("t :" + t + ", vc :" + vc + ", ir :" + ir);
			
			vinList.add(new Coord(t, vin));
			vcList.add(new Coord(t, vc));
		}
		
		List<Serie> serieList = new ArrayList<>(2);
		
		serieList.add(new Serie("Vin", 1, vinList));
		serieList.add(new Serie("Vc", 2, vcList));
		
		return serieList;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
		if(e.widget == fScale){
			
			f = fScale.getValue();
		}
		
		else if(e.widget == dutyScale){
			
			duty = dutyScale.getSelection();
		}	
		
		else if(e.widget == rScale){
			
			r = rScale.getValue();
		}
		
		else if(e.widget == cScale){
			
			c = cScale.getValue();
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
