package com.flobberworm.demo.suspension.recycle;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flobberworm.demo.DemoApplication;
import com.flobberworm.demo.R;

/**
 * SimpleRecycleViewHolder
 * Created by Kornan on 2017/5/20.
 */
public class SimpleRecycleViewHolder extends SimpleRecycleAdapter.ViewHolder<SimpleContentData> {
    private ImageView ivImage;

    public SimpleRecycleViewHolder(View itemView) {
        super(itemView);
        ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
    }

    @Override
    public void bindView(SimpleContentData data) {
        Glide.with(DemoApplication.application)
                .load(data.imageUrl)
                .into(ivImage);
    }
}