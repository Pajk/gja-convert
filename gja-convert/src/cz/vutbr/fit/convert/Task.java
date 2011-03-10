package cz.vutbr.fit.convert;

import java.awt.List;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import cz.vutbr.fit.convert.gui.MainWindow;
import cz.vutbr.fit.convert.settings.Config;
import cz.vutbr.fit.convert.settings.Lang;
import it.sauronsoftware.jave.*;
import javax.swing.JOptionPane;

import javax.swing.JProgressBar;

public class Task extends List implements EncoderProgressListener {

    private static final long serialVersionUID = 3619958613264215820L;
    private boolean cut;
    private String ifilename;
    private String oformat;
    private String ofilename;
    public JProgressBar progress;
    public float starttime;
    public float duration;
    public Thread running;
    private int bitrate;
    private int samplingrate;
    private int channels;

    public Task(String filename, String mode) {
        cut=false;
        ifilename = filename;
        oformat = mode;
        progress = new JProgressBar(0, 1000);
        progress.setEnabled(true);
        progress.setString(ifilename);
        progress.setStringPainted(true);
        progress.setToolTipText("<html>" + Lang.get("input_file") + ": " + ifilename + "<br>" + Lang.get("convert_into") + ": " + oformat + "</html>");
        loadConfig();
        this.start();
    }

    public Task(String filename, String mode, float starttime1, float duration1,String filename_new){
        starttime=starttime1;
        duration=duration1;
        ofilename=filename_new;
        cut=true;
        ifilename = filename;
        oformat = mode;
        progress = new JProgressBar(0, 1000);
        progress.setEnabled(true);
        progress.setString(ifilename);
        progress.setStringPainted(true);
        progress.setToolTipText("<html>" + Lang.get("input_file") + ": " + ifilename + "<br>" + Lang.get("convert_into") + ": " + oformat + "</html>");
        loadConfig();
        this.start();
    }

    private void loadConfig() {
        if (oformat == "FLAC") {
            try {
                bitrate = Integer.decode(Config.get("flac_bitrate"));
            } catch (Exception e) {
                bitrate = 128000;
            }
            try {
                samplingrate = Integer.decode(Config.get("flac_samplingrate"));
            } catch (Exception e) {
                samplingrate = 44100;
            }
            try {
                channels = Integer.decode(Config.get("flac_channels"));
            } catch (Exception e) {
                channels = 2;
            }
        } else if (oformat == "OGG") {
            try {
                bitrate = Integer.decode(Config.get("ogg_bitrate"));
            } catch (Exception e) {
                bitrate = 128000;
            }
            try {
                samplingrate = Integer.decode(Config.get("ogg_samplingrate"));
            } catch (Exception e) {
                samplingrate = 44100;
            }
            try {
                channels = Integer.decode(Config.get("ogg_channels"));
            } catch (Exception e) {
                channels = 2;
            }
        }
    }

    private void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                somethingChanged();
                File file;
                if ((Config.get("MaxTasks") != null) && (Integer.decode(Config.get("MaxTasks")) != 0)) {
                    while (TaskManager.numberRunningTasks() > Integer.decode(Config.get("MaxTasks"))) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                file = new File(ifilename);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat timeFormat = new SimpleDateFormat("HH-mm-ss-SS");
                if (cut){
                    ofilename = Config.get("output_dir") + File.separator + ofilename;
                } else {
                    ofilename = Config.get("output_dir") + File.separator;
                    ofilename += Config.get("o_filename_format").replaceAll("<P>", file.getName()).replaceAll("<D>", dateFormat.format(new Date())).replaceAll("<T>", timeFormat.format(new Date()));
                }
                ofilename += "." + oformat.toLowerCase();
                TaskManager.increaseRunningTasks();
                try {
                    File ofile = new File(ofilename);
                    recode(file, ofile);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Chyba pri vyhodnoceni parametru","Error",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                    progress.setEnabled(false);
                    TaskManager.decreaseRunningTasks();
                    return;
                } catch (InputFormatException e) {
                    JOptionPane.showMessageDialog(null, "Chyba vstupniho souboru","Error",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                    progress.setEnabled(false);
                    TaskManager.decreaseRunningTasks();
                    return;
                } catch (EncoderException e) {
                    JOptionPane.showMessageDialog(null, "Chyba enkoderu","Error",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                    progress.setEnabled(false);
                    TaskManager.decreaseRunningTasks();
                    return;
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Jina chyba","Error",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                    progress.setEnabled(false);
                    TaskManager.decreaseRunningTasks();
                    return;
                }
                progress.setValue(1000);
                progress.setEnabled(false);
                TaskManager.decreaseRunningTasks();
                somethingChanged();
            }
        }).start();
    }

    private void recode(File input, File output) throws IllegalArgumentException, InputFormatException, EncoderException {
        AudioAttributes audio = new AudioAttributes();
        EncodingAttributes attrs = new EncodingAttributes();
        Encoder encoder = new Encoder();
        if (cut){
            attrs.setOffset(starttime);
            if (duration<0) {
                attrs.setDuration(encoder.getInfo(input).getDuration()-starttime);
            } else attrs.setDuration(duration);
        }
        if (oformat == "OGG") {
            audio.setCodec("vorbis");
            audio.setBitRate(bitrate);
            audio.setChannels(channels);
            audio.setSamplingRate(samplingrate);
            attrs.setFormat("ogg");
            attrs.setAudioAttributes(audio);
            encoder.encode(input, output, attrs, (EncoderProgressListener) this);
        } else if (oformat == "FLAC") {
            audio.setCodec("flac");
            audio.setBitRate(bitrate);
            audio.setChannels(channels);
            audio.setSamplingRate(samplingrate);
            attrs.setFormat("flac");
            attrs.setAudioAttributes(audio);
            encoder.encode(input, output, attrs, (EncoderProgressListener) this);
        }
    }

    public void sourceInfo(it.sauronsoftware.jave.MultimediaInfo info) {
    }

    public void progress(int permil) {
        progress.setValue(permil);
        somethingChanged();
    }

    public void message(java.lang.String message) {
    }

    private void somethingChanged() {
        try{
            progress.setToolTipText("<html>" + Lang.get("input_file") + ": " + ifilename + "<br>" + Lang.get("output_file") + ": " + ofilename + "<br>" + Lang.get("convert_into") + ": " + oformat + "</html>");
            MainWindow.refresh();
        }catch(Exception e){}
    }
}
