package com.szreach.ybolotv;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ybolo.szreach.com.live_vod.base.BaseActivity;
import ybolo.szreach.com.live_vod.bean.UserInfo;
import ybolo.szreach.com.live_vod.db.UserInfoDao;
import ybolo.szreach.com.live_vod.mInterface.Interface;
import ybolo.szreach.com.live_vod.utils.mLog;
import ybolo.szreach.com.live_vod.utils.mToast;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_setting)
    ImageView loginSetting;
    @BindView(R.id.login_user_name)
    EditText UserName;
    @BindView(R.id.login_user_psd)
    EditText UserPsd;
    @BindView(R.id.login_btn_login)
    Button BtnLogin;

    private AlertDialog.Builder builder;
    private EditText IpAddress;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_setting, R.id.login_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_setting:
                setServceAddress();
                break;
            case R.id.login_btn_login:
                if(isLogin()){
                    Request<JSONObject> request= NoHttp.createJsonObjectRequest(Interface.Login(), RequestMethod.POST);
                    params.clear();
                    objects.clear();
                    params.add("userlogin");
                    params.add("userpass");
                    objects.add(UserName.getText().toString().trim());
                    objects.add(UserPsd.getText().toString().trim());
                    request.setDefineRequestBodyForJson(addParams(params,objects));
                    SendRequest(1, request, new OnResponseListener<JSONObject>() {
                        @Override
                        public void onStart(int what) {
                            handler.sendEmptyMessage(00);
                        }

                        @Override
                        public void onSucceed(int what, Response<JSONObject> response) {
                            mLog.e("reponse","-->"+response.get().toString());
                            try {
                                JSONObject object=new JSONObject(response.get().toString());
                                JSONObject object1=object.getJSONObject("msgHeader");
                                if(object1.getBoolean("result")){
                                    userInfo=gson.fromJson(object.getString("data"),UserInfo.class);
                                    //保存数据,向数据库中插入UserInfo表
                                    UserInfoDao userInfoDao=daoSession.getUserInfoDao();
                                    userInfoDao.insertOrReplace(userInfo);
                                    Intent login=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(login);
                                    finish();
                                }else {
                                    Msg=object1.getString("errorInfo");
                                    handler.sendEmptyMessage(88);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(int what, Response<JSONObject> response) {
                            handler.sendEmptyMessage(99);
                        }

                        @Override
                        public void onFinish(int what) {
                            handler.sendEmptyMessage(11);
                        }
                    });
                }
                break;
        }
    }

    private boolean isLogin() {
        boolean isLog=false;
        if(!UserName.getText().toString().trim().equals("")){
            if(!UserPsd.getText().toString().trim().equals("")){
                isLog=true;
            }else {
                mToast.setToastShort(getApplicationContext(),"请输入密码");
            }
        }else {
            mToast.setToastShort(getApplicationContext(),"请输入用户名");
        }
        return isLog;
    }

    private void setServceAddress(){
        if(builder==null&&IpAddress==null){
            builder=new AlertDialog.Builder(this);
        }
        IpAddress=new EditText(this);
        if( !Interface.getIpAddress(App.getApplication()).equals("")){
            IpAddress.setText(Interface.getIpAddress(App.getApplication()));
        }
        IpAddress.setHint("如：http://www.ybolo.com");
        builder.setTitle("请设置服务器地址");
        builder.setView(IpAddress);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(IpAddress.getText().toString().trim().equals("")){
                    mToast.setToastShort(getApplicationContext(),"服务器地址不能为空");
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

}
