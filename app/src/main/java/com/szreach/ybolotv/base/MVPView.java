package com.szreach.ybolotv.base;

public interface MVPView extends BaseView{

    /**
     * 当请求数据成功后回调此接口
     * @param data
     */
    @Override
    void showData(Object data);
}
