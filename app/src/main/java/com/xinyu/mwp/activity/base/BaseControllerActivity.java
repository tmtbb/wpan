package com.xinyu.mwp.activity.base;


import com.xinyu.mwp.manage.IifecycleManage;
import com.xinyu.mwp.util.StringUtil;

import org.xutils.x;

/**
 * 可注册多控制器Activity
 * Created by yaowang on 16/3/31.
 */
public abstract class BaseControllerActivity extends BaseActivity {

    protected IifecycleManage controllers = new IifecycleManage();

    /**
     * 注册控制器
     *
     * @param key        控制器key
     * @param controller
     * @param isInject   是否注册 view 或 事件
     */
    protected void registerController(String key, BaseController controller, boolean isInject) {
        if (!StringUtil.isEmpty(key) && controller != null) {
            controllers.register(key, controller);
            if (isInject) {
                x.view().inject(controller, rootView);
            }
        }
    }


    protected  void registerController(BaseController controller, boolean isInject) {
        if( controller != null )
        {
            registerController(controller.getClass().getSimpleName(),controller,isInject);
        }
    }


    /**
     * 获取注册的控制器
     *
     * @param key
     * @return
     */
    public <Controller extends BaseController> Controller getController(String key) {
        return (Controller) controllers.get(key);
    }

    @Override
    protected void onInit() {
        super.onInit();
        onRegisterController();
    }

    /**
     * 开始注册控制器
     */
    protected void onRegisterController() {

    }


    @Override
    protected void initView() {
        super.initView();
        controllers.initView();
    }

    @Override
    protected void initListener() {
        super.initListener();
        controllers.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
        controllers.initData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        controllers.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();
        controllers.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        controllers.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        controllers.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controllers.onDestroy();
    }
}
