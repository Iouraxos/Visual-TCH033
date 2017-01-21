package graph;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import electrical.Complex;

public class PolarGraph extends Canvas implements PaintListener, DisposeListener {

	private final Color COLOR_BLACK;
	private final Color COLOR_DARK_GRAY;
	private final Color COLOR_GRAY;
	private final Color COLOR_WHITE;
	private final Color COLOR_RED;
	private final Color COLOR_GREEN;
	
	private String title = "";
	private Font titleFont;
	
	private int leftMargin = 50;
	private int rightMargin = 30;
	private int topMargin = 50;
	private int bottomMmargin = 20;
	
	private List<Complex> phasorArray;
	
	public PolarGraph(Composite parent, int style) {
		super(parent, style);
		
		Display currentDisplay = Display.getCurrent();
		
		COLOR_BLACK = currentDisplay.getSystemColor(SWT.COLOR_BLACK);
		COLOR_DARK_GRAY = currentDisplay.getSystemColor(SWT.COLOR_DARK_GRAY);
		COLOR_GRAY = currentDisplay.getSystemColor(SWT.COLOR_GRAY);
		COLOR_WHITE = currentDisplay.getSystemColor(SWT.COLOR_WHITE);
		COLOR_RED = currentDisplay.getSystemColor(SWT.COLOR_RED);
		COLOR_GREEN = currentDisplay.getSystemColor(SWT.COLOR_GREEN);
			
		// Title font
		FontData[] fontDataArray = currentDisplay.getSystemFont().getFontData();
		
		if(fontDataArray != null && fontDataArray.length > 0){
			
			fontDataArray[0].setHeight(16);
			
			titleFont = new Font(currentDisplay, fontDataArray[0]);
		}
		else{
			
			System.err.println("Gros problème en essayant de loader la font");
		}
		
		this.addPaintListener(this);
		this.addDisposeListener(this);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setPhasorArray(List<Complex> phasorArray) {
		this.phasorArray = phasorArray;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		
		int totalWidth = leftMargin + 400 + rightMargin;
		int toalHeight = topMargin + 400 + bottomMmargin;
		
		return new Point(totalWidth, toalHeight);
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		
		System.out.println("Dispose des ressources du graph polaire : " + title);
		
		COLOR_BLACK.dispose();
		COLOR_DARK_GRAY.dispose();
		COLOR_GRAY.dispose();
		COLOR_GREEN.dispose();
		COLOR_RED.dispose();
		COLOR_WHITE.dispose();
		
		titleFont.dispose();
	}

	@Override
	public void paintControl(PaintEvent e) {
		
		// Dessin du Titre
		e.gc.setForeground(COLOR_BLACK);
		e.gc.setFont(titleFont);
		e.gc.drawText(title, leftMargin, 10);
		e.gc.setFont(null);
		
		// Dessin du rond blanc
		e.gc.setBackground(COLOR_WHITE);		
		e.gc.fillOval(leftMargin, topMargin, 400, 400);
		
		List<Graduation> graduationList = new ArrayList<>();
		
		graduationList.add(new Graduation(0.1, "0.1"));
		graduationList.add(new Graduation(0.3, "0.3"));
		graduationList.add(new Graduation(0.7, "0.7"));
		
		for(Graduation graduation : graduationList){

			double radius = graduation.getValue();
			int xPos = radiusToXPos(radius);
			int yPos = radiusToYPos(radius);
			int distance = radiusToDistance(radius);

			e.gc.setForeground(COLOR_GRAY);
			e.gc.drawOval(xPos, yPos, distance * 2, distance * 2);
			
//			e.gc.setForeground(COLOR_BLACK);
//			e.gc.drawLine(x, topMargin + graphArea.getHeight() - 2, x, topMargin + graphArea.getHeight() + 2);
			
			if(graduation.getLabel() != null){
				
				e.gc.drawText(graduation.getLabel(), xPos, yPos, true);
			}			
		}
		
		graduationList = new ArrayList<>();
		
		graduationList.add(new Graduation(0, "0.1"));
		graduationList.add(new Graduation(90, "0.3"));
		graduationList.add(new Graduation(180, "0.7"));
		
		int xPosCenter = radiusToXPos(0.);
		int yPosCenter = radiusToYPos(0.);
		
		for(Graduation graduation : graduationList){
			
			Point endPos = complexToPos(Complex.getComplexPol(1., graduation.getValue()));			

			e.gc.setForeground(COLOR_GRAY);
			e.gc.drawLine(xPosCenter, yPosCenter, endPos.x, endPos.y);
			
			e.gc.setForeground(COLOR_BLACK);
//			e.gc.drawLine(leftMargin - 2, y, leftMargin + 2, y);
			
			if(graduation.getLabel() != null){
				
				String label = graduation.getLabel();
				Point labelDimension = e.gc.stringExtent(label);
				
				e.gc.drawText(graduation.getLabel(), endPos.x , endPos.y , true);
			}
		}
		
		// Box the whole shit
		e.gc.setForeground(COLOR_DARK_GRAY);
		e.gc.drawOval(leftMargin, topMargin, 400, 400);
		
		// Dessin de la fonction		
		if(phasorArray != null){
			
			e.gc.setForeground(COLOR_RED);
			e.gc.setLineWidth(2);
			
			for(Complex currentPhasor : phasorArray){				

				Point pos = complexToPos(currentPhasor);

				e.gc.drawLine(xPosCenter, yPosCenter, pos.x, pos.y);
			}
		}		
	}
	
	private Point complexToPos(Complex c){
		
		int xPos = leftMargin + 200 + (int)Math.round(200. * c.real());
		int yPos = topMargin + 200 - (int)Math.round(200. * c.imag());
		
		return new Point(xPos, yPos);
	}
	
	private int radiusToXPos(double radius){
		
		return leftMargin + 200 - (int)Math.round(200. * radius);
	}
	
	private int radiusToYPos(double radius){
		
		return topMargin + 200 - (int)Math.round(200. * radius);
	}
	
	private int radiusToDistance(double radius){
		
		return (int)Math.round(200. * radius);
	}
}
