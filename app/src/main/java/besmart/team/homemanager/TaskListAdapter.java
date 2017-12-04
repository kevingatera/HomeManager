package besmart.team.homemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import besmart.team.homemanager.logic.Task;

/**
 * Created by kevingatera on 23/11/17.
 */

public class TaskListAdapter extends ArrayAdapter<Task> {

    Context myContext;
    int resource;
    List<Task> taskList;


    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("/task");


    public TaskListAdapter(Context myContext, int resource, List<Task> taskList){
        super(myContext, resource, taskList);
        this.myContext = myContext;
        this.resource = resource;
        this.taskList = taskList;
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // This method will return
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.list_task, null);
        TextView taskName = view.findViewById(R.id.taskTitle);
        TextView taskDueDate = view.findViewById(R.id.taskDueDate);
        Task t = taskList.get(position);
        taskName.setText(t.getTitle());
        taskDueDate.setText("Due date: " + t.getDueDate());

        view.findViewById(R.id.buttonTaskDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Called when the user clicks on a list item */

            }
        });


        view.findViewById(R.id.taskRowView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task t = taskList.get(position);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = LayoutInflater.from(getContext()).inflate(R.layout.details, null);

                TextView modalTitle = mView.findViewById(R.id.taskModalTitle);
                TextView modalID = mView.findViewById(R.id.taskModalID);
                TextView modalDueDate = mView.findViewById(R.id.taskModalDueDate);
                TextView modalDescription = mView.findViewById(R.id.taskModalDescription);
                TextView modalStatus = mView.findViewById(R.id.taskModalStatus);
                TextView modalAssignee = mView.findViewById(R.id.taskModalAssignee);

                Button deleteButton = mView.findViewById(R.id.taskModalDelete);
                Button modifyButton = mView.findViewById(R.id.taskModalModifyButton);

                modalID.setText(t.getId());
                modalStatus.setText(modalStatus.getText() + t.getStatus().toUpperCase());
                modalTitle.setText(t.getTitle());
                modalDescription.setText(t.getDescription());
                modalDueDate.setText(t.getDueDate());
                modalAssignee.setText(t.getAssigneeName());

                mBuilder.setView(mView);
                final AlertDialog taskDetailsDialog = mBuilder.create();

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Task toBeRemoved = taskList.get(position);
                        myRef.child(toBeRemoved.getId()).removeValue();
                        taskList.remove(toBeRemoved);
                        notifyDataSetChanged();
                        taskDetailsDialog.dismiss();
                    }
                });

                modifyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Task toBeModified = taskList.get(position);
                        Intent i = new Intent(getContext(), TaskActivity.class);
                        i.putExtra("Title", toBeModified.getTitle());
                        i.putExtra("Description", toBeModified.getDescription());
                        i.putExtra("Due Date", toBeModified.getDueDate());
                        i.putExtra("Score", toBeModified.getScore());
                        i.putExtra("ID", toBeModified.getId());
                        i.putExtra("AssigneeName", toBeModified.getAssigneeName());
                        ((Activity) getContext()).startActivityForResult(i, 22);
                        taskDetailsDialog.dismiss();
                    }
                });

                taskDetailsDialog.show();
            }
        });

        return view;
    }

}

