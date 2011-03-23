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
import cz.vutbr.fit.convert.settings.Lang;

/**
 * Main menu class
 * Main menu aplikace
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public final class MainMenu extends JMenuBar{
	private static final long serialVersionUID = -6886186422189033178L;
        /**
         * Tlacitko pro konverzi do formatu OGG
         */
        private static JMenuItem toOGG=new JMenuItem();
        /**
         * Tlacitko pro konverzi do formatu FLAC
         */
        private static JMenuItem toFLAC=new JMenuItem();
        /**
         * Tlacitko pro zobrazeni okna s nastavenim
         */
        private static JMenuItem settings=new JMenuItem();
        /**
         * Tlacitko pro zobrazeni okna O aplikaci
         */
        private static JMenuItem about=new JMenuItem();
        /**
         * Konstruktor
         */
	public MainMenu(){
		setNames();
		toOGG.setMnemonic(KeyEvent.VK_O);
		toOGG.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) { 
            	FileDialog fd=new FileDialog(new Frame(),"Vyberte soubor pro konverzi do OGG",FileDialog.LOAD);
                /*fd.setFilenameFilter(new FilenameFilter(){
                @Override
                public boolean accept(File f,String name) {
                    return f.isDirectory() || (f.isFile() &&
                            (name.endsWith(".flac") ||
                            name.endsWith(".ape") ||
                            name.endsWith(".m4a") ||
                            name.endsWith(".wv") ||
                            name.endsWith(".ogg") ||
                            name.endsWith(".mp3") ||
                            name.endsWith(".mod") ||
                            name.endsWith(".it") ||
                            name.endsWith(".xm") ||
                            name.endsWith(".s3m")));
                }
            } );*/
            	fd.setLocation(50, 50);
            	fd.show();
            	if (fd.getFile() != null) TaskManager.addTask(fd.getDirectory() + fd.getFile(),"OGG");
            }
        });
		this.add(toOGG);
		toFLAC.setMnemonic(KeyEvent.VK_F);
		toFLAC.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) { 
            	FileDialog fd=new FileDialog(new Frame(),"Vyberte soubor pro konverzi do FLAC",FileDialog.LOAD);
            	fd.setLocation(50, 50);
            	fd.show();
            	if (fd.getFile() != null) TaskManager.addTask(fd.getDirectory() + fd.getFile(),"FLAC");
            }
        });
		this.add(toFLAC);
		settings.setMnemonic(KeyEvent.VK_S);
		settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { new SettingsWindow(); }
        });
		this.add(settings);
		about.setMnemonic(KeyEvent.VK_A);
		about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { new About(); }
        });
		this.add(about);
		this.setVisible(true);
	}
	/**
         * Funkce nastavi texty tlacitek dle jazykove lokalizace
         */
	public static void setNames(){
		toOGG.setText(Lang.get("ogg_convert"));
		toFLAC.setText(Lang.get("flac_convert"));
		settings.setText(Lang.get("settings"));
		about.setText(Lang.get("about"));
	}
}
