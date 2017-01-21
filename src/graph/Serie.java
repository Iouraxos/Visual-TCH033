package graph;

import java.util.List;

public class Serie {
	
	private String name;
	private int colorIndex;
	private List<Coord> coordList;
	
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	
	public Serie(String name, int colorIndex, List<Coord> coordList) {
		this(name, colorIndex, coordList, true);
	}
	
	public Serie(String name, int colorIndex, List<Coord> coordList, boolean calcExtremeFlag) {
		super();
		this.name = name;
		this.colorIndex = colorIndex;
		this.coordList = coordList;
		
		if(calcExtremeFlag){
			calcExtreme();			
		}
	}

	public String getName() {
		return name;
	}

	public int getColorIndex() {
		return colorIndex;
	}

	public List<Coord> getCoordList() {
		return coordList;
	}
	
	public void calcExtreme(){
		
		xMin = Double.MAX_VALUE;
		xMax = -Double.MAX_VALUE;
		yMin = Double.MAX_VALUE;
		yMax = -Double.MAX_VALUE;
		
		for(Coord coord : coordList){
			
			double x = coord.getX();
			
			if(x < xMin){
				
				xMin = x;
			}
			else if(x > xMax){
				
				xMax = x;
			}
			
			double y = coord.getY();
			
			if(y < yMin){
				
				yMin = y;
			}
			else if(y > yMax){
				
				yMax = y;
			}
		}
	}

	public double getxMin() {
		return xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public double getyMin() {
		return yMin;
	}

	public double getyMax() {
		return yMax;
	}	
}
