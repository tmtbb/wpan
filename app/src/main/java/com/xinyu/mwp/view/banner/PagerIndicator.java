package com.xinyu.mwp.view.banner;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-10-06 14:30
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public interface PagerIndicator {

    void setNum(int num, int index);

    int getTotal();

    void setSelected(int index);

    int getCurrentIndex();

    void setHidden(boolean hidden);
}
