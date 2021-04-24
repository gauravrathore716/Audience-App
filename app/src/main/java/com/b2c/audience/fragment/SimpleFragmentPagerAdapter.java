package com.b2c.audience.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SimpleFragmentPagerAdapter(FragmentManager fm,int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs=mNumOfTabs;


    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                UpcommingEventFragment tab1 = new UpcommingEventFragment();
                return tab1;
            case 1:
                PastEventFragment tab2 = new PastEventFragment();
                return tab2;

            default:
                return  null;

        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
