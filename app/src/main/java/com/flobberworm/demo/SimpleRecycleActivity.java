package com.flobberworm.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecycleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SimpleRecycleAdapter simpleRecycleAdapter;
    private List<String> dataList = new ArrayList<>();
    private List<SectionDecoration.NameBean> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupView();
    }

    public void setupView() {
        for (int i = 0; i < 20; i++) {

            if (i % 5 == 0) {
                SectionDecoration.NameBean nameBean = new SectionDecoration.NameBean();
                nameBean.setName("标题:" + i);
                titleList.add(nameBean);
            }
            dataList.add("内容:" + i);
        }
        linearLayoutManager = new LinearLayoutManager(this);
        simpleRecycleAdapter = new SimpleRecycleAdapter(this, dataList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(simpleRecycleAdapter);

        recyclerView.addItemDecoration(new SectionDecoration(titleList, this, new SectionDecoration.DecorationCallback() {
            //返回标记id (即每一项对应的标志性的字符串)
            @Override
            public String getGroupId(int position) {
                if (titleList.get(position).getName() != null) {
                    return titleList.get(position).getName();
                }
                return "-1";
            }

            //获取同组中的第一个内容
            @Override
            public String getGroupFirstLine(int position) {
                if (titleList.get(position).getName() != null) {
                    return titleList.get(position).getName();
                }
                return "";
            }
        }));
    }

    class SimpleRecycleAdapter extends RecyclerView.Adapter<SimpleRecycleViewHolder> {
        private LayoutInflater mInflater;
        private List<String> dataList;

        public SimpleRecycleAdapter(Context context, List<String> dataList) {
            this.mInflater = LayoutInflater.from(context);
            this.dataList = dataList;
        }

        @Override
        public SimpleRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleRecycleViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(SimpleRecycleViewHolder holder, int position) {
            holder.bindView(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

    }

    class SimpleRecycleViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public SimpleRecycleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bindView(String title) {
            textView.setText(title);
        }
    }
}
