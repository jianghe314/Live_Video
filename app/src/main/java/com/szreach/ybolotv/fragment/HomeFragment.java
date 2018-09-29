package com.szreach.ybolotv.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.activity.SettingActivity;
import com.szreach.ybolotv.activity.UserInfoActivity;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.utils.ShowToast;
import com.szreach.ybolotv.utils.mLog;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by ZX on 2018/9/18
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private ImageView head_iv;
    private TextView name_tv;
    private RelativeLayout container_head;
    private RelativeLayout container_setting;
    private RelativeLayout container_logout;

    private UserInfo userInfo;

    @Override
    protected int setContentView() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void initView(View view) {
        head_iv=view.findViewById(R.id.mefragment_head_iv);
        name_tv=view.findViewById(R.id.mefragment_name_tv);
        container_head=view.findViewById(R.id.mefragment_head_container1);
        container_setting=view.findViewById(R.id.mefragment_head_container2);
        container_logout=view.findViewById(R.id.mefragment_head_container3);
        container_head.setOnClickListener(this);
        container_setting.setOnClickListener(this);
        container_logout.setOnClickListener(this);
        userInfo= MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);
    }

    @Override
    protected void loadData() {
        RequestOptions options=new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_avatar).error(R.drawable.ic_avatar);
        Glide.with(getActivity()).load(Interface.getIpAddress(MyApplication.getApplication())+"/Rec/userImg/"+userInfo.getUserImg()).apply(options).into(head_iv);
        name_tv.setText(userInfo.getUserName());
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mefragment_head_container1:
                //设置个人信息
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.mefragment_head_container2:
                //密码设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.mefragment_head_container3:
                //退出登录
                SharedPreferences sp=getActivity().getSharedPreferences("Status",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("logout",true);
                editor.apply();
                getActivity().finish();
                break;
        }
    }

    @Override
    public Context getContent() {
        return null;
    }


}
