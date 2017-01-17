package com.xinyu.mwp.constant;

public interface ActionConstant {
    interface Action {
        int EMPTY_BUTTON = 10000;
    }

    interface Code {
        int CHAT_CONTACT_REQUEST_CODE = 1;
        int CHAT_CONTACT_RESULT_CODE = CHAT_CONTACT_REQUEST_CODE + 1;
        int CHAT_CONTACT_RESULT_CANCEL_CODE = CHAT_CONTACT_RESULT_CODE + 1;
        int CHOOSE_IMGS_CODE = CHAT_CONTACT_RESULT_CANCEL_CODE + 1;
        int CHOOSE_IMGS_CODE_CANCEL = CHOOSE_IMGS_CODE + 1;
        int CHOOSE_IMGS_CAMERA = CHOOSE_IMGS_CODE_CANCEL + 1;

        int BIG_IMAGE = CHOOSE_IMGS_CAMERA + 1;
        int IMAGE_CROP = BIG_IMAGE + 1;
        int COMMENT_UPDATE = IMAGE_CROP + 1;
    }
}
