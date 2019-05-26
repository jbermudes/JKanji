import java.awt.*;
// The kanji fingerprint table
public class KanjiProfile
{
	private Point[][] profile;
	
	public KanjiProfile(Point[][] p)
	{
		setProfile(p);
	}
	
	public void setProfile(Point[][] p)
	{
		profile = new Point[p.length][p[0].length];
		for(int y = 0; y < profile.length; y++)
		{
			for(int x = 0; x < profile[0].length; x++)
			{
				profile[y][x] = p[y][x];
			}
		}
	}
	
	public boolean equals(KanjiProfile kp)
	{
		for(int y = 0; y < profile.length; y++)
		{
			for(int x = 0; x < profile[0].length; x++)
			{
				if(!kp.getCell(y, x).equals(profile[y][x]))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Point getCell(int row, int col)
	{
		return profile[row][col];
	}
	
	public void setCell(int row, int col, Point val)
	{
		profile[row][col] = val;
	}
	
	public String toString()
	{
		String s = "";
		for(int y = 0; y < profile.length; y++)
		{
			s+= "[";
			for(int x = 0; x < profile[0].length; x++)
			{
				s+= "(" + profile[y][x].x + "," + profile[y][x].y + ") ";
			}
			s+= "] ";
		}
		
		return s;
	}
	
	
}