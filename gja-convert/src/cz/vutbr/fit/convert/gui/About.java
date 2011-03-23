package cz.vutbr.fit.convert.gui;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;

import cz.vutbr.fit.convert.settings.Config;

/**
 * About window class
 * Jednoduche okno "O programu"
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public final class About extends JDialog {
	private static final long serialVersionUID = 5428214418774521988L;
        /**
         * Konstruktor - jeho zavolanim se okno zobrazi
         */
	public About(){
		this.setTitle("About");
		String temp=Config.get("AboutWindowPosX");
		Integer posX=0;
		if (temp!=null) posX=Integer.decode(temp);
		temp=Config.get("AboutWindowPosY");
		Integer posY=0;
		if (temp!=null) posY=Integer.decode(temp);
		this.setLocation(posX, posY);
		Container content = this.getContentPane();
	    content.setLayout(new GridLayout(3, 1));
		JLabel headline = new JLabel("coNvert");
		headline.setFont(new Font("Courier New",Font.BOLD,20));
		headline.setHorizontalAlignment(JLabel.CENTER);
		content.add(headline);
		JLabel about = new JLabel("<html>Drag`n`drop program pro konverzi hudebních formátu.<br>Podporována konverze do formátu OGG a FLAC.<html>");
		content.add(about);
		JLabel credits = new JLabel("Autori: Tomás Izák (xizakt00) a Pavel Pokorny (xpokor12).");
		content.add(credits);
		pack();
		this.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		          Config.set("AboutWindowPosX", Integer.toString(e.getWindow().getLocation().x));
		          Config.set("AboutWindowPosY", Integer.toString(e.getWindow().getLocation().y));
		        }
		      });
		this.setResizable(false);
		this.setModal(true);
		setVisible(true);
	}
}