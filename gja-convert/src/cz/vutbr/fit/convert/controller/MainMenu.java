package cz.vutbr.fit.convert.controller;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cz.vutbr.fit.convert.TaskManager;
import cz.vutbr.fit.convert.gui.About;
import cz.vutbr.fit.convert.gui.SettingsWindow;
import cz.vutbr.fit.convert.lang.Lang;

public final class MainMenu extends JMenuBar{
	private static final long serialVersionUID = -6886186422189033178L;

	public MainMenu(){
		JMenuItem toOGG=new JMenuItem(Lang.get("ogg_convert"));
		toOGG.setMnemonic(KeyEvent.VK_O);
		toOGG.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            @Override
			public void actionPerformed(ActionEvent e) { 
            	FileDialog fd=new FileDialog(new Frame(),"Vyberte soubor pro konverzi do OGG",FileDialog.LOAD);
            	fd.setLocation(50, 50);
            	fd.setVisible(true);
            	if (fd.getFile() != null) TaskManager.addTask(fd.getDirectory() + fd.getFile(),"OGG");
            }
        });
		this.add(toOGG);
		JMenuItem toFLAC=new JMenuItem(Lang.get("flac_convert"));
		toFLAC.setMnemonic(KeyEvent.VK_F);
		toFLAC.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            @Override
			public void actionPerformed(ActionEvent e) { 
            	FileDialog fd=new FileDialog(new Frame(),"Vyberte soubor pro konverzi do FLAC",FileDialog.LOAD);
            	fd.setLocation(50, 50);
            	fd.setVisible(true);
            	if (fd.getFile() != null) TaskManager.addTask(fd.getDirectory() + fd.getFile(),"FLAC");
            }
        });
		this.add(toFLAC);
		JMenuItem settings=new JMenuItem(Lang.get("settings"));
		settings.setMnemonic(KeyEvent.VK_S);
		settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new SettingsWindow(); }
        });
		this.add(settings);
		JMenuItem about=new JMenuItem(Lang.get("about"));
		about.setMnemonic(KeyEvent.VK_A);
		about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new About(); }
        });
		this.add(about);
		this.setVisible(true);
	}
}
