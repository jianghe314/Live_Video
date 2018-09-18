package com.szreach.ybolotv.base;

/**
 * Created by ZX on 2018/9/18
 */
public class BasePresenter<V extends BaseView> {

    //绑定需要的View
    private V view;

    //绑定view
    public void attachView(V view){
        this.view=view;
    }

    //解绑view
    public void detachView(){
        this.view=null;
    }

    //获取绑定状态
    public boolean isViewAttach(){
        return view!=null;
    }

    //获取绑定的View
    public V getView(){
        return view;
    }
}
