package cz.vutbr.fit.convert;
import cz.vutbr.fit.convert.gui.*;
import cz.vutbr.fit.convert.settings.Config;
import cz.vutbr.fit.convert.settings.Lang;

public class Convert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Config.init();
		Lang.init();
		new MainWindow();
	}

}
