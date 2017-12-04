package besmart.team.homemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import besmart.team.homemanager.logic.ShoppingItem;

/**
 * Created by kevingatera on 30/11/17.
 */

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {

    Context myContext;
    int resource;
    List<ShoppingItem> itemList;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("/shopping_items");


    public ShoppingItemAdapter(Context myContext, int resource, List<ShoppingItem> itemList){
        super(myContext, resource, itemList);
        this.myContext = myContext;
        this.resource = resource;
        this.itemList = itemList;
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // This method will return
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.list_item, null); // List it the way task is listed
        TextView itemName = view.findViewById(R.id.shoppingItemTitle);
        TextView itemQuantity = view.findViewById(R.id.shoppingItemQty);
        final LinearLayout taskRowView = view.findViewById(R.id.itemRowView);
        ShoppingItem item = itemList.get(position);

        itemName.setText(item.getName());
        itemQuantity.setText(itemQuantity.getText() + item.getQuantity());


//        taskDueDate.setText("Due date: " + item.getDueDate());
//        imageView.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_launcher_background));

        view.findViewById(R.id.shoppingItemCheckbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Called when the user clicks on a list item */
                ShoppingItem toBeRemoved = itemList.get(position);

                if(toBeRemoved.getType().equals("Groceries")) {
                    myRef.child("groceries").child(toBeRemoved.getId()).removeValue();
                } else if (toBeRemoved.getType().equals("Materials")) {
                    myRef.child("materials").child(toBeRemoved.getId()).removeValue();
                }

                itemList.remove(toBeRemoved);
                notifyDataSetChanged();
            }
        });


        return view;
    }
}


