package com.example.android.securityapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences authentication;
    SharedPreferences.Editor edit;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    String username,roll_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar);

        authentication = getApplicationContext().getSharedPreferences(Prefer.AUTH_FILE, MODE_PRIVATE);
        edit = authentication.edit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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

        FloatingActionButton f = (FloatingActionButton) findViewById(R.id.fab);
        f.setImageResource(R.drawable.fab);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.navhead);
        TextView nav_roll = (TextView)hView.findViewById(R.id.navroll);

        username=authentication.getString(Prefer.DISPLAY_NAME,null);
        roll_no=authentication.getString(Prefer.ROLL_NO,null);
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
        } else if (id == R.id.nav_changepass) {
             Intent settings = new Intent(MainActivity.this,SettingsActivity.class);
             startActivity(settings);
             return true;

        }
        else if(id==R.id.nav_logout){
             edit.remove(Prefer.ROLL_NO);
             edit.remove(Prefer.USER_ID);
             edit.remove(Prefer.DISPLAY_NAME);
             edit.remove(Prefer.USER_EMAIL);
             edit.remove(Prefer.USER_ROLE);
             edit.remove(Prefer.USER_LOGGED_IN);
             edit.apply();
            Intent out=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(out);
            return true;
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
