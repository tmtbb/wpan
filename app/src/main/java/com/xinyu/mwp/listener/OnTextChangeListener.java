package com.xinyu.mwp.listener;

import android.text.TextWatcher;

/**
 * Created by Benjamin on 17/1/13.
 */

public interface OnTextChangeListener {
    void addTextChangedListener(TextWatcher textWatcher);

    String getEditTextString();
}
