package cz.vutbr.fit.convert.settings;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import java.util.prefs.*;

/**
 *
 * @author Pavel Pokorny
 */
public class Config {

    private static Properties default_values = null;
    private static Preferences user_values = null;
    
    private static String configFileName = "convert.properties";
    private static String defaultOutputDir = "ConvertedMusic";
    
    /**
     * Vytvoří defaultní konfiguraci programu.
     */
    public static void setDefaults() { 
        
        for(String key : default_values.stringPropertyNames()) {
            user_values.put(key, default_values.getProperty(key));
        }
    }
    
    
    /**
     * Inicializace správy nastavení programu. Nutno zavolat před použitím
     * metod get nebo set. Načte konfigurační soubor nebo defaultní nastavení.
     */
    public static void init() {
        
        // ziskani default nastaveni
        default_values = Config.loadConfigResource();
        
        // ziskani uzivatelskeho nastaveni
        user_values = Preferences.userNodeForPackage(Config.class);
    }
    
    /**
     * Uložení změn v uživatelově konfiguraci
     * 
     * @throws BackingStoreException 
     */
    public static void save() throws BackingStoreException {
        user_values.flush();
    }
    
    /**
     * Ziskání hodnoty nastavení na základě klíče.
     * 
     * @param key Klíč
     * @return Hodnota odpovídající klíči nebo null, pokud neexistuje
     */
    public static String get(String key) {
        // ziskani uzivatelskeho nastaveni, pokud neni k dispozici, pouzije se defaultni
        return user_values.get(key, default_values.getProperty(key, "SETTINGS MISSING"));
    }
    
    /**
     * Uložení konfigurační hodnoty
     * 
     * @param key Klíč
     * @param value Hodnota
     */
    public static void set(String key, String value) {
        user_values.put(key, value);
    }
    
    /**
     * Načtení konfiguračního souboru
     * 
     * @return Properties Načtené nastavení nebo null při neúspěchu
     */
    private static Properties loadConfigResource() 
    {
        Properties result = null;
        InputStream in = null;
        try {
            // ziskani souboru jako inputstream
            in = Config.class.getResourceAsStream(Config.configFileName);
            if (in != null) {
                result = new Properties ();
                result.load(in);
            }
        }
        catch (Exception e) {
            result = null;
        }
        finally {
            // pokus o zavreni streamu
            if (in != null) try { in.close (); } catch (Throwable ignore) {}
        }
        result.setProperty("output_dir", getDefaultOutputDir());
        return result;
    }
    
    /**
     * Získání defaultního adresáře pro ukládání zkonvertovaných souborů
     * 
     * @return Cesta k adresáři
     */
    private static String getDefaultOutputDir() {
        return System.getProperty("user.home")+File.separatorChar+Config.defaultOutputDir;
    }
    
}
