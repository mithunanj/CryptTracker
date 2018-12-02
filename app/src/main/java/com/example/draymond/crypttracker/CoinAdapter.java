package com.example.draymond.crypttracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private final ArrayList<Coin> coins;
    private Fragment fragment;
    public static final String coin = "COIN";

    private final Context context;
    public static final String IMAGE_URL="https://s2.coinmarketcap.com/static/img/coins/64x64/";


    public CoinAdapter(Context context, ArrayList<Coin> coins,Fragment fragment ){
        this.fragment = fragment;
        this.context= context;
        this.coins = coins;
    }
    @Override
    public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View mItemView = inflater.inflate(R.layout.list_item, parent, false);
        return new CoinViewHolder(mItemView,this);
    }


    @Override
    public void onBindViewHolder(@NonNull final CoinAdapter.CoinViewHolder holder, int position) {
        final Coin mCurrent = coins.get(position);

        holder.bindTo(mCurrent);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), CoinDetails.class);
                intent.putExtra(coin,mCurrent);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        Cursor result = DatabaseHelper.getInstance(context).getData();

        if(fragment instanceof YourCoins){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){


                @Override
                public boolean onLongClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ID",mCurrent.getId());
                    DeleteDialog deleteDialog = new DeleteDialog();
                    deleteDialog.setArguments(bundle);


                    deleteDialog.show(((FragmentActivity)context).getSupportFragmentManager(),"delete dialog");





                    return true;





                }
            });




        }







    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class CoinViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView price;
        private final TextView volume ;
        private final TextView change;
        private final ImageView icon;
        private final TextView market;


        private final TextView name;






        final CoinAdapter mAdapter;
        public CoinViewHolder(View itemView,CoinAdapter coinListAdapter) {




            super(itemView);
            price = itemView.findViewById(R.id.price_label);
            volume =  itemView.findViewById(R.id.volume_label);
            change = itemView.findViewById(R.id.percent);
            market = itemView.findViewById(R.id.market_cap);


            name = itemView.findViewById(R.id.name);
            icon= itemView.findViewById(R.id.icon);


            this.mAdapter = coinListAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {




        }

        public void bindTo(Coin mCurrent) {

            if(mCurrent.getChange().contains("-")){
                change.setText( mCurrent.getChange()+'%');

                change.setTextColor(Color.RED);

            }
            else {
                change.setText( "+" + mCurrent.getChange()+'%');
                change.setTextColor(Color.GREEN);
            }

            price.setText("Price: $ "+ Util.parseLongString(mCurrent.getPrice()));



            volume.setText("24hVolume: $ " +Util.parseLongString(mCurrent.getVolume()));

            market.setText("Market Cap: $ " + Util.parseLongString(mCurrent.getMarketCap()));
            name.setText(mCurrent.getSymbol());
            name.setTypeface(null, Typeface.BOLD);



            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(context).load(IMAGE_URL+mCurrent.getImageUrl()).apply(options).into(icon);




        }
    }

}
