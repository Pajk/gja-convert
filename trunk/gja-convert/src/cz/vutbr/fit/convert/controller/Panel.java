package cz.vutbr.fit.convert.controller;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.io.IOException;
import java.util.List;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

import cz.vutbr.fit.convert.TaskManager;
import cz.vutbr.fit.convert.settings.Config;


public class Panel extends JPanel implements DropTargetListener{
	private static final long serialVersionUID = 5420077495778750284L;
	private String panelName;
	public Panel(String path,String panelNam){
		@SuppressWarnings("unused")
		DropTarget dt;
		BufferedImage myPicture;
		JLabel picLabel;
		this.panelName=panelNam;
		try {
			myPicture = ImageIO.read(Config.class.getResource(path));
			picLabel = new JLabel(new ImageIcon(myPicture));
		} catch (Exception e) {
			picLabel = new JLabel("Soubor "+path+" nenalezen");
		}
		picLabel.setToolTipText("Sem pretahnete soubor, ktery chcete konvertovat do souboru "+panelNam);
		dt=new DropTarget(picLabel,this);
		picLabel.addMouseListener(new MouseAdapter() {
			  @SuppressWarnings("deprecation")
			public void mouseClicked(MouseEvent e) { if (e.getClickCount() == 2) {
				  FileDialog fd=new FileDialog(new Frame(),"Vyberte soubor pro konverzi do "+panelName,FileDialog.LOAD);
				  fd.setLocation(50, 50);
				  fd.show();
				  if (fd.getFile() != null) TaskManager.addTask(fd.getDirectory() + fd.getFile(),panelName);  
			  }
			  }
				});
		this.add(picLabel);
    }
	
	public void dragEnter(DropTargetDragEvent dtde) {}
	public void dragExit(DropTargetEvent dte) {}
	public void dragOver(DropTargetDragEvent dtde) {}
	public void dropActionChanged(DropTargetDragEvent dtde) {}
	@SuppressWarnings("rawtypes")
	public void drop(DropTargetDropEvent dtde) {
		Transferable tr = dtde.getTransferable();
		DataFlavor[] flavors = tr.getTransferDataFlavors();
		for (int i = 0; i < flavors.length; i++) {
			//System.out.println("Possible flavor: " + flavors[i].getMimeType());
			if (flavors[i].isFlavorJavaFileListType()) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				List list;
				try {
					list = (List)tr.getTransferData(flavors[i]);
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j)!="") TaskManager.addTask(list.get(j).toString(),panelName);
					}
				} catch (UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dtde.dropComplete(true);
				return;
			}
		}
	}
}
