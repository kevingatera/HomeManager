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

public class Task {
	
	private String title;
	private String creationDate;
	private String dueDate;
	private String description;
	private String note;
	private String repeat;
	private String isAssigned;
	private String score;
	private Queue<Tool> toolQueue; //list contains tools
	
	//first constructor

	public Task() {
		title=null;
		creationDate=null;
		dueDate=null;
		description=null;
		note=null;
		repeat=null;
		isAssigned="false";
		toolQueue = new LinkedList<Tool>();
	}

	//second constructor which take parameters 
	/*
    public Task(String title1,
				String creationDate1,
				String dueDate1,
				String description1,
				String note1,String repeat1,
				boolean isAssigned1,
				Queue<Tool> toolQueue1,
				Integer score1) {
        title = title1;
        creationDate = creationDate1;
        dueDate = dueDate1;
        description = description1;
        note = note1;
        repeat = repeat1;
        isAssigned = isAssigned1;
        score = score1;
        toolQueue = toolQueue1;
	}*/

    public Task( String creationDate, String description, String dueDate, String score,  String note, String title) {
        this.title = title;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.description = description;
        this.note = note;
        this.score = score;
    }

    //setters
	 public void setTitle(String title1) {
		 title=title1;
	 }
	 public void setCreationDate(String creationDate1) {
		 creationDate=creationDate1;
	 }
	 public void setDueDate(String dueDate1) {
		 dueDate=dueDate1;
	 }
	 public void setDescription(String description1) {
		 description=description1;
	 }
	 public void setNote(String note1) {
		 note=note1;
	 }
	 public void setRepeat(String repeat1) {
		 repeat=repeat1;
	 }
	 public void setIsAssigned(String isAssigned1) {
		 isAssigned = isAssigned1;
	 }
	 public void setScore(String score1) {
		 score = score1;
	 }
	 public void setToolQueue( Queue<Tool> toolQueue1) {
		 toolQueue = toolQueue1;
	 }
	 
	 //getters
	 public String getTitle() {
		 return title;
	 }
	 public String getCreationDate() {
		 return creationDate;
	 }
	 public String getDueDate() {
		 return dueDate;
	 }
	 public String getDescription() {
		 return description;
	 }
	 public String getNote() {
		 return note;
	 }
	 public String getRepeat() {
		 return repeat;
	 }
	 public String getIsAssigned() {
		 return isAssigned;
	 }
	 public String getScore() {
		 return score;
	 }
	 public Queue<Tool> getToolQueue() {
		 return toolQueue;
	 }
	 
	 //method that create a tool
	  public void addTool(Tool tool1) {
		  toolQueue.add(tool1);
	  }
	  public void deleteTask(Task task1) {
		  task1.title=null;
		  task1.creationDate=null;
		  task1.dueDate=null;
		  task1.description=null;
		  task1.note=null;
		  task1.repeat=null;
		  task1.isAssigned="false";
		  task1.toolQueue = null;
	  }
}