package cz.vutbr.fit.convert.controller;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.List;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
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
import cz.vutbr.fit.convert.settings.Lang;
import java.io.File;
import java.net.URI;
import java.net.URLDecoder;
import java.util.StringTokenizer;
/**
 * Panel class
 * Panel naslouchajici pro drop
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public class Panel extends JPanel implements DropTargetListener {

    private static final long serialVersionUID = 5420077495778750284L;
    /**
     * Jmeno panelu - OGG nebo FLAC
     */
    private String panelName;
    /**
     * Konstruktor
     * @param path jmeno souboru se zobrazovanym obrazkem
     * @param panelNam jmeno panelu - OGG nebo FLAC
     */
    public Panel(String path, String panelNam) {
        @SuppressWarnings("unused")
        DropTarget dt;
        BufferedImage myPicture;
        JLabel picLabel;
        this.panelName = panelNam;
        try {
            myPicture = ImageIO.read(Config.class.getResource(path));
            picLabel = new JLabel(new ImageIcon(myPicture));
        } catch (Exception e) {
            picLabel = new JLabel(Lang.get("file") + " " + path + " " + Lang.get("not_found"));
        }
        //TODO language localization
        picLabel.setToolTipText("Sem pretahnete soubor, ktery chcete konvertovat do souboru " + panelNam);
        dt = new DropTarget(picLabel, this);

        picLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //TODO language localization and file filter
                    FileDialog fd = new FileDialog(new Frame(), "Vyberte soubor pro konverzi do " + panelName, FileDialog.LOAD);
                    fd.setLocation(50, 50);
                    fd.show();
                    if (fd.getFile() != null) {
                        TaskManager.addTask(fd.getDirectory() + fd.getFile(), panelName);
                    }
                }
            }
        });
        this.add(picLabel);
    }
    /**
     * Nedela nic
     * @param dtde
     */
    public void dragEnter(DropTargetDragEvent dtde) {
    }
    /**
     * Nedela nic
     * @param dte
     */
    public void dragExit(DropTargetEvent dte) {
    }
    /**
     * Nedela nic
     * @param dtde
     */
    public void dragOver(DropTargetDragEvent dtde) {
    }
    /**
     * Nedela nic
     * @param dtde
     */
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }
    /**
     * Funkce po akci drag analyzuje prijata data a pripadne prida novou ulohu ke zpracovani
     * Funguje pod MacOS X a Windows
     * Linux: otestovano pod CentOS
     * @param dtde
     */
    @SuppressWarnings("rawtypes")
    public void drop(DropTargetDropEvent dtde) {
        try {
            Transferable tr = dtde.getTransferable();
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            DataFlavor linux = new DataFlavor("text/uri-list;class=java.lang.String");
            for (int i = 0; i < flavors.length; i++) {
                if (flavors[i].isFlavorJavaFileListType()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    List list;
                        list = (List) tr.getTransferData(flavors[i]);
                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j) != "") {
                                TaskManager.addTask(list.get(j).toString(), panelName);
                            }
                        }
                    
                    dtde.dropComplete(true);
                    return;
                }
                if (flavors[i].equals(linux)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    String data = (String) tr.getTransferData(linux);
                    for (StringTokenizer st = new StringTokenizer(data, "\r\n"); st.hasMoreTokens();) {
                        String token = st.nextToken().trim();
                        if (token.startsWith("#") || token.isEmpty()) {
                            continue;
                        }
                        //System.out.println(new String(token.getBytes(System.getProperty("file.encoding"))));
                        File file = new File(new URI(token));
                        
                        TaskManager.addTask(file.getAbsolutePath(), panelName);
                    }
                    dtde.dropComplete(true);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
