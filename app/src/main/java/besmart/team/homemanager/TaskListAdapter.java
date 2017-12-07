package besmart.team.homemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import besmart.team.homemanager.logic.Child;
import besmart.team.homemanager.logic.Task;

import static android.content.ContentValues.TAG;

/**
 * Created by kevingatera on 23/11/17.
 */

public class TaskListAdapter extends ArrayAdapter<Task> {

    private final fragment_main_tasks myFragment;
    Context myContext;
    int resource;
    ArrayList<Task> taskList;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public TaskListAdapter(Context myContext, int resource, ArrayList<Task> taskList, fragment_main_tasks myFragment){
        super(myContext, resource, taskList);
        this.myFragment = myFragment;
        this.myContext = myContext;
        this.resource = resource;
        this.taskList = taskList;
    }

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("/users/children");

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

        if(!t.getStatus().equals("incomplete")){
            view.findViewById(R.id.buttonTaskDone).setVisibility(View.GONE);
        }

        view.findViewById(R.id.buttonTaskDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Called when the user clicks on a list item's done button */

                // Add a listener to the buttton
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    Task task = taskList.get(position);
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            Child c = child.getValue(Child.class);

                            if(c.getEmail() == null) {
                                break;
                            }

                            if (c.getEmail().equals(user.getEmail()) && task.getStatus().equals("incomplete")) {
                                System.out.println(c.getEmail() + " " + user.getEmail());
                                System.out.println("Position : " + position + " and Size : " + taskList.size());
                                FirebaseDatabase.getInstance().getReference("/task/").child(task.getId()).child("status").setValue("complete");
                                task.setStatus("complete");

                                // Increase the child score
                                c.incScore(task.getScore());
                                userRef.child(child.getKey()).setValue(c);

                                Toast.makeText(getContext(), "CONGRATULATIONS! You've earned " + task.getScore() + " points.", Toast.LENGTH_LONG).show();
                            }
                        }
                        notifyDataSetChanged();
                        myFragment.reloadView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });
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
                        (FirebaseDatabase.getInstance().getReference("/task")).child(toBeRemoved.getId()).removeValue();
                        taskList.remove(toBeRemoved);
                        notifyDataSetChanged();

                        taskDetailsDialog.dismiss();
                    }
                });

                if(t.getStatus().equals("complete")) {
                    modifyButton.setText("RESET");
                    modifyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Add a reset button to the task
                            Task t = taskList.get(position);
                            FirebaseDatabase.getInstance().getReference("/task/").child(t.getId()).child("status").setValue("incomplete");
                            taskDetailsDialog.dismiss();
                            Toast.makeText(getContext(), "The Task is now in set as incomplete", Toast.LENGTH_LONG).show();
                            myFragment.reloadView();

                        }
                    });
                }

                if(modifyButton.getText().equals("Modify")){
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
                }

                taskDetailsDialog.show();
            }
        });

        return view;
    }

}

