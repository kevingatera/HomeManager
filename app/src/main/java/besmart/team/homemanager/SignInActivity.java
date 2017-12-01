package besmart.team.homemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signInButton;
    private EditText loginEmail;
    private EditText loginPassword;
    private TextView inviteToRegister;

    FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        // Check if the user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            // User is already logged in
            // Start new activity
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        loginEmail = (EditText) findViewById(R.id.email);
        loginPassword = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.email_sign_in_button);
        inviteToRegister = (TextView) findViewById(R.id.invtationToRegister);

        progressDialog = new ProgressDialog(this);

        signInButton.setOnClickListener(this);
        inviteToRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == signInButton){
            performLogin();
        }

        if (view == inviteToRegister){
            // Open the registration page
//            setContentView(R.layout.activity_login);
            finish();
            startActivity(new Intent(this, RegistrationActivity.class));
        }
    }

    private void performLogin() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        // Check if the username is empty
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_LONG).show();
        }

        // Check if the password is empty
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Logging in... Please wait.");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    // This method will be called once the login is completed
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            // Start the MainActivity is everything is successful
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),  "The username or password is invalid.", Toast.LENGTH_LONG).show();
                            System.out.println(task.getException());
                        }
                    }
                });

       /*firebaseAuth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           progressDialog.dismiss();
                           FirebaseUser user = firebaseAuth.getCurrentUser();
                           System.out.println("USER CREATEDDDDD!!!!!!!!!!!!!");
                       }

                       else {
                           System.out.println("User not created" + task.getException());
                       }

                   }
               });*/



    }

}
