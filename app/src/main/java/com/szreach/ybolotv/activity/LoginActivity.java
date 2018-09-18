package com.szreach.ybolotv.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.szreach.ybolotv.R;
import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.utils.ShowToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements MVPView{


    @BindView(R.id.login_setting)
    ImageView loginSetting;
    @BindView(R.id.login_user_name)
    EditText loginUserName;
    @BindView(R.id.login_user_psd)
    EditText loginUserPsd;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showData(Object data) {

    }

    @Override
    public void onRefresh(Object data) {

    }


    @OnClick({R.id.login_setting, R.id.login_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_setting:
                //设置IP

                break;
            case R.id.login_btn_login:
                //登录
                String name=loginUserName.getText().toString().trim();
                String psd=loginUserPsd.getText().toString().trim();
                if(name.equals("")||psd.equals("")){
                    ShowToast.setToastShort("用户名或密码不能为空");
                }else {

                }

                break;
        }
    }
}
