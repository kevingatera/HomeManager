package besmart.team.homemanager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

import besmart.team.homemanager.logic.ShoppingItem;

/**
 * Created by kevingatera on 22/11/17.
 */

public class fragment_main_shopping extends Fragment {

    /* adding the firebase */
    ArrayList<ShoppingItem> myGroceryArrayList;
    ListView myGroceryList;

    ArrayList<ShoppingItem> myMaterialArrayList;
    ListView myMaterialList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("/shopping_items");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView2 = inflater.inflate(R.layout.fragment_main_shopping, container, false);
        return rootView2;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myGroceryArrayList = new ArrayList<>();
        myMaterialArrayList = new ArrayList<>();

        final ShoppingItemAdapter m1 = new ShoppingItemAdapter(getActivity(), R.layout.list_item, myGroceryArrayList);
        final ShoppingItemAdapter m2 = new ShoppingItemAdapter(getActivity(), R.layout.list_item, myMaterialArrayList);

        myGroceryList = view.findViewById(R.id.groceryList);
        myMaterialList = view.findViewById(R.id.materialList);

        myGroceryList.setAdapter(m1);
        myMaterialList.setAdapter(m2);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {

                    ShoppingItem item = child.getValue(ShoppingItem.class);
                    if(item.getType().equals("Groceries")){
                        myGroceryArrayList.add(item);
                    }

                    else if (item.getType().equals("Materials")) {
                        myMaterialArrayList.add(item);
                    }
//                    System.out.println(dataSnapshot.getValue());
                }
//                String myChildValues = dataSnapshot.getValue(String.class);
//                myArrayList.add(myChildValues);
                m1.notifyDataSetChanged();
                m2.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                m1.notifyDataSetChanged();
                m2.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                m1.notifyDataSetChanged();
                m2.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            Activity tempActivity = getActivity();
            tempActivity.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ShoppingActivity.class));
                }
            });

            TextView fabTextView = tempActivity.findViewById(R.id.fabTextView);
            fabTextView.setText("Add an article");

            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
//                Log.e("MyFragment", "\n\n\n\n Not visible anymore.  Stopping audio. \n \n");

            }
        }
    }
}


// Make sure that we are currently visible