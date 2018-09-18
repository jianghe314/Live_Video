package com.szreach.ybolotv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.szreach.ybolotv.base.BaseFragment;

import java.util.List;

/**
 * Created by ZX on 2018/9/18
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
