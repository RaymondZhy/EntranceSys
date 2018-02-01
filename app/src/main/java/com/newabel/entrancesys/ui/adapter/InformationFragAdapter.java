package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.newabel.entrancesys.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2017/12/7 09:08
 * Description:
 */

public class InformationFragAdapter extends FragmentStatePagerAdapter {

    List<BaseFragment> fragments = new ArrayList<BaseFragment>();
    String[] tabNames = new String[3];

    public InformationFragAdapter(FragmentManager fm, List<BaseFragment> fragments, String[] tabNames) {
        super(fm);
        this.fragments = fragments;
        this.tabNames = tabNames;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments != null) {
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
