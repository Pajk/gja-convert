package cz.vutbr.fit.convert;

import java.awt.List;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import cz.vutbr.fit.convert.gui.MainWindow;
import cz.vutbr.fit.convert.settings.Config;

import javax.swing.JProgressBar;

public class Task extends List{
	private static final long serialVersionUID = 3619958613264215820L;
	public int id;
	public String ifilename;
	public String oformat;
	public String ofilename;
	public JProgressBar progress;
	public int starttime;
	public int stoptime;
	public Thread running;
	public Task(String filename,String mode){
		ifilename=filename;
		oformat=mode;
		progress=new JProgressBar(0,100);
		progress.setEnabled(true);
		progress.setString(ifilename);
		progress.setStringPainted(true);
		progress.setToolTipText("<html>Vstupní soubor: "+ifilename+"<br>Konverze do: "+oformat+"</html>");
		this.start();
	}
	public int start(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				somethingChanged();
				File file;
				try {
					if ((Config.get("MaxTasks")!=null) && (Integer.decode(Config.get("MaxTasks"))!=0)){
						while (TaskManager.numberRunningTasks() > Integer.decode(Config.get("MaxTasks"))){
							Thread.sleep(5000);
						}
					}
					file = new File(ifilename);
				}  catch (InterruptedException e) {e.printStackTrace(); return;}
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat timeFormat = new SimpleDateFormat("HH-mm-ss-SS");
				ofilename=Config.get("output_dir")+File.separator;
				ofilename+=Config.get("o_filename_format").replaceAll("<P>", file.getName()).replaceAll("<D>", dateFormat.format(new Date())).replaceAll("<T>", timeFormat.format(new Date()));
				ofilename+="."+oformat.toLowerCase();
				progress.setToolTipText("<html>Vstupní soubor: "+ifilename+"<br>Vystupní soubor: "+ofilename+"<br>Konverze do: "+oformat+"</html>");
				TaskManager.increaseRunningTasks();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {}
				progress.setValue(50);
				somethingChanged();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {}
				progress.setValue(100);
				progress.setEnabled(false);
				TaskManager.decreaseRunningTasks();
				progress.setString(ifilename+" FINISHED");
				somethingChanged();
			}
		}).start();
		return 0;
	}
	public int pause(){
		return 0;
	}
	public int stop(){
		return 0;
	}
	private void somethingChanged(){
		MainWindow.refresh();
	}
}
