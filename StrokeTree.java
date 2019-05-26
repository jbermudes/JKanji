/**
 * Stroke Tree
 * A tree representing all possible paths to an N stroke kanji
 *
 * Inserts are done by providing a path, and the last item in the path is the dir
 * of where to be inserted
 *
 * There are several rules that must be followed in order to traverse the tree properly
 *   - A leaf will have an array of the possible strokes, but the 0th spot will have the 
 *     kanji group
 **/
import java.util.*;

public class StrokeTree
{
	private Node myParent;
	private int numStrokes;
	public StrokeTree(int strokes)
	{
		myParent = new Node(-1);
		numStrokes = strokes;
		
	}
	
	public boolean insert(String path, KanjiEntry k)
	{
		Node current = myParent;
		Node temp;
		char c;
		int index=0, strokeCode;
		boolean success = false;
		
		if(path.length() > numStrokes)
		{
			System.out.println("insert failed");
			return success;
		}
		
		while(path.length() > 0)
		{
			c = path.charAt(0);
			strokeCode = Character.getNumericValue(c);
			
			temp = current.getChild(strokeCode);
			if(temp == null) // uh oh, create a spot
			{
				temp = new Node(strokeCode);
				current.setChild(strokeCode, temp);
			}
			
			current = temp;//current.getChild(strokeCode);
			System.out.println(current.getID());
			path = path.substring(1, path.length());
		}
		
		Node kGroup = new Node(0);
		kGroup.addEntry(k);
		current.setChild(0, kGroup);
		success = true;
		return success;
	}
	
	public ArrayList findEntries(Kanji kanji)
	{
		Node current = myParent;
		for(int strokeIndex = 0; strokeIndex < kanji.getStrokeCount(); strokeIndex++)
		{
			KanjiStroke stroke = kanji.getStroke(strokeIndex);
			int strokeCode = stroke.getCode();
			System.out.println("I'm about to jump to child #" + strokeCode);
			
			current = current.getChild(strokeCode);
			if(current == null)
				return null;
		}
		current = current.getChild(0);
		return current.getEntries();
	}
}