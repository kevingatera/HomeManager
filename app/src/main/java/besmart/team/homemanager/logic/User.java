package besmart.team.homemanager.logic;

import java.util.Queue;
import java.util.LinkedList;
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

	protected boolean isAuthenticad;
	protected String name;
	protected String gender;
	protected String age;
	protected String avatarLocation;
	protected String totalScore;
	protected Queue<Task> taskQueue; //list contains tasks
	
	//first constructor
	public User() {
		isAuthenticad=false;
		name=null;
		gender=null;
		age="0";
		avatarLocation=null;
		totalScore = "0";
		taskQueue = new LinkedList<Task>();
	}
		//second constructor which take parameters 
	public User(boolean isAuthenticad1,String name1,String gender1,String age1,String avatarLocation1, Queue<Task> taskQueue1, String totalScore1) {
		isAuthenticad=isAuthenticad1;
		name=name1;
		gender=gender1;
		age=age1;
		avatarLocation=avatarLocation1;
		//totalScore = totalScore1;
		//taskQueue =taskQueue1;
	}
	
		//setters
		public void setIsAuthenticad(boolean isAuthenticad1) {
			isAuthenticad=isAuthenticad1;
		 }
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
		public boolean getIsAuthenticad() {
			 return isAuthenticad;
		 }
		public String getName() {
			 return name;
		 }
		public String getGender() {
			 return gender;
		 }
		public String getAvatarLocation() {
			 return avatarLocation;
		 }
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
