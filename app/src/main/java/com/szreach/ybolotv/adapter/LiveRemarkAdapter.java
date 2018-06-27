package com.szreach.ybolotv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.szreach.ybolotv.App;
import com.szreach.ybolotv.bean.LiveRemark;
import com.szreach.ybolotv.mInterface.Interface;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ybolo.szreach.com.live_vod.R;


public class LiveRemarkAdapter extends RecyclerView.Adapter<LiveRemarkAdapter.LiveListHolder> {

    private List<LiveRemark> data;
    private Context context;

    public LiveRemarkAdapter(List<LiveRemark> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new LiveListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remark_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LiveListHolder holder, int position) {
        LiveRemark item=data.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LiveListHolder extends RecyclerView.ViewHolder{

        CircleImageView head;
        TextView name;
        TextView content;
        TextView time;

        private LiveListHolder(View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.item_remark_head);
            name=itemView.findViewById(R.id.item_remark_name);
            content=itemView.findViewById(R.id.item_remark_content);
            time=itemView.findViewById(R.id.item_reamrk_time);
        }

        private void bindData(LiveRemark item){
            RequestOptions options=new RequestOptions().placeholder(R.drawable.head).error(R.drawable.head);
            Glide.with(context).load(Interface.getIpAddress(App.getApplication())+Interface.USER_IMG+item.getUserImg()).apply(options).into(head);
            name.setText(item.getUserName());
            content.setText(item.getCommContent());
            time.setText(item.getCommTime());
        }
    }
}
