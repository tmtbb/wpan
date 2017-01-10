package com.xinyu.mwp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinyu.mwp.R;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-07-07 15:00
 * @describe : LoaderLayout
 * @for your attention : none
 * @revise : none
 */
public class LoaderLayout extends BaseFrameLayout {

    protected ProgressBar progressBar;
    @ViewInject(R.id.msg_content)
    protected TextView msgContent;

    public LoaderLayout(Context context) {
        super(context);
    }

    public LoaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @OnClick(R.id.loader)
//    private void onClick(View view) {
//
//    }

    @Override
    protected int layoutId() {
        return R.layout.ly_comm_loader;
    }

    public void setMsgContent(int resId) {
        msgContent.setText(resId);
    }

    public void setMsgContent(String msgContent) {
        this.msgContent.setText(msgContent);
    }

}
