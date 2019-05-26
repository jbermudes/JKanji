/**
 * Kanji
 * A class to represent one Japanese Kanji pictograph
 *
 * The relation codes tell how two strokes are related to each other when the
 * first stroke is centered to position 5:
 *   1 2 3
 *   4 5 6
 *   7 8 9
 **/

import java.util.*;
import java.awt.geom.*;
import java.awt.*;

public class Kanji
{
	private ArrayList strokes;
	private KanjiStroke first;
	private Point2D.Double[] startStrokeVectors, endStrokeVectors;
	private KanjiProfile myProfile;
	
	public Kanji()
	{
		strokes = new ArrayList();
	}
	
	public void addStroke(KanjiStroke s)
	{
		strokes.add(s);
	}
	
	public int getStrokeCount()
	{
		return strokes.size();
	}
	
	public KanjiStroke getStroke(int k)
	{
		return (KanjiStroke)(strokes.get(k));
	}
	
	public KanjiProfile getProfile()
	{
		return myProfile;
	}
	
	// analyze the kanji's strokes
	public void finalizeKanji()
	{
		Point[][] profile = new Point[getStrokeCount()][getStrokeCount()];
		KanjiStroke row, col;
		Point2D.Double startVec, endVec;
		int numStrokes = getStrokeCount();
		for(int y = 0; y < numStrokes; y++)
		{
			row = (KanjiStroke)(strokes.get(y));
			for(int x = 0; x < numStrokes; x++)
			{
				if(y != x)
				{
					col = (KanjiStroke)(strokes.get(x));
					startVec = row.compareTo(col, 0);
					endVec = row.compareTo(col, -1);
					profile[y][x] = new Point(getRelationCode(startVec), getRelationCode(endVec));
				}
				else
				{
					profile[y][x] = new Point(0,0);
				}
			}
			
		}
		myProfile = new KanjiProfile(profile);
		//System.out.println(myProfile.toString());
	}
	
	// figure out the relationship between stroke points
	public int getRelationCode(Point2D.Double vec)
	{
		double angle = Math.toDegrees(Math.atan(vec.y / vec.x));
		double distance = Math.sqrt(Math.pow(vec.x, 2.) + Math.pow(vec.y, 2.));
		int relationCode = 0;
		if(angle == -90)
				angle = 0;
		
		if(distance >= 15)
		{
			if(vec.x < 0) // if x is negative
			{
				if(angle > 60) // if -90 < theta < -60 
				{
					relationCode = 8;
				}
				else if(angle > 30)
				{
					relationCode = 7;
				}
				else if(angle > -30)
				{
					relationCode = 4;
				}
				else if(angle > -60)
				{
					relationCode = 1;
				}
				else if(angle > -90)
				{
					relationCode = 2;
				}
			}
			else // x is positive
			{
				if(angle > 60) // if -90 < theta < -60 
				{
					relationCode = 2;
				}
				else if(angle > 30)
				{
					relationCode = 3;
				}
				else if(angle > -30)
				{
					relationCode = 6;
				}
				else if(angle > -60)
				{
					relationCode = 9;
				}
				else if(angle > -90)
				{
					relationCode = 8;
				}
				
			}
		}
		else
		{
			relationCode = 5;
		}
		
		return relationCode;
	}
	
	
	public String toString()
	{
		String s = "" + getStrokeCount() + ": ";
		Iterator itr = strokes.iterator();
		while(itr.hasNext())
		{
			KanjiStroke stroke = (KanjiStroke)(itr.next());
			s += stroke.getCode() + " ";
		}
		return s + myProfile.toString();
	}
	
}