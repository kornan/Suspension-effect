package com.flobberworm.demo.recycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flobberworm.demo.R;

import java.util.ArrayList;
import java.util.List;

import static android.text.style.TtsSpan.TYPE_TIME;
import static com.flobberworm.demo.recycle.SimpleRecycleAdapter.TYPE_SUSPENSION;


public class SimpleRecycleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private View flSuspension;
    private TextView tvText1;
    private LinearLayoutManager linearLayoutManager;
    private SimpleRecycleAdapter simpleRecycleAdapter;
    private List<SimpleRecycleAdapter.ItemData> dataList = new ArrayList<>();
    int suspensionHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        flSuspension = findViewById(R.id.fl_suspension);
        tvText1 = (TextView) findViewById(R.id.text1);
        setupView();
    }

    int mCurrentPosition;

    public void setupView() {
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                SimpleRecycleAdapter.SuspensionData suspensionData = new SimpleRecycleAdapter.SuspensionData();
                suspensionData.title = "至顶悬浮：" + i;
                dataList.add(suspensionData);
            }
            SimpleRecycleAdapter.ContentData contentData = new SimpleRecycleAdapter.ContentData();
            contentData.title = "内容:" + i;
            dataList.add(contentData);
        }
        SimpleRecycleAdapter.SuspensionData suspensionData = new SimpleRecycleAdapter.SuspensionData();
        suspensionData.title = "至顶悬浮：最后一个";
        dataList.add(suspensionData);
        SimpleRecycleAdapter.ContentData contentData = new SimpleRecycleAdapter.ContentData();
        contentData.title = "内容:最后一个";
        dataList.add(contentData);

        linearLayoutManager = new LinearLayoutManager(this);
        simpleRecycleAdapter = new SimpleRecycleAdapter(this, dataList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(simpleRecycleAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                suspensionHeight = flSuspension.getHeight();
                Log.w("scroll", "suspensionHeight=" + suspensionHeight + " flSuspension.Y=" + flSuspension.getY());
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                if(simpleRecycleAdapter.getItemViewType(firstPosition)==TYPE_SUSPENSION)
                if (simpleRecycleAdapter.getItemViewType(mCurrentPosition + 1) == TYPE_SUSPENSION) {
                    View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                    if (view != null) {
                        Log.w("scroll", "mCurrentPosition=" + mCurrentPosition + " view.getTop=" + view.getTop());
                        //下一个itemSuspension 到顶部的距离小于suspensionBar的高度时,通过setY来移动该View
                        if (view.getTop() <= suspensionHeight) {
                            flSuspension.setY(-(suspensionHeight - view.getTop()));
                        } else {
                            //setY(0)固定该View
                            flSuspension.setY(0);
                        }
                    }
                }

                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    flSuspension.setY(0);
                    updateInfo(mCurrentPosition);
                }
            }
        });
        updateInfo(0);
    }

    private void updateInfo(int position) {

        if (dataList.get(position) instanceof SimpleRecycleAdapter.SuspensionData) {
            SimpleRecycleAdapter.SuspensionData suspensionData = (SimpleRecycleAdapter.SuspensionData) dataList.get(position);
            tvText1.setText(suspensionData.title);
        }
    }
}
