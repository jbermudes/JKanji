/**
 * Node - A class to represent a node in a tree
 *
 * ID values: 
 *   -1 = root
 *    0 = leaf
 *   >0 = parent
 *
 **/

import java.util.*;
public class Node
{
	private int myID;
	private Node[] myLeaves;
	private ArrayList kanjiEntries;
	
	public Node(int id)
	{
		myID = id;
		myLeaves = new Node[KanjiStroke.NUM_STROKES+1]; // +1 to include 0 for entries
		kanjiEntries = new ArrayList();
	}
	
	public void setChildren(Node[] leaves)
	{
		myLeaves = leaves;
	}
	
	public void setChild(int index, Node child)
	{
		myLeaves[index] = child;
	}
	
	public boolean isLeaf()
	{
		return myLeaves.length > 0;
	}
	
	public int getID()
	{
		return myID;
	}
	
	public ArrayList getEntries()
	{
		return kanjiEntries;
	}
	
	public void addEntry(KanjiEntry k)
	{
		kanjiEntries.add(k);
	}
	
	public Node getChild(int index)
	{
		return myLeaves[index];
	}
	
	
	
	
}