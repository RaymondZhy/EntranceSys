package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/19 0019.
 */

public class RegisterRecordAdapter extends FragmentPagerAdapter {

    private String[] tabNames;
    private List<Fragment> fragments;

    public RegisterRecordAdapter(FragmentManager fm, String[] tabNames, List<Fragment> fragments) {
        super(fm);
        this.tabNames = tabNames;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabNames.length > fragments.size() ? fragments.size() : tabNames.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
