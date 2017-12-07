package besmart.team.homemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import besmart.team.homemanager.logic.Child;
import besmart.team.homemanager.logic.Parent;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText regEmail;
    private EditText regName;
    private RadioGroup regTypeRadioGroup;
    private RadioGroup regGenderRadioGroup;
    private Button addMember;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase db;

    private String type;
    private String name;
    private String gender;
    private String email;
    private String password;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("/users");

        regEmail = findViewById(R.id.regEmail);

        regName = findViewById(R.id.regName);
        regGenderRadioGroup = findViewById(R.id.regGenderRadiogroup);
        regTypeRadioGroup = findViewById(R.id.itemTypeRadiogroup);

        progressDialog = new ProgressDialog(this);

        addMember = findViewById(R.id.regUserButton);

        addMember.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        performRegistration();
        finish();
    }

    public void performRegistration() {

        if(!((EditText)findViewById(R.id.regPassword1)).getText().toString().equals(((EditText)findViewById(R.id.regPassword1)).getText())){
            Toast.makeText(this, "Passwords mismatch!", Toast.LENGTH_LONG).show();
            return;
        }

        email = regEmail.getText().toString().trim();
        password = ((EditText)findViewById(R.id.regPassword1)).getText().toString().trim();

        progressDialog.show();

        if(regTypeRadioGroup == null) {
            Toast.makeText(this, "Please choose a type.", Toast.LENGTH_LONG).show();
            return;
        }

        if(regGenderRadioGroup == null) {
            Toast.makeText(this, "Please choose a gender.", Toast.LENGTH_LONG).show();
            return;
        }


        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    int selectedTypeId = regTypeRadioGroup.getCheckedRadioButtonId();
                                    int selectedGenderId = regGenderRadioGroup.getCheckedRadioButtonId();
                                    type = ((Button) findViewById(selectedTypeId)).getText().toString();
                                    name = regName.getText().toString();
                                    gender = ((Button) findViewById(selectedGenderId)).getText().toString();

                                    Random r = new Random();

                                    if(type.equals("Child")) {
                                        id = "child" + Integer.toString(r.nextInt(999999-100000) + 100000);
                                        Child c = new Child(id, name, email, gender);
                                        databaseReference.child("children").child(id).setValue(c);
                                    } else if (type.equals("Parent")) {
                                        id = "parent" + Integer.toString(r.nextInt(999999-100000) + 10000);
                                        Parent p = new Parent(id, name, email, gender);
                                        databaseReference.child("parent").child(id).setValue(p);
                                    }
                                    Toast.makeText(getApplication(), "User has been successfully created", Toast.LENGTH_LONG).show();
                                }

                                else {
                                    System.out.println("User not created" + task.getException());
                                }
                            }
                        }
                )
        ;
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }
}
