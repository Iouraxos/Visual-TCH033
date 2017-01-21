package graph;

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


public class Graph extends Canvas implements PaintListener, DisposeListener {
	
	private final Color COLOR_BLACK;
	private final Color COLOR_DARK_GRAY;
	private final Color COLOR_GRAY;
	private final Color COLOR_WHITE;
	private final Color COLOR_RED;
	private final Color COLOR_GREEN;
	private final Color COLOR_BLUE;
	private final Color COLOR_MAGENTA;
	
	private String title = "";
	private Font titleFont;
	
	private int leftMargin = 50;
	private int rightMargin = 30;
	private int topMargin = 50;
	private int bottomMmargin = 20;
	
	private List<Serie> serieList;
	
	private List<Double> verticalBarList;
	
	private IGraphArea graphArea;
	
	private boolean showDot = true;

	public Graph(Composite parent, int style) {
		
		super(parent, style | SWT.DOUBLE_BUFFERED);
		
		Display currentDisplay = Display.getCurrent();
		
		COLOR_BLACK = currentDisplay.getSystemColor(SWT.COLOR_BLACK);
		COLOR_DARK_GRAY = currentDisplay.getSystemColor(SWT.COLOR_DARK_GRAY);
		COLOR_GRAY = currentDisplay.getSystemColor(SWT.COLOR_GRAY);
		COLOR_WHITE = currentDisplay.getSystemColor(SWT.COLOR_WHITE);
		COLOR_RED = currentDisplay.getSystemColor(SWT.COLOR_RED);
		COLOR_GREEN = currentDisplay.getSystemColor(SWT.COLOR_GREEN);
		COLOR_BLUE = currentDisplay.getSystemColor(SWT.COLOR_BLUE);
		COLOR_MAGENTA = currentDisplay.getSystemColor(SWT.COLOR_MAGENTA);
		
		graphArea = new PhaseGraphArea();
			
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
	
	public void setGraphArea(IGraphArea graphArea) {
		this.graphArea = graphArea;
	}

	public void setCoordArray(List<Serie> serieList) {
		this.serieList = serieList;		
	}
	
	public void setVerticalBarList(List<Double> verticalBarList) {
		this.verticalBarList = verticalBarList;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		
		int totalWidth = leftMargin + graphArea.getWidth() + rightMargin;
		int toalHeight = topMargin + graphArea.getHeight() + bottomMmargin;
		
		return new Point(totalWidth, toalHeight);
	}
	

	@Override
	public void paintControl(PaintEvent e) {
		
		// Dessin du Titre
		e.gc.setForeground(COLOR_BLACK);
		e.gc.setFont(titleFont);
		e.gc.drawText(title, leftMargin, 10, true);
		e.gc.setFont(null);
		
		// Dessin du carré blanc
		e.gc.setBackground(COLOR_WHITE);		
		e.gc.fillRectangle(leftMargin, topMargin, graphArea.getWidth(), graphArea.getHeight());
		
		// Mon premier itérateur à vie, bravo Iouri
		for(Graduation graduation : graphArea.getXAxisGraduationList()){

			int x = xCoordToXPos(graduation.getValue());			

			e.gc.setForeground(COLOR_GRAY);
			e.gc.drawLine(x, topMargin + graphArea.getHeight() + 2, x, topMargin);
			
			e.gc.setForeground(COLOR_BLACK);
			e.gc.drawLine(x, topMargin + graphArea.getHeight() - 2, x, topMargin + graphArea.getHeight() + 2);
			
			if(graduation.getLabel() != null){
				
				e.gc.drawText(graduation.getLabel(), x, topMargin + graphArea.getHeight() + 2, true);
			}			
		}
		
		// Mon deuxième itérateur à vie, bravo Iouri
		for(Graduation graduation : graphArea.getYAxisGraduationList()){
			
			int y = yCoordToYPos(graduation.getValue());			

			e.gc.setForeground(COLOR_GRAY);
			e.gc.drawLine(leftMargin + 2, y, leftMargin + graphArea.getWidth(), y);
			
			e.gc.setForeground(COLOR_BLACK);
			e.gc.drawLine(leftMargin - 2, y, leftMargin + 2, y);
			
			if(graduation.getLabel() != null){
				
				String label = graduation.getLabel();
				Point labelDimension = e.gc.stringExtent(label);
				
				e.gc.drawText(graduation.getLabel(), leftMargin - labelDimension.x -4 , y - labelDimension.y / 2 , true);
			}
		}
		
		
		// Dessin des axes
		e.gc.setForeground(COLOR_BLACK);
		if(graphArea.isXCoordInside(0.)){
			e.gc.drawLine(xCoordToXPos(0.), topMargin, xCoordToXPos(0.), topMargin + graphArea.getHeight());
		}
		
		if(graphArea.isYCoordInside(0.)){
			e.gc.drawLine(leftMargin, yCoordToYPos(0.), leftMargin + graphArea.getWidth(), yCoordToYPos(0.));
		}
		
		// Box the whole shit
		e.gc.setForeground(COLOR_DARK_GRAY);
		e.gc.drawRectangle(leftMargin, topMargin, graphArea.getWidth(), graphArea.getHeight());
		
		// Dessin de la fonction		
		if(serieList != null){			
			
			System.out.println("Il y a " + serieList.size() + " courbe(s) a tracer");
			
			e.gc.setForeground(COLOR_RED);
			e.gc.setLineWidth(2);
			
			e.gc.setClipping(leftMargin - 1, topMargin - 1, graphArea.getWidth() + 2, graphArea.getHeight() + 2);
			
			Coord previousCoord = null;
			
			for(Serie currentSerie : serieList){
			
				for(Coord currentCoord : currentSerie.getCoordList()){
					
					double xCoord = currentCoord.getX();
					double yCoord = currentCoord.getY();
					
					// Seulement si le point est dans le graphique
					//if(graphArea.isXCoordInside(xCoord) && graphArea.isYCoordInside(yCoord)){
						
						int xPos = xCoordToXPos(xCoord);
						int yPos = yCoordToYPos(yCoord);
						
						// System.out.println("Dessin d'un point à :" + currentCoord.getX() + ", " + currentCoord.getY());
						
						if(showDot){
							e.gc.drawOval(xPos - 2, yPos - 2, 4, 4);						
						}
						
						// Si il y a une coordonnée précédente, on peut tracer la ligne
						if(previousCoord != null){
							
							e.gc.drawLine(xPos, yPos, xCoordToXPos(previousCoord.getX()), yCoordToYPos(previousCoord.getY()));
						}
						
						previousCoord = currentCoord;
					//}
				}
			}
		}
		
		// Dessin des barres verticale
		if(verticalBarList != null){
			
			int i = 0; //patch super scketchy
			
			for(Double verticalBar : verticalBarList){
				
				if(graphArea.isXCoordInside(verticalBar)){
					
					switch(i){
					case 0:
						e.gc.setForeground(COLOR_GREEN);
						break;
					case 1:
					case 2:
						e.gc.setForeground(COLOR_BLUE);
						break;
					default:
						e.gc.setForeground(COLOR_MAGENTA);
					}
					
					int xPos = xCoordToXPos(verticalBar);
					
					e.gc.drawLine(xPos, topMargin, xPos, topMargin + graphArea.getHeight());
				}
				
				i++;
			}
		}
		
		//debug stuff
//		e.gc.setForeground(COLOR_RED);
//		System.out.println("(x, y) " + xCoordToXPos(10) + " " + yCoordToYPos(10));
//		e.gc.drawLine(xCoordToXPos(0), yCoordToYPos(0), xCoordToXPos(6), yCoordToYPos(6));
	}
	
	
	private int xCoordToXPos(double xCoord){
		
		return leftMargin +  graphArea.xCoordToXPos(xCoord);
	}
	
		
	private int yCoordToYPos(double yCoord){
		
		return topMargin + graphArea.yCoordToYPos(yCoord);
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		
		System.out.println("Dispose des ressources du graph : " + title);
		
		COLOR_BLACK.dispose();
		COLOR_DARK_GRAY.dispose();
		COLOR_GRAY.dispose();
		COLOR_GREEN.dispose();
		COLOR_RED.dispose();
		COLOR_WHITE.dispose();
		COLOR_BLUE.dispose();
		COLOR_MAGENTA.dispose();
		
		titleFont.dispose();
		
	}
}
