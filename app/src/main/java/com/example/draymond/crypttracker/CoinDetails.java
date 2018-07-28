package com.example.draymond.crypttracker;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.TimeZone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.draymond.crypttracker.CoinAdapter.IMAGE_URL;

public class CoinDetails extends AppCompatActivity {
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    public static final String COIN_HISTORY = "https://min-api.cryptocompare.com/data/histominute?fsym=";
    public static final String LIMIT = "&tsym=USD&limit=144&aggregate=10";
    private String url ;
    private String symbol;
    private TextView volume;
    private TextView cap;
    private TextView change;

    private ArrayList<Entry> entry = new ArrayList<>();
    private LineChart lineChart;
    private LineData lineData;
    private TextView price;
    private EditText amount;
    private TextView date;
    private TextView name;
    private ImageView icon;
    private Coin coin;
    private String id;
    DatabaseHelper myDb;


    private ArrayList<String > times = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_details);
        getSupportActionBar().setElevation(0);
        myDb =  DatabaseHelper.getInstance(getApplicationContext());

        lineChart = (LineChart) findViewById(R.id.chart);
        Intent intent = getIntent();

        amount = findViewById(R.id.amount);




        price = findViewById(R.id.detail_price);
        volume = findViewById(R.id.detail_volume);
        cap = findViewById(R.id.detail_market_cap);
        change = findViewById(R.id.detail_percent);
        amount = findViewById(R.id.amount);
        date = findViewById(R.id.date);
        name = findViewById(R.id.detail_name);
        icon = findViewById(R.id.detail_icon);




        date.setText(Util.getDate());
        coin =intent.getParcelableExtra(CoinAdapter.coin);
        symbol = coin.getSymbol();
        price.setText("$ " + Util.parseLongString(coin.getPrice()));

        id = coin.getId();



        volume.setText("$ "+ Util.parseLongString(coin.getVolume()));

        cap.setText("$ "+ Util.parseLongString(coin.getMarketCap()));

        String percentChange = coin.getChange();
        if(percentChange.contains("-")){

            change.setText( percentChange+'%');
            change.setTextColor(Color.RED);

        }

        else {
            change.setText( "+" + percentChange+'%');
            change.setTextColor(Color.GREEN);
        }



        String coin_name = coin.getName();
        setTitle(coin_name);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(getApplicationContext()).load(IMAGE_URL+coin.getImageUrl()).apply(options).into(icon);


        name.setText(coin_name);




        requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(new Rest(entry,times).dayHistoryCall(symbol));
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {


            @Override
            public void onRequestFinished(Request<StringRequest> request) {
                Log.d("hll",Integer.toString(entry.size()));
                if(entry.size()!=0){
                LineDataSet lineDataSet = new LineDataSet(entry, "24 hour change");
                lineDataSet.setColor(Color.BLUE);
                lineDataSet.setLineWidth(0.5f);
                lineDataSet.setValueTextSize(5f);
                lineDataSet.setFillColor(Color.BLUE);
                lineDataSet.setDrawHighlightIndicators(true);
                lineDataSet.setDrawFilled(true);
                lineDataSet.setDrawCircles(false);
                lineDataSet.setDrawCircleHole(false);
                lineDataSet.setDrawValues(false);
                lineDataSet.setCircleRadius(1);
                lineDataSet.setHighlightLineWidth(2);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setDrawHighlightIndicators(true);
                lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setValueFormatter(new MyXAxisFormatter(times));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                lineChart.invalidate();

                }}



        });
        }


    public void addCoin(View view) {
        Double coinAmount = Double.parseDouble(amount.getText().toString());
        Double price = Double.parseDouble(coin.getPrice());
        boolean isInserted = myDb.insert(id,symbol,coinAmount,price);

        if(isInserted){
            Toast.makeText(CoinDetails.this, coin.getName() +" Added",Toast.LENGTH_LONG).show();
        }

        Intent i = new Intent(this,MainActivity.class);
        getApplicationContext().startActivity(i);
    }



    public class MyXAxisFormatter implements IAxisValueFormatter{

        private ArrayList<String > values;


        public MyXAxisFormatter(ArrayList<String > values) {
            this.values = values;

        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return values.get((int)Math.round(value));
        }
    }


}
