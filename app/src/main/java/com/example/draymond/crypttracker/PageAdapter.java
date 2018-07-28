package com.example.draymond.crypttracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs){

        super(fm);
        this.numOfTabs= numOfTabs;
    }
    @Override
    public Fragment getItem(int position) {


        switch ((position)){
            case 0:
                return new TopCoins();
            case 1:
                return new YourCoins();

            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
