package cn.wang.viewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private ArrayList<View> viewList;
    private View view1,view2,view3;
    //底部小点的图片
    private ImageView[] points;

    //记录当前选中位置
    private int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();
    }
    //初始化数据
    private void initDatas() {
        LayoutInflater lf = LayoutInflater.from(this); //getLayoutInflater().from(this);
        view1 = lf.inflate(R.layout.layout1, null);
        view2 = lf.inflate(R.layout.layout2, null);
        view3 = lf.inflate(R.layout.layout3, null);
        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
    }
    //初始化View组件
    private void initViews() {
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPageAdapter());
        viewPager.setPageMargin(20);
        //添加滑动事件处理，在此主要用来在滑动时切换圆点的状态
        viewPager.addOnPageChangeListener(this);
//        viewPager.setPageTransformer(true,new RotatePageTransformer());
//        viewPager.setPageTransformer(true,new DepthPageTransformer());
        viewPager.setPageTransformer(true,new ZoomOutPageTransformer());
//        initPoint();

//        Timer timer = new Timer();
//        TimerTask timeTask = new TimerTask(){
//            @Override
//            public void run(){
//                runOnUiThread(new Runnable(){
//                    @Override
//                    public void run(){
//                        int index = viewPager.getCurrentItem();
//                        //ViewPageSize代表你的ViewPage所有Pager的页码数量
//                        index = (1 + index) % viewList.size();
//                        viewPager.setCurrentItem(index, true);
//                    }
//                });
//            }
//        };
//        //设置滚动时间
//        timer.schedule(timeTask , 1000, 1000);
    }
    /**
     * 初始化底部小点
     */
    private void initPoint(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);

        points = new ImageView[viewList.size()];
        //循环取得小点图片
        for (int i = 0; i < viewList.size(); i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            //为小圆点左右添加间距
            params.leftMargin = 10;
            params.rightMargin = 10;

            imageView.setBackgroundResource(R.drawable.point);
            //为LinearLayout添加ImageView
            linearLayout.addView(imageView, params);

            //得到一个LinearLayout下面的每一个子元素
            points[i] = imageView;
            //默认都设为灰色
            points[i].setEnabled(true);
            //给每个小点设置监听
            points[i].setOnClickListener(this);
            //设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
        }

        //设置当面默认的位置
        currentIndex = 0;
        //设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
    }
    /**
     * 通过点击事件来切换当前的页面
     */
    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position){
        if (position < 0 || position >= viewList.size()) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon){
        if (positon < 0 || positon > viewList.size() - 1 || currentIndex == positon) {
            return;
        }
//        points[positon].setEnabled(false);
//        points[currentIndex].setEnabled(true);

        currentIndex = positon;
    }
    /**
     * 滑动监听器OnPageChangeListener
     *  OnPageChangeListener这个接口需要实现三个方法：onPageScrollStateChanged，onPageScrolled ，
     *  onPageSelected
     *      1、onPageScrollStateChanged(int state) 此方法是在状态改变的时候调用。
     *          其中state这个参数有三种状态（0，1，2）
     *              state ==1的时表示正在滑动，state==2的时表示滑动完毕了，state==0的时表示什么都没做
     *              当页面开始滑动的时候，三种状态的变化顺序为1-->2-->0
     *      2、onPageScrolled(int position,float positionOffset,int positionOffsetPixels) 当页面在
     *      滑动的时候会调用此方法，在滑动被停止之前，此方法回一直被调用。
     *          其中三个参数的含义分别为：
     *              position :当前页面，及你点击滑动的页面
     *              positionOffset:当前页面偏移的百分比
     *              positionOffsetPixels:当前页面偏移的像素位置
     *      3、onPageSelected(int position) 此方法是页面跳转完后被调用，arg0是你当前选中的页面的
     *      Position（位置编号）
     */
    /**
     * 当当前页面被滑动时调用
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    /**
     * 当新的页面被选中时调用
     */
    @Override
    public void onPageSelected(int position) {
        //设置底部小点选中状态
        setCurDot(position);
    }
    /**
     * 当滑动状态改变时调用
     */
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    class MyPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewList.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;//官方提示这样写
        }
        //instantiateItem该方法的功能是创建指定位置的页面视图。finishUpdate(ViewGroup)返回前，页面应该保证被构造好
        //返回值：返回一个对应该页面的object，这个不一定必须是View，但是应该是对应页面的一些其他容器
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position), 0);//添加页卡
            return viewList.get(position);
        }
        //该方法的功能是移除一个给定位置的页面。
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));//删除页卡
        }
    }

    class RotatePageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_ROTATION=20.0f;
        /**
         * position取值特点：
         * 假设页面从0～1，则：
         * 第一个页面position变化为[0,-1]
         * 第二个页面position变化为[1,0]
         *
         * @param page
         * @param position
         */
        @Override
        public void transformPage(View page, float position) {
            System.out.println("-----position:"+position);
            if(position<-1) {
                rotate(page, -MAX_ROTATION);
            }else if(position<=1){
                rotate(page, MAX_ROTATION*position);
            }else {
                rotate(page, MAX_ROTATION);
            }
        }

        private void rotate(View view, float rotation) {
            view.setPivotX(view.getWidth()*0.5f);
            view.setPivotY(view.getHeight());
            view.setRotation(rotation);
        }

    }

    class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) {
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) {
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) {
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1)
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

}
