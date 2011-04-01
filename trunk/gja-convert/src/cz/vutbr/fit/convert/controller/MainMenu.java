package cz.vutbr.fit.convert.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cz.vutbr.fit.convert.TaskManager;
import cz.vutbr.fit.convert.gui.About;
import cz.vutbr.fit.convert.gui.MainWindow;
import cz.vutbr.fit.convert.gui.SettingsWindow;
import cz.vutbr.fit.convert.settings.Lang;
import java.awt.Desktop;
import java.net.URI;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

/**
 * Main menu class
 * Main menu aplikace
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public final class MainMenu extends JMenuBar {

    private static final long serialVersionUID = -6886186422189033178L;
    /**
     * Menu pro Konverzi do různých formátů
     */
    private static JMenu convert = new JMenu();
    /**
     * Menu pro Nápovědu, O aplikaci, Nastavení
     */
    private static JMenu others = new JMenu();
    /**
     * Tlacitko pro help
     */
    private static JMenuItem help = new JMenuItem();
    /**
     * Tlacitko pro konverzi do formatu OGG
     */
    private static JMenuItem toOGG = new JMenuItem();
    /**
     * Tlacitko pro konverzi do formatu FLAC
     */
    private static JMenuItem toFLAC = new JMenuItem();
    /**
     * Tlacitko pro zobrazeni okna s nastavenim
     */
    private static JMenuItem settings = new JMenuItem();
    /**
     * Tlacitko pro zobrazeni okna O aplikaci
     */
    private static JMenuItem about = new JMenuItem();

    /**
     * Konstruktor
     */
    public MainMenu() {
        setNames();

        //toOGG.setMnemonic(KeyEvent.VK_O);
        toOGG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        toOGG.addActionListener(new ActionListener() {

            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                /*FileDialog fd=new FileDialog(new Frame(),Lang.get("filedialog_header")+" OGG",FileDialog.LOAD);
                fd.setLocation(50, 50);
                fd.show();
                if (fd.getFile() != null) TaskManager.addTask(fd.getDirectory() + fd.getFile(),"OGG");*/
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new ExtensionFileFilter("Music", new String[]{"CUE", "FLAC", "APE", "M4A", ".WV", "MP3", "MOD", "IT", "XM", "WAV", "S3M"});
                chooser.setFileFilter(filter);
                chooser.setDialogTitle(Lang.get("filedialog_header") + " OGG");
                int returnVal1 = chooser.showOpenDialog(MainWindow.ogg_frame);
                if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                    TaskManager.addTask(chooser.getSelectedFile().getPath(), "OGG");
                }
            }
        });
        convert.add(toOGG);
        //toFLAC.setMnemonic(KeyEvent.VK_F);
        toFLAC.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        toFLAC.addActionListener(new ActionListener() {

            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new ExtensionFileFilter("Music", new String[]{"CUE", "OGG", "APE", "M4A", ".WV", "MP3", "MOD", "IT", "XM", "WAV", "S3M"});
                chooser.setFileFilter(filter);
                chooser.setDialogTitle(Lang.get("filedialog_header") + " FLAC");
                int returnVal1 = chooser.showOpenDialog(MainWindow.flac_frame);
                if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                    TaskManager.addTask(chooser.getSelectedFile().getPath(), "FLAC");
                }
            }
        });
        convert.add(toFLAC);
        //settings.setMnemonic(KeyEvent.VK_S);
        settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        settings.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new SettingsWindow();
            }
        });
        others.add(settings);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        help.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://fituska.eu"));
                } catch (IOException ex) {
                } catch (URISyntaxException ex) {
                }
            }
        });
        others.add(help);
        //about.setMnemonic(KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        about.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new About();
            }
        });
        others.add(about);
        this.add(convert);
        this.add(others);
        this.setVisible(true);
    }

    /**
     * Funkce nastavi texty tlacitek dle jazykove lokalizace
     */
    public static void setNames() {
        toOGG.setText(Lang.get("ogg_convert"));
        toFLAC.setText(Lang.get("flac_convert"));
        settings.setText(Lang.get("settings"));
        about.setText(Lang.get("about"));
        convert.setText(Lang.get("convert_into"));
        others.setText(Lang.get("help"));
        help.setText(Lang.get("help"));
    }
}
