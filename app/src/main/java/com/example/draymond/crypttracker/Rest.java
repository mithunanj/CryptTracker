package com.example.draymond.crypttracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Rest {

    private  JsonObjectRequest objectRequest;
    private static JsonObjectRequest coinRequest;
    private static RequestQueue requestQueue ;
    private static String price;
    private ArrayList<Coin> coins;

    private HashMap<String,String> allCoins;
    public static final String TOP_COINS = "https://api.coinmarketcap.com/v2/ticker/?start=";
    public static final String LIMIT = "&limit=100";
    public static final String COIN_URL = "https://api.coinmarketcap.com/v2/ticker/";

    public static final String COIN_HISTORY = "https://min-api.cryptocompare.com/data/histominute?fsym=";
    public static final String AGGREGATE = "&tsym=USD&limit=144&aggregate=10";
    public static final String ALL_COIN = "https://api.coinmarketcap.com/v2/listings/";

    private ArrayList<Entry> entry = new ArrayList<>();
    private ArrayList<String > times = new ArrayList<>();



    public Rest(ArrayList<Coin> coins){
        this.coins = coins;


    }

    public Rest(ArrayList<Entry> entry, ArrayList<String> times){

        this.entry = entry ;
        this.times  = times;
    }

    public  JsonObjectRequest topCoinsCall(int start) {


        String url = TOP_COINS + Integer.toString(start) + LIMIT;

        objectRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {



            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONObject coinInfo;
                    Iterator<String> iterator = jsonObject.keys();

                    while (iterator.hasNext()) {
                        coinInfo = jsonObject.getJSONObject(iterator.next().toString());

                        String id = coinInfo.getString("id");

                        String symbol = coinInfo.getString("symbol");
                        String image_url = id+".png";
                        String fullName = coinInfo.getString("name");
                        JSONObject quotes = coinInfo.getJSONObject("quotes").getJSONObject("USD");


                        coins.add(new Coin(fullName,symbol,quotes.getString("price"),quotes.getString("percent_change_24h"),quotes.getString("volume_24h"),quotes.getString("market_cap"),image_url,id));
                    }
                }













                catch (Exception e ){

                }



                }
        },new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        return objectRequest;






    }

    public  JsonObjectRequest getCoinRequest(String id) {
        String url = COIN_URL + id;

        objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("amount", "hello");
                try{
                    JSONObject coinInfo = response.getJSONObject("data");



                    String id = coinInfo.getString("id");

                    String symbol = coinInfo.getString("symbol");
                    String image_url = id+".png";
                    String fullName = coinInfo.getString("name");
                    JSONObject quotes = coinInfo.getJSONObject("quotes").getJSONObject("USD");





                    coins.add(new Coin(fullName,symbol,quotes.getString("price"),quotes.getString("percent_change_24h"),quotes.getString("volume_24h"),quotes.getString("market_cap"),image_url,id));


                }



                catch (Exception e ){

                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        return objectRequest;
    }


    public JsonObjectRequest dayHistoryCall(String symbol){

        String url =COIN_HISTORY+symbol+AGGREGATE;



        objectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try{

                    JSONArray jsonArray = response.getJSONArray("Data");
                    for(int i =0;i<jsonArray.length()-1;i++){

                        int time = jsonArray.getJSONObject(i).getInt("time");
                        float close = Float.parseFloat(jsonArray.getJSONObject(i).getString("close"));
                        times.add(Util.getTImeStamp(time));
                        entry.add(new Entry(i,close));

                    }

                }

                catch (Exception e ){

                    Log.d("HistoryCall",e.toString());

                }


            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        return objectRequest;








    }}
