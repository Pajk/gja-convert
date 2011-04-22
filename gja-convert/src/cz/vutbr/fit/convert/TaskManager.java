package cz.vutbr.fit.convert;

import cz.vutbr.fit.convert.settings.Lang;
import cz.vutbr.fit.convert.settings.Config;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 * TaskManager class
 * Trida uchovavajici vsechny tasky jako list
 * coNvert project for GJA 2010/2011 - FIT VUT Brno
 * @author xizakt00
 * @see Task
 */
public final class TaskManager {
        /**
         * Seznam vsech tasku
         */
	private static List<Task> tasks=new ArrayList<Task>();
        /**
         * Pocet aktualne bezicich tasku
         */
	private static int runningTasks=0;
        /**
         * Prazny konstruktor
         */
	public TaskManager(){
	}
        /**
         * Funkce vrati seznam Tasku
         * @see Task
         * @return seznam tasku
         */
	public static List<Task> getTasks(){
		return tasks;
	}
        /**
         * Funkce vrati vsech tasku
         * @return pocet vsech tasku
         */
	public static int numberTasks(){
		return tasks.size();
	}
        /**
         * Funkce prida novy task
         * @see Task
         * @param filename jmeno zpracovavaneho souboru
         * @param mode typ konverze - ogg nebo flac
         */
	public static void addTask(String filename,String mode){
            if (filename.endsWith(".cue")){
                String file="";
                int temp;
                String track_no="";
                String title="";
                String performer="";
                float starttime=0;
                String track_no_prev="";
                String title_prev="";
                String performer_prev="";
                float starttime_prev=0;
                try{
                    FileReader fr = new FileReader(new File(filename));
                    while ((temp = fr.read()) != -1) { file+=(char)temp;}
                    fr.close();
                } catch(IOException e){}
                String[] content=file.split("\n");
                temp=0;
                try{
                for (String radek:content){
                    radek=radek.trim();
                    if (radek.startsWith("FILE")){
                        int index=radek.indexOf('\"')+1;
                        file=(new File(filename).getParentFile().getAbsolutePath())+File.separator +radek.substring(index, radek.indexOf('\"',index));
                    } else if (radek.startsWith("TRACK")){
                        track_no_prev=track_no;
                        int index=radek.indexOf("TRACK")+6;
                        track_no=radek.substring(index, radek.indexOf("AUDIO",index)-1).trim();
                    } else if ((track_no == null ? "" != null : !track_no.equals("")) && radek.startsWith("TITLE")){
                        title_prev=title;
                        int index=radek.indexOf('\"')+1;
                        title=radek.substring(index, radek.indexOf('\"',index));
                    } else if ((track_no == null ? "" != null : !track_no.equals("")) && radek.startsWith("PERFORMER")){
                        performer_prev=performer;
                        int index=radek.indexOf('\"')+1;
                        performer=radek.substring(index, radek.indexOf('\"',index));
                    } else if ((track_no == null ? "" != null : !track_no.equals("")) && radek.startsWith("INDEX 01")){
                        starttime_prev=starttime;
                        int index=radek.indexOf(':');
                        starttime=Integer.parseInt(radek.substring(9, index));
                        starttime*=60;
                        index=radek.indexOf(':',index);
                        starttime+=Integer.parseInt(radek.substring(index-2, index));
                        starttime*=60;
                        starttime+=Integer.parseInt(radek.substring(index+1, index+3));
                        starttime/=60;
                        if (track_no_prev == null ? "" != null : !track_no_prev.equals("")){
                            tasks.add(new Task(file,mode,starttime_prev,starttime-starttime_prev,filename(track_no_prev,performer_prev,title_prev)));
                        }
                    }
                }
                if (track_no == null ? "" != null : !track_no.equals("")){
                    tasks.add(new Task(file,mode,starttime,-1,filename(track_no,performer,title)));
                }
                }catch(Exception e){ JOptionPane.showMessageDialog(null, Lang.get("bad_format")+" "+filename,"Error",JOptionPane.ERROR_MESSAGE); }
            } else if ((filename.endsWith(".flac") ||
                            filename.endsWith(".ape") ||
                            filename.endsWith(".m4a") ||
                            filename.endsWith(".wv") ||
                            filename.endsWith(".ogg") ||
                            filename.endsWith(".mp3") ||
                            filename.endsWith(".mod") ||
                            filename.endsWith(".it") ||
                            filename.endsWith(".xm") ||
                            filename.endsWith(".wav") ||
                            filename.endsWith(".s3m")))
		tasks.add(new Task(filename,mode));
            else JOptionPane.showMessageDialog(null, Lang.get("bad_format")+" "+filename,"Error",JOptionPane.ERROR_MESSAGE);
	}
        /**
         * Funkce otestuje, zda je dostatek volnych prostredku a pokud je, zvysi pocet zpracovavanych uloh
         * @return true - bylo dosazeno maxima uloh, false - je volno a uloha byla zarazena mezi zpracovavane
         */
        public synchronized static boolean isBusy(){
                if (runningTasks >= Integer.decode(Config.get("MaxTasks"))) return true;
                else {
                    runningTasks++;
                    return false;
                }
        }
        /**
         * Funkce dekrementuje pocet aktualne bezicich tasku
         */
	public synchronized static void decreaseRunningTasks(){
		runningTasks--;
	}
        /**
         * Funkce vrati pocet aktualne bezicich tasku
         * @return pocet aktualne bezicich tasku
         */
	public static int numberRunningTasks(){
		return runningTasks;
	}
        /**
         * Funkce vrati naformatovany string vystupniho souboru pro konverzi pomoci .cue souboru
         * @param track_no cislo skladby
         * @param performer autor skladby
         * @param title nazev skladby
         * @return nazev vystupniho souboru
         */
        private static String filename(String track_no,String performer, String title){
            String temp=Config.get("o_cue_filename_format");
            temp.replaceAll("<P>", performer);
            temp.replaceAll("<L>", title);
            temp.replaceAll("<N>", track_no);
            return temp;
        }
}
