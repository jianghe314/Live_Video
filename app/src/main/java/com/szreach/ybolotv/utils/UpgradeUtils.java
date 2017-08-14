package com.szreach.ybolotv.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;

import com.szreach.ybolotv.beans.ApkVersion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Adams.Tsui on 2017/8/11 0011.
 */

public class UpgradeUtils {
    private Context context;

    // 下载中
    private static final int DOWNLOADING = 1;
    // 下载完成后
    private static final int DOWNLOAD_FINISH = 2;
    // 保存路径
    private String mSavePath;
    // 当前进度
    private int progress;
    // 是否取消升级
    private boolean cancelUpdate = false;

    private ProgressDialog progressDialog;

    private ApkVersion apkVersion;

    public UpgradeUtils(Context context, ApkVersion apkVersion) {
        this.context = context;
        this.apkVersion = apkVersion;
    }

    /**
     * 开始下载升级
     */
    public void upgradeVersion() {
        this.showDownloadDialog();
        new UpgradeThread(this.upgradeHandler, this.apkVersion).start();
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadDialog() {
        progressDialog = UIUtils.createDownloadDialog(this.context);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
            progressDialog.dismiss();
            // 设置取消状态
            cancelUpdate = true;
            }
        });
        progressDialog.show();
    }

    /**
     * 子线程结果处理
     */
    private Handler upgradeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOADING:
                    // 设置进度条位置
                    progressDialog.setMessage("更新中..." + progress + "%");
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 子线程
     */
    private class UpgradeThread extends Thread {
        private Handler handler;
        private ApkVersion apkVersion;

        public UpgradeThread(Handler handler, ApkVersion apkVersion) {
            this.handler = handler;
            this.apkVersion = apkVersion;
        }

        @Override
        public void run() {
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(this.apkVersion.getApkPath());
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);

                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }

                    File apkFile = new File(mSavePath, this.apkVersion.getApkSaveName());
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;

                    // 缓存
                    byte buf[] = new byte[1024];

                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        this.handler.sendEmptyMessage(DOWNLOADING);
                        if (numread <= 0) {
                            // 下载完成
                            this.handler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        fos.write(buf, 0, numread);

                    } while (!cancelUpdate);

                    fos.close();
                    is.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void installApk() {
        File apkfile = new File(mSavePath, this.apkVersion.getApkSaveName());
        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (!apkfile.exists()) {
            return;
        }

        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(context, "com.szreach.ybolotv.utils.fileprovider", apkfile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(apkfile);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
