package besmart.team.homemanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import besmart.team.homemanager.logic.Child;
import besmart.team.homemanager.logic.User;

/**
 * Created by kevingatera on 25/11/17.
 */

public class UserListAdapter extends ArrayAdapter<User> {

    Context myContext;
    int resource;
    List<User> userList;


    public UserListAdapter(Context myContext, int resource, List<User> userList){
        super(myContext, resource, userList);
        this.myContext = myContext;
        this.resource = resource;
        this.userList = userList;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // This method will return
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.list_people, null);
        TextView userNameLabel = view.findViewById(R.id.userNameLabel);
        TextView userScoreLabel = view.findViewById(R.id.userScoreLabel);
        ImageView imageFamilyMember = view.findViewById(R.id.imageFamilyMember);
        System.out.println("Position is => " + Integer.toString(position));
        System.out.println("The list has => " + Integer.toString(userList.size()) + " items");
        User user = userList.get(position);
        userNameLabel.setText(user.getName());
        userScoreLabel.setText(user.gettotalScore());
        imageFamilyMember.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_launcher_background));

//        view.findViewById(R.id.buttonTaskDone).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /* Called when the user clicks on a list item */
//                Intent intent = new Intent(myContext, TaskActivity.class);
//                myContext.startActivity(intent);
//            }
//        });
        return view;
    }
}


