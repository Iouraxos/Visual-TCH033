package graph;

import java.util.List;
import java.util.Observable;

public abstract class AbstractGraphArea extends Observable implements IGraphArea {
	
	protected int width = 700;
	protected int height = 700;
	
	protected double xAxisMin;
	protected double xAxisMax;
	protected double yAxisMin;
	protected double yAxisMax;
	
	protected List<Graduation> xAxisGraduationList;
	protected List<Graduation> yAxisGraduationList;

	public AbstractGraphArea() {

	}
	
	public AbstractGraphArea(double XAxisMin, double XAxisMax, double YAxisMin, double YAxisMax) {
		setAxisLimits(XAxisMin, XAxisMax, YAxisMin, YAxisMax);
	}	
	
	@Override
	public void setDimension(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		setChanged();
		notifyObservers(this);
	}	

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public void setAxisLimits(double XAxisMin, double XAxisMax, double YAxisMin, double YAxisMax){
	
		this.xAxisMin = XAxisMin;
		this.xAxisMax = XAxisMax;
		this.yAxisMin = YAxisMin;
		this.yAxisMax = YAxisMax;
		
		recalculateAxisGraduationList();
		
		setChanged();
		notifyObservers();
	}

	@Override
	public List<Graduation> getXAxisGraduationList() {

		return xAxisGraduationList;
	}

	@Override
	public List<Graduation> getYAxisGraduationList() {
		
		return yAxisGraduationList;
	}

	@Override
	public int xCoordToXPos(double xCoord) {
		
		// The basic linear implementation, to be overridden almost for sure
		return (int)Math.round((xCoord - xAxisMin) / (xAxisMax - xAxisMin) * (double)width);
	}

	@Override
	public int yCoordToYPos(double yCoord) {

		// The basic linear implementation, to be overridden almost for sure
		return height - (int)Math.round((yCoord - yAxisMin) / (yAxisMax - yAxisMin) * (double)height);
	}

	@Override
	public boolean isXCoordInside(double xCoord) {
		return xCoord >= xAxisMin && xCoord < xAxisMax;
	}

	@Override
	public boolean isYCoordInside(double yCoord) {
		return yCoord >= yAxisMin && yCoord < yAxisMax;
	}
	
	abstract protected void recalculateAxisGraduationList();
}
