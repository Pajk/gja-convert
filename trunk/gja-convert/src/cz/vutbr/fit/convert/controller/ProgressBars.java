package cz.vutbr.fit.convert.controller;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import cz.vutbr.fit.convert.Task;
import cz.vutbr.fit.convert.TaskManager;

public final class ProgressBars extends JPanel {
	private static final long serialVersionUID = 4352233997832717092L;
	public ProgressBars(){
		actualize();
	}
	public void actualize(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for (Task task:TaskManager.getTasks()){
			this.add(task.progress);
		}
	    this.revalidate();
	    this.repaint();
	}
}
