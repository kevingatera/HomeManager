package besmart.team.homemanager;

import besmart.team.homemanager.logic.Task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class TaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText taskDescription;
    private EditText taskScore;
    private EditText taskDueDate;
    private Button taskSubmit;
    private DatabaseReference databaseTask;

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
                addTask();
            }
        });
    }

    private void addTask() {
        String title = taskName.getText().toString();
        String description = taskDescription.getText().toString();
        String score = taskScore.getText().toString();
        String dueDate = taskDueDate.getText().toString();

        if(!TextUtils.isEmpty(title) &&
                !TextUtils.isEmpty(description) &&
                !TextUtils.isEmpty(score) &&
                !TextUtils.isEmpty(dueDate)) {

            // Generate an id
            Random r = new Random();
            String id = "task" + Integer.toString(r.nextInt(999999-100000) + 100000);

            Task task;
            task = new Task(id, title, description, score, dueDate);

            databaseTask.child(id).setValue(task);

            Toast.makeText(this, "The task has been successfully added!", Toast.LENGTH_LONG).show();

            this.finish();
        }

        else {
            Toast.makeText(this, "One or more fields missing", Toast.LENGTH_LONG).show();
        }
    }

}
