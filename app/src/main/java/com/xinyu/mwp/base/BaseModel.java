package com.xinyu.mwp.base;

import java.io.Serializable;

/**
 * Created by Don on 2016/11/11 18:21.
 * Describe：${基础Model}
 * Modified：${TODO}
 * isCorrect可以用于BaseModel子类的数据校验
 * implements Serializable 是为了网络传输字节流转换
 */

public abstract class BaseModel implements Serializable {

    /**
     * default, 怎么设置子类都有warning
     */
    private static final long serialVersionUID = 1L;

    public long id;

    //对子类不起作用
    //	/**默认构造方法，JSON等解析时必须要有
    //	 */
    //	public BaseModel() {
    //	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 数据正确性校验
     *
     * @param data
     * @return
     */
    public static boolean isCorrect(BaseModel data) {
        return data != null && data.isCorrect();
    }

    /**
     * 数据正确性校验
     *
     * @return
     */
    public abstract boolean isCorrect();
}
