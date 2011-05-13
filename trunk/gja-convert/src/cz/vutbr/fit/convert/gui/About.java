/**
 * Convert
 * Program pro prevod skladeb do formatu ogg a flac
 * Projekt do GJA na FIT VUT 2010/2011
 *
 * Autori:
 * Tomas Izak <izakt00@stud.fit.vutbr.cz>
 * Pavel Pokorny <xpokor12@stud.fit.vutbr.cz>
 */
package cz.vutbr.fit.convert.gui;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;

import cz.vutbr.fit.convert.settings.Config;
import cz.vutbr.fit.convert.settings.Lang;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

/**
 * Okno "O programu"
 *
 * @author xizakt00
 */
public final class About extends JDialog {
	private static final long serialVersionUID = 5428214418774521988L;
        /**
         * Konstruktor - jeho zavolanim se okno zobrazi
         */
	public About(){
		this.setTitle("About");
                this.setBackground(Color.white);
		String temp=Config.get("AboutWindowPosX");
		Integer posX=0;
		if (temp!=null) posX=Integer.decode(temp);
		temp=Config.get("AboutWindowPosY");
		Integer posY=0;
		if (temp!=null) posY=Integer.decode(temp);
		this.setLocation(posX, posY);
		Container content = this.getContentPane();
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
                getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		BufferedImage myPicture;
                JLabel picLabel;
                String path="logo.jpg";
                try {
                    myPicture = ImageIO.read(Config.class.getResource(path));
                    picLabel = new JLabel(new ImageIcon(myPicture));
                } catch (Exception e) {
                    picLabel = new JLabel(Lang.get("file") + " " + path + " " + Lang.get("not_found"));
                }
                this.add(picLabel);
		JLabel about = new JLabel("<html>Drag&Drop program pro konverzi hudebních formátů.<br>Podporována konverze do formátu OGG a FLAC.</html>");
		content.add(about);
		JLabel credits = new JLabel("<html><br>Autoři:<ul><li>Tomáš Ižák (xizakt00@stud.fit.vutbr.cz)</li><li>Pavel Pokorný (xpokor12@stud.fit.vutbr.cz)</li></ul><br>Projekt do předmětu GJA na FIT VUT Brno</html>");
		content.add(credits);
		pack();
		this.addWindowListener(new WindowAdapter() {
                @Override
		      public void windowClosing(WindowEvent e) {
		          Config.set("AboutWindowPosX", Integer.toString(e.getWindow().getLocation().x));
		          Config.set("AboutWindowPosY", Integer.toString(e.getWindow().getLocation().y));
		        }
		      });
		this.setResizable(false);
		this.setModal(true);
	}
}