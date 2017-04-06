package com.ex.administrator.zhanhui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.mylibrary.common.CustomLoading;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class BaseActivity extends AppCompatActivity {
    private CustomLoading mCustomLoading;

    /**
     * 停止加载动画
     */
    public void stopLoading() {
        if (mCustomLoading != null) {
            mCustomLoading.dismiss();
//            mCustomLoading.dismiss();
        }
    }

    /**
     * 加载动画
     */
    public void startLoading(String msg) {
        if (mCustomLoading == null) {
            mCustomLoading = CustomLoading.CreateLoadingDialog(this);
            mCustomLoading.setMessage(msg);
            mCustomLoading.setCanceledOnTouchOutside(false);//不允许点击取消

            Window wd = mCustomLoading.getWindow();
            WindowManager.LayoutParams lp = wd.getAttributes();
            lp.alpha = 0.5f;
            wd.setAttributes(lp);
        }
        mCustomLoading.show();
    }

}
