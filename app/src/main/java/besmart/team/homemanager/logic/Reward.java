package besmart.team.homemanager.logic;

public class Reward {

    private String id;
    private String name;

    public Reward(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Reward() {
        id = null;
        name = "Nothing";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
