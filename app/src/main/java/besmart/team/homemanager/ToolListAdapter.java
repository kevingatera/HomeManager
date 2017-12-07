package besmart.team.homemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import besmart.team.homemanager.logic.Tool;

/**
 * Created by kevingatera on 30/11/17.
 */

public class ToolListAdapter extends ArrayAdapter<Tool> {

    Context myContext;
    int resource;
    List<Tool> toolList;

//    TaskActivity myActivity;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("/tools");


    public ToolListAdapter(Context myContext, int resource, List<Tool> toolList){
        super(myContext, resource, toolList);
        this.myContext = myContext;
        this.resource = resource;
        this.toolList = toolList;
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // This method will return
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.list_tool, null); // List it the way task is listed
        TextView toolName = view.findViewById(R.id.toolTitle);
        TextView toolQuantity = view.findViewById(R.id.toolQuantity);

        view.findViewById(R.id.shoppingItemDeleteButton);
        Tool tool = toolList.get(position);

        toolName.setText(tool.getName());
        toolQuantity.setText(toolQuantity.getText() + tool.getQuantity());

        ((CheckBox) view.findViewById(R.id.toolChoiceCheckBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            Tool tool = toolList.get(position);
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ((TaskActivity) getContext()).addSelectedToolsList(tool);
                } else {
                    ((TaskActivity) getContext()).removeSelectedToolFromList(tool);
                }
            }
        });

        notifyDataSetChanged();


        return view;
    }
}


