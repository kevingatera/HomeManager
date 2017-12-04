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

import besmart.team.homemanager.logic.Task;

/**
 * Created by kevingatera on 22/11/17.
 */

public class fragment_main_tasks extends Fragment {

    /* adding the firebase */

    ArrayList<String> myArrayList = new ArrayList<>();
    ArrayList<Task> myTaskList;
    ListView myListView;
    // SwipeMenuListView myListView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("/task");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView2 = inflater.inflate(R.layout.fragment_main_tasks, container, false);

        return rootView2;
    }


    // retrieve data and populate upon startup of the app
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myTaskList = new ArrayList<>();


        final TaskListAdapter myListAdapter = new TaskListAdapter(getActivity(), R.layout.list_task, myTaskList);

        myListView = view.findViewById(R.id.list);
        myListView.setAdapter(myListAdapter);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setid(dataSnapshot.getKey());
                myTaskList.add(task);
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

        // Write a message to the database
        // DatabaseReference myRef = database.getReference("test");
        // myRef.setValue("Hello, World!");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            ((TextView) getActivity().findViewById(R.id.fabTextView)).setText("Add a task");
            getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), TaskActivity.class));
                }
            });
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                //
            }
        }
    }
}
