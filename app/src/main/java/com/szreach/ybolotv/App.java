package com.szreach.ybolotv;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cache.DBCacheStore;

import ybolo.szreach.com.live_vod.db.DaoMaster;
import ybolo.szreach.com.live_vod.db.DaoSession;

public class App extends Application {
    private static Context context;
    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initNohttp();
        initGreenDao();
    }

    private void initNohttp() {
        //配置nohttp链接超时和缓存到数据库
        InitializationConfig.Builder builder=InitializationConfig.newBuilder(this);
        builder.connectionTimeout(10*1000)
                .readTimeout(10*1000)
                .cacheStore(new DBCacheStore(this).setEnable(true)).build();
        InitializationConfig config= builder.build();
        NoHttp.initialize(config);
    }

    //初始化GreenDao
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"data.db");
        SQLiteDatabase db=helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        daoSession=daoMaster.newSession();
    }

    //获取DaoSession
    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static Context getApplication(){
        return context;
    }
}
