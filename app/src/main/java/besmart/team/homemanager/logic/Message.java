package besmart.team.homemanager.logic;
/*** 
 * This is class implements a task 
 * author:
 * Name:Gbadamassi Salwath   
 * Student Number: 8522039
 * Uottawa Email: sgbad044@uottawa.ca
 * 
 *
 */
public class Message {
	private String creationDate;
	private String content;
	private User sender; //the sender is an user
	private User receiver;
	
	// constructor which take parameters 
	public Message(String creationDate1,String content1,User sender1,User receiver1) {
		
		creationDate=creationDate1;
		content=content1;
		sender=sender1;
		receiver=receiver1;
		
	}
	
		//setters
		public void setCreationDate(String creationDate1) {//type change a string au lieu de int
			creationDate=creationDate1;
		 }
		 public void setContent(String content1) {
			 content=content1;
		 }
		 public void setSender(User sender1) {
			 sender=sender1;
		 }
		 public void setReceiver(User receiver1) {
			 receiver=receiver1;
		 }
		 
		 //getters
		 public String getCreationDate() {
			 return creationDate;
		 }
		 public String getContent() {
			 return content;
		 }
		 public User getSender() {
			 return sender;
		 }
		 public User getReceiver() {
			 return receiver;
		 }
}
