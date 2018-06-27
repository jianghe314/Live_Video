package com.szreach.ybolotv.fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ybolo.szreach.com.live_vod.App;
import ybolo.szreach.com.live_vod.R;
import ybolo.szreach.com.live_vod.base.BaseFragment;
import ybolo.szreach.com.live_vod.utils.mToast;


public class LiveFragment extends BaseFragment implements LiveListFragment.NoticeListDataChange{


    @BindView(R.id.live_fragment_title_iv)
    ImageView titleIv;
    @BindView(R.id.live_fragment_livelist_tv)
    TextView livelistTv;
    @BindView(R.id.live_fragment_theme_tv)
    TextView themeTv;
    @BindView(R.id.live_fragment_liveContent)
    FrameLayout LiveContent;
    Unbinder unbinder;

    private LiveListFragment liveListFragment;
    private LiveListSubjectFragment listSubjectFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected int setContentView() {
        return R.layout.livefragment_layout;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        switchFragment(R.id.live_fragment_livelist_tv);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.live_fragment_livelist_tv, R.id.live_fragment_theme_tv})
    public void onViewClicked(View view) {
        switchFragment(view.getId());
    }


    @Override
    public void dataChange(int size) {
        livelistTv.setText(Html.fromHtml("直播列表<font color='#ff0015'>(" + size +")</font>"));
    }

    private void switchFragment(int Id) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        hideAllFragment();
        switch (Id) {
            case R.id.live_fragment_livelist_tv:
                clearColor();
                setTextColor(livelistTv);
                if (null==liveListFragment) {
                    liveListFragment = new LiveListFragment();
                    fragmentTransaction.add(R.id.live_fragment_liveContent,liveListFragment);
                }else {
                    fragmentTransaction.show(liveListFragment);
                }
                break;
            case R.id.live_fragment_theme_tv:
                mToast.setToastShort(App.getApplication(),"暂无相关内容");
                /*
                clearColor();
                setTextColor(themeTv);
                if(null==listSubjectFragment){
                    listSubjectFragment=new LiveListSubjectFragment();
                    fragmentTransaction.add(R.id.live_fragment_liveContent,listSubjectFragment);
                }else {
                    fragmentTransaction.show(listSubjectFragment);
                }*/
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragment() {
        if(liveListFragment!=null){
            fragmentTransaction.hide(liveListFragment);
        }
        if(listSubjectFragment!=null){
            fragmentTransaction.hide(listSubjectFragment);
        }
    }

    private void clearColor(){
        livelistTv.setTextColor(getResources().getColor(R.color.color_black_gray));
        themeTv.setTextColor(getResources().getColor(R.color.color_black_gray));
    }
    private void setTextColor(TextView tv){
        tv.setTextColor(getResources().getColor(R.color.live_textColor));
    }


}
