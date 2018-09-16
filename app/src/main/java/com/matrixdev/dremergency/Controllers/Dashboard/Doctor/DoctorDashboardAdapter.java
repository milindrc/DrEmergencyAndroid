package com.matrixdev.dremergency.Controllers.Dashboard.Doctor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class DoctorDashboardAdapter extends FragmentStatePagerAdapter {
    String Titles[] = {"Availability", "Requests", "Accepted"};

    public DoctorDashboardAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 : return new SubscriptionsFragment();
            case 1 : return new RequestsFragment();
            case 2 : return new AcceptFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return Titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

}
