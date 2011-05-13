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

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import cz.vutbr.fit.convert.Task;
import cz.vutbr.fit.convert.TaskManager;
/**
 * Sekce obsahujici progress bary
 * Panel s ukazately prubehu prevodu, kazdy panel predstavuje jednu ulohu.
 * @author xizakt00
 */
public final class ProgressBars extends JPanel {
	private static final long serialVersionUID = 4352233997832717092L;
        /**
         * Konstruktor volajici pouze aktualizaci
         * @see #actualize()
         */
	public ProgressBars(){
		actualize();
	}
        /**
         * Funkce znovuvykresli vsechny progress bary
         */
	public void actualize(){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            for (Task task:TaskManager.getTasks()){
                task.actualizeText();
		this.add(task.progress);
            }
            this.revalidate();
            this.repaint();
	}
}
