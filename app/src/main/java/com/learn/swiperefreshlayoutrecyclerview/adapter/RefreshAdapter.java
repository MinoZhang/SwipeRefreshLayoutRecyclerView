package com.learn.swiperefreshlayoutrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.swiperefreshlayoutrecyclerview.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class RefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> mList;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    //上拉加载
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多
    public static final int NO_LOAD_MORE =2;
    //上拉加载更多状态（默认为0）
    private  int mLoadMoreStatus = 0;
    public RefreshAdapter(Context context,List<String> list){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){
            View itemView = mLayoutInflater.inflate(R.layout.item_refresh_recyclerview,parent,false);
            return new ItemViewHolder(itemView);
        }else if(viewType ==TYPE_FOOTER){
            View itemView = mLayoutInflater.inflate(R.layout.load_more_footview,parent,false);
            return new FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String str =mList.get(position);
            itemViewHolder.textView.setText(str);
        }else if(holder instanceof FooterViewHolder){
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus){
                case PULLUP_LOAD_MORE:
                    footerViewHolder.textView.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footerViewHolder.textView.setText("正在加载更多...");
                    break;
                case NO_LOAD_MORE:
                    //停止加载
                    footerViewHolder.linearLayout.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1 == getItemCount()){
          return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            initView();
            initListener();
        }

        private void initListener() {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "这是第"+getAdapterPosition()+"个Item", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void initView() {
             textView  = (TextView) itemView.findViewById(R.id.tvContent);
        }
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView textView;
        LinearLayout linearLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            progressBar = (ProgressBar) itemView.findViewById(R.id.pbLoad);
            textView = (TextView) itemView.findViewById(R.id.tvLoad);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.loadLayout);
        }
    }
    public void AddHeaderItem(List<String> items) {
        mList.addAll(0, items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(List<String> items) {
        mList.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 更新加载更多状态
     * @param status
     */
    public void changeMoreStatus(int status){
        mLoadMoreStatus=status;
        notifyDataSetChanged();
    }
}
