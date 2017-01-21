package graph;

import java.util.List;

/**
 * @author Iouri S. Colbert
 *
 */
public interface IGraphArea {

	/**
	 * Sets the width and height of the graph area
	 * @param width
	 * @param height
	 */
	public void setDimension(int width, int height);

	/**
	 * Returns the width of the graph area
	 * @return the width in pixels
	 */
	public int getWidth();

	/**
	 * Returns the height of the graph area
	 * @return the height in pixels
	 */
	public int getHeight();
	
	/**
	 * Set the limits of both axis
	 * @param XAxisMin
	 * @param XAxisMax
	 * @param YAxisMin
	 * @param YAxisMax
	 */
	public void setAxisLimits(double XAxisMin, double XAxisMax, double YAxisMin, double YAxisMax);
	
	/**
	 * Returns a list of pair of label and value of the place on the axis where
	 * there should be a graduation on the X axis
	 * 
	 * @return The list of pairs
	 */
	public List<Graduation> getXAxisGraduationList();

	/**
	 * Returns a list of pair of label and value of the place on the axis where
	 * there should be a graduation on the Y axis
	 * 
	 * @return The list of pairs
	 */
	public List<Graduation> getYAxisGraduationList();
	
	/**
	 * Convert a coordinate on the X axis to a pixel in the graph area
	 * @param xCoord
	 * @return return the pixel location of that coordinate
	 */
	public int xCoordToXPos(double xCoord);

	/**
	 * Convert a coordinate on the X axis to a pixel in the graph area
	 * @param yCoord
	 * @return return the pixel location of that coordinate
	 */
	public int yCoordToYPos(double yCoord);

	/**
	 * Verify if a coordinate is inside horizontal span of the graph area
	 * @param xCoord
	 * @return true if it is
	 */
	public boolean isXCoordInside(double xCoord);

	/**
	 * Verify if a coordinate is inside vertical span of the graph area
	 * @param yCoord
	 * @return true if it is
	 */
	public boolean isYCoordInside(double yCoord);
}
