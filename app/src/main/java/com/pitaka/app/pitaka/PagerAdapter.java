package com.pitaka.app.pitaka;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter  extends FragmentStatePagerAdapter{

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {

        super(fm);
        this.mNoOfTabs=NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Sinhala tab1=new Sinhala();
                return tab1;

            case 1:
                Paali tab2=new Paali();
                return tab2;

             default:
                 return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
