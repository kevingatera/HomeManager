package besmart.team.homemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import besmart.team.homemanager.logic.ShoppingItem;

/**
 * Created by kevingatera on 22/11/17.
 */

public class fragment_main_shopping extends Fragment {

    /* adding the firebase */
    private ArrayList<ShoppingItem> myGroceryArrayList;
    private ListView myGroceryList;
    private ShoppingItemAdapter m1;

    private ArrayList<ShoppingItem> myMaterialArrayList;
    private ListView myMaterialList;
    private ShoppingItemAdapter m2;

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

        m1 = new ShoppingItemAdapter(getActivity(), R.layout.list_item, myGroceryArrayList);
        m2 = new ShoppingItemAdapter(getActivity(), R.layout.list_item, myMaterialArrayList);

        myGroceryList = view.findViewById(R.id.groceryList);
        myMaterialList = view.findViewById(R.id.materialList);

        myGroceryList.setAdapter(m1);
        myMaterialList.setAdapter(m2);

        populate(myRef);

}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getActivity(), ShoppingActivity.class), 1);
                }
            });

            TextView fabTextView = getActivity().findViewById(R.id.fabTextView);
            fabTextView.setText("Add an article");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1 && requestCode == 1) {
            reloadShoppingView();
        }
    }

    public void reloadShoppingView(){
        myGroceryArrayList.clear();
        myMaterialArrayList.clear();
        populate(myRef);
    }

    private void populate(DatabaseReference myRef) {
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
                }
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
}


// Make sure that we are currently visible