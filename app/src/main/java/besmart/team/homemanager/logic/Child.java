package besmart.team.homemanager.logic;

public class Child extends  User {

    public Child(){
        super();
    }

    public Child(String id, String name, String email, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public void  incScore(String inc) {
        this.totalScore = Integer.toString(Integer.parseInt(inc) + Integer.parseInt(totalScore));
    }
}
