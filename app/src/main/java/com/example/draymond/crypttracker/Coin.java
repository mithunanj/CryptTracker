package com.example.draymond.crypttracker;


import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class Coin implements Parcelable{



    private String name;
    private String symbol;
    private String marketCap;
    private String volume;

    private String price;
    private String change;
    private String value;
    private String id;


    private String  imageUrl;



    public Coin(String name, String symbol, String price, String change, String volume, String marketCap, String imageUrl, String id ){


        this.name = name;
        this.symbol = symbol;
        this.id  = id;

        this.volume = volume;
        this.imageUrl = imageUrl;
        this.marketCap =marketCap;

        this.price= price;
        this.change= change;



    }

    protected Coin(Parcel in) {
        name = in.readString();
        symbol = in.readString();
        marketCap = in.readString();
        volume = in.readString();
        price = in.readString();
        change = in.readString();
        value = in.readString();
        id = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Coin> CREATOR = new Creator<Coin>() {
        @Override
        public Coin createFromParcel(Parcel in) {
            return new Coin(in);
        }

        @Override
        public Coin[] newArray(int size) {
            return new Coin[size];
        }
    };



    public String getImageUrl() {
        return imageUrl;
    }


    public String getName() {
        return name;
    }

    public String getPrice(){
        return price;

    }
    public String getChange(){
        return change;

    }
    public String getSymbol(){
        return symbol;

    }

    public String getId(){
        return id;
    }


    public String getMarketCap() {
        return marketCap;
    }

    public String getVolume() {
        return volume;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeString(marketCap);
        dest.writeString(volume);
        dest.writeString(price);
        dest.writeString(change);
        dest.writeString(value);
        dest.writeString(id);
        dest.writeString(imageUrl);
    }
}
