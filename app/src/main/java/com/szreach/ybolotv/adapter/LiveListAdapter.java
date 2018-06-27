package com.szreach.ybolotv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ybolo.szreach.com.live_vod.App;
import ybolo.szreach.com.live_vod.R;
import ybolo.szreach.com.live_vod.activity.LiveDetailActivity;
import ybolo.szreach.com.live_vod.bean.LiveList;
import ybolo.szreach.com.live_vod.mInterface.Interface;

public class LiveListAdapter extends RecyclerView.Adapter<LiveListAdapter.LiveListHolder> {

    private List<LiveList> data;
    private Context context;

    public LiveListAdapter(List<LiveList> data, Context context) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public LiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LiveListHolder holder;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_list_layout,parent,false);
        holder=new LiveListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveListHolder holder, int position) {
        final LiveList item=data.get(position);
        holder.bindData(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail=new Intent(context,LiveDetailActivity.class);
                detail.putExtra("liveId",item.getLiveId());
                detail.putExtra("coId",item.getCoId());
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LiveListHolder extends RecyclerView.ViewHolder{
        ImageView brand;
        ImageView isLive;
        TextView title;
        TextView time;
        TextView persons;


        private LiveListHolder(View itemView) {
            super(itemView);
            brand=itemView.findViewById(R.id.item_live_list_iv);
            isLive=itemView.findViewById(R.id.item_live_list_tag);
            title=itemView.findViewById(R.id.item_live_list_title);
            time=itemView.findViewById(R.id.item_live_list_time);
            persons=itemView.findViewById(R.id.item_live_list_person);

        }

        private void bindData(LiveList item){
            title.setText(item.getLiveName());
            time.setText(item.getLiveStart()+"-"+item.getLiveEnd().substring(11));
            persons.setText(item.getOnlineCount()+"");
            if(item.getLiveFlag()==1){
                isLive.setImageResource(R.drawable.live_red);
            }else {
                isLive.setImageResource(R.drawable.live_gray);
            }
            RequestOptions options=new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.banner_placeholder).error(R.drawable.banner_error);
            Glide.with(context).load(Interface.getIpAddress(App.getApplication())+Interface.URL_REC_COIMG+item.getHeadImg()).apply(options).into(brand);

        }
    }
}
