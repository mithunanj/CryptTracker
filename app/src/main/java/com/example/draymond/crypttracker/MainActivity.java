package com.example.draymond.crypttracker;

import android.app.SearchManager;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private RequestQueue requestQueue;
    private ArrayList<Coin> coins = new ArrayList<>();
    private Cursor result;
    private double value = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        final TextView portfolioValue = findViewById(R.id.portfolio_value);

        myDb = DatabaseHelper.getInstance(getApplicationContext());
        result  = myDb.getData();




        while(result.moveToNext()){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(new Rest(coins).getCoinRequest(result.getString(1)));


        }
        result.moveToFirst();


        if(result.getCount()!=0){

            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {

                @Override
                public void onRequestFinished(Request<StringRequest> request) {

                    for(Coin coin :coins){

                        value = value +(Double.parseDouble(coin.getPrice())*result.getDouble(3));
                        result.moveToNext();








                    }
                    portfolioValue.setText("$"+Util.parseLongString(Double.toString(value)));



                }


            });


        }
        else{

            portfolioValue.setText("No Coins");
        }
















        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.top_coins));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.your_coins));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);







        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });









    }


}
