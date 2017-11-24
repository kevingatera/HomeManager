package besmart.team.homemanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

/**
 * Created by kevingatera on 22/11/17.
 */

public class fragment_main_tasks extends Fragment {

    /* adding the firebase */

    ArrayList<String> myArrayList = new ArrayList<>();
    ArrayList<TestTask> myTaskList;
    ListView myListView;
    // SwipeMenuListView myListView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

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


        myTaskList.add(new TestTask(R.drawable.ic_delete, "Take the dog out", "Today"));
        myTaskList.add(new TestTask(R.drawable.ic_launcher_background, "Call the veterinary", "Today"));
        myTaskList.add(new TestTask(R.drawable.ic_launcher_background, "Take the garbage out", "Sunday"));
        myTaskList.add(new TestTask(R.drawable.ic_delete, "Take the dog out", "Tomorrow"));
        myTaskList.add(new TestTask(R.drawable.ic_launcher_background, "Prepare dinner", "2 hours"));

        final CustomListAdapter myArrayAdapter = new CustomListAdapter(getActivity(), R.layout.list_item, myTaskList);

        // final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , myArrayList);

        // myListView = (SwipeMenuListView) view.findViewById(R.id.list); */
        myListView = (ListView) view.findViewById(R.id.list);
        myListView.setAdapter(myArrayAdapter);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String myChildValues = dataSnapshot.getValue(String.class);
                myArrayList.add(myChildValues);
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

        // Write a message to the database
        // DatabaseReference myRef = database.getReference("test");
        // myRef.setValue("Hello, World!");
    }
}

    /*

    public class TaskArrayAdapter extends ArrayAdapter<String>  {
        private final Context context;
        private final String[] values;

        public TaskArrayAdapter(Context context, String[] values) {
            super(context, R.layout.list_team_layout, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_team_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.line01);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText(values[position]);
            // Change the icon for Windows and iPhone
            String s = values[position];
            if (s == null || s.isEmpty() || s.equals("empty")) {
                imageView.setImageResource(R.drawable.ic_logo_empty);
            } else {
                imageView.setImageResource(R.drawable.ic_logo_mil);
            }
            return rowView;
        }
    }
    RÉSULTAT
            resultOfPreviousSlide
    GESTION DE MÉMOIRE
    Un patron de conceptions utiles dans la gestion d'application et de mémoire est le Singleton.
    SINGLETON
    Patron de conception utile pour stocker les informations.
    Une limite d'un seul instance assure la cohérence entre vos attentes et la réalité entre lecture / écriture.
    singleton

    */



