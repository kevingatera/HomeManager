package besmart.team.homemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private EditText name;
    private EditText gender;
    private EditText type;
    private Button addMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
    }

    @Override
    public void onClick(View v) {

    }

    public void performRegistration() {

    }

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
