package com.example.android.securityapp;

//import com.example.android.securityapp.SaveSharedPreference;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import org.w3c.dom.Text;

import static android.R.attr.id;
import static android.R.id.edit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SharedPreferences authentication;
    SharedPreferences.Editor edit;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String username,roll_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        authentication = getApplicationContext().getSharedPreferences(Prefer.AUTH_FILE, MODE_PRIVATE);
        edit = authentication.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(MainActivity.this, NewComplaint.class);
                startActivity(numbersIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

      //  loadDrawableAsync(this, Icon.OnDrawableLoadedListener, Handler);
        FloatingActionButton f = (FloatingActionButton) findViewById(R.id.fab);
        f.setImageResource(R.drawable.fab);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.navhead);
        TextView nav_roll = (TextView)hView.findViewById(R.id.navroll);
        username = getIntent().getStringExtra("username");
        roll_no = getIntent().getStringExtra("roll_no");
        nav_user.setText(username);
        nav_roll.setText(roll_no);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return ComplaintsFragment.newInstance(position);
                case 1:
                    return HallComplaintsFragment.newInstance(position);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Security";
                case 1:
                    return "Hall";
            }
            return null;
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.isChecked()) item.setChecked(false);
        else item.setChecked(true);

        int id = item.getItemId();

         if (id == R.id.nav_my) {
             Intent mycomplaints = new Intent(MainActivity.this, MyComplaints.class);
             startActivity(mycomplaints);
             return true;
        } else if (id == R.id.nav_settings) {
             Intent settings = new Intent(MainActivity.this,SettingsActivity.class);
             startActivity(settings);
             return true;

        } else if (id == R.id.nav_share) {

        }
        else if(id==R.id.nav_logout){
            edit.putString(Prefer.ROLL_NO, null);
            Intent out=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(out);
            return true;
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
//    {
//        // call Login Activity
//    }
//else
//    {
//        // Stay at the current activity.
//    }

}
