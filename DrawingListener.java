import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

// listener for the mouse drawing actions
public class DrawingListener extends MouseInputAdapter
{
    private KanjiCanvas canvas;
    private Point start;
    private boolean penDown;
    private KanjiStroke currentStroke;
 
    public DrawingListener(KanjiCanvas kc)
    {
        canvas = kc;
    }
 
    public void mousePressed(MouseEvent e)
    {
        start = e.getPoint();
        penDown = true;
        currentStroke = new KanjiStroke();
        currentStroke.addPoint(start);
    }
    
    public void mouseReleased(MouseEvent e)
    {
    	penDown = false;
    	currentStroke.addPoint(e.getPoint());
    	currentStroke.finalizeStroke();
    	canvas.drawBoundingBox(currentStroke.getLeftPoint().x-1, currentStroke.getTopPoint().y-1,
    							currentStroke.getRightPoint().x+1, currentStroke.getBottomPoint().y+1);
    	JKanji.getKanji().addStroke(currentStroke);
    }
 
    public void mouseDragged(MouseEvent e)
    {
        Point p = e.getPoint();
        canvas.draw(start, p);
        currentStroke.addPoint(e.getPoint());
        start = p;
    }
}