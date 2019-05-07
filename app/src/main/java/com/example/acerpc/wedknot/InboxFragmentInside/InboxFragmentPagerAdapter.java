package com.example.acerpc.wedknot.InboxFragmentInside;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class InboxFragmentPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabItems = {"Sent", "Received", "Accepted"};

    public InboxFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new InterestSentFragment();
        } else if (i == 1) {
            return new InterestReceivedFragment();
        } else {
            return new InboxConnectedFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabItems[position];
    }
}
