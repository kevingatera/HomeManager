package besmart.team.homemanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import besmart.team.homemanager.logic.Task;

public class TaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText taskDescription;
    private EditText taskScore;
    private EditText taskDueDate;
    private Button taskSubmit;
    private DatabaseReference databaseTask;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setTitle("Create new task");

        databaseTask = FirebaseDatabase.getInstance().getReference("/task");

        taskName = (EditText) findViewById(R.id.taskNameEntry);
        taskDescription = (EditText) findViewById(R.id.taskDescriptionEntry);
        taskScore = (EditText) findViewById(R.id.taskScoreEntry);
        taskDueDate = (EditText) findViewById(R.id.taskDueDateEntry);
        taskSubmit = (Button) findViewById(R.id.submitButton);

        taskSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrModifyTask("add");
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
        id = extras.getString("ID");

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

        if(!TextUtils.isEmpty(title) &&
                !TextUtils.isEmpty(description) &&
                !TextUtils.isEmpty(score) &&
                !TextUtils.isEmpty(dueDate)) {

            // Generate an id
            if (choice.equals("add")){
                Random r = new Random();
                id = "task" + Integer.toString(r.nextInt(999999-100000) + 100000);
            }

            Task task;
            task = new Task(id, title, description, dueDate, score);

            databaseTask.child(id).setValue(task);

            Toast.makeText(this, "The task has been successfully added!", Toast.LENGTH_LONG).show();

            this.finish();
        }

        else {
            Toast.makeText(this, "One or more fields missing", Toast.LENGTH_LONG).show();
        }
    }

}
