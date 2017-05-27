package com.flobberworm.demo.suspension.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flobberworm.demo.DemoApplication;
import com.flobberworm.demo.R;

import java.util.List;

/**
 * Created by Kornan on 2017/5/20.
 */

public class SimpleRecycleAdapter extends RecyclerView.Adapter<SimpleRecycleAdapter.ViewHolder> {
    public static final int TYPE_SUSPENSION = 0x1001;

    private LayoutInflater mInflater;
    private List<ItemData> dataList;

    public SimpleRecycleAdapter(Context context, List<ItemData> dataList) {
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public SimpleRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SUSPENSION) {
            return new SuspensionViewHolder(mInflater.inflate(R.layout.item_suspension, parent, false));
        } else {
            return new SimpleRecycleViewHolder(mInflater.inflate(R.layout.item_recycle_content, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(SimpleRecycleAdapter.ViewHolder holder, int position) {
        holder.bindView(dataList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    interface ItemData {
        int getType();
    }

    public static class SuspensionData implements ItemData {
        public String title;

        @Override
        public int getType() {
            return TYPE_SUSPENSION;
        }
    }

    public static abstract class ViewHolder<T extends ItemData> extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindView(T data);
    }

    class SuspensionViewHolder extends ViewHolder<SuspensionData> {
        private TextView tvTitle;

        public SuspensionViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.text1);
        }

        @Override
        public void bindView(SuspensionData data) {
            tvTitle.setText(data.title);
        }
    }

}

