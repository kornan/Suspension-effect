package com.flobberworm.demo.parallax;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.flobberworm.demo.R;
import com.flobberworm.demo.utils.Cubic;
import com.flobberworm.demo.utils.Sine;

public class ParallaxActivity extends AppCompatActivity {
    private ViewPager viewPager;
    FrameLayout backgroundLayout;
    HorizontalScrollView background_srcollview;
    HorizontalScrollView layer_srcollview;
    private ParallaxAdapter adapter;
    int total_page;
    int backgoundWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_parallax);

        initView();
        initAdapter();
        setViewPagerParams();
    }

    private void setViewPagerParams() {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                total_page = adapter.getCount();

                float realOffset = Cubic.easeIn(positionOffset, 0, 1, 1);
                float offset = (float) ((float) (position + realOffset) * 1.0 / total_page);
                int offsetPosition = (int) (backgoundWidth * offset);
                background_srcollview.scrollTo(offsetPosition,0);

                float layerRealOffset = Sine.easeIn(positionOffset, 0, 1, 1);
                float layerOffset = (float) ((float) (position + layerRealOffset) * 1.0 / total_page);
                int layerOffsetPosition = (int) (backgoundWidth * layerOffset);
                layer_srcollview.scrollTo(layerOffsetPosition, 0);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initAdapter() {
        adapter = new ParallaxAdapter(this);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.image_pager);
        backgroundLayout = (FrameLayout) findViewById(R.id.backgroundLayout);
        background_srcollview = (HorizontalScrollView) findViewById(R.id.backgroundScrollView);
        background_srcollview.setHorizontalScrollBarEnabled(false);
        layer_srcollview = (HorizontalScrollView) findViewById(R.id.layerScrollView);
        layer_srcollview.setHorizontalScrollBarEnabled(false);
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        backgoundWidth = dm.widthPixels * 5;
        ViewGroup.LayoutParams layoutParams;

//        ImageView back_image_one = (ImageView) findViewById(R.id.back_image_one);
//        layoutParams = back_image_one.getLayoutParams();
//        layoutParams.height = dm.heightPixels;
//        layoutParams.width = dm.widthPixels;
//        back_image_one.setLayoutParams(layoutParams);
//
//        FrameLayout.LayoutParams frameLayoutParams;
//        ImageView layer_image_one = (ImageView) findViewById(R.id.layer_image_one);
//        frameLayoutParams = (FrameLayout.LayoutParams) layer_image_one.getLayoutParams();
//        frameLayoutParams.height = dm.heightPixels;
//        frameLayoutParams.width = dm.widthPixels;
//        layer_image_one.setLayoutParams(frameLayoutParams);
//
//        ImageView layer_image_two = (ImageView) findViewById(R.id.layer_image_two);
//        frameLayoutParams = (FrameLayout.LayoutParams) layer_image_two.getLayoutParams();
//        frameLayoutParams.height = dm.heightPixels;
//        frameLayoutParams.width = dm.widthPixels;
//        layer_image_two.setLayoutParams(frameLayoutParams);
//
//        ImageView layer_image_three = (ImageView) findViewById(R.id.layer_image_three);
//        frameLayoutParams = (FrameLayout.LayoutParams) layer_image_three.getLayoutParams();
//        frameLayoutParams.height = dm.heightPixels;
//        frameLayoutParams.width = dm.widthPixels;
//        layer_image_three.setLayoutParams(frameLayoutParams);
//
//        ImageView layer_image_four = (ImageView) findViewById(R.id.layer_image_four);
//        frameLayoutParams = (FrameLayout.LayoutParams) layer_image_four.getLayoutParams();
//        frameLayoutParams.height = dm.heightPixels;
//        frameLayoutParams.width = dm.widthPixels;
//        layer_image_four.setLayoutParams(frameLayoutParams);
//
//        ImageView layer_image_five = (ImageView) findViewById(R.id.layer_image_five);
//        frameLayoutParams = (FrameLayout.LayoutParams) layer_image_five.getLayoutParams();
//        frameLayoutParams.height = dm.heightPixels;
//        frameLayoutParams.width = dm.widthPixels;
//        layer_image_five.setLayoutParams(frameLayoutParams);
    }

}
