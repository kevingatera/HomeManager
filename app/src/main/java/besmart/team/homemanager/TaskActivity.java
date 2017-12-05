package besmart.team.homemanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import besmart.team.homemanager.logic.Task;
import besmart.team.homemanager.logic.User;

public class TaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText taskDescription;
    private EditText taskScore;
    private EditText taskDueDate;
    private Spinner assigneeChoices;
    private ArrayAdapter<String> assigneeAdapter;
    private ArrayList<String> assigneeNameList;

    private Button taskSubmit;
    private DatabaseReference databaseTask;
    private DatabaseReference databaseUsers;
    private String id;
    private String assignee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setTitle("Create new task");

        databaseUsers = FirebaseDatabase.getInstance().getReference().child("/users/children");

        databaseTask = FirebaseDatabase.getInstance().getReference("/task");

        taskName = (EditText) findViewById(R.id.taskNameEntry);
        taskDescription = (EditText) findViewById(R.id.taskDescriptionEntry);
        taskScore = (EditText) findViewById(R.id.taskScoreEntry);
        taskDueDate = (EditText) findViewById(R.id.taskDueDateEntry);
        taskSubmit = (Button) findViewById(R.id.submitButton);
        assigneeChoices = findViewById(R.id.taskAssigneeSpinner);

        assigneeNameList = new ArrayList<String>();
        assigneeNameList.add("NONE");


        assigneeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, assigneeNameList);

        assigneeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assigneeChoices.setAdapter(assigneeAdapter);

        taskSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrModifyTask("add");
            }
        });

        databaseUsers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for(DataSnapshot child : dataSnapshot.getChildren()){
                User user = dataSnapshot.getValue(User.class);
                assigneeNameList.add(user.getName());
                assigneeAdapter.notifyDataSetChanged();

                if(assignee != null) {
                    if(user.getName().equals(assignee)){
                        assigneeChoices.setSelection(assigneeNameList.indexOf(assignee));
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                assigneeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                assigneeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                assigneeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        // get data via the key
        String title = extras.getString("Title");
        String description = extras.getString("Description");
        String score = extras.getString("Score");
        String dueDate = extras.getString("Due Date");
        final String assignee = extras.getString("AssigneeName");

        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        id = extras.getString("ID");

        System.out.println("8888888888888888888888888");

        if (title != null &&
                description != null &&
                score != null & dueDate != null) {
            // do something with the data
            setTitle("Modify Task");
            taskSubmit.setText("MODIFY");
            taskName.setText(title);
            taskDescription.setText(description);
            taskScore.setText(score);
            taskDueDate.setText(dueDate);
            assigneeChoices.setSelection(assigneeAdapter.getPosition(assignee));;

            taskSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addOrModifyTask("modify");
                }
            });

        }
    }


    private void addOrModifyTask(String choice) {
        String title = taskName.getText().toString();
        String description = taskDescription.getText().toString();
        String score = taskScore.getText().toString();
        String dueDate = taskDueDate.getText().toString();
        String assigneeName = assigneeChoices.getSelectedItem().toString();

        if(!TextUtils.isEmpty(title) &&
                !TextUtils.isEmpty(description) &&
                !TextUtils.isEmpty(score) &&
                !TextUtils.isEmpty(dueDate) &&
                !TextUtils.isEmpty(assigneeName)) {


            // Generate an id
            if (choice.equals("add")){
                Random r = new Random();
                System.out.println("BOOOOOOOOOOOOOOOOO");
                id = "task" + Integer.toString(r.nextInt(999999-100000) + 100000);
            }


            Task task;

            task = new Task(id, title, description, dueDate, score, assigneeName);

            databaseTask.child(id).setValue(task);

            Toast.makeText(this, "The task has been successfully added!", Toast.LENGTH_LONG).show();

            this.finish();
        }

        else {
            Toast.makeText(this, "One or more fields missing", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void finish() {
        setResult(22);
        super.finish();
    }
}
