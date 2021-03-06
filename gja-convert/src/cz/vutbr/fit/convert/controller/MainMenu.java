/**
 * Convert
 * Program pro prevod skladeb do formatu ogg a flac
 * Projekt do GJA na FIT VUT 2010/2011
 *
 * Autori:
 * Tomas Izak <izakt00@stud.fit.vutbr.cz>
 * Pavel Pokorny <xpokor12@stud.fit.vutbr.cz>
 */
package cz.vutbr.fit.convert.controller;

import cz.vutbr.fit.convert.TaskManager;
import cz.vutbr.fit.convert.gui.About;
import cz.vutbr.fit.convert.gui.MainWindow;
import cz.vutbr.fit.convert.gui.SettingsWindow;
import cz.vutbr.fit.convert.settings.Lang;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

/**
 * Hlavni menu aplikace
 *
 * 
 * @author xizakt00
 */
public final class MainMenu extends JMenuBar {
    private static final long serialVersionUID = -6886186422189033178L;

    /**
     * Menu pro Nápovědu, O aplikaci, Nastavení
     */
    private static JMenu others = new JMenu();

    /**
     * Tlacitko pro help
     */
    private static JMenuItem help = new JMenuItem();

    /**
     * Menu pro Konverzi do různých formátů
     */
    private static JMenu convert = new JMenu();

    /**
     * Tlacitko pro konverzi do formatu OGG
     */
    private static JMenuItem toOGG = new JMenuItem();

    /**
     * Tlacitko pro konverzi do formatu FLAC
     */
    private static JMenuItem      toFLAC         = new JMenuItem();
    private static SettingsWindow settingsWindow = new SettingsWindow();

    /**
     * Tlacitko pro zobrazeni okna s nastavenim
     */
    private static JMenuItem settings    = new JMenuItem();
    private static JMenuItem quit        = new JMenuItem();
    private static About     aboutDialog = new About();

    /**
     * Tlacitko pro zobrazeni okna O aplikaci
     */
    private static JMenuItem about = new JMenuItem();

    /**
     * Konstruktor
     */
    public MainMenu() {
        setNames();
        convert.setName("file_menu");

        // toOGG.setMnemonic(KeyEvent.VK_O);
        toOGG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        toOGG.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {

                /*
                 * FileDialog fd=new FileDialog(new Frame(),Lang.get("filedialog_header")+" OGG",FileDialog.LOAD);
                 * fd.setLocation(50, 50);
                 * fd.show();
                 * if (fd.getFile() != null) TaskManager.addTask(fd.getDirectory() + fd.getFile(),"OGG");
                 */
                JFileChooser chooser = new JFileChooser();

                chooser.setName("file_chooser");

                FileFilter filter = new ExtensionFileFilter("Music", new String[] {
                    "CUE", "FLAC", "APE", "M4A", ".WV", "MP3", "MOD", "IT", "XM", "WAV", "S3M"
                });

                chooser.setFileFilter(filter);
                chooser.setDialogTitle(Lang.get("filedialog_header") + " OGG");
                chooser.setMultiSelectionEnabled(true);
                int returnVal1 = chooser.showOpenDialog(MainWindow.ogg_frame);

                if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                    File[] files=chooser.getSelectedFiles();
                        for (File temp:files){
                            TaskManager.addTask(temp.getPath(), "OGG");
                        }
                }
            }
        });
        toOGG.setName("file_ogg_menuitem");
        convert.add(toOGG);

        // toFLAC.setMnemonic(KeyEvent.VK_F);
        toFLAC.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        toFLAC.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileFilter   filter  = new ExtensionFileFilter("Music", new String[] {
                    "CUE", "OGG", "APE", "M4A", ".WV", "MP3", "MOD", "IT", "XM", "WAV", "S3M"
                });
                chooser.setMultiSelectionEnabled(true);
                chooser.setFileFilter(filter);
                chooser.setDialogTitle(Lang.get("filedialog_header") + " FLAC");

                int returnVal1 = chooser.showOpenDialog(MainWindow.flac_frame);

                if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                    File[] files=chooser.getSelectedFiles();
                        for (File temp:files){
                            TaskManager.addTask(temp.getPath(), "FLAC");
                        }
                }
            }
        });
        convert.add(toFLAC);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        convert.add(quit);

        // settings.setMnemonic(KeyEvent.VK_S);
        settings.setName("settings_window_menuitem");
        settingsWindow.setName("settings_window_dialog");
        settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                settingsWindow.setVisible(true);
            }
        });
        others.add(settings);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://code.google.com/p/gja-convert/wiki/usage"));
                } catch (IOException ex) {}
                catch (URISyntaxException ex) {}
            }
        });
        others.add(help);

        // about.setMnemonic(KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aboutDialog.setVisible(true);
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
        quit.setText(Lang.get("program_exit"));
        toOGG.setText(Lang.get("ogg_convert"));
        toFLAC.setText(Lang.get("flac_convert"));
        settings.setText(Lang.get("settings"));
        about.setText(Lang.get("about"));
        convert.setText(Lang.get("convert_into"));
        others.setText(Lang.get("help"));
        help.setText(Lang.get("help"));
    }
}