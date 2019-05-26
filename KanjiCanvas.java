import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;

// the JPanel responsible for drawing
public class KanjiCanvas extends JPanel implements MouseMotionListener
{
	private BufferedImage image;
	private Color brushColor;
	private Stroke stroke;
	Point mouse;
	public KanjiCanvas()
	{
		super();
		setBackground(Color.white);
		brushColor = Color.black;
		stroke = new BasicStroke(10f, BasicStroke.CAP_ROUND,
                                     BasicStroke.JOIN_MITER);
        mouse = new Point(0,0);
        addMouseMotionListener(this);
        requestFocus();
	}
	
	public void draw(Point start, Point end)
	{
		Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(brushColor);
        g2.setStroke(stroke);
        g2.draw(new Line2D.Double(start, end));
        g2.dispose();
        repaint();
	}
	
	public void drawBoundingBox(int x1, int y1, int x2, int y2)
	{
		Graphics g2 = image.createGraphics();
		
        g2.setColor(Color.red);
        //g2.setStroke(stroke);
        g2.drawRect(x1, y1, (x2-x1),(y2-y1));
        g2.dispose();
        repaint();
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
        if(image == null)
            initImage();
        g.drawImage(image, 0, 0, this);
        g.setColor(Color.red);
        g.drawLine(mouse.x,0, mouse.x,getHeight());
        g.drawLine(0,mouse.y, getWidth(),mouse.y);
	}
	
	private void initImage()
    {
        int w = getWidth();
        int h = getHeight();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setPaint(getBackground());
        g2.fillRect(0,0,w,h);
        g2.dispose();
    }
    
    public void clearImage()
    {
        Graphics g = image.getGraphics();
        g.setColor(getBackground());
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        repaint();
    }
    
    public void mouseMoved(MouseEvent e)
    {
    	mouse = e.getPoint();
    	//System.out.println("mousemoved");
    	repaint();
    	
    }
    
    public void mouseDragged(MouseEvent e)
    {
    	mouse = e.getPoint();
    	repaint();
    }
}