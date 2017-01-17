package com.xinyu.mwp.fragment;

import android.widget.ImageView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.ImageItem;
import com.xinyu.mwp.fragment.base.BaseFragment;
import com.xinyu.mwp.util.MyImageUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/3/17 0017
 * @describe : ChooseImgsScanFragment
 */
public class ChooseUploadImgsScanFragment extends BaseFragment {

    @ViewInject(R.id.imv_img)
    private ImageView imv_img;
    private ImageItem imageItem;

    @Override
    protected int getLayoutID() {
        return R.layout.item_chooseuploadimgs;
    }

    @Override
    protected void initData() {
        super.initData();
        imageItem = (ImageItem) getArguments().getSerializable(Constant.IntentKey.CHOOSE_IMGS_SCAN);
        MyImageUtils.loadImage(imv_img, "file://" + imageItem.getImagePath());
    }
}
