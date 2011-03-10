package cz.vutbr.fit.convert.gui;

import cz.vutbr.fit.convert.lang.Lang;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import cz.vutbr.fit.convert.settings.Config;

public final class SettingsWindow extends JDialog implements ActionListener{
    // ppo: tohle je tu na co?
	private static final long serialVersionUID = -3491161084404651198L;
	private JTextField ofilenameformat=new JTextField();
	private JComboBox languages=new JComboBox(Lang.getSupported());
	private JTextField maxtasks = new JTextField();
	private JTextField outputdir = new JTextField();
	private JButton confirm=new JButton();
	public SettingsWindow(){
		this.setTitle(Lang.get("settings"));
		String temp=Config.get("SettingsWindowPosX");
		Integer posX=0;
		if (temp!=null) posX=Integer.decode(temp);
		temp=Config.get("SettingsWindowPosY");
		Integer posY=0;
		if (temp!=null) posY=Integer.decode(temp);
		this.setLocation(posX, posY);
		maxtasks.setText(Config.get("max_task"));
        languages.setSelectedItem(Lang.getSelectedLang());
        languages.addActionListener(this);
		Container contentPane = this.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);
		JLabel headline = new JLabel(Config.get("app_name"));
		headline.setFont(new Font("Courier New",Font.BOLD,20));
		headline.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(headline);
		JTabbedPane panely=new JTabbedPane();
		JComponent panel1 = new JPanel(false);
        panel1.setLayout(new GridLayout(8,2));
        panel1.add(new JLabel(Lang.get("max_threads")));
        panel1.add(maxtasks);
        panel1.add(new JLabel(Lang.get("choose_language")));
        panel1.add(languages);
        panel1.add(new JLabel(Lang.get("output_filename_format")));
        ofilenameformat.setText("<P>-<D>-<T>");
        panel1.add(ofilenameformat);
        panel1.add(new JLabel(Lang.get("output_dir")));
        outputdir.setText(Config.get("output_dir"));
        outputdir.setSize(100,outputdir.getHeight());
        panel1.add(outputdir);
        panel1.add(new JLabel());
		panely.addTab(Lang.get("general"), panel1);
		JComponent panel2 = new JPanel(false);
        panel2.setLayout(new GridLayout(1, 1));
		panely.addTab(Lang.get("ogg_format"), panel2);
		JComponent panel3 = new JPanel(false);
        panel3.setLayout(new GridLayout(1, 1));
		panely.addTab(Lang.get("flac_format"), panel3);
		panely.setPreferredSize(new Dimension(400,300));
		contentPane.add(panely);
		confirm.setName("OK");
		confirm.setText("OK");
		confirm.addActionListener(this);
		contentPane.add(confirm);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headline,0,SpringLayout.HORIZONTAL_CENTER, contentPane);
		layout.putConstraint(SpringLayout.NORTH, headline,5,SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panely,0,SpringLayout.HORIZONTAL_CENTER, contentPane);
		layout.putConstraint(SpringLayout.NORTH, panely,25,SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, confirm,0,SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, confirm,0,SpringLayout.SOUTH, contentPane);
		this.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		          Config.set("SettingsWindowPosX", Integer.toString(e.getWindow().getLocation().x));
		          Config.set("SettingsWindowPosY", Integer.toString(e.getWindow().getLocation().y));
		        }
		      });
		this.pack();
		this.setResizable(false);
		this.setSize(400, 400);
		this.setModal(true);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==confirm) {
			int max_tasks=0;
			try{
				max_tasks=Integer.decode(maxtasks.getText());
			} catch (Exception ex){
				maxtasks.setText(Lang.get("warning_valid_number"));
				return;
			}
			save(max_tasks);
			this.dispose();
		} 
        // nastaveni jazyka
        // TODO: preklesit hlavni okno, aby se natahly ceske popisky
        else if (e.getSource() == languages) {
            Lang.set(languages.getSelectedIndex());
        }
	}
	private void save(int max_tasks){
		Config.set("max_task", Integer.toString(max_tasks));
		if (outputdir.getText()!="") Config.set("output_dir", outputdir.getText());
		if (ofilenameformat.getText()!="") Config.set("o_filename_format",ofilenameformat.getText());
	}
}
