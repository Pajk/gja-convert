package cz.vutbr.fit.convert.gui;
import cz.vutbr.fit.convert.settings.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import cz.vutbr.fit.convert.controller.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public final class MainWindow extends JFrame {
	private static final long serialVersionUID = -187450838294806957L;
	public static ProgressBars panel=new ProgressBars();
	public MainWindow(){
		String temp=Config.get("MainWindowPosX");
		Integer posX=0;
		if (temp!=null) posX=Integer.decode(temp);
		temp=Config.get("MainWindowPosY");
		Integer posY=0;
		if (temp!=null) posY=Integer.decode(temp);
		this.setLocation(posX, posY);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setJMenuBar(new MainMenu());
		this.setSize(525, 500);
		this.setTitle(Config.get("app_name"));
		Panel ogg_frame=new Panel("ogg.jpg","OGG");
		ogg_frame.setBackground(Color.WHITE);
		ogg_frame.setSize(this.getSize().width/2, 30);
		this.add(ogg_frame,BorderLayout.WEST);
		Panel flac_frame=new Panel("flac.jpg","FLAC");
		flac_frame.setSize(this.getSize().width/2, 30);
		flac_frame.setBackground(Color.WHITE);
		this.add(flac_frame,BorderLayout.EAST);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		          Config.set("MainWindowPosX", Integer.toString(e.getWindow().getLocation().x));
		          Config.set("MainWindowPosY", Integer.toString(e.getWindow().getLocation().y));
		        }
		      });
		//TODO this.setIconImage(image);
		actualize();
		setVisible(true);
	}
	private void actualize(){
		JScrollPane state=new JScrollPane(panel);
		state.setPreferredSize(new Dimension(200, 200));
		panel.setBackground(Color.WHITE);
		this.add(state,BorderLayout.SOUTH);
		this.invalidate();
		this.repaint();
	}
	public static void refresh(){
		panel.actualize();
	}
	
}
