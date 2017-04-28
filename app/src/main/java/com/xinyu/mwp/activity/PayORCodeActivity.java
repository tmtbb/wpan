package com.xinyu.mwp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.activity.base.BaseControllerActivity;
import com.xinyu.mwp.constant.Constant;
import com.xinyu.mwp.entity.EventBusMessage;
import com.xinyu.mwp.entity.UnionPayReturnEntity;
import com.xinyu.mwp.util.LogUtil;
import com.xinyu.mwp.util.QRCodeUtil;
import com.xinyu.mwp.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/28.
 */
public class PayORCodeActivity extends BaseControllerActivity {
    @ViewInject(R.id.iv_orcode)
    ImageView orcodeImg;
    private String paymentInfo = null;
    private Bitmap bitmap = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_orcode;
    }

    @Override
    protected void initView() {
        super.initView();
        String title = "二维码付款";
        Bundle bundle = getIntent().getBundleExtra("pay");
        UnionPayReturnEntity entity = (UnionPayReturnEntity) bundle.getSerializable("payment");
        if (entity != null) {
            if (entity.getPayType().equals(Constant.payType.WECHAT_QRCODE_PAY)) {
                title = "微信扫码付款";
            } else if (entity.getPayType().equals(Constant.payType.ALIPAY_QRCODE_PAY)) {
                title = "支付宝扫码付款";
            }

            paymentInfo = entity.getPaymentInfo();
            bitmap = QRCodeUtil.createQRCode(paymentInfo, 500);
            orcodeImg.setImageBitmap(bitmap);
        }
        setTitle(title);
    }

    @Event(value = {R.id.btn_save_orcode})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.btn_save_orcode:
                if (bitmap == null) {
                    return;
                }
                saveImageToGallery(context, bitmap);
                break;
        }
    }

    /**
     * 保存图片到图库
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "WeiPan");   // 首先保存图片
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ToastUtils.show(context, "二维码已保存至:" + Environment.getExternalStorageDirectory() + "/CoolImage/" + "目录文件夹下");
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }


    @Override
    protected void onDestroy() {
        LogUtil.d("发送消息,关闭页面");
        EventBus.getDefault().postSticky(new EventBusMessage(-10));    //发送广播
        super.onDestroy();
    }
}
