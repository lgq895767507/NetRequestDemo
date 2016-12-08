package com.flw.netrequestdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flw.netrequestdemo.R;
import com.flw.netrequestdemo.entity.Contents;

import java.util.List;

/**
 * Created by lgq on 2016/12/7.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>{

    private Context context;
    private List<Contents> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public RecycleAdapter(Context context,List<Contents> mDatas){
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position).getTitle());
        //设置监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.tv,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

}
