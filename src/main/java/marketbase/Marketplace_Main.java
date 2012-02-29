package marketbase;

import java.awt.*;


import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import simbase.Sim;
import configbase.DBConfig;
import configbase.SimConfig;
import core.MyEventListener;
import simbase.Main_GUI;

public class Marketplace_Main extends JPanel implements ActionListener {
	SpringLayout		layout	= new SpringLayout();
	Marketplace_Simulation	simConfig;
	JButton save_Config;
	JButton import_Config;
	JButton reset;
	
	Main_GUI main;
	ImageIcon runIcon = new ImageIcon("Run.png");
	ImageIcon nextIcon = new ImageIcon("Next.png");
	ImageIcon previousIcon = new ImageIcon("Previous.png");
	
	JButton run;
	JButton next;
	JButton previous;
	
	DBConfig dbConfig;

	public Marketplace_Main() {
		this.setLayout(layout);
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);

		simConfig = new Marketplace_Simulation();
		TitledBorder simTitle = BorderFactory
				.createTitledBorder(blackline, "Marketplace Configuration");
		simConfig.setBorder(simTitle);
		
		
		save_Config = new JButton("Save Configuration");
		save_Config.addActionListener(this);
		import_Config = new JButton("Import Configuration");
		import_Config.addActionListener(this);
		reset = new JButton("Reset");
		reset.addActionListener(this);
		
		next = new JButton(nextIcon);
		next.addActionListener(this);
		previous = new JButton(previousIcon);
		previous.setVisible(false);
		previous.addActionListener(this);
		run = new JButton(runIcon);
		run.addActionListener(this);
		//this.setPreferredSize(new Dimension(50, 50));
		
		this.add(simConfig);
		this.add(save_Config);
		this.add(import_Config);
		this.add(reset);
		this.add(next);
		this.add(previous);
		this.add(run);
		
		layout.putConstraint(SpringLayout.WEST, simConfig, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, simConfig, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, simConfig, -10, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.WEST, save_Config, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, save_Config, 10, SpringLayout.SOUTH, simConfig);
		
		layout.putConstraint(SpringLayout.WEST, import_Config, 10, SpringLayout.EAST, save_Config);
		layout.putConstraint(SpringLayout.NORTH, import_Config, 0, SpringLayout.NORTH, save_Config);
		
		layout.putConstraint(SpringLayout.WEST, reset, 10, SpringLayout.EAST, import_Config);
		layout.putConstraint(SpringLayout.NORTH, reset, 0, SpringLayout.NORTH, import_Config);
		
		layout.putConstraint(SpringLayout.WEST, next, 10, SpringLayout.EAST, reset);
		layout.putConstraint(SpringLayout.NORTH, next, 0, SpringLayout.NORTH, import_Config);
		layout.putConstraint(SpringLayout.SOUTH, next, 0, SpringLayout.SOUTH, import_Config);
		
		layout.putConstraint(SpringLayout.WEST, previous, 10, SpringLayout.EAST, reset);
		layout.putConstraint(SpringLayout.NORTH, previous, 0, SpringLayout.NORTH, import_Config);
		layout.putConstraint(SpringLayout.SOUTH, previous, 0, SpringLayout.SOUTH, import_Config);
		
		layout.putConstraint(SpringLayout.WEST, run, 10, SpringLayout.EAST, next);
		layout.putConstraint(SpringLayout.NORTH, run, 0, SpringLayout.NORTH, import_Config);
		layout.putConstraint(SpringLayout.SOUTH, run, 0, SpringLayout.SOUTH, import_Config);
		
		

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save_Config)
		{
			String[] filename = initFileSaveChooser();
			System.out.println(filename[1]);
			String[] check = filename[1].split("\\.", 0);
			if (check[check.length-1].equalsIgnoreCase("dat"))
			{
				filename[1] = check[0];
			}
			String[] key = simConfig.configuration(filename);
						
			if ((check.length == 1) || (!check[check.length - 1].equalsIgnoreCase("dat")))
			{
				filename[0] = filename[0] + ".dat";
				filename[1] = filename[1] + ".dat";
			}
			try
			{
				PrintWriter output = new PrintWriter(filename[0]);
				output.print("simConfig=");
				output.println(key[4]);
				output.print("agentConfig=");
				output.println(key[0]);
				output.print("productConfig=");
				output.println(key[1]);
				output.print("schedConfig=");
				output.println(key[2]);
				output.print("masterConfig=");
				output.println(key[3]);
				output.close();
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			
			
		}
		else if (e.getSource() == import_Config)
		{
			
		}
		else if (e.getSource() == reset)
		{
			
		}
		else if (e.getSource() == next)
		{
			simConfig.agentConfig.setVisible(false);
			simConfig.productConfig.setVisible(false);
			simConfig.schedConfig.setVisible(false);
			simConfig.masterConfig.setVisible(true);
			this.next.setVisible(false);
			this.previous.setVisible(true);
		}
		else if (e.getSource() == previous)
		{
			simConfig.masterConfig.setVisible(false);
			simConfig.brsConfig.setVisible(false);
			simConfig.travosConfig.setVisible(false);
			simConfig.personalizedConfig.setVisible(false);
			simConfig.othersConfig.setVisible(false);
			simConfig.agentConfig.setVisible(true);
			simConfig.productConfig.setVisible(true);
			simConfig.schedConfig.setVisible(true);
			this.next.setVisible(true);
			this.previous.setVisible(false);
			
		}
		else if (e.getSource() == run)
		{
			SimConfig simConfig = new SimConfig();
			setUpRun();
			try {
				PropertyConfigurator.configure("src/main/resources/log4j.properties");
				simConfig.readConfig("src/test/resources/simbase/SimConfig.ini");
				Sim sim = new Sim();
				sim.setSimConfig(simConfig);
				sim.setDb(dbConfig.setUpDb());
				sim.registerEventListeners((MyEventListener) main.panels[2]);
				sim.run();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

	}
	
	public String[] initFileOpenChooser()
	{
		String[] filename = new String[2];
		
		try
		{
			JFileChooser fc = new JFileChooser();
			int value = fc.showOpenDialog(this);
			if (value == fc.APPROVE_OPTION)
			{
				filename[0] = fc.getSelectedFile().getAbsolutePath();
				filename[1] = fc.getSelectedFile().getName();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return filename;
	}
	
	public String[] initFileSaveChooser()
	{
		String[] filename = new String[2];
		try
		{
			JFileChooser fc = new JFileChooser();
			int value = fc.showSaveDialog(this);
			if (value == fc.APPROVE_OPTION)
			{
				filename[0] = fc.getSelectedFile().getAbsolutePath();
				filename[1] = fc.getSelectedFile().getName();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return filename;
	}
	
	public void setUpRun() {
		dbConfig = new DBConfig(null);
		dbConfig.addDdlFile("src/main/resources/sql/Products.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Agents.ddl");
		dbConfig.addDdlFile("src/main/resources/sql/Inventories.ddl");		
		dbConfig.addDdlFile("src/main/resources/sql/Executions.ddl");	
	}
}
