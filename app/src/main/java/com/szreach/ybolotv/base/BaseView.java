package com.szreach.ybolotv.base;

import android.content.Context;

public interface BaseView<T> {

    /**
     * 显示正在加载
     */
    void showLoading();

    /**
     * 关闭加载
     */
    void hideLoading();

    /**
     * 提示信息
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示错误提示
     */
    void showError(String msg);

    /**
     * 显示网络请求数据
     * @param data
     */
    void showData(T data);

    /**
     * 数据刷新
     * @param data
     */
    void onRefresh(T data);

    /**
     * 获取上下文
     * @return
     */
    Context getContent();
}
