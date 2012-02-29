package marketbase;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Marketplace_Personnel extends JPanel implements FocusListener {
	SpringLayout	layout		= new SpringLayout();
	String[]		labels		= { "Number Of Buyer:", "Number Of Seller:", "Initial Balance:" };
	
	final String[] DEFAULT =
		{
			"<For Example: 100>",
			"<For Example: 100>",
			"<For Example: 100.54>"
		};
	JLabel[]		label		= new JLabel[3];
	JTextField[]	textfield	= new JTextField[3];

	public Marketplace_Personnel() {
		this.setLayout(layout);

		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(labels[i]);
			textfield[i] = new JTextField(20);
			this.add(label[i]);
			this.add(textfield[i]);
			textfield[i].addFocusListener(this);
		}
		
		setTextField();
		
		layout.putConstraint(SpringLayout.WEST, label[0], 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, label[0], 5, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, textfield[0], 113, SpringLayout.EAST, label[0]);
		layout.putConstraint(SpringLayout.NORTH, textfield[0], 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, this, 10, SpringLayout.EAST, textfield[0]);

		for (int i = 1; i < label.length; i++) {
			layout.putConstraint(SpringLayout.WEST, label[i], 5, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, label[i], 5, SpringLayout.SOUTH,
					textfield[i - 1]);

			layout.putConstraint(SpringLayout.WEST, textfield[i], 0, SpringLayout.WEST,
					textfield[i - 1]);
			layout.putConstraint(SpringLayout.NORTH, textfield[i], 5, SpringLayout.SOUTH,
					textfield[i - 1]);
			layout.putConstraint(SpringLayout.EAST, textfield[i], 0, SpringLayout.EAST,
					textfield[0]);
		}
		layout.putConstraint(SpringLayout.SOUTH, this, 10, SpringLayout.SOUTH, textfield[textfield.length - 1]);
		this.setVisible(true);
	}

	public String configuration(String[] filename) {
		String fileS = "SavedConfiguration\\" + filename[1];
		Boolean success = new File(fileS).mkdirs();
		try {
			File file = new File(fileS + "\\AgentConfiguration.ini");
			PrintWriter output = new PrintWriter(file);
			output.print("buyerNum=");
			output.println(this.textfield[0].getText());
			output.print("sellerNum=");
			output.println(this.textfield[1].getText());
			output.print("initialBalance=");
			output.println(this.textfield[2].getText());
			output.close();
			fileS = file.getAbsolutePath();
		} catch (Exception ex) {
			System.out.println("IO Exception occured");
		}
		this.setTextField();
		return fileS;
	}
	
	public void importConfig(String filename)
	{
		File file = new File(filename);
		String[] key = null;
		int i = 0;
		
		try
		{
			Scanner reader = new Scanner(file);
			
			while (reader.hasNext())
			{
				String data = reader.nextLine();
				key = data.split("=", 0);
				textfield[i].setForeground(Color.black);
				textfield[i].setText(key[1]);
				i++;
			}
			
			reader.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

	public void focusGained(FocusEvent e) {
		for (int i = 0; i < textfield.length; i++) {
			if (e.getSource() == textfield[i]) {
				textfield[i].setForeground(Color.black);
				if (textfield[i].getText().equalsIgnoreCase(DEFAULT[i]))
				{
					textfield[i].setText("");
				}
				
			}
		}
	}

	public void focusLost(FocusEvent e) {}
	
	public void setTextField()
	{
		Color color = Color.gray;
		for (int i = 0; i < textfield.length; i++)
		{
			textfield[i].setForeground(color);
			textfield[i].setText(DEFAULT[i]);
			textfield[i].setToolTipText(DEFAULT[i]);
		}
		

	}
}
