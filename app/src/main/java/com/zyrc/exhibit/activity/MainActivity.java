package com.zyrc.exhibit.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.VersionUpDateBean;
import com.zyrc.exhibit.fragment.FindFragment;
import com.zyrc.exhibit.fragment.HomeFragment;
import com.zyrc.exhibit.fragment.MyFragment;
import com.zyrc.exhibit.fragment.TrendsFragment;
import com.zyrc.exhibit.model.Model;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_ex_home)
    private LinearLayout homeLinearLayout;
    @ViewInject(R.id.ll_ex_trends)
    private LinearLayout trendsLinearLayout;
    @ViewInject(R.id.ll_ex_find)
    private LinearLayout findLinearLayout;
    @ViewInject(R.id.ll_ex_my)
    private LinearLayout myLinearLayout;
    @ViewInject(R.id.tv_ex_home)
    private TextView tvHome;
    @ViewInject(R.id.tv_ex_trends)
    private TextView tvTrends;
    @ViewInject(R.id.tv_ex_find)
    private TextView tvFind;
    @ViewInject(R.id.tv_ex_my)
    private TextView tvMy;
    @ViewInject(R.id.iv_ex_home)
    private ImageView ivHome;
    @ViewInject(R.id.iv_ex_trends)
    private ImageView ivTrends;
    @ViewInject(R.id.iv_ex_find)
    private ImageView ivFind;
    @ViewInject(R.id.iv_ex_my)
    private ImageView ivMy;


    private HomeFragment mHomeFragment;
    private TrendsFragment mTrendsFragment;
    private FindFragment mFindFragment;
    private MyFragment mMyfragment;


    private TextView[] textViews = new TextView[4];
    private ImageView[] imageViews = new ImageView[4];
    private Fragment[] fragments = new Fragment[4];

    private int clickIndex;
    private int currentIndexFragment = 0;
    private Model mModel;
    private ProgressDialog pBar;
    private String path;

    private static final String TAG = "MainActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String json = (String) msg.obj;
                    VersionUpDateBean mVersionUpDateBean = new Gson().fromJson(json, VersionUpDateBean.class);
                    path = mVersionUpDateBean.getData().getAppUrl();
                    if (isNeedUpdate(mVersionUpDateBean.getData().getVersionNo())) {
                        showUpdateDialog(mVersionUpDateBean.getData().getVersionNo());
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        //显示默认的界面
        showFragment();
        //把控件都添加到数组中;
        addFragments();
        //设置监听器
        setListeners();
//        更新版本
        upDateVersion();
    }

    /**
     * 为控件设置监听器
     */
    private void setListeners() {
        homeLinearLayout.setOnClickListener(this);
        trendsLinearLayout.setOnClickListener(this);
        findLinearLayout.setOnClickListener(this);
        myLinearLayout.setOnClickListener(this);
    }

    /**
     * 把控件都添加到数组中
     */
    private void addFragments() {
        mTrendsFragment = new TrendsFragment();
        mFindFragment = new FindFragment();
        mMyfragment = new MyFragment();
        //fragment
        fragments[0] = mHomeFragment;
        fragments[1] = mTrendsFragment;
        fragments[2] = mFindFragment;
        fragments[3] = mMyfragment;
        //textview
        textViews[0] = tvHome;
        textViews[1] = tvTrends;
        textViews[2] = tvFind;
        textViews[3] = tvMy;
        //imageview
        imageViews[0] = ivHome;
        imageViews[1] = ivTrends;
        imageViews[2] = ivFind;
        imageViews[3] = ivMy;
    }

    /**
     * 显示默认的界面
     */
    private void showFragment() {
        mHomeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        //Transaction 事务：包含多个动作，多个动作有一个出了问题，全部取消，都执行成功，提交事务
        FragmentTransaction transaction = manager.beginTransaction();
        //动作1：添加
        transaction.add(R.id.fragment_container, mHomeFragment);
        //动作2：显示
        transaction.show(mHomeFragment);
        //两个动作都成功了,提交事务
        transaction.commit();
//        //设置图片
        ivHome.setSelected(true);
//        //设置文字颜色
        tvHome.setTextColor(ContextCompat.getColor(this, R.color.colorOrange));
    }

    /**
     * 控件的监听事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ex_home:
                clickIndex = 0;
                break;
            case R.id.ll_ex_trends:
                clickIndex = 1;
                break;
            case R.id.ll_ex_find:
                clickIndex = 2;
                break;
            case R.id.ll_ex_my:
                clickIndex = 3;
                break;
        }
        //显示点击的fragment
        showClickFragment(clickIndex);
    }

    @Override
    public void recreate() {
        try {//避免重启太快 恢复
            //重启activity后，要移除所有的fragmeny，然后系统才会重新创建
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            for (Fragment fragment : fragments) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.recreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //显示点击的fragment
    public void showClickFragment(int clickIndex) {
        //判断点击的不是当前显示的按钮
        if (clickIndex != currentIndexFragment) {
            //开始事务
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            //隐藏以前的事务
            Fragment hideFragment = fragments[currentIndexFragment];
            transaction.hide(hideFragment);
            //添加新的事务
            Fragment showFragment = fragments[clickIndex];
            if (!showFragment.isAdded()) {
                transaction.add(R.id.fragment_container, showFragment);
            }
            //显示新的事务
            transaction.show(showFragment);
            //提交事务
            transaction.commit();
//            //变颜色
            imageViews[currentIndexFragment].setSelected(false);
            textViews[currentIndexFragment].setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorGray));
            imageViews[clickIndex].setSelected(true);
            textViews[clickIndex].setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorOrange));

            //把点击的fragment赋值给当前的fragment
            currentIndexFragment = clickIndex;
        }
    }

    //判断版本号
    private boolean isNeedUpdate(String version) {
        return !version.equals(getVersion());
    }

    private void upDateVersion() {
        if (mModel == null) {
            mModel = new Model();
        }
        String param = "?versionType=android";
        mModel.getData(handler, UrlConstant.APT_UPDATE_VERSION, HandlerConstant.SEARCH_SUCCESS, param);
    }

    // 获取当前版本的版本号
    private String getVersion() {
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }

    /**
     * 弹出升级的对话框
     */
    private void showUpdateDialog(String version) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("请升级APP至版本" + version);
        builder.setCancelable(false);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    downFile(path);
                } else {
                    Toast.makeText(MainActivity.this, "SD卡不可用，请插入SD卡", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        builder.create().show();
    }

    /**
     * 下载文件
     */
    private void downFile(final String url) {
        //下载安装包的进度条
        pBar = new ProgressDialog(this);
        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pBar.setCanceledOnTouchOutside(false);
        pBar.setTitle("正在下载");
        pBar.setMessage("请稍后");
        pBar.setProgress(0);
        pBar.show();
        if (mModel == null) {
            mModel = new Model();
        }
        mModel.download(url, new Model.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                pBar.cancel();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "zyrc.apk")), "application/vnd.android.package-archive");
                startActivity(intent);
            }

            @Override
            public void onDownloading(final int progress) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pBar.setProgress(progress);
                    }
                });
            }

            @Override
            public void onDownloadFailed() {
                pBar.cancel();
            }
        });
    }
}
