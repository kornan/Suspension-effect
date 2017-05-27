package com.flobberworm.demo.suspension.recycle;

/**
 * Created by Kornan on 2017/5/27.
 */

public class SimpleContentData implements SimpleRecycleAdapter.ItemData {
    public String imageUrl;

    @Override
    public int getType() {
        return 0;
    }
}