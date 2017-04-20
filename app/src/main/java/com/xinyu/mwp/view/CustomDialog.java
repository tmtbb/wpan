package com.xinyu.mwp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xinyu.mwp.R;
import com.xinyu.mwp.constant.Constant;


/**
 * 自定义dialog
 * Created by Administrator on 2017/2/11.
 */
public class CustomDialog extends Dialog {

    private static Button mPositiveButton;
    private static Button mNegativeButton;
    public static UiSeeKBar mSeekBar;
    //    public static TextView mEarnestMoney;//定金
//    public static TextView mEarnestMoneyPercent; //运费比例
    public static TextView mTurnoverMoney;  //成交额
    public static TextView mServiceCharge;
    public static TextView mCurrentPosition;  //当前仓位
    private static RadioButton mFreight;
    private static RadioButton mReturnDouble;
    public static TextView mCurrentCount;
    private static TextView mCurrentCountShow;
    private static TextView mTitle;
    private static TextView mMessage;
    public static TextView maxLot;
    public static TextView minLot;


    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String positiveButtonText;
        private String negativeButtonText;
        private int type;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private View layout;
        private String title;
        private String message;
        private int process;
        private int maxCount;
        private int minCount = 1;

        public Builder(Context context, int type) {
            this.context = context;
            this.type = type;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setProcess(int process) {
            this.process = process;
            return this;
        }

        public Builder setMaxLot(int max) {
            this.maxCount = max;
            return this;
        }

        public Builder setMinLot(int min) {
            this.minCount = min;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.custom_dialog);
            double ratio;
            if (type == Constant.TYPE_BUY_MINUS) {
                layout = inflater.inflate(R.layout.dialog_buy_minus, null);
                initView();
                initSeekBar();
                ratio = 0.9;
            } else if (type == Constant.TYPE_BUY_PLUS) {
                layout = inflater.inflate(R.layout.dialog_buy_plus, null);
                initView();
                initSeekBar();
                ratio = 0.9;
            } else {
                layout = inflater.inflate(R.layout.dialog_insufficient_balance, null);
                initView();
                ratio = 0.8;
            }

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            if (positiveButtonText != null) {
                mPositiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    mPositiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                mPositiveButton.setVisibility(
                        View.GONE);
            }
            if (negativeButtonText != null) {
                mNegativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    mNegativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                mNegativeButton.setVisibility(View.GONE);
            }
            if (title != null) {
                mTitle.setText(title);
            }
            if (message != null) {
                mMessage.setText(message);
            }

            dialog.setContentView(layout);
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = (int) (d.widthPixels * ratio); // 宽度设置为屏幕的0.9 /0.8
            dialogWindow.setAttributes(lp);
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            return dialog;
        }

        private void initView() {
            mPositiveButton = (Button) layout.findViewById(R.id.btn_buy_positive); //买涨/买跌
            mNegativeButton = (Button) layout.findViewById(R.id.btn_buy_negative);
            mTitle = (TextView) layout.findViewById(R.id.dialog_title);
            mMessage = (TextView) layout.findViewById(R.id.dialog_message);
            maxLot = (TextView) layout.findViewById(R.id.tv_maxLot);
            minLot = (TextView) layout.findViewById(R.id.tv_minLot);

            //当前选择手数
            mCurrentCount = (TextView) layout.findViewById(R.id.tv_current_choose_count);
            //当前选择手数显示图标
            mCurrentCountShow = (TextView) layout.findViewById(R.id.tv_current_choose_count_show);
            //拖动选择
            mSeekBar = (UiSeeKBar) layout.findViewById(R.id.trade_seekbar);
            //定金
//            mEarnestMoney = (TextView) layout.findViewById(R.id.tv_earnest_money);
            //运费比例
//            mEarnestMoneyPercent = (TextView) layout.findViewById(R.id.tv_earnest_money_percent);
            //成交额
            mTurnoverMoney = (TextView) layout.findViewById(R.id.tv_turnover_money);
            //手续费
            mServiceCharge = (TextView) layout.findViewById(R.id.tv_trade_service_charge);
//            //货运
//            mFreight = (RadioButton) layout.findViewById(R.id.rb_trade_freight);
//            //双倍返还
//            mReturnDouble = (RadioButton) layout.findViewById(R.id.rb_trade_return_double);
            mCurrentPosition = (TextView) layout.findViewById(R.id.tv_current_position);
        }


        private void initSeekBar() {
            //初始化
            int preProcess = mSeekBar.getProgress();
            mCurrentCount.setText(preProcess + "");  //当前手数
            mSeekBar.setMax(maxCount - 1);
            mSeekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                    if (progressChangeListener != null) {
                        progressChangeListener.processData(progress + 1);
                    }
                    mSeekBar.setNumText(progress + 1 + "");
                    mCurrentCount.setText(progress + 1 + "");
                }

                @Override
                public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(android.widget.SeekBar seekBar) {

                }
            });
        }

        public interface ProgressChangeListener {
            void processData(int process);
        }

        private ProgressChangeListener progressChangeListener;

        public Builder setProgressChangeListener(ProgressChangeListener progressChangeListener) {
            this.progressChangeListener = progressChangeListener;
            return this;
        }
    }

}
