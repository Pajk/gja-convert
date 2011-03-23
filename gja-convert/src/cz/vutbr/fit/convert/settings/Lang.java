package cz.vutbr.fit.convert.settings;

import cz.vutbr.fit.convert.settings.Config;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Lokalizacni trida, nacita jazykove soubory podle aktualniho nastaveni.
 * Metody jsou staticke, kvuli jednoduchosti pouziti v cele aplikaci.
 *
 * @author Pavel Pokorný
 */
public class Lang {
    
    /** podporovane jazyky */
    private static Locale[] supportedLocales = {
        new Locale("cs", "CZ"),
        new Locale("en", "US")
    };
    
    /** aktualne zvoleny jazyk */
    private static Locale currentLocale = null;
    
    /** popisky ve zvolenem jazyce */
    private static ResourceBundle labelsBundle = null;
    
    /**
     * Načtení lokalizačního souboru, nutné zavolat po startu aplikace.
     */
    public static void init() {
        
        Lang.currentLocale = new Locale(Config.get("language"));
        
        Lang.labelsBundle = ResourceBundle.getBundle(Lang.class.getPackage().getName()+".LabelsBundle", Lang.currentLocale);
        
    }
    
    /**
     * Ziskani lokalizovaneho retezce podle klice.
     * 
     * @param key klic podle ktereho hledat
     * @return hledany string
     */
    public static String get(String key) {
        return Lang.labelsBundle.getString(key);
    }
    
    /**
     * Ziskani seznamu podporovanych jazyku.
     * 
     * @return Pole nazvu jazyku
     */
    public static String[] getSupported() {
        ArrayList<String> lang = new ArrayList<String>();
        
        for(Locale l : Lang.supportedLocales) {
            lang.add(l.getDisplayLanguage());
        }
        return lang.toArray(new String[0]);
    }
    
    /**
     * Ziskani seznamu podporovanych jazyku.
     * 
     * @return Pole nazvu jazyku
     */
    public static int getCurrentIndex() {
        int i=0;
        for(Locale l : Lang.supportedLocales) {
            if (l.getDisplayLanguage()==Lang.currentLocale.getDisplayLanguage()) return i;
            i++;
        }
        return 0;
    }
    
    /**
     * Zjisteni aktualne nastaveneho jazyka
     * 
     * @return Nazev aktualniho jazyka
     */
    public static String getSelectedLang() {
        return Lang.currentLocale.getDisplayLanguage();
    }
    
    /**
     * Nastaveni noveho jazyka
     * 
     * @param index Pozice v poli ziskanem metodou getSupported()
     */
    public static void set(Integer index) {
        Config.set("language", Lang.supportedLocales[index].getLanguage());
        
        Lang.init();
    }

}
