package besmart.team.homemanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import besmart.team.homemanager.logic.Tool;

public class MainActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(user == null) {
            finish();

            System.out.println("LOGGED OUT !!!!!");
        }

        else{
            Log.w("USER IS", user.toString());
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.addToolMenuButton) {

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = LayoutInflater.from(this).inflate(R.layout.activity_tools, null);

            final EditText modalTitle = mView.findViewById(R.id.toolTitle);
            final EditText modalQuantity = mView.findViewById(R.id.toolQuantity);

            Button addButton = mView.findViewById(R.id.addToolButton);


            mBuilder.setView(mView);
            final AlertDialog addToolDetailsDialog = mBuilder.create();

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = modalTitle.getText().toString();
                    String quantity = modalQuantity.getText().toString();

                    Random r = new Random();

                    String id = "tool" + Integer.toString(r.nextInt(999999-100000) + 100000);

                    Tool toBeAdded = new Tool(id, name, quantity);

                    (FirebaseDatabase.getInstance().getReference("/tools")).child(toBeAdded.getId()).setValue(toBeAdded);

                    addToolDetailsDialog.dismiss();
                }
            });

            addToolDetailsDialog.show();

            return true;
        }


        if(id == R.id.logOutButton) {
            return true;
        }

        // return super.onOptionsItemSelected(item);
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
//        MenuItem checkable = menu.findItem(R.id.checkable_menu);
//        checkable.setChecked(onlyMyTasks);
        return true;
    }

    public void logOut(MenuItem view) {
        FirebaseAuth.getInstance().signOut();
        System.out.println("Signing out");
        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Create a switch case to return the fragments
            TextView fabTextView;
            switch (position) {
                case 0:
                    fragment_main_shopping tab1 = new fragment_main_shopping();
                    findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(getApplication(), ShoppingActivity.class), 1);
                        }
                    });
                    return tab1;
                case 1:
                    return new fragment_main_tasks();
                case 2:
                    return new fragment_main_family();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Shopping";
                case 1:
                    return "Tasks";
                case 2:
                    return "Family";
            }
            return null;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void finish() {
        FirebaseAuth.getInstance().signOut();
        super.finish();
    }
}
