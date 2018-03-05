package cn.wang.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAndBottom extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener{
    private Fragment fragment1,fragment2,fragment3,fragment4;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private ViewPager viewPager;

    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private RadioButton rb_message;
    private RadioButton rb_better;
    private RadioButton rb_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom);

        initDatas();
        initViews();
        rb_channel.setChecked(true);
    }

    private void initViews() {
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_message = (RadioButton) findViewById(R.id.rb_message);
        rb_better = (RadioButton) findViewById(R.id.rb_better);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        rg_tab_bar.setOnCheckedChangeListener(this);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        //viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setAdapter(new MyAdapter1(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }

    private void initDatas() {
        fragment1 = Fragment1.newInstance(getResources().getString(R.string.tab_menu_alert),"");
        fragment2 = Fragment1.newInstance(getResources().getString(R.string.tab_menu_message),"");
        fragment3 = Fragment1.newInstance(getResources().getString(R.string.tab_menu_mine),"");
        fragment4 = Fragment1.newInstance(getResources().getString(R.string.tab_menu_setting),"");
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mFragments.add(fragment4);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_channel:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_message:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_better:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rb_setting:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case 0:
                    rb_channel.setChecked(true);
                    break;
                case 1:
                    rb_message.setChecked(true);
                    break;
                case 2:
                    rb_better.setChecked(true);
                    break;
                case 3:
                    rb_setting.setChecked(true);
                    break;
            }
        }
    }


    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
    class MyAdapter1 extends FragmentStatePagerAdapter{

        public MyAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
