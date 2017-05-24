package com.flobberworm.demo.parallax;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.flobberworm.demo.R;
import com.flobberworm.demo.utils.Cubic;
import com.flobberworm.demo.utils.Sine;

public class ParallaxActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private HorizontalScrollView backgroundScrollview;
    private HorizontalScrollView layerScrollview;
    private ParallaxAdapter adapter;
    private int pageCount;
    private int backgroundWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        setupView();
    }


    private void setupView() {
        backgroundScrollview = (HorizontalScrollView) findViewById(R.id.backgroundScrollView);
        layerScrollview = (HorizontalScrollView) findViewById(R.id.layerScrollView);
        viewPager = (ViewPager) findViewById(R.id.image_pager);

        backgroundScrollview.setHorizontalScrollBarEnabled(false);
        layerScrollview.setHorizontalScrollBarEnabled(false);

        setSecondPixels();
        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new ParallaxAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pageCount = adapter.getCount();
                //缓动算法(学习中) 参考http://easings.net/zh-cn

                //背景滚动
                float backgroundOffset = Cubic.easeIn(positionOffset, 0, 1, 1);
                float offset = (float) ((position + backgroundOffset) * 1.0 / pageCount);
                int backgroundX = (int) (backgroundWidth * offset);
                backgroundScrollview.scrollTo(backgroundX, 0);

                //layer滚动
                float layerRealOffset = Sine.easeIn(positionOffset, 0, 1, 1);
                float layerOffset = (float) ((position + layerRealOffset) * 1.0 / pageCount);
                int layerX = (int) (backgroundWidth * layerOffset);
                layerScrollview.scrollTo(layerX, 0);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 解决HorizontalScrollView中match_parent无效问题
     * 将第2层的总宽度与viewPager的总宽度保持一致，每一个view的宽高对应手机的宽高
     */
    private void setSecondPixels() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        backgroundWidth = dm.widthPixels * 5;
        ViewGroup.LayoutParams layoutParams;

        View view1 = findViewById(R.id.view_1);
        layoutParams = view1.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        view1.setLayoutParams(layoutParams);

        View view2 = findViewById(R.id.view_2);
        layoutParams = view1.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        view2.setLayoutParams(layoutParams);

        View view3 = findViewById(R.id.view_3);
        layoutParams = view1.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        view3.setLayoutParams(layoutParams);

        View view4 = findViewById(R.id.view_4);
        layoutParams = view1.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        view4.setLayoutParams(layoutParams);

        View view5 = findViewById(R.id.view_5);
        layoutParams = view1.getLayoutParams();
        layoutParams.height = dm.heightPixels;
        layoutParams.width = dm.widthPixels;
        view5.setLayoutParams(layoutParams);
    }

}
