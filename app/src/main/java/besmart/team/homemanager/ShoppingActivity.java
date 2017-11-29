package besmart.team.homemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import besmart.team.homemanager.logic.ShoppingItem;

public class ShoppingActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton itemTypeRadioButton;
    private EditText itemName;
    private EditText itemQuantity;
    private Button addItemButton;
    private DatabaseReference databaseItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item);
        setTitle("Create a new item");

        databaseItem = FirebaseDatabase.getInstance().getReference("/shopping_items");

        radioGroup = (RadioGroup) findViewById(R.id.itemTypeRadiogroup);
        itemName = (EditText) findViewById(R.id.itemName);
        itemQuantity = (EditText) findViewById(R.id.itemQuantity);
        addItemButton = (Button) findViewById(R.id.addItemButton);

        addItemButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int selectedTypeId = radioGroup.getCheckedRadioButtonId();
        itemTypeRadioButton = (RadioButton) findViewById(selectedTypeId);
        String type;

        if(itemTypeRadioButton == null) {
            Toast.makeText(this, "Please choose an item type.", Toast.LENGTH_LONG).show();
            return;
        }

        type = itemTypeRadioButton.getText().toString();

        // Create more arguments for the Item's POJO
        String name = itemName.getText().toString();
        String quantity = itemQuantity.getText().toString();

        if (!TextUtils.isEmpty(name) || TextUtils.isEmpty(quantity)) {
            // generate an ID
            Random r = new Random();
            String id = "item" + Integer.toString(r.nextInt(999999-100000) + 100000);

            ShoppingItem item = new ShoppingItem(id, name, quantity, type);

            databaseItem.child(type.toLowerCase()).child(id).setValue(item);

            Toast.makeText(this, "The item has been successfully added", Toast.LENGTH_SHORT).show();

            this.finish();

        }

        else {
            Toast.makeText(this, "One or more fields missing", Toast.LENGTH_LONG).show();
        }
    }
}
