package com.zyrc.exhibit.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.base.BaseFragment;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.activity.AllOrderActivity;
import com.zyrc.exhibit.activity.LoginActivity;
import com.zyrc.exhibit.activity.MyApplyActivity;
import com.zyrc.exhibit.activity.MyBrowseActivity;
import com.zyrc.exhibit.activity.MyCollectActivity;
import com.zyrc.exhibit.activity.MyExhibiActivity;
import com.zyrc.exhibit.activity.MyMsgActivity;
import com.zyrc.exhibit.activity.PersonalInfoActivity;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.UserBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    @ViewInject(R.id.rl_my_login)
    private RelativeLayout rlMyLogin;//点击登录
    @ViewInject(R.id.iv_my_touxiang)
    private CircleImageView ivTouxiang;//头像
    @ViewInject(R.id.tv_my_username)
    private TextView tvName;//用户名
    @ViewInject(R.id.tv_my_city)
    private TextView tvMyCity;//常住地
    @ViewInject(R.id.ll_my_apply)
    private LinearLayout llMyApply;//我的报名
    @ViewInject(R.id.ll_my_exhibi)
    private LinearLayout llMyExhibi;//我的展会
    @ViewInject(R.id.ll_all_order)
    private LinearLayout llAllOrder;//全部订单
    @ViewInject(R.id.ll_my_browse)
    private LinearLayout llBrowse;
    @ViewInject(R.id.ll_my_pay_ok)
    private LinearLayout llPayOk;//已付款
    @ViewInject(R.id.ll_my_pay_not)
    private LinearLayout llPayNot;//未付款
    @ViewInject(R.id.iv_my_msg)
    private ImageView ivMsg;//推送消息
    @ViewInject(R.id.ll_my_collect)
    private LinearLayout llCollect;//我的收藏
    @ViewInject(R.id.rl_my_call_phone)
    private RelativeLayout rlCallPhone;//联系客服
    @ViewInject(R.id.rl_my_sign_in)
    private RelativeLayout llSignIn;

    private QBadgeView mQBadgeView;
    private Model mModel;
    private String TAG;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String data = (String) msg.obj;
                    if (mQBadgeView != null) {
                        mQBadgeView.setBadgeNumber(showMsgCount(data));
                    }
                    break;
                case HandlerConstant.LOGIN_SUCCESS:
                    String json = (String) msg.obj;
                    SPUtils.put(getActivity(), "userinfo" + MyApplication.userId, json);
                    UserBean bean = new Gson().fromJson(json, UserBean.class);
                    showUserInfo(bean);
                    break;
            }
        }
    };

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        x.view().inject(this, view);
        setListeners();//设置监听器
        initView();
        TAG = getActivity().getLocalClassName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData() {

    }

    //初始化控件
    private void initView() {
        mQBadgeView = new QBadgeView(getActivity());
        mQBadgeView.bindTarget(ivMsg).setBadgePadding(1, true);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        rlMyLogin.setOnClickListener(this);//登录
        llMyApply.setOnClickListener(this);//我的报名
        llAllOrder.setOnClickListener(this);//全部订单
        llPayNot.setOnClickListener(this);//未付款
        llPayOk.setOnClickListener(this);//已付款
        llMyExhibi.setOnClickListener(this);//我的展会
        ivMsg.setOnClickListener(this);//推送消息
        llCollect.setOnClickListener(this);//我的收藏
        llBrowse.setOnClickListener(this);//我的浏览
        rlCallPhone.setOnClickListener(this);//联系客服
        llSignIn.setOnClickListener(this);//我的签到记录
    }

    @Override
    public void onClick(View v) {
        if (MyApplication.isLogin) {//已登录
            switch (v.getId()) {
                //点击跳转登录界面
                case R.id.rl_my_login://登录
                    startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
                    break;
                case R.id.ll_my_apply://我的报名
                    startActivity(new Intent(getActivity(), MyApplyActivity.class));
                    break;
                case R.id.ll_my_exhibi://我的展会
                    startActivity(new Intent(getActivity(), MyExhibiActivity.class));
                    break;
                case R.id.iv_my_msg:
                    startActivity(new Intent(getActivity(), MyMsgActivity.class));
                    break;
                case R.id.ll_all_order://所有订单
                    startActivity(new Intent(getActivity(), AllOrderActivity.class).putExtra("index", 0));
                    break;
                case R.id.ll_my_pay_not://待付款
                    startActivity(new Intent(getActivity(), AllOrderActivity.class).putExtra("index", 1));
                    break;
                case R.id.ll_my_pay_ok://已付款
                    startActivity(new Intent(getActivity(), AllOrderActivity.class).putExtra("index", 2));
                    break;
                case R.id.ll_my_collect:
                    startActivity(new Intent(getActivity(), MyCollectActivity.class));
                    break;
                case R.id.ll_my_browse:
                    startActivity(new Intent(getActivity(), MyBrowseActivity.class));
                    break;
                case R.id.rl_my_call_phone:
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:13922480038"));
                    //开启系统拨号器
                    startActivity(intent);
                    break;
                case R.id.rl_my_sign_in:
                    startActivity(new Intent(getActivity(), MyExhibiActivity.class));
                    break;
            }
        } else { //未登录
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    //获取未读消息个数
    private void getMsgCount() {
        if (MyApplication.isLogin) {
            if (mModel == null) {
                mModel = new Model();
            }
            String param = "?userId=" + MyApplication.userId;
            mModel.getData(handler, UrlConstant.HTTP_URL_MSG_COUNT, HandlerConstant.SEARCH_SUCCESS, param);
        } else {
            mQBadgeView.setBadgeNumber(0);
        }
    }

    //显示未读消息个数
    private int showMsgCount(String data) {
        try {
            JSONObject jsonobject = new JSONObject(data);
            return jsonobject.getInt("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        //显示头像
        showUserInfo();
        //获取未读推送消息条数
        getMsgCount();
    }

    //显示默认用户资料
    private void showUserInfo() {
        if (MyApplication.isLogin) {
            if (mModel == null) {
                mModel = new Model();
            }
            Map<String, String> param = new HashMap<>();
            param.put("userId", MyApplication.userId);
            mModel.postData(handler, UrlConstant.HTTP_URL_FIND_USERINFO, HandlerConstant.LOGIN_SUCCESS, param);
        } else {
            tvName.setText("请登录");
            tvMyCity.setText("登录享优惠");
            ivTouxiang.setImageResource(R.drawable.touxiang);
        }
    }

    //显示默认用户资料
    private void showUserInfo(UserBean bean) {
        if (MyApplication.isLogin) {
            Glide.with(getActivity()).load(bean.getData().getHeaderImgUrl()).error(R.drawable.touxiang).into(ivTouxiang);
            tvName.setText(bean.getData().getNickName());
            tvMyCity.setText(bean.getData().getCity());
        }
    }


}
