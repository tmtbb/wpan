package com.xinyu.mwp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseFragmentActivity;
import com.xinyu.mwp.fragment.MyPushOrderFragment;
import com.xinyu.mwp.fragment.MyShareOrderFragment;
import com.xinyu.mwp.fragment.ShareOrderPageFragment;
import com.xinyu.mwp.util.DisplayImageOptionsUtil;
import com.xinyu.mwp.util.ImageUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2017/1/11 0011
 * @describe : com.xinyu.mwp.activity
 */
public class UserShareOrderActivity extends BaseFragmentActivity {

    @ViewInject(R.id.headImage)
    private ImageView headImage;
    @ViewInject(R.id.push)
    private TextView push;
    @ViewInject(R.id.attentionIcon)
    private ImageView attentionIcon;
    @ViewInject(R.id.attention)
    private TextView attention;
    @ViewInject(R.id.tabLayout)
    private LinearLayout tabLayout;
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_user_share_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("若忆流年");
        ImageLoader.getInstance().displayImage(ImageUtil.getRandomUrl(), headImage, DisplayImageOptionsUtil.getInstance().getUserHeaderOptions());
        viewPager.setOffscreenPageLimit(tabLayout.getChildCount());
        fragmentList.add(new ShareOrderPageFragment());
        fragmentList.add(new MyShareOrderFragment());
        fragmentList.add(new MyPushOrderFragment());
        tabLayout.getChildAt(0).setSelected(true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList != null ? fragmentList.size() : 0;
            }

            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tabLayout.getChildCount(); i++){
                    tabLayout.getChildAt(i).setSelected(position == i);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Event({R.id.itemLayout1, R.id.itemLayout2, R.id.itemLayout3})
    private void onClick(View view){
        viewPager.setCurrentItem(tabLayout.indexOfChild(view));
    }
}
