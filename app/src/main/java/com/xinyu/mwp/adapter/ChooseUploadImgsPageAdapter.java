package com.xinyu.mwp.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.ImageItem;
import com.xinyu.mwp.fragment.ChooseUploadImgsScanFragment;

import java.util.List;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/3/17 0017
 * @describe : ChooseImgsPageAdapter
 */
public class ChooseUploadImgsPageAdapter extends FragmentStatePagerAdapter {

    private List<ImageItem> imageItems;

    public List<ImageItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(List<ImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public ChooseUploadImgsPageAdapter(FragmentManager fm, List<ImageItem> imageItems) {
        super(fm);
        this.imageItems = imageItems;
    }

    @Override
    public Fragment getItem(int position) {
        ChooseUploadImgsScanFragment fragment = new ChooseUploadImgsScanFragment();
        Bundle b = new Bundle();
        b.putSerializable(Constant.IntentKey.CHOOSE_IMGS_SCAN, imageItems.get(position));
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public int getCount() {
        return imageItems != null && imageItems.size() > 0 ? imageItems.size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
