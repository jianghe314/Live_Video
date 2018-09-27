package com.szreach.ybolotv;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.edge.pcdn.PcdnManager;
import com.edge.pcdn.PcdnType;
import com.szreach.ybolotv.db.DaoMaster;
import com.szreach.ybolotv.db.DaoSession;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cache.DBCacheStore;

import org.greenrobot.greendao.AbstractDaoMaster;

/**
 * Created by ZX on 2018/9/18
 * 这个版本为MVP架构测试版
 */

public class MyApplication extends Application {

    private static Context context;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initNohttp();
        initGreenDao();

        //初始化PCDN
        PcdnManager.start(this, PcdnType.LIVE,"60009c01005b86553238bed989d37402b95680375bb9c9cba1",null,"1",null);
        //初始化PCDN
        PcdnManager.start(this, PcdnType.VOD,"60009c01005b86553238bed989d37402b95680375bb9c9cba1",null,"1",null);
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
    public static DaoSession getDaoSession() {
        return daoSession;
    }


    public static Context getApplication(){
        return context;
    }

}
