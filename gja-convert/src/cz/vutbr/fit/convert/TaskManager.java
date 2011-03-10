package cz.vutbr.fit.convert;

import java.util.ArrayList;
import java.util.List;

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
		tasks.add(new Task(filename,mode));
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
}
