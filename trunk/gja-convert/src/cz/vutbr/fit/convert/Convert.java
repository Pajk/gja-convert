package cz.vutbr.fit.convert;

import cz.vutbr.fit.convert.gui.*;
import cz.vutbr.fit.convert.settings.Config;
import cz.vutbr.fit.convert.settings.Lang;
import javax.swing.UIManager;

/**
 * Main class
 * Hlavni trida aplikace
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public class Convert {

    public static MainWindow application;

    /**
     * Konstruktor - nacte se konfigurace a prislusne jazykove popisky, pak hlavni okno aplikace
     * @param args
     */
    public static void main(String[] args)
    {
        Config.init();
        Lang.init();

        //TODO kvuli ladeni, v ostre vyhodit
        //Config.setDefaults();

        // nastaveni systemoveho vzhledu
        try {
            // osx menu
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "CoNvert");

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // nepovedlo se, nevadi, vzhled bude multiplatformni
        }

        application = new MainWindow();
    }
}
