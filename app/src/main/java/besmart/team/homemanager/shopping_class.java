package besmart.team.homemanager;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class shopping_class extends AppCompatActivity {

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_shopping_class);
    //}
    private Fragment option;
    private String name;
    private int quantity;
     private String assignee;

    public shopping_class(Fragment option, String name, int quantity, String assignee){
        this.option = option;
        this.name = name;
        this. quantity = quantity;
        this. assignee = assignee;
    }

    void setOption(Fragment opt){
        this.option = opt;
    }
    void setName(String nom){
        this.name = nom;
    }
    void setQuantity(Integer quant){
        this.quantity = quant;
    }
    void setAssignee(String parent){
        this.assignee = parent;
    }
    Fragment getOption(){
        return option;
    }
    String getName(){
        return name;
    }
    String getAssignee(){
        return assignee;
    }
    Integer getQuantity(){
        return quantity;
    }
}
