package besmart.team.homemanager;

/**
 * Created by kevingatera on 23/11/17.
 */

public class TestTask {
    int image;
    String name;
    String dueDate;

    public TestTask(int image, String name, String dueDate) {
        this.image = image;
        this.name = name;
        this.dueDate = dueDate;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }
}
