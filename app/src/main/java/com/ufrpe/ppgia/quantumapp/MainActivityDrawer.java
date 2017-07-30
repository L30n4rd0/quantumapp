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

import com.ufrpe.ppgia.quantumapp.circuit.Complex;
import com.ufrpe.ppgia.quantumapp.fragments.AboutFragment;
import com.ufrpe.ppgia.quantumapp.fragments.ControledNotFragment;
import com.ufrpe.ppgia.quantumapp.fragments.ControledPhaseFragment;
import com.ufrpe.ppgia.quantumapp.fragments.EditorFragment;
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

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        this.mFragmentManager = getSupportFragmentManager();
        this.mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragment = new EditorFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mFragment)
                .commit();
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

        if (id == R.id.sub_menu_editor) {
            mFragment = new EditorFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_history) {
            mFragment = new HistoryFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_fundamentals) {
            mFragment = new FundamentalsFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_hadamard) {
            mFragment = new HadamardFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_pauli_matrix) {
            mFragment = new PauliFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_fase) {
            mFragment = new FaseFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_pi_8) {
            mFragment = new Pi8Fragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_controled_not) {
            mFragment = new ControledNotFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_controled_z) {
            mFragment = new ControledZFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_controled_phase) {
            mFragment = new ControledPhaseFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_swap) {
            mFragment = new SwapFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        } else if (id == R.id.sub_menu_about_app) {
            mFragment = new AboutFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mFragment)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
