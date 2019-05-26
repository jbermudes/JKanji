import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class JKanji extends JApplet implements ActionListener
{
	private int pWidth, pHeight;
	private MenuPanel menu;
	private KanjiCanvas canvas;
	private DrawingListener dListener;
	private OutputPanel output;
	private static Kanji currentKanji;
	private StrokeTree[] strokeTree;
	public void init()
	{
		pWidth = getSize().width;
		pHeight = getSize().height;
		setLayout(new BorderLayout());
		menu = new MenuPanel(this);
		canvas = new KanjiCanvas();
		output = new OutputPanel();
		dListener = new DrawingListener(canvas);
		
		canvas.addMouseListener(dListener);
		canvas.addMouseMotionListener(dListener);
		
		add(menu, BorderLayout.WEST);
		add(canvas, BorderLayout.CENTER);
		add(output, BorderLayout.SOUTH);
		
		currentKanji = new Kanji();
		
		// Sample data for the kanji stroke tree
		KanjiEntry ichi = new KanjiEntry("1: 1 [(0,0) ] ", "\u4e00", "ichi (one)");
		
		KanjiEntry ni = new KanjiEntry("2: 1 1 [(0,0) (8,8) ] [(2,2) (0,0) ] ", "\u4e8c", "ni (two)");
		KanjiEntry hachi = new KanjiEntry("2: 4 5 [(0,0) (6,6) ] [(4,4) (0,0) ] ", "\u516b", "hachi (eight)");
		KanjiEntry jin = new KanjiEntry("2: 4 5 [(0,0) (8,6) ] [(2,4) (0,0) ] ", "\4eba", "jin (person)");
		KanjiEntry juu = new KanjiEntry("2: 1 2 [(0,0) (3,7) ] [(7,3) (0,0) ] ", "\u5341", "juu (ten)");
		
		KanjiEntry san = new KanjiEntry("3: 1 1 1 [(0,0) (9,7) (8,8) ] [(1,3) (0,0) (7,9) ] [(2,2) (3,1) (0,0) ]", "\u4e09", "san (three)");
		KanjiEntry tsuchi = new KanjiEntry("3: 1 2 1 [(0,0) (3,7) (8,8) ] [(7,3) (0,0) (8,6) ] [(2,2) (2,4) (0,0) ] " ,"\u571f", "tsuchi (earth)");
		KanjiEntry ue = new KanjiEntry("3: 2 1 1 [(0,0) (8,2) (8,6) ] [(2,8) (0,0) (7,8) ] [(2,4) (3,2) (0,0) ] ","\u4e0a", "ue (above)");
		KanjiEntry shita = new KanjiEntry("3: 1 2 5 [(0,0) (6,8) (6,7) ] [(4,2) (0,0) (5,2) ] [(4,3) (5,8) (0,0) ] ", "\u4e0b", "shita (below)");
		KanjiEntry kawa = new KanjiEntry("3: 2 2 2 [(0,0) (6,2) (6,6) ] [(4,8) (0,0) (6,9) ] [(4,4) (4,1) (0,0) ] ", "\u5ddd", "kawa (river)");
		KanjiEntry kuchi = new KanjiEntry("3: 2 3 1 [(0,0) (5,6) (8,6) ] [(5,4) (0,0) (8,5) ] [(2,4) (2,5) (0,0) ] ","\u53e3", "kuchi (mouth)");
		
		KanjiEntry nichi = new KanjiEntry("4: 2 3 1 1 [(0,0) (5,6) (8,3) (8,6) ] [(5,4) (0,0) (8,2) (8,5) ] [(2,7) (2,8) (0,0) (8,8) ] [(2,4) (2,5) (2,2) (0,0) ] ", "\u65e5", "nichi (sun)");
		
		// create tree and insert kanji
		strokeTree = new StrokeTree[4];
		
		strokeTree[0] = new StrokeTree(1);
		strokeTree[0].insert("1", ichi);
		
		strokeTree[1] = new StrokeTree(2);
		strokeTree[1].insert("11", ni);
		strokeTree[1].insert("45", hachi);
		strokeTree[1].insert("45", jin);
		strokeTree[1].insert("12", juu);
		
		strokeTree[2] = new StrokeTree(3);
		strokeTree[2].insert("111", san);
		strokeTree[2].insert("121", tsuchi);
		strokeTree[2].insert("211", ue);
		strokeTree[2].insert("125", shita);
		strokeTree[2].insert("222", kawa);
		strokeTree[2].insert("231", kuchi);
		//strokeTree[2].insert("145", dai);
		
		strokeTree[3] = new StrokeTree(4);
		strokeTree[3].insert("2311", nichi);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		System.out.println(cmd);
		if(cmd.equals("clear canvas"))
		{
			canvas.clearImage();
			currentKanji = new Kanji();
			output.setTextBox("-");
		}
		else if(cmd.equals("finalize"))
		{
			// finalize the kanji, analyze strokes, and look for the kanji in the tree
			currentKanji.finalizeKanji();
			System.out.println("Searching for " + currentKanji.toString());
			
			// make sure the tree isnt null before searching
			if(strokeTree[currentKanji.getStrokeCount()-1] != null)
			{
				// look for it based on a percentage match
				KanjiEntry entry = findKanji(currentKanji, strokeTree[currentKanji.getStrokeCount()-1].findEntries(currentKanji));
				if(entry != null) // if a kanji was returned
				{
					// output the data
					output.setTextBox(entry.getUnicode() + " [" + entry.getEntry() + "]");
				}
				else // didn't find it
				{
					output.setTextBox("Not Found");
				}
			}
			else
			{
				output.setTextBox("Not Found");
			}
		}
	}
	
	public static Kanji getKanji()
	{
		return currentKanji;
	}
	
	public void updateOutput(String text)
	{
		output.setTextBox(text);
	}
	
	// find kanji based on a percentage match of the fingerprint table
	public KanjiEntry findKanji(Kanji kanji, ArrayList list)
	{
		if(list != null)
		{
			Iterator itr = list.iterator();
			KanjiEntry current;
			while(itr.hasNext())
			{
				current = (KanjiEntry)(itr.next());
				System.out.println("Testing " + current.getFingerprint());
				
				// give user a little room for error, not everyone's perfect
				if(similarity(current.getFingerprint().trim(), kanji.toString().trim()) > 0.9)
				{
					System.out.println("FOUND!!!" + current.getEntry());
					return current;
				}
			}
		}
		System.out.println("Not Found");
		return null;
	}
	
	public double similarity(String s1, String s2)
	{
		double sum = 0;
		for(int k = 0; k < s1.length(); k++)
		{
			if(s1.charAt(k) == s2.charAt(k))
				sum += 1;
		}
		
		return sum / s1.length();
	}
}
