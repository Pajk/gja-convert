/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.vutbr.fit.convert.controller;

import cz.vutbr.fit.convert.settings.Lang;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * DraggableLabel class
 * Label, ktery je pripraven na drag
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public class DraggableLabel extends JLabel {
    private String value;
    public DraggableLabel(String label, String value){
        super(Lang.get(label));
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);
        this.setBackground(Color.blue);
        this.setToolTipText(Lang.get(label+"_tooltip"));
        this.setOpaque(true);
        this.setForeground(Color.white);
        this.setFont(new Font("Verdana", Font.PLAIN, 11));
        Border border = BorderFactory.createLoweredBevelBorder();
        this.setBorder(border);
        this.value=value;
        this.setTransferHandler(new TransferHandler("value"));
        MouseListener ml = new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                JComponent jc = (JComponent)e.getSource();
                TransferHandler th = jc.getTransferHandler();
                th.exportAsDrag(jc, e, TransferHandler.COPY);
            }
        };
        this.addMouseListener(ml);
    }
    public String getvalue(){
        return value;
    }
    public void setvalue(String data){
        value=data;
    }
}
