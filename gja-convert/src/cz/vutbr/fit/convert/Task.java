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
/**
 * Task class
 * Trida reprezentujici jednu ulohu
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 */
public class Task extends List implements EncoderProgressListener {
    private static final long serialVersionUID = 3619958613264215820L;
    /**
     * Priznak indikujici, ze ma dojit k orezani souboru
     * True - dojde k orezani
     * False - soubor se zpracuje cely
     */
    private boolean cut;
    /**
     * nazev vstupniho souboru
     */
    private String ifilename;
    /**
     * vystupni format souboru - ogg nebo flac
     */
    private String oformat;
    /**
     * nazev vystupniho souboru
     */
    private String ofilename;
    /**
     * zobrazovany progress bar
     */
    public JProgressBar progress;
    /*
     * zacatek dekodovani souboru
     */
    private float starttime;
    /**
     * delka vysledneho souboru
     */
    private float duration;
    /**
     * zvoleny bitrate
     */
    private int bitrate;
    /**
     * zvoleny sampling rate
     */
    private int samplingrate;
    /**
     * zvoleny pocet kanalu
     */
    private int channels;
    /**
     * Zvolena hlasitost - 256=bez zmeny, <256 snizeni hlasitosti, >256 zvyseni hlasitosti
     */
    private int volume;
    /**
     * Konstruktor - vytvori novou ulohu a spusti ji v novem vlakne
     * @param filename cesta a nazev vstupniho souboru
     * @param mode typ konverze - ogg nebo flac
     */
    public Task(String filename, String mode) {
        cut=false;
        ifilename = filename;
        oformat = mode;
        progress = new JProgressBar(0, 1000);
        progress.setEnabled(true);
        progress.setString(ifilename.substring(ifilename.lastIndexOf(File.separatorChar)+1));
        progress.setStringPainted(true);
        progress.setToolTipText("<html>" + Lang.get("input_file") + ": " + ifilename + "<br>" + Lang.get("convert_into") + ": " + oformat + "</html>");
        loadConfig();
        this.start();
    }
    /**
     * Konstruktor pro orezavane skladby
     * @param filename cesta a nazev vstupniho souboru
     * @param mode typ konverze - ogg nebo flac
     * @param starttime1 zacatek dekodovani
     * @param duration1 delka vystupniho souboru
     * @param filename_new jmeno noveho souboru
     */
    public Task(String filename, String mode, float starttime1, float duration1,String filename_new){
        starttime=starttime1;
        duration=duration1;
        ofilename=filename_new;
        cut=true;
        ifilename = filename;
        oformat = mode;
        progress = new JProgressBar(0, 1000);
        progress.setEnabled(true);
        progress.setString(ifilename.substring(ifilename.lastIndexOf(File.separatorChar)+1));
        progress.setStringPainted(true);
        progress.setToolTipText("<html>" + Lang.get("input_file") + ": " + ifilename + "<br>" + Lang.get("convert_into") + ": " + oformat + "</html>");
        loadConfig();
        this.start();
    }
    /**
     * Funkce nacte potrebne udaje pro konverzi z nastaveni
     */
    private void loadConfig() {
        if (oformat == null ? "FLAC" == null : oformat.equals("FLAC")) {
            try {
                volume = (int) (Integer.decode(Config.get("flac_volume")) * 2.56);
            } catch (Exception e) {
                volume = 256;
            }
//          u flacu se bitrate nenastavuje
//            try {
//                bitrate = Integer.decode(Config.get("flac_bitrate"));
//            } catch (Exception e) {
//                bitrate = 128000;
//            }
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
        } else if (oformat == null ? "OGG" == null : oformat.equals("OGG")) {
            try {
                volume = (int) (Integer.decode(Config.get("ogg_volume")) * 2.56);
            } catch (Exception e) {
                volume = 256;
            }
            try {
                // u ogg se nastavuje kvalita (0-?), vyuzijem vsak promennou
                // bitrate, maji k sobe blizko
                bitrate = Integer.decode(Config.get("ogg_quality"));
            } catch (Exception e) {
                bitrate = 50;
            }
            try {
                samplingrate = Integer.decode(Config.get("ogg_samplingrate"));
            } catch (Exception e) {
                samplingrate = 44100;
            }
            channels = 2;
        }
    }
    /**
     * Funkce pro zahajeni nove ulohy v novem vlakne
     * Uloha pokracuje ve zpracovani, pokud je je dostatek volnych prostredku (pocet paralelne zpracovavanych uloh je mensi nez max)
     */
    private void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                somethingChanged();
                File file;
                if ((Config.get("MaxTasks") != null) && (Integer.decode(Config.get("MaxTasks")) != 0)) {
                    while (TaskManager.isBusy()) {
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
                try {
                    File ofile = new File(ofilename);
                    recode(file, ofile);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Chyba pri vyhodnoceni parametru","Error",JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                    progress.setEnabled(false);
                    TaskManager.decreaseRunningTasks();
                    return;
                } catch (InputFormatException e) {
                    JOptionPane.showMessageDialog(null, "Chyba vstupniho souboru","Error",JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                    progress.setEnabled(false);
                    actualizeText();
                    TaskManager.decreaseRunningTasks();
                    return;
                } catch (EncoderException e) {
                    JOptionPane.showMessageDialog(null, "Chyba enkoderu","Error",JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                    progress.setEnabled(false);
                    TaskManager.decreaseRunningTasks();
                    return;
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Jina chyba","Error",JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                    progress.setEnabled(false);
                    TaskManager.decreaseRunningTasks();
                    return;
                }
                progress.setValue(1000);
                progress.setEnabled(false);
                TaskManager.decreaseRunningTasks();
                try {
                    Thread.sleep(100); //aby nedochazelo k vyjimce z rychleho prekresleni
                } catch (InterruptedException ex) {}
                somethingChanged();
            }
        }).start();
    }
    /**
     * Funkce provede rekodovani souboru
     * @param input soubor se vstupem
     * @param output soubor s vystupem
     * @throws IllegalArgumentException
     * @throws InputFormatException
     * @throws EncoderException
     */
    private void recode(File input, File output) throws IllegalArgumentException, InputFormatException, EncoderException {
        AudioAttributes audio = new AudioAttributes();
        EncodingAttributes attrs = new EncodingAttributes();
        Encoder encoder = new Encoder();
        float delka=encoder.getInfo(input).getDuration();
        if (cut){
            attrs.setOffset(starttime);
            if (duration<0) {
                attrs.setDuration(delka-starttime);
            } else attrs.setDuration(duration);
        } else {
            //attrs.setDuration(delka);
        }
        if (oformat == null ? "OGG" == null : oformat.equals("OGG")) {
            audio.setCodec("vorbis");
            audio.setQuality(bitrate);
            audio.setChannels(channels);
            audio.setSamplingRate(samplingrate);
            audio.setVolume(volume);
            attrs.setFormat("ogg");
            attrs.setAudioAttributes(audio);
            encoder.encode(input, output, attrs, (EncoderProgressListener) this);
        } else if (oformat == null ? "FLAC" == null : oformat.equals("FLAC")) {
            audio.setCodec("flac");
            //audio.setBitRate(bitrate);
            audio.setChannels(channels);
            audio.setSamplingRate(samplingrate);
            audio.setVolume(volume);
            attrs.setFormat("flac");
            attrs.setAudioAttributes(audio);
            encoder.encode(input, output, attrs, (EncoderProgressListener) this);
        }
    }
    /**
     * Funkce nic nedela
     * @param info
     */
    public void sourceInfo(it.sauronsoftware.jave.MultimediaInfo info) {
    }
    /**
     * Funkce je volana po zmene progressu rekodovaci funkce
     * - zmeni progress progress baru a vyvola jeho repaint
     * @param permil
     */
    public void progress(int permil) {
        progress.setValue(permil);
        somethingChanged();
    }
    /**
     * Funkce nic nedela
     * @param message
     */
    public void message(java.lang.String message) {
    }
    /**
     * Funkce aktializuje popisek progress baru, aby odpovidala jazykovemu nastaveni
     */
    public void actualizeText(){
        progress.setToolTipText("<html>" + Lang.get("input_file") + ": " + ifilename + "<br>" + Lang.get("output_file") + ": " + ofilename + "<br>" + Lang.get("convert_into") + ": " + oformat + "</html>");
    }
    /**
     * Funkce je volana, pokud se neco zmenilo - dojde k repaintu progress baru
     */
    private void somethingChanged() {
        try{
        MainWindow.refresh();
        }catch(Exception e){
        }
    }
}
