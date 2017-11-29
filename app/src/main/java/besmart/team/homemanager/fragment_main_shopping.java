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

import java.util.ArrayList;

/**
 * Created by kevingatera on 22/11/17.
 */

public class fragment_main_shopping extends Fragment {

    /* adding the firebase */
    ArrayList<String> myArrayList = new ArrayList<>();
    ListView myListView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView2 = inflater.inflate(R.layout.fragment_main_shopping, container, false);

        return rootView2;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myArrayList);

        myListView = view.findViewById(R.id.list);
        myListView.setAdapter(myArrayAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String myChildValues = dataSnapshot.getValue(String.class);
//                myArrayList.add(myChildValues);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                myArrayAdapter.notifyDataSetChanged();
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
            Log.e("MyFragment", "\n\n" + this.getClass().getSimpleName() +" IS visible anymore. \n  STARTING audio. \n \n");
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