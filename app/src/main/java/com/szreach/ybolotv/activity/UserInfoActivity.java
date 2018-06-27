package com.szreach.ybolotv.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.szreach.ybolotv.App;
import com.szreach.ybolotv.base.BaseActivity;
import com.szreach.ybolotv.bean.UserInfo;
import com.szreach.ybolotv.mInterface.Interface;
import com.szreach.ybolotv.utils.FileUtils;
import com.szreach.ybolotv.utils.mLog;
import com.szreach.ybolotv.utils.mToast;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import ybolo.szreach.com.live_vod.R;


public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.user_info_back)
    ImageView back;
    @BindView(R.id.user_info_save)
    TextView save;
    @BindView(R.id.user_info_head)
    ImageView head_iv;
    @BindView(R.id.user_info_name)
    EditText name_tv;
    @BindView(R.id.user_info_work)
    TextView work;
    @BindView(R.id.user_info_sign_edit)
    EditText sign_tv;
    @BindView(R.id.user_info_switch)
    Switch userInfoSwitch;

    private UserInfo userInfo;
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private static final int REQUEST_CROP = 102;
    private File mTmpFile;
    private File mCropImageFile;
    private String photoPath="";
    private Disposable disposable;

    @Override
    protected void HandlerMsg(int msg) {
        super.HandlerMsg(msg);
        switch (msg){
            case 22:
                waitDialog.dismiss();
                errorDialog.show();
                errorDialog.setTextMsg(Msg);
                break;
            case 33:
                //更新本地数据库
                userInfo.setUserName(name_tv.getText().toString().trim());
                userInfo.setIntroduction(sign_tv.getText().toString().trim());
                userInfo.setUserSex(userInfoSwitch.isChecked()?2:1);
                daoSession.getUserInfoDao().insertOrReplace(userInfo);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        userInfo=daoSession.getUserInfoDao().loadAll().get(0);
        RequestOptions options=new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_avatar).error(R.drawable.ic_avatar);
        Glide.with(this).load(Interface.getIpAddress(App.getApplication())+"/Rec/userImg/"+userInfo.getUserImg()).apply(options).into(head_iv);
        name_tv.setText(userInfo.getUserName());
        if(userInfo.getUserSex()==2){
            userInfoSwitch.setTextOn("女");
        }else {
            userInfoSwitch.setTextOff("男");
        }
        work.setText(userInfo.getCoName());
        sign_tv.setText(userInfo.getIntroduction());

    }


    @OnClick({R.id.user_info_back, R.id.user_info_save, R.id.user_info_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_info_back:
                finish();
                break;
            case R.id.user_info_save:
                SaveInfo();
                break;
            case R.id.user_info_head:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("设置头像");
                String[] items = { "选择本地照片", "拍照" };
                builder.setNegativeButton("取消", null);
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // 选择本地照片
                                gallery();
                                break;
                            case 1: // 拍照
                                if(mcheckPermission()){
                                    camera();
                                }
                                break;
                        }
                    }
                });
                builder.create().show();
                break;
        }
    }

    //保证数据
    private void SaveInfo() {
        if(name_tv.getText().toString().equals("")||sign_tv.getText().toString().equals("")){
            mToast.setToastShort(getApplicationContext(),"用户名或个人签名不能为空");
        }else {
            handler.sendEmptyMessage(00);
            //上传用户数据，头像和文字数据分开传
            Observable<Boolean> observable=Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe( ObservableEmitter<Boolean> emitter) throws Exception {
                    try{
                        //如果头像文件！=null上传头像
                        if(null!=mCropImageFile){
                            OkHttpClient client = new OkHttpClient();
                            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("multipart/form-data;charset=utf-8");
                            RequestBody requestBody = new MultipartBody.Builder()
                                    .addFormDataPart("coverPath","/Rec/userImg/")
                                    .addFormDataPart("file", mCropImageFile.getName(), RequestBody.create(MEDIA_TYPE_MARKDOWN, mCropImageFile))
                                    .build();
                            okhttp3.Request request = new okhttp3.Request.Builder()
                                    .url(Interface.getIpAddress(getApplicationContext())+"/sys/ajax/upload/img")
                                    .post(requestBody)
                                    .build();
                            okhttp3.Response response = client.newCall(request).execute();
                            JSONObject object=new JSONObject(response.body().string());
                            mLog.e("photoReponse","-->"+object.toString());
                            if(object.getBoolean("success")){
                                photoPath=object.getString("filename");
                                emitter.onNext(true);
                                emitter.onComplete();
                            }else {
                                emitter.onNext(false);
                                emitter.onComplete();
                            }
                        }else {
                            emitter.onNext(true);
                            emitter.onComplete();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        emitter.onError(new Throwable());
                    }

                }
            });
            Observer<Boolean> observer=new Observer<Boolean>() {
                @Override
                public void onSubscribe(Disposable d) {
                    disposable=d;
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if(aBoolean){
                        //开始上传文字数据
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                params.clear();
                                objects.clear();
                                params.add("userId");
                                params.add("introduction");
                                params.add("userImg");
                                params.add("userName");
                                params.add("userSex");
                                objects.add(userInfo.getUserId());
                                objects.add(sign_tv.getText().toString().trim());
                                objects.add(null!=mCropImageFile?mCropImageFile.getAbsolutePath():userInfo.getUserImg());
                                objects.add(name_tv.getText().toString().trim());
                                objects.add(userInfoSwitch.isChecked()?2:1);
                                String url=Interface.getIpAddress(getApplicationContext())+Interface.URL_PREFIX_MY_HOME+Interface.URL_POST_USER_MODIFY_INFO;
                                Request<JSONObject> request= NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
                                request.setDefineRequestBodyForJson(addParams(params,objects));
                                SendRequest(7, request, new OnResponseListener<JSONObject>() {
                                    @Override
                                    public void onStart(int what) {

                                    }

                                    @Override
                                    public void onSucceed(int what, Response<JSONObject> response) {
                                        mLog.e("reponse","sendInfo-->"+response.get().toString());
                                        try {
                                            JSONObject object=new JSONObject(response.get().toString());
                                            JSONObject object1=object.getJSONObject("msgHeader");
                                            if(object1.getBoolean("result")){
                                                //在上传成功时更新本地数据库
                                                handler.sendEmptyMessage(33);
                                            }
                                            Msg=object1.getString("errorInfo");
                                            handler.sendEmptyMessage(22);
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
                        }).start();
                    }else {
                        Msg="头像上传失败，请稍后再试";
                        handler.sendEmptyMessage(88);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            observable.subscribeOn(Schedulers.newThread()).subscribe(observer);
        }
    }

    @Override
    protected void onDestroy() {
        if(disposable!=null){
            disposable.dispose();
        }
        super.onDestroy();
    }

    private boolean mcheckPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int checkPermission_WR= ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkPermission_CAMERA= ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA);
            if(checkPermission_WR!= PackageManager.PERMISSION_GRANTED && checkPermission_CAMERA!= PackageManager.PERMISSION_GRANTED){
                //没有授权,请求授权
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},1);
                return false;
            }else {
                return true;
            }
        }else {
            return true;
        }
    }


    private void camera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            mTmpFile = new File(FileUtils.createRootPath(getBaseContext()) + "/" + System.currentTimeMillis() + ".jpg");
            FileUtils.createFile(mTmpFile);
            if(Build.VERSION.SDK_INT < 24){
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }else {
                //适配安卓7.0
                ContentValues contentValues=new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA,mTmpFile.getAbsolutePath());
                Uri uri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                grantUriPermission("ybolo.szreach.com.live_vod",uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
        }
    }

    private void gallery(){
        Intent intent = new Intent();
        //打开能打开相册的应用
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CAMERA){
            if (resultCode == RESULT_OK){
                crop(mTmpFile.getAbsolutePath());
            }else {
                Toast.makeText(getApplicationContext(), "拍照失败", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==REQUEST_CROP){
            if (resultCode == RESULT_OK){
                //head_iv.setImageURI(Uri.fromFile(mCropImageFile));
                RequestOptions options=new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_avatar).error(R.drawable.ic_avatar);
                Glide.with(this).load(mCropImageFile).apply(options).into(head_iv);
            }else {
                Toast.makeText(getApplicationContext(), "截图失败", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==REQUEST_GALLERY){
            if (resultCode == RESULT_OK && data != null){
                String imagePath = handleImage(data);
                crop(imagePath);
            }else {
                Toast.makeText(getApplicationContext(), "打开图库失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void crop(String imagePath){
        //mCropImageFile = FileUtils.createTmpFile(getBaseContext());
        mCropImageFile = getmCropImageFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(new File(imagePath)), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropImageFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUEST_CROP);
    }


    //把fileUri转换成ContentUri
    public Uri getImageContentUri(File imageFile){
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    //获取裁剪的图片保存地址
    private File getmCropImageFile(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"temp.jpg");
            File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            return file;
        }
        return null;
    }

    private String handleImage(Intent data) {
        Uri uri = data.getData();
        String imagePath = null;
        if (Build.VERSION.SDK_INT >= 19) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("" +
                            "content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equals(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            }
        } else {
            imagePath = getImagePath(uri, null);
        }
        return imagePath;
    }


    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
