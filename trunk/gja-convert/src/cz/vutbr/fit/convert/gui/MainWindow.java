/**
 * Convert
 * Program pro prevod skladeb do formatu ogg a flac
 * Projekt do GJA na FIT VUT 2010/2011
 *
 * Autori:
 * Tomas Izak <izakt00@stud.fit.vutbr.cz>
 * Pavel Pokorny <xpokor12@stud.fit.vutbr.cz>
 */
package cz.vutbr.fit.convert.gui;

import cz.vutbr.fit.convert.settings.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import cz.vutbr.fit.convert.controller.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Hlavni okno aplikace
 * Obsahuje dva Drag&Drop panely, ukazatele prubehu prevodu, hlavni menu.
 *
 * @author xizakt00
 */
public final class MainWindow extends JFrame {

    private static final long serialVersionUID = -187450838294806957L;
    /**
     * Panel s progress bary - ukazatel
     */
    public static ProgressBars panel = new ProgressBars();
    /**
     * Main menu - ukazatel
     */
    public static MainMenu menu = new MainMenu();
    /**
     * Panel pro drop - konverze do formatu ogg
     */
    public static Panel ogg_frame = new Panel("ogg.jpg", "OGG");
    /**
     * Panel pro drop - konverze do formatu flac
     */
    public static Panel flac_frame = new Panel("flac.jpg", "FLAC");

    /**
     * Konstruktor
     * Pred praci s hlavnim oknem je treba ho zavolat
     */
    public MainWindow() {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String temp = Config.get("MainWindowPosX");
        Integer posX = 0;
        if (temp != null && temp.compareTo("SETTINGS MISSING") != 0) {
            posX = Integer.decode(temp);
        }
        temp = Config.get("MainWindowPosY");
        Integer posY = 0;
        if (temp != null && temp.compareTo("SETTINGS MISSING") != 0) {
            posY = Integer.decode(temp);
        }
        this.setLocation(posX, posY);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(525, 500);
        this.setTitle("coNvert");
        this.setJMenuBar(menu);
        ogg_frame.setBackground(Color.WHITE);
        ogg_frame.setSize(this.getSize().width / 2, 30);
        this.add(ogg_frame, BorderLayout.WEST);
        flac_frame.setSize(this.getSize().width / 2, 30);
        flac_frame.setBackground(Color.WHITE);
        this.add(flac_frame, BorderLayout.EAST);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                Config.set("MainWindowPosX", Integer.toString(e.getWindow().getLocation().x));
                Config.set("MainWindowPosY", Integer.toString(e.getWindow().getLocation().y));
            }
        });
        //TODO this.setIconImage(image);
        actualize();
        setVisible(true);
    }

    /**
     * Funkce aktualizuje obsah hlavniho okna
     */
    private void actualize() {
        try {
            JScrollPane state = new JScrollPane(panel);
            state.setPreferredSize(new Dimension(200, 200));
            panel.setBackground(Color.WHITE);
            ogg_frame.actualize();
            flac_frame.actualize();
            this.add(state, BorderLayout.SOUTH);
        } catch (Exception e) {
        }
    }

    /**
     * Funkce aktualizuje obsah panelu s progress bary okna
     */
    public static void refresh() {
        try {
            panel.actualize();
            ogg_frame.actualize();
            flac_frame.actualize();
        } catch (Exception e) {
        }
    }
}
