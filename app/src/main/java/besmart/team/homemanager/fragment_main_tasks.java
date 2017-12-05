package besmart.team.homemanager;


import android.content.Intent;
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

import besmart.team.homemanager.logic.Task;

/**
 * Created by kevingatera on 22/11/17.
 */

public class fragment_main_tasks extends Fragment {

    /* adding the firebase */

    ArrayList<Task> myAssignedTaskList;
    ListView myAssignedTaskListView;
    TaskListAdapter myListAdapter;

    ArrayList<Task> myUnassignedTaskList;
    ListView myUnassignedTaskListView;
    TaskListAdapter myUnassignedListAdapter;

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

        myAssignedTaskList = new ArrayList<>();
        myUnassignedTaskList = new ArrayList<>();


        myListAdapter = new TaskListAdapter(getActivity(), R.layout.list_task, myAssignedTaskList);
        myUnassignedListAdapter = new TaskListAdapter(getActivity(), R.layout.list_task, myUnassignedTaskList);


        myAssignedTaskListView = view.findViewById(R.id.assignedTasksList);
        myAssignedTaskListView.setAdapter(myListAdapter);

        myUnassignedTaskListView = view.findViewById(R.id.unassignedTasksList);
        myUnassignedTaskListView.setAdapter(myUnassignedListAdapter);

        populate(myRef);
    }

    private void populate(DatabaseReference myRef) {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setId(dataSnapshot.getKey());
                if(task.getStatus().equals("complete") || task.getAssigneeName().equals("NONE")){
                    if(myAssignedTaskList.indexOf(task) != -1) {
                        myAssignedTaskList.remove(myAssignedTaskList.indexOf(task));
                    }
                    myUnassignedTaskList.add(task);
                }

                else {
                    if(myUnassignedTaskList.indexOf(task) != -1) {
                        myUnassignedTaskList.remove(myAssignedTaskList.indexOf(task));
                    }
                    myAssignedTaskList.add(task);
                }
                myListAdapter.notifyDataSetChanged();
                myUnassignedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                myListAdapter.notifyDataSetChanged();
                myUnassignedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                myListAdapter.notifyDataSetChanged();
                myUnassignedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                myListAdapter.notifyDataSetChanged();
                myUnassignedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getActivity(), TaskActivity.class), 22);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 22 && requestCode == 22){
            reloadView();
        }
    }

    private void reloadView() {
        myAssignedTaskList.clear();
        myUnassignedTaskList.clear();
        populate(myRef);
    }
}
