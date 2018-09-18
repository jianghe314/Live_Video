package com.szreach.ybolotv.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szreach.ybolotv.widgets.ErrorDialog;
import com.szreach.ybolotv.widgets.WaitDialog;

/**
 * Created by ZX on 2018/9/18
 */
public abstract class BaseFragment extends Fragment implements BaseView{

    private View view;
    //是否已经加载
    private boolean isLoad=false;

    private WaitDialog waitDialog;
    private ErrorDialog errorDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(setContentView(),container,false);
        waitDialog=new WaitDialog(getContext());
        errorDialog=new ErrorDialog(getContext());
        initView(view);
        return view;
    }

    protected abstract void initView(View view);


    private void lazyLoadData() {
        loadData();
        isLoad=true;
    }

    protected abstract void loadData();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser&&!isLoad){
            lazyLoadData();
        }
    }

    protected abstract int setContentView();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
