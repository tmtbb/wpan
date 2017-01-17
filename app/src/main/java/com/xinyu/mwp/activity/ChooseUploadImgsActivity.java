package com.xinyu.mwp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseFragmentActivity;
import com.xinyu.mwp.adapter.ChooseUploadImgsAdapter;
import com.xinyu.mwp.adapter.ChooseUploadImgsPageAdapter;
import com.xinyu.mwp.application.MyApplication;
import com.xinyu.mwp.constant.ActionConstant;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.ImageBucket;
import com.xinyu.mwp.entity.ImageItem;
import com.xinyu.mwp.helper.AlbumHelper;
import com.xinyu.mwp.util.ActivityUtil;
import com.xinyu.mwp.util.CameraTools;
import com.xinyu.mwp.util.FileCacheUtil;
import com.xinyu.mwp.util.SPUtils;
import com.xinyu.mwp.util.ToastUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author : Created by xiepeng
 * @email : xiepeng2015929@gmail.com
 * @created time : 2016/3/16 0016
 * @describe : ChooseImgsActivity
 */
public class ChooseUploadImgsActivity extends BaseFragmentActivity implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.gv_imgs)
    private GridView gv_imgs;
    @ViewInject(R.id.vp_scan)
    private ViewPager vp_scan;
    private ArrayList<ImageItem> dataList;
    private AlbumHelper helper;
    private List<ImageBucket> contentList;
    private ChooseUploadImgsAdapter gridAdapter;
    private ChooseUploadImgsPageAdapter pageAdapter;
    private Uri origUri;
    private int pos = -1;

    private final String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";

    @Override
    protected int getLayoutID() {
        return R.layout.activity_chooseimgs;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("选择图片");
        rightText.setText("确定");

    }

    @Override
    protected void initData() {
        super.initData();
        helper = new AlbumHelper();
        helper.init(getApplicationContext());
        contentList = helper.getImagesBucketList(false);
        dataList = new ArrayList<ImageItem>();
        ArrayList<ImageItem> noCameraList = new ArrayList<>();
//        ImageItem imageItem = new ImageItem();
//        imageItem.setImagePath("-1");
//        dataList.add(imageItem);
        for (int i = 0; i < contentList.size(); i++) {
            dataList.addAll(contentList.get(i).imageList);
        }
        Collections.sort(dataList, new Comparator<ImageItem>() {
            @Override
            public int compare(ImageItem lhs, ImageItem rhs) {
                if (lhs.getModifyTime() == rhs.getModifyTime()) {
                    return 0;
                }
                return lhs.getModifyTime() < rhs.getModifyTime() ? 1 : -1;
            }
        });
        noCameraList.addAll(dataList);
        ImageItem imageItem = new ImageItem();
        imageItem.setImagePath("-1");
        dataList.add(0, imageItem);
        gridAdapter = new ChooseUploadImgsAdapter(this, dataList);
        gv_imgs.setAdapter(gridAdapter);

        pageAdapter = new ChooseUploadImgsPageAdapter(getSupportFragmentManager(), noCameraList);
        vp_scan.setAdapter(pageAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });
        gv_imgs.setOnItemClickListener(this);
        vp_scan.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            doTakePhoto();
        } else {
            scanImage(position);
        }
    }

    /**
     * 拍照
     */
    private void doTakePhoto() {
        if (!CameraTools.isCameraCanUse()) {
            ToastUtils.show(this, "相机不可用");
            return;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String origFileName = "osc_" + timeStamp + ".jpg";
        //有的手机拍照会将origUri清掉，这里先保存下路径
        SPUtils.putString(ChooseUploadImgsActivity.class.getName(), origFileName);
        origUri = Uri.fromFile(new File(FILE_SAVEPATH, origFileName));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, origUri);
        try {
            //部分手机会抛
            //Fatal Exception: android.content.ActivityNotFoundException
            //No Activity found to handle Intent { act=android.media.action.IMAGE_CAPTURE (has extras) }
            startActivityForResult(intent, ActionConstant.Code.CHOOSE_IMGS_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 预览图片
     *
     * @param position
     */
    private void scanImage(int position) {
        if (position > 0) {
            pos = position - 1;
        }
        rightText.setVisibility(View.VISIBLE);
        vp_scan.setVisibility(View.VISIBLE);
        gv_imgs.setVisibility(View.GONE);
        vp_scan.setCurrentItem(pos, false);

        pageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActionConstant.Code.CHOOSE_IMGS_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        if (origUri == null) {//重新判断下空，用上次保存的地址再次创建
                            origUri = Uri.fromFile(new File(FILE_SAVEPATH
                                    , SPUtils.getString(ChooseUploadImgsActivity.class.getName()
                                    , "osc_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg")));

                        }
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + origUri.getPath())));
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setImagePath(origUri.getPath());
                        dataList.add(1, takePhoto);
                        gridAdapter.notifyDataSetChanged();

                        pageAdapter.getImageItems().add(0, takePhoto);
                        pageAdapter.notifyDataSetChanged();
                        scanImage(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        if (requestCode == ActionConstant.Code.IMAGE_CROP) {
            if (data == null)
                return;
            String filePath = getFilePath(data);
            if (filePath == null) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(Constant.IntentKey.CHOOSE_IMGS_RES, TextUtils.isEmpty(filePath) ?
                    pageAdapter.getImageItems().get(pos).getImagePath()
                    : filePath);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @NonNull
    private String getFilePath(Intent data) {

        Bitmap bmap = data.getParcelableExtra("data");
        if (bmap == null)
            return null;
        FileOutputStream foutput = null;
        String filePath = null;
        try {
            filePath = FileCacheUtil.getInstance().getCacheDir(MyApplication.getApplication(), null) + "/" + UUID.randomUUID().toString() + ".jpg";
            foutput = new FileOutputStream(filePath);
            bmap.compress(Bitmap.CompressFormat.PNG, 100, foutput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != foutput) {
                try {
                    foutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }

    @Event(value = {R.id.rightText})
    private void onClick(View view) {
        String imagePath = pageAdapter.getImageItems().get(pos).getImagePath();
        if (imagePath != null)
            ActivityUtil.nextImageCropForResult(this, imagePath);

    }

    /**
     * 点击返回图标或返回键
     */
    private void doBack() {
        if (vp_scan.getVisibility() == View.VISIBLE) {
            rightText.setVisibility(View.GONE);
            gv_imgs.setVisibility(View.VISIBLE);
            vp_scan.setVisibility(View.GONE);
            gridAdapter.notifyDataSetChanged();
        } else {
            setResult(ActionConstant.Code.CHOOSE_IMGS_CODE_CANCEL);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        doBack();
    }
}
