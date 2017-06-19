package com.example.mylibrary.common;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mylibrary.R;


/**
 * Created by user on 2016/3/19.
 */
public class CustomLoading extends Dialog {
    private Context context;
    private  static CustomLoading customLoadingDialog = null;


    public CustomLoading(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public static CustomLoading CreateLoadingDialog(Context context){

        customLoadingDialog = new CustomLoading(context, R.style.CustomProgressDialog);
        customLoadingDialog.setContentView(R.layout.customprogressdialog_load);
        customLoadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        //这样就不会被清掉
//        customLoadingDialog.setCancelable(false);
        customLoadingDialog.setCanceledOnTouchOutside(false);


        return customLoadingDialog;
    }
    public  CustomLoading setMessage(String Title){
        TextView tvTitle = (TextView) customLoadingDialog.findViewById(R.id.id_tv_loadingmsg);
        if(tvTitle !=null){
            tvTitle.setText(Title);
        }
        return customLoadingDialog;
    }

}
