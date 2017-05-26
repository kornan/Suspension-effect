package com.flobberworm.demo.recycle;

import android.support.v4.widget.SwipeRefreshLayout;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private SimpleRecycleAdapter simpleRecycleAdapter;
    private List<SimpleRecycleAdapter.ItemData> dataList = new ArrayList<>();
    private int suspensionHeight;
    private int mCurrentPosition;
    private String name[] = {
            "缅因州 Maine",
            "纽约州 New York",
            "犹他州 Utah",
            "内华达州 Nevada",
            "田纳西州 Tennessee",
            "伊利诺州 Illinois",
            "佛蒙特州 Vermont",
            "肯塔基州 Kentucky",
            "阿肯色州 Arkansas",
            "俄亥俄州 Ohio",
            "夏威夷州 Hawaii",
            "马里兰州 Maryland",
            "密西根州 Michigan",
            "密苏里州 Missouri",
            "乔治亚州 Georgia",
            "堪萨斯州 Kansas",
            "华盛顿州 Washington",
            "奥勒冈州 Oregon",
            "爱荷华州 Iowa",
            "爱达荷州 Idaho",
            "国际组织 (1)",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycle);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        flSuspension = findViewById(R.id.fl_suspension);
        tvText1 = (TextView) findViewById(R.id.text1);
        setupData();
        setupView();
    }

    /**
     * simple数据
     */
    private void setupData() {
        for (int i = 0; i < name.length; i++) {
            SimpleRecycleAdapter.SuspensionData suspensionData = new SimpleRecycleAdapter.SuspensionData();
            suspensionData.title = name[i];
            dataList.add(suspensionData);
            SimpleRecycleAdapter.ContentData contentData = new SimpleRecycleAdapter.ContentData();
            if (i % 2 == 1) {
                contentData.imageUrl = "http://img.ivsky.com/img/tupian/pre/201611/02/yangmingshan_guojiasenlin_gongyuan-003.jpg";
            } else {
                contentData.imageUrl = "http://img.ivsky.com/img/tupian/pre/201611/02/yangmingshan_guojiasenlin_gongyuan-002.jpg";
            }
            dataList.add(contentData);
            contentData = new SimpleRecycleAdapter.ContentData();
            if (i % 2 == 0) {
                contentData.imageUrl = "http://img.ivsky.com/img/tupian/pre/201611/02/yangmingshan_guojiasenlin_gongyuan-003.jpg";
            } else {
                contentData.imageUrl = "http://img.ivsky.com/img/tupian/pre/201611/02/yangmingshan_guojiasenlin_gongyuan-002.jpg";
            }
            dataList.add(contentData);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void setupView() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        linearLayoutManager = new LinearLayoutManager(this);
        simpleRecycleAdapter = new SimpleRecycleAdapter(this, dataList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(simpleRecycleAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //获取悬浮框的高度(在onScrolled之前获取均可)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    suspensionHeight = flSuspension.getHeight();
                }
                Log.w("position", "newState=" + newState);
            }

            //当前状态
            private int status = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int position = firstPosition + 1;
                if (position >= simpleRecycleAdapter.getItemCount()) {
                    return;
                }
                //当前界面第一个View的类型是否悬浮类型
                if (simpleRecycleAdapter.getItemViewType(position) == TYPE_SUSPENSION) {
                    View view = linearLayoutManager.findViewByPosition(position);
                    if (view != null) {
//                        下一个itemSuspension 到顶部的距离小于suspensionBar的高度时,通过setY来移动该View
                        if (view.getTop() <= suspensionHeight) {
                            flSuspension.setY(-(suspensionHeight - view.getTop()));
                            Log.w("position", "getY=" + flSuspension.getY() + " position=" + position + " dy=" + dy);
                        } else {
                            //setY(0)固定悬浮View在顶部
                            flSuspension.setY(0);
                            Log.w("position", "悬浮View在顶部" + flSuspension.getY() + " position=" + position + " dy=" + dy);
                        }

                        if (dy > 0 && view.getTop() <= suspensionHeight) {
                            //上滑
//                            updateInfo(position);
                        } else if (dy < 0 && view.getTop() <= suspensionHeight) {
                            //下滑
                            for (int i = firstPosition; i >= 0; i--) {
                                if (simpleRecycleAdapter.getItemViewType(i) == TYPE_SUSPENSION) {
                                    updateInfo(i);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    flSuspension.setY(0);
//                    updateInfo(position);
                }

//                updateInfo(position);
                //RecycleView至顶时隐藏掉悬浮View
//                flSuspension.setVisibility(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 ? View.GONE : View.VISIBLE);
//                首个
//                当滚动到要悬浮的item
//                if (simpleRecycleAdapter.getItemViewType(mCurrentPosition + 1) == TYPE_SUSPENSION) {
//                    View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
//                    if (view != null) {
//                        //下一个itemSuspension 到顶部的距离小于suspensionBar的高度时,通过setY来移动该View
//                        if (view.getTop() <= suspensionHeight) {
//                            flSuspension.setY(-(suspensionHeight - view.getTop()));
//                        } else {
//                            //setY(0)固定悬浮View
//                            flSuspension.setY(0);
//                        }
//                    }
//                }
//                //RecycleView至顶时隐藏掉悬浮View
//                flSuspension.setVisibility(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 ? View.GONE : View.VISIBLE);
//                //
//                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
//                    //记录第一个item的位置
//                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                    Log.w("position", "position=" + mCurrentPosition + " data=" + dataList.get(mCurrentPosition));
//                    flSuspension.setY(0);
//                    updateInfo(mCurrentPosition);
//                }
            }
        });
        updateInfo(0);
    }

    /**
     * 更新悬浮UI
     *
     * @param position
     */
    private void updateInfo(int position) {
        if (dataList.get(position) instanceof SimpleRecycleAdapter.SuspensionData) {
            SimpleRecycleAdapter.SuspensionData suspensionData = (SimpleRecycleAdapter.SuspensionData) dataList.get(position);
            tvText1.setText(suspensionData.title);
        }
    }
}
