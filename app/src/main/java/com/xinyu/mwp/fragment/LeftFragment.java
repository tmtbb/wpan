package com.xinyu.mwp.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.fragment.base.BaseControllerFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Benjamin on 17/1/10.
 */

public class LeftFragment extends BaseControllerFragment {
    @ViewInject(R.id.icon)
    private ImageView icon;
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.unLoginLayout)
    private View unLoginLayout;
    @ViewInject(R.id.assetsCount)
    private TextView assetsCount;
    @ViewInject(R.id.assetsText)
    private TextView assetsText;
    @ViewInject(R.id.scoreCount)
    private TextView scoreCount;
    @ViewInject(R.id.scoreText)
    private TextView scoreText;

    private LeftClickListener leftClickListener;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_left;
    }

    @Event(value = {R.id.icon, R.id.login, R.id.register
            , R.id.myAssetsLayout, R.id.myScoreLayout
            , R.id.myAttention, R.id.myPushOrder, R.id.myShareOrder, R.id.dealDetail
            , R.id.feedback, R.id.score, R.id.about})
    private void click(View v) {
        if (null != leftClickListener) {
            leftClickListener.click(v, v.getId(), null);
        }
    }

    public void update() {

    }

    public void userUpdate(boolean isLogin) {

    }

    public void setLeftClickListener(LeftClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
    }

    public interface LeftClickListener {
        void click(View v, int action, Object obj);
    }
}
