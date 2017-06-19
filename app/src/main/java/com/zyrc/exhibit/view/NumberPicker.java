package com.zyrc.exhibit.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.model.OrderModel;
import com.zyrc.exhibit.util.NetUtils;
import com.zyrc.exhibit.util.ToastUtil;


/**
 * Created by Administrator on 2017/3/24.
 */

public class NumberPicker extends LinearLayout {
    private TextView tvShow;
    private RelativeLayout plus;
    private RelativeLayout minus;
    private int defaultValue = 1;
    private int current = 1;
    private int productId;
    private String totalCount;

    private Context mContext;
    private OrderModel model = new OrderModel();
    private static NumberPicker sInstance;
    public InterfaceOnNumChange listener;

    public interface InterfaceOnNumChange {
        void onNumChange(NumberPicker numberPicker);
    }

    public void setOnNumChangeListener(InterfaceOnNumChange listener) {
        this.listener = listener;
    }

    public OnLoadListener onLoadListener;

    public interface OnLoadListener {
        void loading();
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }


    public NumberPicker(Context context) {
        super(context);
    }

    public NumberPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        sInstance = this;
        init(context);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.SEARCH_SUCCESS) {
                totalCount = (String) msg.obj;
                current = defaultValue;
                tvShow.setText(String.valueOf(current));
                if (listener != null)
                    listener.onNumChange(sInstance);
            }

            if (msg.what == HandlerConstant.REQUEST_FAIL) {
                defaultValue = current;
                if (listener != null)
                    listener.onNumChange(sInstance);
            }
        }
    };


    private void init(Context context) {

        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.num_picker, this);

        tvShow = (TextView) findViewById(R.id.tv_show);
        plus = (RelativeLayout) findViewById(R.id.tv_plus);
        minus = (RelativeLayout) findViewById(R.id.tv_minus);

        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtils.isConnected(mContext)) {
                    if (onLoadListener != null) {
                        onLoadListener.loading();
                        String param = "?productId=" + productId + "&qty=" + (++defaultValue);
                        model.getTotalPrice(handler, UrlConstant.HTTP_URL_TOTAL_PRICE, param);
                    }
                } else {
                    ToastUtil.show(mContext,"请检查网络");
                }

            }
        });

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtils.isConnected(mContext)) {
                    if (defaultValue > 1) {
                        if (onLoadListener != null) {
                            onLoadListener.loading();
                            String param = "?productId=" + productId + "&qty=" + (--defaultValue);
                            model.getTotalPrice(handler, UrlConstant.HTTP_URL_TOTAL_PRICE, param);
                        }
                    }
                } else {
                    ToastUtil.show(mContext,"请检查网络");
                }

            }
        });

    }

    public NumberPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getValue() {
        return Integer.valueOf(tvShow.getText().toString());
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setValue(int value) {
        tvShow.setText(String.valueOf(value));
        defaultValue = value;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
