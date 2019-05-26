import java.awt.*;
import javax.swing.*;

// Panel for output of text
public class OutputPanel extends JPanel
{
	private JEditorPane textBox;
	private String leftHalf = "<html> <center><h1>";
	private String rightHalf = "</h1> </center></html>";
	public OutputPanel()
	{
		super();
		setLayout(new BorderLayout());
		
		textBox = new JEditorPane();
		textBox.setContentType("text/html");
		textBox.setText("<html> <center><h1>-</h1> </center></html>");
		
		JScrollPane scroll = new JScrollPane(textBox);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scroll, BorderLayout.CENTER);
	}
	
	public void setTextBox(String text)
	{
		textBox.setText(leftHalf + text + rightHalf);
	}
}