package cz.vutbr.fit.convert;
import cz.vutbr.fit.convert.gui.*;
import cz.vutbr.fit.convert.settings.Config;
import cz.vutbr.fit.convert.settings.Lang;
/**
 * Main class
 * Hlavni trida aplikace
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public class Convert {

	/**
         * Konstruktor - nacte se konfigurace a prislusne jazykove popisky, pak hlavni okno aplikace
	 * @param args
	 */
	public static void main(String[] args) {
		Config.init();
		Lang.init();
		new MainWindow();
	}

}
