package cz.vutbr.fit.convert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public final class TaskManager {
	private static List<Task> tasks=new ArrayList<Task>();
	private static int runningTasks=0;
	public TaskManager(){
	}
	public static List<Task> getTasks(){
		return tasks;
	}
	public static int numberTasks(){
		return tasks.size();
	}
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
                    } else if (track_no!="" && radek.startsWith("TITLE")){
                        title_prev=title;
                        int index=radek.indexOf('\"')+1;
                        title=radek.substring(index, radek.indexOf('\"',index));
                    } else if (track_no!="" && radek.startsWith("PERFORMER")){
                        performer_prev=performer;
                        int index=radek.indexOf('\"')+1;
                        performer=radek.substring(index, radek.indexOf('\"',index));
                    } else if (track_no!="" && radek.startsWith("INDEX 01")){
                        starttime_prev=starttime;
                        int index=radek.indexOf(':');
                        starttime=Integer.parseInt(radek.substring(9, index));
                        starttime*=60;
                        index=radek.indexOf(':',index);
                        starttime+=Integer.parseInt(radek.substring(index-2, index));
                        starttime*=60;
                        starttime+=Integer.parseInt(radek.substring(index+1, index+3));
                        starttime/=60;
                        if (track_no_prev!=""){
                            tasks.add(new Task(file,mode,starttime_prev,starttime-starttime_prev,filename(track_no_prev,performer_prev,title_prev)));
                        }
                    }
                }
                if (track_no!=""){
                    tasks.add(new Task(file,mode,starttime,-1,filename(track_no,performer,title)));
                }
                }catch(Exception e){ JOptionPane.showMessageDialog(null, "Chybny format souboru "+filename,"Error",JOptionPane.ERROR_MESSAGE); }
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
            else JOptionPane.showMessageDialog(null, "Chybny format souboru "+filename,"Error",JOptionPane.ERROR_MESSAGE);
	}
	public static void increaseRunningTasks(){
		runningTasks++;
	}
	public static void decreaseRunningTasks(){
		runningTasks--;
	}
	public static int numberRunningTasks(){
		return runningTasks;
	}

       private static String filename(String track_no,String performer, String title){
           return track_no+"-"+performer+"-"+title;
       }
}
