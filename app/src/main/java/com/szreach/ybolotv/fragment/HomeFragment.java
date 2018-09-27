package com.szreach.ybolotv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.utils.ShowToast;
import com.szreach.ybolotv.utils.mLog;


/**
 * Created by ZX on 2018/9/18
 */
public class HomeFragment extends BaseFragment {

    @Override
    protected int setContentView() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void loadData() {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showData(Object data) {

    }

    @Override
    public void onRefresh(Object data) {

    }

    @Override
    protected void stopLoad() {

    }

    @Override
    public Context getContent() {
        return null;
    }
}
