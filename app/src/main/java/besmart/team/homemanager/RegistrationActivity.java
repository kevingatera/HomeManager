package besmart.team.homemanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText regEmail;
    private EditText regPassword;
    private EditText regName;
    private EditText regGender;
    private RadioGroup typeRadioGroup;
    private Button addMember;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        auth = FirebaseAuth.getInstance();

        regEmail = (EditText) findViewById(R.id.regEmail);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regName = (EditText) findViewById(R.id.regName);
        regGender = (EditText) findViewById(R.id.regGender);
        typeRadioGroup = (RadioGroup) findViewById(R.id.itemTypeRadiogroup);

        progressDialog = new ProgressDialog(this);

        addMember = (Button) findViewById(R.id.regUserButton);

        addMember.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        performRegistration();
        finish();

    }

    public void performRegistration() {

        int selectedTypeId = typeRadioGroup.getCheckedRadioButtonId();

        String email = regEmail.getText().toString().trim();
        String password = regPassword.getText().toString().trim();

        if(typeRadioGroup == null) {
            Toast.makeText(this, "Please choose a type.", Toast.LENGTH_LONG).show();
            return;
        }

         auth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           progressDialog.dismiss();
                           FirebaseUser user = auth.getCurrentUser();
                           Toast.makeText(getApplication(), "User has been successfully created", Toast.LENGTH_LONG).show();
                       }

                       else {
                           System.out.println("User not created" + task.getException());
                       }

                   }
               });

    }

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
