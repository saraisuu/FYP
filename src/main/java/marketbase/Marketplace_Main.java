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
	JButton run;
	
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
		reset = new JButton("Reset");
		run = new JButton(runIcon);
		
		this.add(simConfig);
		this.add(save_Config);
		this.add(import_Config);
		this.add(reset);
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
		
		layout.putConstraint(SpringLayout.WEST, run, 10, SpringLayout.EAST, reset);
		layout.putConstraint(SpringLayout.NORTH, run, 0, SpringLayout.NORTH, reset);
		
		

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save_Config)
		{
			String[] filename = initFileSaveChooser();
			String[] check = filename[1].split(".", 0);
			
			if (!check[check.length - 1].equalsIgnoreCase("dat"))
			{
				filename[0] = filename[0] + ".dat";
				filename[1] = filename[1] + ".dat";
			}
			System.out.println(filename[0]);
		}
		else if (e.getSource() == import_Config)
		{
			
		}
		else if (e.getSource() == reset)
		{
			
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
