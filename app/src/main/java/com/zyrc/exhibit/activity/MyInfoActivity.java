package com.zyrc.exhibit.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.base.BaseActivity;
import com.example.mylibrary.common.ActionSheetDialog;
import com.google.gson.Gson;
import com.zaaach.citypicker.CityPickerActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.entity.UserBean;
import com.zyrc.exhibit.entity.UserPhotoBean;
import com.zyrc.exhibit.model.UserModel;
import com.zyrc.exhibit.util.FileUtil;
import com.zyrc.exhibit.util.SPUtils;
import com.zyrc.exhibit.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import cn.qqtheme.framework.picker.DatePicker;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/3/23 0023.
 * 我的个人信息页面
 */

public class MyInfoActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_my_info_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.rl_my_info_photo)
    private RelativeLayout rlMyInfoPhoto;//选择我的头像
    @ViewInject(R.id.iv_my_info_photo)
    private CircleImageView ivPhoto;//我的头像
    @ViewInject(R.id.rl_change_name)
    private RelativeLayout rlChandeName;//修改昵称
    @ViewInject(R.id.tv_my_name)
    private TextView tvMyName;//昵称
    @ViewInject(R.id.rl_choice_sex)
    private RelativeLayout rlChoiceSex;//性别
    @ViewInject(R.id.tv_choice_sex)
    private TextView tvChoiceSex;//性别
    @ViewInject(R.id.rl_choice_birthday)
    private RelativeLayout rlBirthDay;//生日
    @ViewInject(R.id.tv_choice_birthday)
    private TextView tvBirthDay;//生日
    @ViewInject(R.id.rl_my_info_city)
    private RelativeLayout rlMyInfoCity;//常住地
    @ViewInject(R.id.tv_my_info_city)
    private TextView tvMyInfoCity;//常住地
    @ViewInject(R.id.rl_ship_address)
    private RelativeLayout rlShipAddress;//收货地址
    @ViewInject(R.id.btn_exit_login)
    private Button btnExitLogin;//退出登录
    @ViewInject(R.id.rl_my_info_pwd)
    private RelativeLayout rlPwd;


    private static final int REQUEST_CODE_PICK_CITY = 100;//城市
    private static final int SELECTOR_GALLERY = 1000;// 从相册中选择
    private static final int SELECTOR_CAMERA = 1001;// 拍照选择
    private static final int CHANFE_NAME = 1;// 修改昵称

    private String birthDay;
    private String userId = MyApplication.userId;
    private UserModel model = new UserModel();
    private UserBean userBean;
    private Intent intent;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.FILE_UPLOAD_SUCCESS) {//上传图片
                stopLoading();//停止加载动画
                UserPhotoBean userPhotoBean = (UserPhotoBean) msg.obj;
                model.userUpDate(handler, userId, "headerImgUrl", userPhotoBean.getData().getImageUrl());
            }
            if (msg.what == HandlerConstant.USER_UPDATE_SUCCESS) {//更新用户资料
                stopLoading();//停止加载动画
                userBean = (UserBean) msg.obj;
                userUpDate(userBean);
                String userinfo = new Gson().toJson(userBean);
                SPUtils.put(MyInfoActivity.this, "userinfo" + userId, userinfo);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        x.view().inject(this);
        setListeners();//设置监听器
        showUserInfo();//显示默认信息
    }

    //设置监听器
    private void setListeners() {
        rlMyInfoPhoto.setOnClickListener(this);
        rlChandeName.setOnClickListener(this);
        rlChoiceSex.setOnClickListener(this);
        rlBirthDay.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlMyInfoCity.setOnClickListener(this);
        rlShipAddress.setOnClickListener(this);
        btnExitLogin.setOnClickListener(this);
        rlPwd.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_info_back:
                finish();
                break;
            case R.id.rl_my_info_photo://头像
                cameraOrAlbum();//相机或者相册
                break;
            case R.id.rl_change_name://昵称
                intent = new Intent(this, ChangeNameActivity.class);
                startActivityForResult(intent, CHANFE_NAME);
                break;
            case R.id.rl_choice_sex://性别
                choiceSex();
                break;
            case R.id.rl_choice_birthday://生日
                choiceBirthDay();
                break;
            case R.id.rl_my_info_city://城市
                startActivityForResult(new Intent(this, CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
                break;
            case R.id.rl_ship_address://地址
                startActivity(new Intent(this, ShipAddressActivity.class));
                break;
            case R.id.rl_my_info_pwd:
                startActivity(new Intent(this,UpDatePwdActivity.class));
                break;
            case R.id.btn_exit_login://退出登录
                exitLogin();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECTOR_GALLERY) {//相册
            if (data != null) {
                Uri uri = data.getData();
                startLoading("上传头像中...");//开始加载动画
                File file = FileUtil.getRealPathFromURI(uri, this);
                model.fileUpLoad(handler, file, userId);
            }
        }
        if (requestCode == SELECTOR_CAMERA) {//拍照
            if (data != null) {
                startLoading("上传头像中...");//开始加载动画
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                File file = FileUtil.saveFile(bitmap, bitmap.toString());
                model.fileUpLoad(handler, file, userId);
            }
        }

        if (requestCode == CHANFE_NAME) {//昵称
            if (data != null) {
                String name = data.getStringExtra("name");
                startLoading("修改中...");//开始加载动画
                model.userUpDate(handler, userId, "nickName", name);
            }
        }
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                tvMyInfoCity.setText(city);
            }
        }
    }

    /**
     * 选择打开相机或者相册，进行图片选择
     */
    private void cameraOrAlbum() {
        ActionSheetDialog dialog = new ActionSheetDialog(this);
        dialog.builder();
        dialog.addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
//                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, SELECTOR_CAMERA);
            }
        });

        dialog.addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                // 激活系统图库，选择一张图片
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intent, SELECTOR_GALLERY);
            }
        });
        dialog.show();
    }

    //选择性别
    private void choiceSex() {
        final String[] items = {"男", "女", "保密"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        listDialog.setTitle("性别选择");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                // ...To-do
                String sex = "3";
                switch (which) {
                    case 0:
                        sex = "1";
                        break;
                    case 1:
                        sex = "2";
                        break;
                    case 2:
                        sex = "3";
                        break;
                }
                startLoading("修改中...");//开始加载动画
                model.userUpDate(handler, userId, "sex", sex);
            }
        });
        listDialog.show();
    }

    //选择生日
    private void choiceBirthDay() {
        try {
            DatePicker picker = new DatePicker(this);
            picker.setRange(1960, 2017);//年份范围
            String[] time = birthDay.split("-");
            int year = Integer.parseInt(time[0]);
            int month = Integer.parseInt(time[1]);
            int day = Integer.parseInt(time[2]);
            picker.setSelectedItem(year, month, day);
//        获得当前的日期
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
//        String date=sdf.format(new java.util.Date());
            picker.setGravity(Gravity.CENTER);
            picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String birthDay = year + "-" + month + "-" + day;
                    tvBirthDay.setText(birthDay);
                    startLoading("修改中...");//开始加载动画
                    model.userUpDate(handler, userId, "birthday", birthDay);
                }
            });
            picker.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示默认用户资料
    private void showUserInfo() {
        try {
            if (SPUtils.contains(this, "userinfo" + userId)) {
                String userinfo = (String) SPUtils.get(this, "userinfo" + userId, "");
                if (!TextUtils.isEmpty(userinfo)) {
                    userBean = new Gson().fromJson(userinfo, UserBean.class);
                    userUpDate(userBean);
                } else {
                    ToastUtil.show(this, "发送请求");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新用户资料
    private void userUpDate(UserBean bean) {
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getData().getNickName())) {
                tvMyName.setText(bean.getData().getNickName());//昵称
            }
            if (!TextUtils.isEmpty(bean.getData().getSex())) {//性别
                String sex;
                switch (bean.getData().getSex()) {
                    case "1":
                        sex = "男";
                        break;
                    case "2":
                        sex = "女";
                        break;
                    case "3":
                        sex = "保密";
                        break;
                    default:
                        sex = "保密";
                        break;
                }
                tvChoiceSex.setText(sex);//性别
                tvBirthDay.setText(bean.getData().getBirthday());//生日
                birthDay = bean.getData().getBirthday();
            }
            if (!TextUtils.isEmpty(bean.getData().getHeaderImgUrl())) {//头像
                Glide.with(MyInfoActivity.this)
                        .load(bean.getData().getHeaderImgUrl())
                        .override(400, 400)
                        .dontAnimate()
                        .placeholder(R.drawable.weixin)
                        .error(R.drawable.touxiang)
                        .into(ivPhoto);
            }
        }
    }

    //退出账号
    private void exitLogin() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MyInfoActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("确定退出该账号吗？");
        normalDialog.setPositiveButton("退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        SPUtils.remove(MyInfoActivity.this, "userId");
                        MyApplication.isLogin = false;
                        finish();
                        PersonalInfoActivity.activity.finish();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}
