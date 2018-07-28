package com.example.draymond.crypttracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {


    public static String getDate(){

        DateFormat dateFormat = new SimpleDateFormat("EEEE MMMM d yyyy");
        Date date = new Date();
        return  (dateFormat.format(date));


    }

    public static String getTImeStamp(int milli) {

        Long seconds = Long.valueOf(milli * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.US);
        String dateString = formatter.format(new Date(seconds));


        return dateString;
    }

    public static String parseLongString(String value){
        Double d = Double.parseDouble(value);

        return String.format("%,.2f",d);





    }








}
