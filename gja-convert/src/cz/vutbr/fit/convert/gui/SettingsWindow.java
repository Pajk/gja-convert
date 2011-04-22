package cz.vutbr.fit.convert.gui;

import cz.vutbr.fit.convert.controller.DraggableLabel;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import cz.vutbr.fit.convert.controller.MainMenu;
import cz.vutbr.fit.convert.settings.Config;
import cz.vutbr.fit.convert.settings.Lang;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
/**
 * Settings window class
 * okno s nastavenim aplikace
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public final class SettingsWindow extends JDialog implements ActionListener {
    private static final long serialVersionUID = -3491161084404651198L;
    /**
     * Textove pole pro zadani formatu nazvu vystupniho souboru
     */
    private JTextField ofilenameformat = new JTextField();
    /**
     * Textove pole pro zadani formatu nazvu vystupniho souboru pri cue formatu
     */
    private JTextField ocuefilenameformat = new JTextField();
    /**
     * ComboBox pro vyber jazyka
     */
    private JComboBox languages = new JComboBox(Lang.getSupported());
    private SpinnerNumberModel ochannelsModel = new SpinnerNumberModel(2, 2, 2, 1);
    private String[] fchannel = {"1", "2", "6"};
    private SpinnerListModel fchannelsModel = new SpinnerListModel(fchannel);
    /**
     * Spinner pro vyber kanalu pro ogg format
     */
    private JSpinner ochannels = new JSpinner(ochannelsModel);
    /**
     * Spinner pro vyber kanalu pro flac format
     */
    private JSpinner fchannels = new JSpinner(fchannelsModel);
    private SpinnerNumberModel osampleRateModel = new SpinnerNumberModel(44100, 12500, 44100, 100);
    private SpinnerNumberModel fsampleRateModel = new SpinnerNumberModel(44100, 1, 655350, 1);
    /**
     * Spinner pro zadani sample rate pro ogg format
     */
    private JSpinner osamplerate = new JSpinner(osampleRateModel);
    /**
     * Spinner pro zadani sample rate pro flac format
     */
    private JSpinner fsamplerate = new JSpinner(fsampleRateModel);
    private SpinnerNumberModel obitRateModel = new SpinnerNumberModel(128, 32, 192, 1);
    //private SpinnerNumberModel fbitRateModel = new SpinnerNumberModel(128, 32, 192, 1);
    /**
     * Spinner pro zadani bit rate pro ogg format
     */
    private JSpinner obitrate = new JSpinner(obitRateModel);
    /*
     * Spinner pro zadani bit rate pro flac format
     */
    //private JSpinner fbitrate = new JSpinner(fbitRateModel);
    private SpinnerNumberModel maxtasksModel = new SpinnerNumberModel(5, 1, 100, 1);
    /**
     * Spinner pro zadani maximalniho poctu paralelne zpracovavanzch uloh
     */
    private JSpinner maxtasks = new JSpinner(maxtasksModel);
    /**
     * Textove pole pro zadani vystupniho adresare
     */
    private JTextField outputdir = new JTextField();
    /**
     * Potvrzovaci tlacitko
     */
    private JButton confirm = new JButton();

    /**
     * Konstruktor - jeho zavolanim se okno s nastavenim zobrazi
     */
    public SettingsWindow() {
        this.setTitle(Lang.get("settings"));
        languages.setSelectedIndex(Lang.getCurrentIndex());
        String temp = Config.get("SettingsWindowPosX");
        Integer posX = 0;
        if (temp != null) {
            posX = Integer.decode(temp);
        }
        temp = Config.get("SettingsWindowPosY");
        Integer posY = 0;
        if (temp != null) {
            posY = Integer.decode(temp);
        }
        this.setLocation(posX, posY);
        Container contentPane = this.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);
        JLabel headline = new JLabel("coNvert");
        headline.setFont(new Font("Courier New", Font.BOLD, 20));
        headline.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(headline);
        JTabbedPane panely = new JTabbedPane();
        JComponent panel1 = new JPanel(false);
        panel1.setLayout(new GridLayout(8, 2));
        panel1.add(new JLabel(Lang.get("max_threads") + ":"));
        try {
            maxtasksModel.setValue(Integer.decode(Config.get("MaxTasks")));
        } catch (Exception e) {
        }
        ((DefaultEditor) maxtasks.getEditor()).getTextField().setEditable(false);
        panel1.add(maxtasks);
        panel1.add(new JLabel(Lang.get("choose_language") + ":"));
        panel1.add(languages);
        panel1.add(new JLabel(Lang.get("output_filename_format") + ":"));
        ofilenameformat.setText(Config.get("o_filename_format"));
        panel1.add(ofilenameformat);
        panel1.add(new JLabel());
        JComponent sub = new JPanel(false);
        SpringLayout layouta=new SpringLayout();
        sub.setLayout(layouta);
        DraggableLabel a1=new DraggableLabel("original","<P>");
        sub.add(a1);
        DraggableLabel a2=new DraggableLabel("date","<D>");
        layouta.putConstraint(SpringLayout.WEST, a2,100,SpringLayout.WEST, sub);
        sub.add(a2);
        DraggableLabel a3=new DraggableLabel("time","<T>");
        layouta.putConstraint(SpringLayout.WEST, a3,150,SpringLayout.WEST, sub);
        sub.add(a3);
        panel1.add(sub);
        panel1.add(new JLabel(Lang.get("output_cue_filename_format") + ":"));
        ocuefilenameformat.setText(Config.get("o_cue_filename_format"));
        panel1.add(ocuefilenameformat);
        
        JComponent subcue = new JPanel(false);
        SpringLayout layoutb=new SpringLayout();
        subcue.setLayout(layoutb);
        DraggableLabel b1=new DraggableLabel("number","<N>");
        subcue.add(b1);
        DraggableLabel b2=new DraggableLabel("performer","<P>");
        layoutb.putConstraint(SpringLayout.WEST, b2,100,SpringLayout.WEST, subcue);
        subcue.add(b2);
        panel1.add(subcue);
        JComponent subcue2 = new JPanel(false);
        SpringLayout layoutc=new SpringLayout();
        subcue2.setLayout(layoutc);
        DraggableLabel b3=new DraggableLabel("title","<L>");
        subcue2.add(b3);
        DraggableLabel b4=new DraggableLabel("date","<D>");
        layoutc.putConstraint(SpringLayout.WEST, b4,100,SpringLayout.WEST, subcue2);
        subcue2.add(b4);
        DraggableLabel b5=new DraggableLabel("time","<T>");
        layoutc.putConstraint(SpringLayout.WEST, b5,150,SpringLayout.WEST, subcue2);
        subcue2.add(b5);
        panel1.add(subcue2);

        panel1.add(new JLabel(Lang.get("output_dir") + ":"));
        outputdir.setText(Config.get("output_dir"));
        outputdir.setSize(100, outputdir.getHeight());
        panel1.add(outputdir);
        panel1.add(new JLabel());
        panely.addTab(Lang.get("general"), panel1);
        JComponent panel2 = new JPanel(false);
        panel2.setLayout(new GridLayout(8, 2));
        panel2.add(new JLabel(Lang.get("bit_rate") + ":"));
        obitRateModel.setValue(Integer.decode(Config.get("ogg_bitrate")));
        ((DefaultEditor) obitrate.getEditor()).getTextField().setEditable(false);
        panel2.add(obitrate);
        panel2.add(new JLabel(Lang.get("sampling_rate") + ":"));
        osampleRateModel.setValue(Integer.decode(Config.get("ogg_samplingrate")));
        ((DefaultEditor) osamplerate.getEditor()).getTextField().setEditable(false);
        panel2.add(osamplerate);
        panel2.add(new JLabel(Lang.get("channels") + ":"));
        ochannelsModel.setValue(Integer.decode(Config.get("ogg_channels")));
        ((DefaultEditor) ochannels.getEditor()).getTextField().setEditable(false);
        panel2.add(ochannels);
        panel2.add(new JLabel());
        panel2.add(new JLabel());
        panel2.add(new JLabel());
        panely.addTab(Lang.get("ogg_format"), panel2);
        JComponent panel3 = new JPanel(false);
        panel3.setLayout(new GridLayout(6, 2));
        /*panel3.add(new JLabel(Lang.get("bit_rate") + ":"));
        fbitRateModel.setValue(Integer.decode(Config.get("flac_bitrate")));
        ((DefaultEditor) fbitrate.getEditor()).getTextField().setEditable(false);
        panel3.add(fbitrate);*/
        panel3.add(new JLabel(Lang.get("sampling_rate") + ":"));
        fsampleRateModel.setValue(Integer.decode(Config.get("flac_samplingrate")));
        ((DefaultEditor) fsamplerate.getEditor()).getTextField().setEditable(false);
        panel3.add(fsamplerate);
        panel3.add(new JLabel(Lang.get("channels") + ":"));
        fchannelsModel.setValue(Config.get("flac_channels"));
        ((DefaultEditor) fchannels.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
        ((DefaultEditor) fchannels.getEditor()).getTextField().setEditable(false);
        panel3.add(fchannels);
        panel3.add(new JLabel());
        panel3.add(new JLabel());
        panel3.add(new JLabel());
        panely.addTab(Lang.get("flac_format"), panel3);
        panely.setPreferredSize(new Dimension(400, 300));
        contentPane.add(panely);
        confirm.setName("OK");
        confirm.setText(Lang.get("ok"));
        confirm.addActionListener(this);
        contentPane.add(confirm);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headline, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.NORTH, headline, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panely, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.NORTH, panely, 25, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, confirm, 0, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, confirm, 0, SpringLayout.SOUTH, contentPane);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Config.set("SettingsWindowPosX", Integer.toString(e.getWindow().getLocation().x));
                Config.set("SettingsWindowPosY", Integer.toString(e.getWindow().getLocation().y));
            }
        });
        this.pack();
        this.setResizable(false);
        this.setSize(425, 400);
        this.setModal(true);
        setVisible(true);
    }
    /**
     * Funkce zajistujici zavolani funkce pro ulozeni nastaveni po stisku tlacitka OK
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            save();
            this.dispose();
        }
    }
    /**
     * Funkce ulozi nastaveni - volat po stisku tlacitka OK
     */
    private void save() {
        Lang.set(languages.getSelectedIndex());
        MainMenu.setNames();
        MainWindow.refresh();
        Config.set("SettingsWindowPosX", Integer.toString(this.getLocation().x));
        Config.set("SettingsWindowPosY", Integer.toString(this.getLocation().y));
        Config.set("MaxTasks", maxtasksModel.getValue().toString());
        if (outputdir.getText() == null ? "" != null : !outputdir.getText().equals("")) {
            Config.set("output_dir", outputdir.getText());
        }
        if (ofilenameformat.getText() == null ? "" != null : !ofilenameformat.getText().equals("")) {
            Config.set("o_filename_format", ofilenameformat.getText());
        }
        if (ocuefilenameformat.getText() == null ? "" != null : !ocuefilenameformat.getText().equals("")) {
            Config.set("o_cue_filename_format", ocuefilenameformat.getText());
        }
        Config.set("ogg_samplingrate", osampleRateModel.getValue().toString());
        Config.set("flac_samplingrate", fsampleRateModel.getValue().toString());
        Config.set("ogg_bitrate", obitRateModel.getValue().toString());
        //Config.set("flac_bitrate", fbitRateModel.getValue().toString());
        Config.set("flac_channels", fchannelsModel.getValue().toString());
        Config.set("ogg_channels", ochannelsModel.getValue().toString());
    }
}
