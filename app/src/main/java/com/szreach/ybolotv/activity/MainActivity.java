package com.szreach.ybolotv.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.adapter.FragmentAdapter;
import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.base.BaseFragment;
import com.szreach.ybolotv.fragment.HomeFragment;
import com.szreach.ybolotv.fragment.LiveFragment;
import com.szreach.ybolotv.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_menu_live_iv)
    ImageView tabMenuLiveIv;
    @BindView(R.id.tab_menu_live_tv)
    TextView tabMenuLiveTv;
    @BindView(R.id.tab_menu1)
    RelativeLayout tabMenu1;
    @BindView(R.id.tab_menu_tv_iv)
    ImageView tabMenuTvIv;
    @BindView(R.id.tab_menu_tv_tv)
    TextView tabMenuTvTv;
    @BindView(R.id.tab_menu2)
    RelativeLayout tabMenu2;
    @BindView(R.id.tab_menu_me_iv)
    ImageView tabMenuMeIv;
    @BindView(R.id.tab_menu_me_tv)
    TextView tabMenuMeTv;
    @BindView(R.id.tab_menu3)
    RelativeLayout tabMenu3;

    private List<BaseFragment> fragmentList=new ArrayList<>();
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentList.add(new LiveFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new HomeFragment());
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);       //默认选中第一个
        setSelect(tabMenuLiveIv,tabMenuLiveTv);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        unSelect();
                        setSelect(tabMenuLiveIv,tabMenuLiveTv);
                        viewPager.setCurrentItem(0,false);
                        break;
                    case 1:
                        unSelect();
                        setSelect(tabMenuTvIv,tabMenuTvTv);
                        viewPager.setCurrentItem(1,false);
                        break;
                    case 2:
                        unSelect();
                        setSelect(tabMenuMeIv,tabMenuMeTv);
                        viewPager.setCurrentItem(2,false);
                        break;
                }
            }
        });

    }


    @OnClick({R.id.tab_menu1, R.id.tab_menu2, R.id.tab_menu3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_menu1:
                unSelect();
                setSelect(tabMenuLiveIv,tabMenuLiveTv);
                viewPager.setCurrentItem(0,false);
                break;
            case R.id.tab_menu2:
                unSelect();
                setSelect(tabMenuTvIv,tabMenuTvTv);
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.tab_menu3:
                unSelect();
                setSelect(tabMenuMeIv,tabMenuMeTv);
                viewPager.setCurrentItem(2,false);
                break;
        }
    }


    private void unSelect(){
        tabMenuLiveIv.setSelected(false);
        tabMenuTvIv.setSelected(false);
        tabMenuMeIv.setSelected(false);

        tabMenuLiveTv.setSelected(false);
        tabMenuTvTv.setSelected(false);
        tabMenuMeTv.setSelected(false);
    }

    private void setSelect(ImageView iv,TextView tv){
        iv.setSelected(true);
        tv.setSelected(true);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("是否确认退出？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sp=getSharedPreferences("Status",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("logout",false);
                editor.apply();
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
