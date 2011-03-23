package cz.vutbr.fit.convert.controller;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import cz.vutbr.fit.convert.Task;
import cz.vutbr.fit.convert.TaskManager;
/**
 * ProgressBars class
 * Okenko s progress bary
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
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
            try{
                this.revalidate();
                this.repaint();
            }catch(NullPointerException e){}
            
	}
}
