package besmart.team.homemanager.logic;

public class Parent extends User {

	private boolean isMarried=true;
	public Parent() {
		super();
		this.name = null;
	}
	  public void addTask(Task task1) {
		  taskQueue.add(task1);
	}

}
