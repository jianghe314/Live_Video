package com.szreach.ybolotv.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_nav_view)
    BottomNavigationView bottomNavView;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;

    private  enum FragmentItem{

        LiveFragment(R.id.tab_menu1,LiveFragment.class),
        VideoFragment(R.id.tab_menu2,VideoFragment.class),
        personFragment(R.id.tab_menu3,HomeFragment.class);

        private BaseFragment fragment;
        private Class<? extends BaseFragment> aClass;
        private int Id;
        FragmentItem(@IdRes int Id,Class<? extends BaseFragment> aClass){
            this.aClass=aClass;
            this.Id=Id;
        }

        public  BaseFragment fragment(){
            if(fragment == null){
                try {
                    fragment=aClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return fragment;
        }


        public static FragmentItem from(@IdRes int Id){
            for (FragmentItem fragmentItem:values()) {
                if(fragmentItem.Id == Id){
                    return fragmentItem;
                }
            }
            return LiveFragment;
        }

        public static void onDestory(){
            for (FragmentItem Item:values()) {
                Item=null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavView.setOnNavigationItemSelectedListener(this);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);       //默认选中第一个
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentItem.values()[position].fragment();
            }

            @Override
            public int getCount() {
                return FragmentItem.values().length;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                bottomNavView.setSelectedItemId(FragmentItem.values()[position].Id);
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        viewPager.setCurrentItem(FragmentItem.from(item.getItemId()).ordinal(),false);
        return true;
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("是否确认退出？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sp = getSharedPreferences("Status", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("logout", false);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentItem.onDestory();
    }
}
