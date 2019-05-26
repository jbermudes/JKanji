/**
 * KanjiStroke
 * A class to represent one stroke of a Japanese Kanji Pictograph
 *
 * Stroke types:
 *		0 - No Stroke
 *		1 - Horizontal Stroke
 *		2 - Vertical Storke
 *		3 - Right-angle Stroke
 *		4 - Diagonal up stroke
 *		5 - Diagonal down stroke
 **/

import java.util.*;
import java.awt.geom.*;
import java.awt.*;

public class KanjiStroke
{
	private ArrayList points;
	private int myType;
	private int flatAllowance = 10;
	private Point farLeft, farRight, farUp, farDown, firstPoint, lastPoint;
	private short xDir, yDir;
	private double myAngle, averageDistance;
	private int horizontalLimit = 20;
	private int verticalLimit = 80;
	private int numHorizontal, numVertical;
	private double strokeWidth, strokeHeight;
	private boolean firstStroke;
	
	public static final int NUM_STROKES = 5;
	public static final int NO_STROKE = 0;
	public static final int HORIZONTAL_STROKE = 1;
	public static final int VERTICAL_STROKE = 2;
	public static final int RIGHT_DOWN_STROKE = 3;
	public static final int DIAGONAL_UP_STROKE = 4;
	public static final int DIAGONAL_DOWN_STROKE = 5;
	public static final int HOOK_STROKE = 6;
	
	public KanjiStroke()
	{
		points = new ArrayList();
	}
	
	public void addPoint(Point p)
	{
		points.add(p);
	}
	
	public Point getPoint(int index)
	{
		return (Point)(points.get(index));
	}
	
	public Point getLastPoint()
	{
		return (Point)(points.get(points.size()-1));
	}
	
	public Point getLeftPoint() { return farLeft; }
	
	public Point getRightPoint() { return farRight; }
	
	public Point getTopPoint() { return farUp; }
	
	public Point getBottomPoint() { return farDown; }
	
	public boolean isFirstStroke() { return firstStroke; };
	
	public void setFirstStroke(boolean val)
	{
		firstStroke = val;
	}
	
	// figure out what kind of stroke was drawn
	public void finalizeStroke()
	{
		firstPoint = getPoint(0);
		lastPoint = getLastPoint();
		analyzePoints();
		myAngle = strokeAngle();
		
		double expectedHorizontal = strokeWidth / averageDistance;
		double expectedVertical = strokeHeight / averageDistance;
		double percentHorizontal = (double)numHorizontal / expectedHorizontal;
		double percentVertical = (double)numVertical / expectedVertical;
		
		if(Math.abs(myAngle) <= horizontalLimit) // Horizontal line: -
		{
			myType = HORIZONTAL_STROKE;
			System.out.println("Horizontal stroke");
		}
		else if(Math.abs(myAngle) >= verticalLimit) // Vertical line: |
		{
			myType = VERTICAL_STROKE;
			System.out.println("Vertical stroke");
		}
		else if(myAngle > 0)// increasing line: /
		{
			myType = DIAGONAL_UP_STROKE;
			System.out.println("Diagonal up stroke");
		}
		else // uhoh, it's a decreasing line "\" OR a hook "-|"
		{
			if(percentHorizontal >= 0.6 && percentVertical >= 0.6)// is most of the height vertical?
			{
				myType = RIGHT_DOWN_STROKE;
				System.out.println("Right-down stroke");
			}
			else // nope, it's just a plain 'ol diagonal
			{
				myType = DIAGONAL_DOWN_STROKE;
				System.out.println("Diagonal down stroke");
			}
		}
		
	}
	
	// analyze the start and end points of each stroke
	private void analyzePoints()
	{
		int oldX = getPoint(0).x, oldY = getPoint(0).y;
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		double angle = 0, distanceSum = 0;
		
		Iterator itr = points.iterator();
		
		while(itr.hasNext())
		{
			Point p = (Point)(itr.next());
			
			if(p.x > maxX)
			{
				maxX = p.x;
				farRight = p;
			}
			
			if(p.x < minX)
			{
				minX = p.x;
				farLeft = p;
			}
			
			if(p.y > maxY)
			{
				maxY = p.y;
				farDown = p;
			}
			
			if(p.y < minY)
			{
				minY = p.y;
				farUp = p;
			}
			
			strokeWidth = farRight.x - farLeft.x;
			strokeHeight = farDown.y - farUp.y;
			
			angle = Math.toDegrees(Math.atan((-slope(oldX, oldY, p.x, p.y)))); 
			distanceSum += Point2D.distance(oldX, oldY, p.x, p.y);
				
			if(Math.abs(angle) <= horizontalLimit)
			{
				numHorizontal++;
			}
			else if(Math.abs(angle) >= verticalLimit)
			{
				numVertical++;
			}
			
			oldX = p.x;
			oldY = p.y;
		}
		
		averageDistance = (double)distanceSum / (double)points.size();	
	}
	
	private double slope(int x1, int y1, int x2, int y2)
	{
		return ((double)y2 - (double)y1) / ((double)x2 - (double)x1);
	}
	
	public double strokeSlope()
	{
		if(((double)lastPoint.x - (double)firstPoint.x) == 0)
		{
			return Integer.MAX_VALUE;
		}
		else
		{
			return ((double)firstPoint.y - (double)lastPoint.y) / ((double)lastPoint.x - (double)firstPoint.x);
		}
		
	}
	
	public double strokeAngle()
	{
		return Math.toDegrees(Math.atan(strokeSlope()));
	}
	
	public int getCode()
	{
		return myType;
	}
	
	public Point2D.Double compareTo(KanjiStroke other, int point)
	{
		Point2D.Double vec = new Point2D.Double();
		
		if(point < 0)
		{
			vec.x = other.getLastPoint().x - getLastPoint().x;
			vec.y = getLastPoint().y - other.getLastPoint().y;
		}
		else
		{
			vec.x = other.getPoint(point).x - getPoint(point).x;
			vec.y = getPoint(point).y - other.getPoint(point).y;
		}
		
		
		return vec;
	}
}