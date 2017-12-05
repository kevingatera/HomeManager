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
public class User {

	protected String id;
	protected String name;
	protected String email;
	protected String gender;
	protected String age;
	protected String avatarLocation;
	protected String totalScore;
	protected Queue<Task> taskQueue; //list contains tasks

	//first constructor
	public User() {
		name=null;
		gender=null;
		age="0";
		avatarLocation=null;
		totalScore = "0";
		taskQueue = new LinkedList<Task>();
	}
	
		//setters
		public void setName(String name1) {
			 name=name1;
		 }
		public void setGender(String gender1) {
			 gender=gender1;
		 }
		public void setAge(String age1) {
			 age=age1;
		 }
		public void setAvatarLocation(String avatarLocation1) {
			 avatarLocation=avatarLocation1;
		 }
		public void setTotalScore(String totalScore1) {
			 totalScore = totalScore1;
		 }
		public void setTaskQueue( Queue<Task> taskQueue1) {
			 taskQueue = taskQueue1;
		 }
		 //getters
		public String getName() {
			 return name;
		 }
		public String getGender() {
			 return gender;
		 }
		public String getAvatarLocation() {
			 return avatarLocation;
		 }
		 public String getEmail() { return email; }
		public String gettotalScore() {
			 return totalScore;
		 }
		public Queue<Task> getTaskQueue() {
			 return taskQueue;
		 }
		 
		 //method that increment the score
		//public Integer increaseScore (Integer TaskScore) {
			//if (isCompleted) {
			//totalScore = + score;
			
		//}
}
