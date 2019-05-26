import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// panel with menu buttons OK/Clear
public class MenuPanel extends JPanel
{
	
	public MenuPanel(ActionListener client)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		
		JButton btn = new JButton("OK");
		btn.setActionCommand("finalize");
		btn.addActionListener(client);
		panel.add(btn);
		
		
		btn = new JButton("Clear");
		btn.setActionCommand("clear canvas");
		btn.addActionListener(client);
		panel.add(btn);
		
		add(panel);
		
	}

}