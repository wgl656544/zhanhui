package com.ex.administrator.zhanhui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ex.administrator.zhanhui.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class ChangeNameActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.et_change_name)
    private EditText etChangeName;
    @ViewInject(R.id.btn_save_name)
    private Button btnSaveName;
    @ViewInject(R.id.iv_change_name_back)
    private ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        x.view().inject(this);
        setListeners();
    }

    //设置监听器
    private void setListeners() {
        btnSaveName.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_name:
                Intent intent = new Intent();
                intent.putExtra("name", etChangeName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_change_name_back:
                finish();
                break;
        }
    }
}
