package com.example.draymond.crypttracker;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YourCoins.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YourCoins#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourCoins extends Fragment {

    DatabaseHelper myDB;
    private ArrayList<Coin> coins = new ArrayList<>();
    private RequestQueue requestQueue ;
    private FragmentActivity activity;
    private CoinAdapter coinAdapter;// TODO: Rename parameter arguments, choose names that matc

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_your_coins, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_your_coins);
        final LinearLayoutManager lLM = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lLM);


        coinAdapter = new CoinAdapter(getActivity(),coins,YourCoins.this);
        Log.d("activity", getActivity().toString());
        recyclerView.setAdapter(coinAdapter);
        myDB = DatabaseHelper.getInstance(getContext());
        Cursor result = myDB.getData();
















            while(result.moveToNext()){
                requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(new Rest(coins).getCoinRequest(result.getString(1)));
                requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {

                    @Override
                    public void onRequestFinished(Request<StringRequest> request) {
                        Log.d("hello","here");
                        coinAdapter.notifyDataSetChanged();



                    }
                });

            }



        return rootView;

    }



}
