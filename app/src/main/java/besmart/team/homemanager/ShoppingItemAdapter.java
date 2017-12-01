package besmart.team.homemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import besmart.team.homemanager.logic.ShoppingItem;
import besmart.team.homemanager.logic.Task;

/**
 * Created by kevingatera on 30/11/17.
 */

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {

    Context myContext;
    int resource;
    List<ShoppingItem> itemList;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("/Shopping");


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
        Log.e("sasa", "pppppppppppppppppppppppppppppppppppppppppppppppppppp");
        View view = inflater.inflate(R.layout.list_item, null); // List it the way task is listed
        TextView itemName = view.findViewById(R.id.shoppingItemTextView);
//        TextView taskDueDate = view.findViewById(R.id.taskDueDate);
//        ImageView imageView = view.findViewById(R.id.imageView);
        System.out.println("SKROOOOOOOOOOOOOOOOOOOOOOOOOOO");
        final LinearLayout taskRowView = view.findViewById(R.id.itemRowView);
//        System.out.println("Position is => " + Integer.toString(position));
//        System.out.println("The list has => " + Integer.toString(taskList.size()) + " items");
        ShoppingItem item = itemList.get(position);
        itemName.setText(item.getName());
//        taskDueDate.setText("Due date: " + item.getDueDate());
//        imageView.setImageDrawable(myContext.getResources().getDrawable(R.drawable.ic_launcher_background));

//        view.findViewById(R.id.buttonTaskDone).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /* Called when the user clicks on a list item */
//                ShoppingItem toBeRemoved = itemList.get(position);
//                System.out.println(toBeRemoved.getId());
//                myRef.child(toBeRemoved.getId()).removeValue();
//                itemList.remove(toBeRemoved);
//                notifyDataSetChanged();
//            }
//        });


        view.findViewById(R.id.itemRowView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("SKKKKKRIIIIIIi ===> " + Integer.toString(position));
            }
        });

        return view;
    }
}


