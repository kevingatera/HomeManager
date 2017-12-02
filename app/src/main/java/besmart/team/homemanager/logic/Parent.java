package besmart.team.homemanager.logic;

public class Parent extends User {

	private boolean isMarried=true;
	public Parent() {
		super();
		this.name = null;
	}

    public Parent(String id, String name, String email, String gender) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
    }

    public void addTask(Task task1) {
		  taskQueue.add(task1);
	}

}
