package cz.vutbr.fit.convert.controller;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.List;
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
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
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
     * Obrazek formatu s tooltipem
     */
    private JLabel picLabel=null;
    /**
     * Konstruktor
     * @param path jmeno souboru se zobrazovanym obrazkem
     * @param panelNam jmeno panelu - OGG nebo FLAC
     */
    public Panel(String path, String panelNam) {
        @SuppressWarnings("unused")
        DropTarget dt;
        BufferedImage myPicture;
        this.panelName = panelNam;
        try {
            myPicture = ImageIO.read(Config.class.getResource(path));
            picLabel = new JLabel(new ImageIcon(myPicture));
        } catch (Exception e) {
            picLabel = new JLabel(Lang.get("file") + " " + path + " " + Lang.get("not_found"));
        }
        actualize();
        dt = new DropTarget(picLabel, this);

        picLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JFileChooser chooser = new JFileChooser();
                FileFilter filter = new ExtensionFileFilter("Music", new String[] { "CUE", "OGG","FLAC","APE","M4A",".WV","MP3","MOD","IT","XM","WAV","S3M"});
                chooser.setFileFilter(filter);
                chooser.setDialogTitle(Lang.get("filedialog_header")+" "+panelName);
                int returnVal1 = chooser.showOpenDialog(new Frame());
                if(returnVal1 == JFileChooser.APPROVE_OPTION) {
                   TaskManager.addTask(chooser.getSelectedFile().getPath(),panelName);
                }
                }
            }
        });
        this.add(picLabel);
    }
    public void actualize(){
        picLabel.setToolTipText(Lang.get("panel_tooltip")+" "+ panelName);
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
                        //TODO on linux systems - bad localization characters
                        //System.out.println(new String(token.getBytes(System.getProperty("file.encoding"))));
                        System.out.println(token);
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
