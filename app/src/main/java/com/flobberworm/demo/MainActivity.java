package com.flobberworm.demo;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private PullToRefreshListView pullToRefreshListView;
    private List<String> dataList = new ArrayList<>();

    private FilterAdapter filterAdapter;

    private View headerView;
    private View headerView2;
    private View filterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        filterView = findViewById(R.id.filterView);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);

        //第一个headerView 这里加载美女图片的地方
        headerView = LayoutInflater.from(this).inflate(R.layout.item_head_filter, null);
        //setLayoutParams防止PullToRefreshListView报Caused by: java.lang.ClassCastException
        AbsListView.LayoutParams headerLayoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(headerLayoutParams);

        //第二个headerView 这里到顶部后
        AbsListView.LayoutParams rgLayoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headerView2 = LayoutInflater.from(this).inflate(R.layout.layout_filter, null);
        headerView2.setLayoutParams(rgLayoutParams);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ListView tempView = pullToRefreshListView.getRefreshableView();
                //添加HeaderView
                tempView.addHeaderView(headerView);
                tempView.addHeaderView(headerView2);
            }
        }, 0);


        pullToRefreshListView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 第二个headerView 到顶部时  悬浮tab出现
                if (firstVisibleItem < 2) {
                    filterView.setVisibility(View.GONE);
                } else {
                    filterView.setVisibility(View.VISIBLE);
                }
            }
        });
        for (int i = 0; i < 20; i++) {
            dataList.add("标题:" + i);
        }
        filterAdapter = new FilterAdapter(this, dataList);
        ListView actualListView = pullToRefreshListView.getRefreshableView();
        actualListView.setAdapter(filterAdapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    class FilterAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<String> dataList;

        public FilterAdapter(Context context, List<String> dataList) {
            this.mInflater = LayoutInflater.from(context);
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(dataList.get(position));
            return convertView;
        }
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
