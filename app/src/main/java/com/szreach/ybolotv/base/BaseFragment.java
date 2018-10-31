package com.szreach.ybolotv.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szreach.ybolotv.utils.ShowToast;
import com.szreach.ybolotv.utils.mLog;
import com.szreach.ybolotv.widgets.ErrorDialog;
import com.szreach.ybolotv.widgets.WaitDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZX on 2018/9/18
 * 这个基类有个bug，在一些情况下，如：前一个页面传递一个数据，给当前页面时
 * 特别是这些参数用于网络请求，会先加载网络数据，这是传递的数据还没有到，即请求先于加载View
 */
public abstract class BaseFragment extends Fragment implements BaseView{

    private View view;
    //是否已经加载
    private boolean isLoad=false;

    private WaitDialog waitDialog;
    private ErrorDialog errorDialog;

    protected List<String> params=new ArrayList<>();
    protected List<Object> values=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(setContentView(),container,false);
        waitDialog=new WaitDialog(getContext());
        errorDialog=new ErrorDialog(getContext());
        initView(view);
        loadData();
        return view;
    }

    protected abstract void initView(View view);

    @Override
    public void showToast(String msg) {
        ShowToast.setToastShort(msg);
    }

    @Override
    public void hideLoading() {
        waitDialog.dismiss();
    }

    @Override
    public void showError(String msg) {
        waitDialog.dismiss();
        errorDialog.show();
        errorDialog.setTextMsg(msg);
    }

    @Override
    public void showLoading() {
        waitDialog.show();
        waitDialog.setWatiContent("加载中");
    }

    @Override
    public void showData(Object data) {

    }

    @Override
    public void onRefresh(Object data) {

    }

    private void lazyLoadData() {
        if(getUserVisibleHint()){
            loadData();
            isLoad=true;
        }

    }

    protected abstract void stopLoad();

    protected abstract void loadData();

    //此方法会在onCreate()方法之前调用一次，当视图切换到当前fragment时，还会调用一次
    //相应的getgetUserVisibleHint()先为false,然后为true

    /*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser&&!isLoad){
            lazyLoadData();
        }else {
            stopLoad();
        }
    }
    */


    protected abstract int setContentView();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
