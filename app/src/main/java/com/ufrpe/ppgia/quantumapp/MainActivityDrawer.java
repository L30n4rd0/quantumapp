package com.ufrpe.ppgia.quantumapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ufrpe.ppgia.quantumapp.fragments.ControledNotFragment;
import com.ufrpe.ppgia.quantumapp.fragments.ControledPhaseFragment;
import com.ufrpe.ppgia.quantumapp.fragments.PauliFragment;
import com.ufrpe.ppgia.quantumapp.fragments.SwapFragment;
import com.ufrpe.ppgia.quantumapp.fragments.ControledZFragment;
import com.ufrpe.ppgia.quantumapp.fragments.FaseFragment;
import com.ufrpe.ppgia.quantumapp.fragments.FundamentalsFragment;
import com.ufrpe.ppgia.quantumapp.fragments.HadamardFragment;
import com.ufrpe.ppgia.quantumapp.fragments.HistoryFragment;
import com.ufrpe.ppgia.quantumapp.fragments.Pi8Fragment;

public class MainActivityDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        this.fragmentManager = getSupportFragmentManager();
        this.fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        fragment = new HistoryFragment();
//        fragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity( new Intent(getApplicationContext(), SettingsActivity.class) );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sub_menu_history) {
            fragment = new HistoryFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_fundamentals) {
            fragment = new FundamentalsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_hadamard) {
            fragment = new HadamardFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_pauli_matrix) {
            fragment = new PauliFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_fase) {
            fragment = new FaseFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_pi_8) {
            fragment = new Pi8Fragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_controled_not) {
            fragment = new ControledNotFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_controled_z) {
            fragment = new ControledZFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_controled_phase) {
            fragment = new ControledPhaseFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        } else if (id == R.id.sub_menu_swap) {
            fragment = new SwapFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
