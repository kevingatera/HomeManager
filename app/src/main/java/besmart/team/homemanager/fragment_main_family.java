package besmart.team.homemanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import besmart.team.homemanager.logic.User;

/**
 * Created by kevingatera on 22/11/17.
 */

public class fragment_main_family extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView3 = inflater.inflate(R.layout.fragment_main_family, container, false);
        return rootView3;
    }

    /* adding the firebase */

    ArrayList<User> userList;
    ListView myListView;
    // SwipeMenuListView myListView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("/users");

    // retrieve data and populate upon startup of the app
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userList = new ArrayList<>();

        final UserListAdapter myListAdapter = new UserListAdapter(getActivity(), R.layout.list_people, userList);

        myListView = (ListView) view.findViewById(R.id.familyList);
        myListView.setAdapter(myListAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for(DataSnapshot familySnapshot : dataSnapshot.getChildren()){
                    User user = familySnapshot.getValue(User.class);
                    userList.add(user);
                }

                myListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                myListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                myListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                myListAdapter.notifyDataSetChanged();
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
                getActivity().findViewById(R.id.fabAddLayout).setVisibility(View.GONE);
        }

        if (!isVisibleToUser && isAdded())  {
            /* isAdded is to check whether the fragment is present before calling getActivity()
            See: https://stackoverflow.com/questions/11631408/android-fragment-getactivity-sometimes-returns-null  */
                getActivity().findViewById(R.id.fabAddLayout).setVisibility(View.VISIBLE);
        }
    }

}

