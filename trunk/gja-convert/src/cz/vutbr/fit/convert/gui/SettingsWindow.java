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
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Settings window class
 * okno s nastavenim aplikace
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public final class SettingsWindow extends JDialog implements ActionListener, ChangeListener {

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
    private String[] fchannel = {"1", "2", "3", "4", "5", "6"};
    private SpinnerListModel fchannelsModel = new SpinnerListModel(fchannel);
    /**
     * Spinner pro vyber kanalu pro flac format
     */
    private JSpinner fchannels = new JSpinner(fchannelsModel);
    private String[] osample = {"8000", "11025", "12000", "16000", "22050", "24000", "32000", "44100", "48000"};
    private SpinnerListModel osampleRateModel = new SpinnerListModel(osample);
    private SpinnerNumberModel fsampleRateModel = new SpinnerNumberModel(44100, 1, 655350, 1);
    /**
     * Spinner pro zadani sample rate pro ogg format
     */
    private JSpinner osamplerate = new JSpinner(osampleRateModel);
    /**
     * Spinner pro zadani sample rate pro flac format
     */
    private JSpinner fsamplerate = new JSpinner(fsampleRateModel);
    private SpinnerNumberModel oqualityModel = new SpinnerNumberModel(Integer.valueOf(Config.get("ogg_quality")).intValue(), 0, 1000, 1);
    /**
     * Spinner pro zadani bit rate pro ogg format
     */
    private JSpinner obitrate = new JSpinner(oqualityModel); //obitRateModel);
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
    public JButton confirm = new JButton();
    /**
     * Tlacitko pro vyber vystupni slozky
     */
    private JButton directoryChooser = new JButton();
    private JSlider volume_ogg = new JSlider();
    private JSlider volume_flac = new JSlider();
    private JLabel volume_ogg_l = new JLabel();
    private JLabel volume_flac_l = new JLabel();

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
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Container contentPane = this.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);
//        JLabel headline = new JLabel("coNvert");
//        headline.setFont(new Font("Courier New", Font.BOLD, 20));
//        headline.setHorizontalAlignment(JLabel.CENTER);
//        contentPane.add(headline);
        JTabbedPane panely = new JTabbedPane();
        panely.setName("settings_tabbed_pane");
        JComponent panel1 = new JPanel(false);
        panel1.setLayout(new GridLayout(8, 2));

        // max pocet pracujicich vlaken
        JLabel threads_caption = new JLabel(Lang.get("max_threads") + ":");
        threads_caption.setToolTipText(Lang.get("max_threads"));
        panel1.add(threads_caption);
        try {
            maxtasksModel.setValue(Integer.decode(Config.get("MaxTasks")));
        } catch (Exception e) {
            maxtasksModel.setValue(4);
        }
        maxtasks.setName("max_tasks_spinner");
        ((DefaultEditor) maxtasks.getEditor()).getTextField().setEditable(false);
        panel1.add(maxtasks);

        JLabel cl = new JLabel(Lang.get("choose_language") + ":");
        cl.setName("choose_language_label");
        panel1.add(cl);
        panel1.add(languages);
        panel1.add(new JLabel(Lang.get("output_filename_format") + ":"));
        ofilenameformat.setText(Config.get("o_filename_format"));
        panel1.add(ofilenameformat);
        panel1.add(new JLabel());
        panel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JComponent sub = new JPanel(false);
        SpringLayout layouta = new SpringLayout();
        sub.setLayout(layouta);
        DraggableLabel a1 = new DraggableLabel("original", "<P>");
        sub.add(a1);
        DraggableLabel a2 = new DraggableLabel("date", "<D>");
        layouta.putConstraint(SpringLayout.WEST, a2, 90, SpringLayout.WEST, sub);
        sub.add(a2);
        DraggableLabel a3 = new DraggableLabel("time", "<T>");
        layouta.putConstraint(SpringLayout.WEST, a3, 140, SpringLayout.WEST, sub);
        sub.add(a3);
        panel1.add(sub);
        JLabel cue_file_format_caption = new JLabel(Lang.get("output_cue_filename_format") + ":");
        cue_file_format_caption.setToolTipText(Lang.get("output_cue_filename_format"));
        panel1.add(cue_file_format_caption);
        ocuefilenameformat.setText(Config.get("o_cue_filename_format"));
        panel1.add(ocuefilenameformat);

        JComponent subcue = new JPanel(false);
        SpringLayout layoutb = new SpringLayout();
        subcue.setLayout(layoutb);
        DraggableLabel b1 = new DraggableLabel("number", "<N>");
        layoutb.putConstraint(SpringLayout.WEST, b1, 30, SpringLayout.WEST, subcue);
        subcue.add(b1);
        DraggableLabel b2 = new DraggableLabel("performer", "<P>");
        layoutb.putConstraint(SpringLayout.WEST, b2, 110, SpringLayout.WEST, subcue);
        subcue.add(b2);
        panel1.add(subcue);
        JComponent subcue2 = new JPanel(false);
        SpringLayout layoutc = new SpringLayout();
        subcue2.setLayout(layoutc);
        DraggableLabel b3 = new DraggableLabel("title", "<L>");
        subcue2.add(b3);
        DraggableLabel b4 = new DraggableLabel("date", "<D>");
        layoutc.putConstraint(SpringLayout.WEST, b4, 90, SpringLayout.WEST, subcue2);
        subcue2.add(b4);
        DraggableLabel b5 = new DraggableLabel("time", "<T>");
        layoutc.putConstraint(SpringLayout.WEST, b5, 140, SpringLayout.WEST, subcue2);
        subcue2.add(b5);
        panel1.add(subcue2);
        JLabel output_caption = new JLabel(Lang.get("output_dir") + ":");
        output_caption.setToolTipText(Lang.get("output_dir"));
        panel1.add(output_caption);
        outputdir.setEditable(false);
        outputdir.setText(Config.get("output_dir"));
        outputdir.setFont(new Font("Arial", Font.PLAIN, 10));
        outputdir.setSize(100, outputdir.getHeight());
        panel1.add(outputdir);
        panel1.add(new JLabel());
        directoryChooser.setText(Lang.get("odir_button"));
        directoryChooser.setMnemonic(KeyEvent.VK_O);
        directoryChooser.addActionListener(this);
        panel1.add(directoryChooser);
        panely.addTab(Lang.get("general"), panel1);
        JComponent panel2 = new JPanel(false);
        panel2.setLayout(new GridLayout(8, 2));
        panel2.add(new JLabel(Lang.get("ogg_quality") + ":"));
        //obitRateModel.setValue(Config.get("ogg_quality"));
        ((DefaultEditor) obitrate.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
        ((DefaultEditor) obitrate.getEditor()).getTextField().setEditable(false);
        obitrate.setName("ogg_quality_spinner");
        panel2.add(obitrate);
        panel2.add(new JLabel(Lang.get("sampling_rate") + ":"));
        osampleRateModel.setValue(Config.get("ogg_samplingrate"));
        ((DefaultEditor) osamplerate.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
        ((DefaultEditor) osamplerate.getEditor()).getTextField().setEditable(false);
        osamplerate.setName("ogg_samplerate_spinner");
        panel2.add(osamplerate);
        panel2.add(new JLabel(Lang.get("volume") + ":"));
        volume_ogg.setMinimum(0);
        volume_ogg.setMaximum(200);
        volume_ogg.setValue(Integer.decode(Config.get("ogg_volume")));
        volume_ogg.addChangeListener(this);
        panel2.add(volume_ogg);
        panel2.add(new JLabel());
        volume_ogg_l.setHorizontalTextPosition(JLabel.RIGHT);
        volume_ogg_l.setText(Integer.toString(volume_ogg.getValue()) + " %");
        panel2.add(volume_ogg_l);
        panel2.add(new JLabel());
        panel2.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel2.setName("ogg_panel");
        panely.addTab(Lang.get("ogg_format"), panel2);
        JComponent panel3 = new JPanel(false);
        panel3.setLayout(new GridLayout(6, 2));
        panel3.add(new JLabel(Lang.get("sampling_rate") + ":"));
        fsampleRateModel.setValue(Integer.decode(Config.get("flac_samplingrate")));
        panel3.add(fsamplerate);
        panel3.add(new JLabel(Lang.get("channels") + ":"));
        fchannelsModel.setValue(Config.get("flac_channels"));
        ((DefaultEditor) fchannels.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
        ((DefaultEditor) fchannels.getEditor()).getTextField().setEditable(false);
        panel3.add(fchannels);
        panel3.add(new JLabel(Lang.get("volume") + ":"));
        volume_flac.setMinimum(0);
        volume_flac.setMaximum(200);
        volume_flac.setValue(Integer.decode(Config.get("flac_volume")));
        volume_flac.addChangeListener(this);
        panel3.add(volume_flac);
        panel3.add(new JLabel());
        volume_flac_l.setHorizontalTextPosition(JLabel.RIGHT);
        volume_flac_l.setText(Integer.toString(volume_flac.getValue()) + " %");
        panel3.add(volume_flac_l);
        panel3.add(new JLabel());
        panel3.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panely.addTab(Lang.get("flac_format"), panel3);
        panely.setPreferredSize(new Dimension(400, 300));
        contentPane.add(panely);
        confirm.setName("okButton");
        confirm.setText(Lang.get("ok"));
        confirm.setMnemonic(KeyEvent.VK_ENTER);
        confirm.addActionListener(this);
        contentPane.add(confirm);
//        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headline, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
//        layout.putConstraint(SpringLayout.NORTH, headline, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panely, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.NORTH, panely, 0, SpringLayout.NORTH, contentPane);
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
        //setVisible(true);
    }

    /**
     * Funkce zajistujici zavolani funkce pro ulozeni nastaveni po stisku tlacitka OK
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == confirm) {
            save();
            setVisible(false);
            //this.dispose();
        } else if (e.getSource() == directoryChooser) {
            System.out.println("Sem tu");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle(Lang.get("odirfiledialog_header"));
            int returnVal1 = chooser.showOpenDialog(this);
            if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                outputdir.setText(chooser.getSelectedFile().getPath());
            }
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
        Config.set("MaxTasks", maxtasks.getValue().toString());
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
        Config.set("ogg_quality", oqualityModel.getValue().toString());
        Config.set("flac_channels", fchannelsModel.getValue().toString());
        Config.set("flac_volume", Integer.toString(volume_flac.getValue()));
        Config.set("ogg_volume", Integer.toString(volume_ogg.getValue()));
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        if (ce.getSource() == volume_flac) {
            volume_flac_l.setText(Integer.toString(volume_flac.getValue()) + " %");
        } else if (ce.getSource() == volume_ogg) {
            volume_ogg_l.setText(Integer.toString(volume_ogg.getValue()) + " %");
        }
    }
}