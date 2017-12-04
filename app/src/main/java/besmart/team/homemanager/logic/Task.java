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

    private String status;
    private String id;
	private String title;
	private String creationDate;
	private String dueDate;
	private String description;
	private String repeat;
	private String assigneeName;
	private String score;
	private Queue<Tool> toolQueue; //list contains tools
	
	//first constructor

	public Task() {
		title=null;
		creationDate=null;
		dueDate=null;
		description=null;
		repeat=null;
		assigneeName = "No one";
		toolQueue = new LinkedList<Tool>();
	}

	//second constructor which take parameters 



    public Task(String id, String title, String description, String dueDate, String score) {
		this.id = id;
    	this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.score = score;
    }

	public Task(String id, String title, String description, String dueDate, String score, String assigneeName) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.score = score;
		this.assigneeName = assigneeName;
		this.status = "incomplete";
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

	public void setRepeat(String repeat1) {
		 repeat=repeat1;
	 }
	 public void setAssigneeName(String isAssigned1) {
		 assigneeName = isAssigned1;
	 }
	 public void setScore(String score1) {
		 score = score1;
	 }
	public void setId(String id) {
		this.id = id;
	}
	 public void setToolQueue( Queue<Tool> toolQueue1) {
		 toolQueue = toolQueue1;
	 }

	 //getters
	public String getId() {return id; }
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
	public String getRepeat() {
		 return repeat;
	 }
	public String getAssigneeName() {
		 return assigneeName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
