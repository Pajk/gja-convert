package cz.vutbr.fit.convert;
import cz.vutbr.fit.convert.gui.*;
import cz.vutbr.fit.convert.lang.Lang;
import cz.vutbr.fit.convert.settings.Config;

public class Convert {

    final public static Boolean DEBUG = true;
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        
        // inicializace nastaveni
		Config.init();
        
        // inicializace prekladu
        Lang.init();
		
        new MainWindow();
	}

    public static void testConfig() {
        // uživatelské nastavení
        Config.set("Konnichiwa", "Sayounara");
        Log.debug(Config.get("Konnichiwa"));
        
        // test defaulního nastavení (ze souboru convert.properties)
        Log.debug("language = " + Config.get("language"));
        
        // test "přetížení defaultního nastavení"
        Config.set("language", "cz");
        Log.debug("language = " + Config.get("language"));
        
        // test nastavení defaultních hodnot
        Config.setDefaults();
        Log.debug("language = " + Config.get("language"));
        
        // test output_dir
        Log.debug("ouput_dir = " + Config.get("output_dir"));
    }
}
