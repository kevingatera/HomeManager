package besmart.team.homemanager;
import besmart.team.homemanager.logic.Task;

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

/**
 * Created by kevingatera on 23/11/17.
 */

public class CustomListAdapter extends ArrayAdapter<Task> {

    Context myContext;
    int resource;
    List<Task> taskList;


    public CustomListAdapter(Context myContext, int resource, List<Task> taskList){
        super(myContext, resource, taskList);
        this.myContext = myContext;
        this.resource = resource;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // This method will return
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.list_item, null);
        TextView taskName = view.findViewById(R.id.taskTitle);
        TextView taskDueDate = view.findViewById(R.id.taskDueDate);
        ImageView imageView = view.findViewById(R.id.imageView);
        System.out.println("Position is => " + Integer.toString(position));
        System.out.println("The list has => " + Integer.toString(taskList.size()) + " items");
        Task t = taskList.get(position);
        taskName.setText(t.getTitle());
        taskDueDate.setText(t.getDueDate());
        imageView.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_launcher_background));

        view.findViewById(R.id.buttonTaskDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Called when the user clicks on a list item */
                Intent intent = new Intent(myContext, TaskActivity.class);
                myContext.startActivity(intent);
            }
        });
        return view;
    }
}

