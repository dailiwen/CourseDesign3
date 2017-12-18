package com.example.administrator.coursedesign.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.coursedesign.R;

import java.util.List;

/**
 * @author dailiwen
 * @date 2017/12/11 下午 2:47
 */

public class MazeAdapter extends RecyclerView.Adapter {

    private List<String> mData;
    private LCallBack lCallBack;
    private Context mContext;

    public MazeAdapter(Context context, LCallBack lCallBack) {
        this.lCallBack = lCallBack;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
        final ViewHolder holder = new ViewHolder(view);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //回调
                lCallBack.answer(mData.get(position));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       ViewHolder viewHolder = (ViewHolder) holder;

       viewHolder.mTextView.setText("迷宫" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0:mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_content);
        }
    }

    public void setData(List<String> data) {
        mData = data;
    }

    public interface LCallBack {
        public void answer(String str);  //类B的内部接口
    }
}
