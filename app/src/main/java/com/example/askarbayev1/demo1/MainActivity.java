package com.example.askarbayev1.demo1;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setCurrentItem(3);
        getItems();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        //bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        //bottomNavigationView.setSelectedItemId(R.id.action_item2);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = BlankFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = BlankFragment2.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = BlankFragment3.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        //bottomNavigationView.setSelectedItemId(R.id.action_item2);
        bottomNavigationView.setSelectedItemId(R.id.action_item2);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, BlankFragment2.newInstance());
        transaction.commit();

    }

    public void getItems(){
        db = new DBHelper(this);
        ArrayList<Item> items = db.getAllItems();
        for (Item item:items){
            Log.d("Item", ""+item.getId()+" - "+item.getName()+" - "+item.getType()+" - "+item.getPrice()+" - "+item.getQuantity());
        }
    }

}