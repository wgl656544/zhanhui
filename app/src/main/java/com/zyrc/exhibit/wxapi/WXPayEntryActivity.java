package com.zyrc.exhibit.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.activity.AllOrderActivity;
import com.zyrc.exhibit.activity.PayOrderActivity;
import com.zyrc.exhibit.util.ToastUtil;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private boolean isPay = false;
    private TextView tvResult;

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private static final String APP_ID = "wxfacc8293ddba891f";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        initView();
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    private void initView() {
        tvResult = (TextView) findViewById(R.id.tv_wxpay);
        Button btnFinish = (Button) findViewById(R.id.btn_wxpay);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayOrderActivity.getInstance().finish();
                if (isPay) {
                    startActivity(new Intent(WXPayEntryActivity.this, AllOrderActivity.class).putExtra("index", 2));
                } else {
                    startActivity(new Intent(WXPayEntryActivity.this, AllOrderActivity.class).putExtra("index", 1));
                }
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.errCode == 0) {
            isPay = true;
            tvResult.setText("支付成功");
            ToastUtil.show(this, "支付成功");
        } else {
            isPay = false;
            tvResult.setText("支付失败");
            ToastUtil.show(this, "支付失败，重试");
        }
    }
}