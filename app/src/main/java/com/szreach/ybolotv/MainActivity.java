package com.szreach.ybolotv;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ybolo.szreach.com.live_vod.fragment.HomeFragment;
import ybolo.szreach.com.live_vod.fragment.LiveFragment;
import ybolo.szreach.com.live_vod.fragment.MeFragment;
import ybolo.szreach.com.live_vod.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.FragmentCallBack{


    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.tab_menu_home_iv)
    ImageView HomeIv;
    @BindView(R.id.tab_menu_home_tv)
    TextView HomeTv;
    @BindView(R.id.tab_menu1)
    LinearLayout tabMenu1;
    @BindView(R.id.tab_menu_live_iv)
    ImageView LiveIv;
    @BindView(R.id.tab_menu_live_tv)
    TextView LiveTv;
    @BindView(R.id.tab_menu2)
    LinearLayout tabMenu2;
    @BindView(R.id.tab_menu_tv_iv)
    ImageView TvIv;
    @BindView(R.id.tab_menu_tv_tv)
    TextView TvTv;
    @BindView(R.id.tab_menu3)
    LinearLayout tabMenu3;
    @BindView(R.id.tab_menu_me_iv)
    ImageView MeIv;
    @BindView(R.id.tab_menu_me_tv)
    TextView MeTv;
    @BindView(R.id.tab_menu4)
    LinearLayout tabMenu4;
    private HomeFragment homeFragment;
    private LiveFragment liveFragment;
    private VideoFragment videoFragment;
    private MeFragment meFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        switchToFragment(R.id.tab_menu1);
    }


    @OnClick({R.id.tab_menu1, R.id.tab_menu2, R.id.tab_menu3, R.id.tab_menu4})
    public void onViewClicked(View view) {
        switchToFragment(view.getId());
    }


    private void switchToFragment(int viewId) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (viewId) {
            case R.id.tab_menu1:
                unSelected();
                HomeIv.setSelected(true);
                HomeTv.setSelected(true);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.main_content, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case R.id.tab_menu2:
                unSelected();
                LiveIv.setSelected(true);
                LiveTv.setSelected(true);
                if (liveFragment == null) {
                    liveFragment = new LiveFragment();
                    fragmentTransaction.add(R.id.main_content, liveFragment);
                } else {
                    fragmentTransaction.show(liveFragment);
                }
                break;
            case R.id.tab_menu3:
                unSelected();
                TvIv.setSelected(true);
                TvTv.setSelected(true);
                if (videoFragment == null) {
                    videoFragment = new VideoFragment();
                    fragmentTransaction.add(R.id.main_content, videoFragment);
                } else {
                    fragmentTransaction.show(videoFragment);
                }
                break;
            case R.id.tab_menu4:
                unSelected();
                MeIv.setSelected(true);
                MeTv.setSelected(true);
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    fragmentTransaction.add(R.id.main_content, meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void unSelected() {
        HomeIv.setSelected(false);
        HomeTv.setSelected(false);
        LiveIv.setSelected(false);
        LiveTv.setSelected(false);
        TvIv.setSelected(false);
        TvTv.setSelected(false);
        MeIv.setSelected(false);
        MeTv.setSelected(false);

    }


    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (liveFragment != null) {
            transaction.hide(liveFragment);
        }
        if (videoFragment != null) {
            transaction.hide(videoFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }

    }

    @Override
    public void setFragmentCallBack(int fragmentId) {
        switchToFragment(fragmentId);
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
