package besmart.team.homemanager.logic;

public class Tool {
	private String name;
	private String quantity;
	private String id;
	
	public Tool() {
		this.name = null;
		this.quantity = "NONE";
	}

	public Tool(String id, String name, String quantity) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setName(String name1) {
		name = name1;
	}
	public String getName () {
		return name;
	}

    public String getId() {
        return id;
    }
}
