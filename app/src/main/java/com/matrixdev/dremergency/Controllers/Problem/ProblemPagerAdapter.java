package com.matrixdev.dremergency.Controllers.Problem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.matrixdev.dremergency.Controllers.Dashboard.Doctor.AcceptFragment;
import com.matrixdev.dremergency.Controllers.Dashboard.Doctor.RequestsFragment;
import com.matrixdev.dremergency.Controllers.Dashboard.Doctor.SubscriptionsFragment;
import com.matrixdev.dremergency.Models.Problem.ProblemData;

public class ProblemPagerAdapter extends FragmentStatePagerAdapter {
    String Titles[] = {"Tips", "Doctors"};
    public ProblemData problemData;

    public ProblemPagerAdapter(FragmentManager fm,ProblemData problemData) {
        super(fm);
        this.problemData = problemData;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 : return new TipsFragment();
            case 1 : return new DoctorFragment();
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
