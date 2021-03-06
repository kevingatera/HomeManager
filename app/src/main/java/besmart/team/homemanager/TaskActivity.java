package besmart.team.homemanager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import besmart.team.homemanager.logic.Reward;
import besmart.team.homemanager.logic.Task;
import besmart.team.homemanager.logic.Tool;
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
    private Button addToolToTask;
    private Button addRewardButton;

    private DatabaseReference databaseTask;
    private DatabaseReference databaseUsers;
    private DatabaseReference databaseTools;

    private String id;
    private String assignee;

    private ArrayList<Tool> availableTools;

    private ArrayList<Tool> mySelectedTools;

    private ToolListAdapter myToolListAdapter;

    private ListView myToolListView;

    ArrayList<Tool> selectedToolsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setTitle("Create new task");

        databaseUsers = FirebaseDatabase.getInstance().getReference().child("/users/children");

        databaseTask = FirebaseDatabase.getInstance().getReference("/task");

        databaseTools = FirebaseDatabase.getInstance().getReference("/tools");

        taskName = findViewById(R.id.taskNameEntry);
        taskDescription = findViewById(R.id.taskDescriptionEntry);
        taskScore = findViewById(R.id.taskScoreEntry);
        taskDueDate = findViewById(R.id.taskDueDateEntry);
        taskSubmit = findViewById(R.id.submitButton);
        assigneeChoices = findViewById(R.id.taskAssigneeSpinner);

        assigneeNameList = new ArrayList<String>();
        assigneeNameList.add("NONE");

        assigneeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, assigneeNameList);

        assigneeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assigneeChoices.setAdapter(assigneeAdapter);

        mySelectedTools = new ArrayList<>();

        taskSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrModifyTask("add");
            }
        });

        addToolToTask = findViewById(R.id.addToolTotaskButton);

        addToolToTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(TaskActivity.this);
                View mView = LayoutInflater.from(TaskActivity.this).inflate(R.layout.display_tools_list, null);

                availableTools = new ArrayList<Tool>();

                myToolListAdapter = new ToolListAdapter(TaskActivity.this, R.layout.list_tool, availableTools);

                myToolListView = mView.findViewById(R.id.toolsChoiceListview);

                myToolListView.setAdapter(myToolListAdapter);

                databaseTools.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        for(DataSnapshot familySnapshot : dataSnapshot.getChildren()){
                            Tool tool = dataSnapshot.getValue(Tool.class);
                            availableTools.add(tool);
//                        }
                        myToolListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        myToolListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        myToolListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        myToolListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });

                mBuilder.setView(mView);
                final AlertDialog addToolDetailsDialog = mBuilder.create();

                Button confirmToolsButton = mView.findViewById(R.id.confirmToolsButton);

                confirmToolsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToolDetailsDialog.dismiss();
                    }
                });

                addToolDetailsDialog.show();
            }
        });

        addRewardButton = findViewById(R.id.addRewardButton);

        addRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(TaskActivity.this);
                View mView = LayoutInflater.from(TaskActivity.this).inflate(R.layout.activity_award_class, null);

                final EditText modalTitle = mView.findViewById(R.id.rewardName);

                Button addRewadButton = mView.findViewById(R.id.addRewadButton);

                mBuilder.setView(mView);
                final AlertDialog addRewardDetailsDialog = mBuilder.create();

                addRewadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = modalTitle.getText().toString();

                        Random r = new Random();

                        String id = "reward" + Integer.toString(r.nextInt(999999-100000) + 100000);

                        Reward toBeAdded = new Reward(id, name);

                        (FirebaseDatabase.getInstance().getReference("/reward")).child(toBeAdded.getId()).setValue(toBeAdded);

                        TextView rewardListTextView = findViewById(R.id.rewardListTextView);

                        rewardListTextView.setText(rewardListTextView.getText() + toBeAdded.getName() + ", ");

                        addRewardDetailsDialog.dismiss();
                    }
                });

                addRewardDetailsDialog.show();
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

        id = extras.getString("ID");


        if (title != null &&
                description != null &&
                score != null & dueDate != null) {
            // do something with the data
            setTitle("Modify Task");
            taskSubmit.setText("MODIFY");
            addToolToTask.setText("Modify Tools");
            taskName.setText(title);
            taskDescription.setText(description);
            taskScore.setText(score);
            taskDueDate.setText(dueDate);
            assigneeChoices.setSelection(assigneeAdapter.getPosition(assignee));

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

        if(!TextUtils.isEmpty(description)) {
            description = "Nothing";
        }

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

    public void addSelectedToolsList(Tool tool) {
        mySelectedTools.add(tool);
        TextView toolListTextView = findViewById(R.id.toolListTextView);
        toolListTextView.setText(toolListTextView.getText() + tool.getName() + ", ");
    }

    public void removeSelectedToolFromList(Tool tool) {
        mySelectedTools.remove(mySelectedTools.indexOf(tool));
    }
}
