package com.szreach.ybolotv.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.presenter.SettingPresenter;
import com.szreach.ybolotv.utils.ShowToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements MVPView {

    @BindView(R.id.setting_back)
    ImageView settingBack;
    @BindView(R.id.setting_save)
    TextView settingSave;
    @BindView(R.id.setting_userName)
    EditText settingUserName;
    @BindView(R.id.setting_oldPsd)
    EditText settingOldPsd;
    @BindView(R.id.setting_newPsd)
    EditText settingNewPsd;
    @BindView(R.id.setting_confirmPsd)
    EditText settingConfirmPsd;

    private UserInfo userInfo;
    private SettingPresenter settingPresenter;
    private String url= Interface.getIpAddress(MyApplication.getApplication())+Interface.URL_PREFIX_MY_HOME+Interface.URL_POST_USER_PWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        userInfo= MyApplication.getDaoSession().getUserInfoDao().loadAll().get(0);
        settingPresenter=new SettingPresenter();
        settingPresenter.attachView(this);
        settingUserName.setText(userInfo.getUserName());
    }

    @OnClick({R.id.setting_back, R.id.setting_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_back:
                finish();
                break;
            case R.id.setting_save:
                //保存
                if(!settingOldPsd.getText().toString().trim().equals("")){
                    if(!settingNewPsd.getText().toString().trim().equals("")){
                       if(!settingConfirmPsd.getText().toString().trim().equals("")){
                           params.clear();
                           values.clear();
                           params.add("newpwd");
                           params.add("oldpwd");
                           params.add("userLogin");
                           values.add(settingNewPsd.getText().toString().trim());
                           values.add(settingOldPsd.getText().toString().trim());
                           values.add(userInfo.getUserName());
                           settingPresenter.sendPsd(url,params,values);
                       }else {
                           ShowToast.setToastShort("确认密码不能为空！");
                       }
                    }else {
                        ShowToast.setToastShort("密码不能为空！");
                    }
                }else {
                    ShowToast.setToastShort("旧密码不能为空！");
                }
                break;
        }
    }



}
