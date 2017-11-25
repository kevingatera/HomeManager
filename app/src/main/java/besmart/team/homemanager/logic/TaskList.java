package besmart.team.homemanager.logic;

import java.util.LinkedList;
import java.util.Queue;

/*** 
 * This is class implements a task 
 * author:
 * Name:Gbadamassi Salwath   
 * Student Number: 8522039
 * Uottawa Email: sgbad044@uottawa.ca
 * 
 *
 */

public class TaskList {
	
	
	//private Task task;
	private Queue<Task> taskListQueue;
	
	public TaskList() {
		taskListQueue = new LinkedList <Task> ();
	}
}
