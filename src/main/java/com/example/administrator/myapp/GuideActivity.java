package com.example.administrator.myapp;

/**
 * Created by Administrator on 2018/10/18.
 */

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

    /**
     * 1、实现ViewPager
     * 2、实现小圆点
     * 3、小圆点展示对应页
     */
    public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

        protected ViewPager mGuideViewPager;

        // Fragment集合
        private List<GuideFragment> mFragmentList;
        // 小圆点集合
        private List<ImageView> mPointList;

        // 3个图片id
        private int[] imgIds = new int[]
                {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            super.setContentView(R.layout.activity_guide);
            initView();
        }

        private void initView() {
            mGuideViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
            LinearLayout pointContainer = (LinearLayout) findViewById(R.id.point_container);

            mFragmentList = new ArrayList<>();
            mPointList = new ArrayList<>();

            // 布局参数，可以指定宽高、权重等信息
            // 视图所在容器是什么，就选择对应的LayoutParams
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            for (int imgId : imgIds) {// 3
                // 实例化Fragment
                GuideFragment guideFragment = GuideFragment.newInstance(imgId);
                mFragmentList.add(guideFragment);

                // 实例化小圆点
                ImageView pointImageView = new ImageView(this);
                pointImageView.setImageResource(R.drawable.guide1);
                // 给子控件设置布局参数
                pointImageView.setLayoutParams(params);
                // 向容器中添加子视图：小圆点
                pointContainer.addView(pointImageView);
                mPointList.add(pointImageView);
            }
            // 初始化第一个小圆点为选中状态，即白色
            mPointList.get(0).setSelected(true);
            // 最后一个碎片设置按钮可见
            mFragmentList.get(mFragmentList.size() - 1).setShowBtn(true);

            GuidePagerAdapter adapter =
                    new GuidePagerAdapter(getSupportFragmentManager(), mFragmentList);

            mGuideViewPager.setAdapter(adapter);
            mGuideViewPager.addOnPageChangeListener(this);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 遍历全部小圆点，当前展示页对应的点设置为白色，其他圆点一律设置为灰色
            for (int i = 0; i < mPointList.size(); i++) {
                mPointList.get(i).setSelected(i == position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
