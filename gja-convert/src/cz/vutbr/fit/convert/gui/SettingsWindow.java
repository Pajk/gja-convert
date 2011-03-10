package cz.vutbr.fit.convert.gui;

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

public final class SettingsWindow extends JDialog implements ActionListener {

    private static final long serialVersionUID = -3491161084404651198L;
    private JTextField ofilenameformat = new JTextField();
    private JComboBox languages = new JComboBox(Lang.getSupported());
    private SpinnerNumberModel ochannelsModel = new SpinnerNumberModel(2, 2, 2, 1);
    private String[] fchannel = {"1", "2", "6"};
    private SpinnerListModel fchannelsModel = new SpinnerListModel(fchannel);
    private JSpinner ochannels = new JSpinner(ochannelsModel);
    private JSpinner fchannels = new JSpinner(fchannelsModel);
    private SpinnerNumberModel osampleRateModel = new SpinnerNumberModel(44100, 12500, 44100, 100);
    private SpinnerNumberModel fsampleRateModel = new SpinnerNumberModel(44100, 12500, 44100, 100);
    private JSpinner osamplerate = new JSpinner(osampleRateModel);
    private JSpinner fsamplerate = new JSpinner(fsampleRateModel);
    private SpinnerNumberModel obitRateModel = new SpinnerNumberModel(128, 32, 192, 1);
    private SpinnerNumberModel fbitRateModel = new SpinnerNumberModel(128, 32, 192, 1);
    private JSpinner obitrate = new JSpinner(obitRateModel);
    private JSpinner fbitrate = new JSpinner(fbitRateModel);
    private SpinnerNumberModel maxtasksModel = new SpinnerNumberModel(5, 1, 100, 1);
    private JSpinner maxtasks = new JSpinner(maxtasksModel);
    private JTextField outputdir = new JTextField();
    private JButton confirm = new JButton();

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
            maxtasksModel.setValue(Config.get("MaxTasks"));
        } catch (Exception e) {
        }
        ((DefaultEditor) maxtasks.getEditor()).getTextField().setEditable(false);
        panel1.add(maxtasks);
        panel1.add(new JLabel(Lang.get("choose_language") + ":"));
        panel1.add(languages);
        panel1.add(new JLabel(Lang.get("output_filename_format") + ":"));
        ofilenameformat.setText("<P>-<D>-<T>");
        panel1.add(ofilenameformat);
        panel1.add(new JLabel(Lang.get("output_dir") + ":"));
        outputdir.setText(Config.get("output_dir"));
        outputdir.setSize(100, outputdir.getHeight());
        panel1.add(outputdir);
        panel1.add(new JLabel());
        panely.addTab(Lang.get("general"), panel1);
        JComponent panel2 = new JPanel(false);
        panel2.setLayout(new GridLayout(8, 2));
        panel2.add(new JLabel(Lang.get("bit_rate") + ":"));
        try {
            obitRateModel.setValue(Integer.decode(Config.get("ogg_bitrate")));
        } catch (Exception e) {
        }
        ((DefaultEditor) obitrate.getEditor()).getTextField().setEditable(false);
        panel2.add(obitrate);
        panel2.add(new JLabel(Lang.get("sampling_rate") + ":"));
        try {
            osampleRateModel.setValue(Integer.decode(Config.get("ogg_samplingrate")));
        } catch (Exception e) {
        }
        ((DefaultEditor) osamplerate.getEditor()).getTextField().setEditable(false);
        panel2.add(osamplerate);
        panel2.add(new JLabel(Lang.get("channels") + ":"));
        try {
            ochannelsModel.setValue(Integer.decode(Config.get("ogg_channels")));
        } catch (Exception e) {
        }
        ((DefaultEditor) ochannels.getEditor()).getTextField().setEditable(false);
        panel2.add(ochannels);
        panel2.add(new JLabel());
        panel2.add(new JLabel());
        panel2.add(new JLabel());
        panely.addTab(Lang.get("ogg_format"), panel2);
        JComponent panel3 = new JPanel(false);
        panel3.setLayout(new GridLayout(8, 2));
        panel3.add(new JLabel(Lang.get("bit_rate") + ":"));
        try {
            fbitRateModel.setValue(Integer.decode(Config.get("flac_bitrate")));
        } catch (Exception e) {
        }
        ((DefaultEditor) fbitrate.getEditor()).getTextField().setEditable(false);
        panel3.add(fbitrate);
        panel3.add(new JLabel(Lang.get("sampling_rate") + ":"));
        try {
            fsampleRateModel.setValue(Integer.decode(Config.get("flac_samplingrate")));
        } catch (Exception e) {
        }
        ((DefaultEditor) fsamplerate.getEditor()).getTextField().setEditable(false);
        panel3.add(fsamplerate);
        panel3.add(new JLabel(Lang.get("channels") + ":"));
        try {
            fchannelsModel.setValue(Config.get("flac_channels"));
        } catch (Exception e) {
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            save();
            this.dispose();
        }
    }

    private void save() {
        Lang.set(languages.getSelectedIndex());
        MainMenu.setNames();
        Config.set("SettingsWindowPosX", Integer.toString(this.getLocation().x));
        Config.set("SettingsWindowPosY", Integer.toString(this.getLocation().y));
        Config.set("MaxTasks", maxtasksModel.getValue().toString());
        if (outputdir.getText() == null ? "" != null : !outputdir.getText().equals("")) {
            Config.set("output_dir", outputdir.getText());
        }
        if (ofilenameformat.getText() == null ? "" != null : !ofilenameformat.getText().equals("")) {
            Config.set("o_filename_format", ofilenameformat.getText());
        }
        Config.set("ogg_samplingrate", osampleRateModel.getValue().toString());
        Config.set("flac_samplingrate", fsampleRateModel.getValue().toString());
        Config.set("ogg_bitrate", obitRateModel.getValue().toString());
        Config.set("flac_bitrate", fbitRateModel.getValue().toString());
        Config.set("flac_channels", fchannelsModel.getValue().toString());
        Config.set("ogg_channels", ochannelsModel.getValue().toString());
    }
}
