package com.szreach.ybolotv.base;

public interface CallBack<T> {

    /**
     * 数据请求中
     */
    void onLoading();

    /**
     * 数据请求成功回调
     * @param data
     */
    void onSuccess(int waht,T data);

    /**
     * 数据刷新
     * @param data
     */
   void onRefresh(int waht,T data);

    /**
     * 网络请求发送错误提示信息
     * @param msg
     */
    void onError(String msg);

    /**
     * 请求结束，无论请求结果是否成功
     */
    void onComplete();

}
