package com.example.draymond.crypttracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopCoins.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopCoins#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopCoins extends Fragment implements SearchView.OnQueryTextListener {
    private JsonObjectRequest objectRequest ;
    private JsonObjectRequest coinRequest ;
    private CoinAdapter coinAdapter;
    private CoinAdapter filterAdapter;
    private int start = 1;
    private   RecyclerView recyclerView;

    private RequestQueue requestQueue ;
    private ArrayList<Coin> coins = new ArrayList<>();
    public static final String TOP_COINS = "https://api.coinmarketcap.com/v2/ticker/?limit=100";
    public static final String COIN_URL = "https://min-api.cryptocompare.com/data/pricemultifull?";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_top_coins, container, false);
        Log.d("heel","this");


        requestQueue = Volley.newRequestQueue(getActivity());


        // Inflate the layout for this fragment






        return rootView;


}




    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem item = menu.findItem((R.id.action_search));
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycle);
        final LinearLayoutManager lLM = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lLM);


        coinAdapter = new CoinAdapter(getActivity(),coins,TopCoins.this);
        recyclerView.setAdapter(coinAdapter);




            requestQueue.add(new Rest(coins).topCoinsCall());
            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {

                @Override
                public void onRequestFinished(Request<StringRequest> request) {
                    coinAdapter.notifyDataSetChanged();




                }
            });









        super.onCreateOptionsMenu(menu, inflater);


    }


    @Override
    public boolean onQueryTextSubmit(String query) {





        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();

        ArrayList<Coin> filterList = new ArrayList<>();
        filterAdapter= new CoinAdapter(getActivity(),filterList,TopCoins.this);
        recyclerView.setAdapter(filterAdapter);


        for( Coin coin : coins ){

            String name = coin.getName().toLowerCase() +"" + coin.getSymbol().toLowerCase();



            if(name.contains(newText)){

                filterList.add(coin);


            }




        }
        filterAdapter.notifyDataSetChanged();


        Log.d("hello",newText);
        return false;
    }




}
