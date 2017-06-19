package com.zyrc.exhibit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.base.BaseActivity;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.GetDataModel;
import com.zyrc.exhibit.util.SPUtils;
import com.zyrc.exhibit.util.ToastUtil;
import com.zyrc.exhibit.view.FlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面
 * Created by Administrator on 2017/4/28 0028.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    @ViewInject(R.id.fl_search)
    private FlowLayout mFlowLayout;
    @ViewInject(R.id.fl_history)
    private FlowLayout hisFlowLayout;
    @ViewInject(R.id.tv_search_back)
    private TextView tvBack;
    @ViewInject(R.id.et_search_input)
    private EditText etInput;
    @ViewInject(R.id.btn_save)
    private Button btnSave;
    @ViewInject(R.id.rl_search_history)
    private RelativeLayout rlHistory;
    private LayoutInflater mInflater;
    private String Tag = "searchActivity";
    private GetDataModel model = new GetDataModel();
    private List<String> mVals = new ArrayList<String>() {{
        add("博鳌");
        add("健康");
        add("医疗");
        add("科技");
        add("新型 ");
    }};
    private final static String SEARCH_HISTORY = "search";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    CommonBean bean = (CommonBean) msg.obj;
                    startActivity(new Intent(SearchActivity.this, HomeFindEXActivity.class).putExtra("data", bean));
                    finish();
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    ToastUtil.show(SearchActivity.this, "搜索出错");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        x.view().inject(this);
        initview();
        setListeners();
    }

    private void setListeners() {
        tvBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        etInput.setOnClickListener(this);
        etInput.setOnEditorActionListener(this);
    }

    private void initview() {
        mInflater = LayoutInflater.from(this);
        initData(mFlowLayout, mVals);
        if (getHistory().size() > 0) {
            initData(hisFlowLayout, getHistory());
        } else {
            rlHistory.setVisibility(View.GONE);
        }
    }

    /**
     * 显示记录
     */
    private void initData(FlowLayout mFlowLayout, List<String> datas) {
        for (int i = 0; i < datas.size(); i++) {
            if (!TextUtils.isEmpty(datas.get(i))) {
                TextView tv = (TextView) mInflater.inflate(
                        R.layout.search_label_tv, mFlowLayout, false);
                tv.setText(datas.get(i));
                final String str = tv.getText().toString();
                //点击事件
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加入搜索历史纪录记录
                        String param = "?keyWord=" + str;
                        model.search(handler, UrlConstant.HTTP_URL_SEARCH_EXHIBI_BY_KEY, param);
                    }
                });
                mFlowLayout.addView(tv);
            }
        }

    }

    //获取历史记录
    private List<String> getHistory() {
        List<String> list = new ArrayList<>();
        if (SPUtils.contains(this, SEARCH_HISTORY)) {
            String text = (String) SPUtils.get(this, SEARCH_HISTORY, "");
            if (text != null) {
                for (Object o : text.split(",")) {
                    list.add((String) o);
                }
            }
            return list;
        }
        return list;
    }

    //储存历史标签
    private void saveLayout(FlowLayout mFlowLayout, String text) {
        if (rlHistory.getVisibility() == View.GONE) {
            rlHistory.setVisibility(View.VISIBLE);
        }
        TextView tv = (TextView) mInflater.inflate(
                R.layout.search_label_tv, mFlowLayout, false);
        tv.setText(text);
        mFlowLayout.addView(tv);
    }


    /**
     * 储存搜索历史
     */
    public void save(String text) {
        String oldText = (String) SPUtils.get(this, SEARCH_HISTORY, "");
        if (oldText != null) {
            if (!TextUtils.isEmpty(text) && !(oldText.contains(text))) {
                SPUtils.put(this, SEARCH_HISTORY, text + "," + oldText);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_back:
                finish();
                break;
            case R.id.btn_save:
                initSpeech(this);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) etInput.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    SearchActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            String s = etInput.getText().toString().trim();
            if (!TextUtils.isEmpty(s)) {
                //发送请求
                String param = "?keyWord=" + s;
                model.search(handler, UrlConstant.HTTP_URL_SEARCH_EXHIBI_BY_KEY, param);
                startLoading("正在搜索中...");
                saveLayout(hisFlowLayout, s);//添加历史标签
                save(s);//保存历史记录
            }
            return true;
        }
        return false;
    }

    /**
     * 初始化语音识别
     */
    public void initSpeech(final Context context) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //解析语音
                    String result = recognizerResult.getResultString();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("ws");
                        JSONObject object = jsonArray.getJSONObject(0);
                        JSONArray jsonArray1 = object.getJSONArray("cw");
                        JSONObject object1 = jsonArray1.getJSONObject(0);
                        String s = object1.getString("w");
                        ToastUtil.show(SearchActivity.this, s);
                        String param = "?keyWord=" + s;
                        model.search(handler, UrlConstant.HTTP_URL_SEARCH_EXHIBI_BY_KEY, param);
                        startLoading("正在搜索中...");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();
    }
}
