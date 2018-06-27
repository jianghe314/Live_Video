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
import com.szreach.ybolotv.activity.VideoDetailActivity;
import com.szreach.ybolotv.bean.VideoList;
import com.szreach.ybolotv.utils.mLog;

import java.util.List;

import ybolo.szreach.com.live_vod.R;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListHolder>{

    private List<VideoList> data;
    private Context context;

    public VideoListAdapter(List<VideoList> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list_layout,parent,false);
        return new VideoListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListHolder holder, int position) {
        final VideoList item=data.get(position);
        holder.bandData(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail=new Intent(context, VideoDetailActivity.class);
                detail.putExtra("videoId",item.getVideoId());
                detail.putExtra("coId",item.getCoId());
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VideoListHolder extends RecyclerView.ViewHolder{

        ImageView brand;
        TextView title;
        TextView source;
        TextView person;

        private VideoListHolder(View itemView) {
            super(itemView);
            brand=itemView.findViewById(R.id.item_video_list_iv);
            title=itemView.findViewById(R.id.item_video_list_title);
            source=itemView.findViewById(R.id.item_video_list_source);
            person=itemView.findViewById(R.id.item_video_list_person);
        }

        private void bandData(VideoList item){
            title.setText(item.getVideoCname());
            source.setText(R.string.video_sec_title);
            person.setText(item.getVideoVod()+"");
            RequestOptions options=new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.placeholder).error(R.drawable.error);
            mLog.e("pic","-->"+item.getVideoImage());
            Glide.with(context).load(item.getVideoImage()).apply(options).into(brand);
        }
    }
}
