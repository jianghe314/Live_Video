package com.szreach.ybolotv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.szreach.ybolotv.MyApplication;
import com.szreach.ybolotv.R;
import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.base.MVPView;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.presenter.LoginPresenter;
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

    private LoginPresenter  loginPresenter;
    private AlertDialog.Builder builder;
    private EditText IpAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter=new LoginPresenter();
        loginPresenter.attachView(this);
        ButterKnife.bind(this);
    }

    @Override
    public void showData(Object data) {
        //跳转
        Intent login=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(login);
        finish();
    }

    @OnClick({R.id.login_setting, R.id.login_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_setting:
                //设置IP
                setServceAddress();
                break;
            case R.id.login_btn_login:
                //登录
                String name=loginUserName.getText().toString().trim();
                String psd=loginUserPsd.getText().toString().trim();
                if(name.equals("")||psd.equals("")){
                    ShowToast.setToastShort("用户名或密码不能为空");
                }else {
                    params.clear();
                    values.clear();
                    params.add("userlogin");
                    params.add("userpass");
                    values.add(name);
                    values.add(psd);
                    loginPresenter.Login(params,values);
                }
                break;
        }
    }

    private void setServceAddress(){
        if(builder==null&&IpAddress==null){
            builder=new AlertDialog.Builder(this);
        }
        IpAddress=new EditText(this);
        if( !Interface.getIpAddress(MyApplication.getApplication()).equals("")){
            IpAddress.setText(Interface.getIpAddress(MyApplication.getApplication()));
        }
        IpAddress.setHint("如：http://www.ybolo.com");
        builder.setTitle("请设置服务器地址");
        builder.setView(IpAddress);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(IpAddress.getText().toString().trim().equals("")){
                    ShowToast.setToastShort("服务器地址不能为空");
                }else {
                    saveIpAddress(IpAddress.getText().toString().trim());
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

    private void saveIpAddress(String address) {
        SharedPreferences sp=getSharedPreferences("Address",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("address",address);
        editor.apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }
}
