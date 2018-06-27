package com.szreach.ybolotv.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import ybolo.szreach.com.live_vod.R;
import ybolo.szreach.com.live_vod.base.BaseActivity;
import ybolo.szreach.com.live_vod.bean.UserInfo;
import ybolo.szreach.com.live_vod.mInterface.Interface;
import ybolo.szreach.com.live_vod.utils.mLog;
import ybolo.szreach.com.live_vod.utils.mToast;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.setting_back)
    ImageView back;
    @BindView(R.id.setting_save)
    TextView save;
    @BindView(R.id.setting_userName)
    EditText user_name;
    @BindView(R.id.setting_oldPsd)
    EditText old_psd;
    @BindView(R.id.setting_newPsd)
    EditText new_psd;
    @BindView(R.id.setting_confirmPsd)
    EditText confirm_psd;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        userInfo=daoSession.getUserInfoDao().loadAll().get(0);
    }
    private void initData() {
        user_name.setText(userInfo.getUserName());
    }

    @OnClick({R.id.setting_back, R.id.setting_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_back:
                finish();
                break;
            case R.id.setting_save:
               if(isEmpty()){
                   params.clear();
                   objects.clear();
                   params.add("newpwd");
                   params.add("oldpwd");
                   params.add("userLogin");
                   objects.add(new_psd.getText().toString().trim());
                   objects.add(old_psd.getText().toString().trim());
                   objects.add(userInfo.getUserName());
                   String url= Interface.getIpAddress(getApplicationContext())+Interface.URL_PREFIX_MY_HOME+Interface.URL_POST_USER_PWD;
                   Request<JSONObject> request= NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
                   request.setDefineRequestBodyForJson(addParams(params,objects));
                   SendRequest(8, request, new OnResponseListener<JSONObject>() {
                       @Override
                       public void onStart(int what) {
                           handler.sendEmptyMessage(00);
                       }

                       @Override
                       public void onSucceed(int what, Response<JSONObject> response) {
                            mLog.e("reponse","setting-->"+response.get().toString());
                           try {
                               JSONObject object=new JSONObject(response.get().toString());
                               JSONObject object1=object.getJSONObject("msgHeader");
                               if(object1.getBoolean("result")){
                                   Msg="修改成功";
                                   handler.sendEmptyMessage(88);
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

                       }
                   });
               }
                break;
        }
    }

    private boolean isEmpty() {
        boolean isempty=false;
        if(!user_name.getText().toString().trim().equals("")){
            if(!old_psd.getText().toString().trim().equals("")){
                if(!new_psd.getText().toString().trim().equals("")){
                   if(!confirm_psd.getText().toString().trim().equals("")){
                       isempty=true;
                   }else {
                       mToast.setToastShort(getApplicationContext(),"确认密码不能为空");
                   }
                }else {
                    mToast.setToastShort(getApplicationContext(),"密码不能为空");
                }
            }else {
                mToast.setToastShort(getApplicationContext(),"旧密码不能为空");
            }
        }else {
            mToast.setToastShort(getApplicationContext(),"用户名不能为空");
        }
        return isempty;
    }
}
