package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.InfoWinAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.DetailExBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 地图页面
 * Created by Administrator on 2017/4/27 0027.
 */

public class MapActivity extends BaseActivity implements View.OnClickListener, AddressPicker.OnAddressPickListener{
    @ViewInject(R.id.map)
    private MapView mMapView;
    @ViewInject(R.id.iv_map_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.btn_map_city)
    private Button btnCity;//选择城市
    private AMap aMap;
    public static final String DETAILEX = "detail";

    private Marker currentMarker;
    private Model model;
    private InfoWinAdapter adapter;
    private DetailExBean mDetailExBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String json = (String) msg.obj;
                    CommonBean common = new Gson().fromJson(json, CommonBean.class);
                    showEx(common);
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        x.view().inject(this);
        mDetailExBean = (DetailExBean) getIntent().getSerializableExtra(DETAILEX);
        model = new Model();
        initListeners();
        mMapView.onCreate(savedInstanceState);
        showMap();//显示地图
    }

    /**
     * 设置监听器
     */
    private void initListeners() {
        ivBack.setOnClickListener(this);
        btnCity.setOnClickListener(this);
    }

    /**
     * 显示地图
     */
    private void showMap() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
//            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            adapter = new InfoWinAdapter();
            aMap.setInfoWindowAdapter(adapter);
            aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    currentMarker = marker;
                    return false;
                }
            });
            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (currentMarker.isInfoWindowShown()) {
                        currentMarker.hideInfoWindow();
                    }
                }
            });
            aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                }
            });
            if(mDetailExBean == null){
                String param = "?city=" + MyApplication.local;
                model.getData(handler, UrlConstant.HTTP_URL_SEARCH_EXHIBITION, HandlerConstant.SEARCH_SUCCESS, param);
            } else {
                showFlag(mDetailExBean);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_map_back:
                finish();
                break;
            case R.id.btn_map_city:
                showCityChoice();
                break;
        }
    }

    //显示展会标记
    private void showEx(CommonBean bean) {
        aMap.clear();
        ArrayList<MarkerOptions> markers = new ArrayList<>();
        for (CommonBean.Data data : bean.getData()) {
            MarkerOptions markerOptions = new MarkerOptions();
            if (data.getLatitude() != null && data.getLongitude() != null) {
                //维度，经度
                markerOptions.position(new LatLng(Double.parseDouble(data.getLatitude()), Double.parseDouble(data.getLongitude())));
                markerOptions.title(data.getName());
                markerOptions.snippet(data.getCity());
                markers.add(markerOptions);
            }
        }
        if (markers.size() > 0) {
            aMap.addMarkers(markers, true);
        } else {
            ToastUtil.show(this, "当前城市没有展会哦!");
        }
    }

    private void showFlag(DetailExBean bean){
        aMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        //维度，经度
        markerOptions.position(new LatLng(bean.getData().getLatitude(),bean.getData().getLongitude()));
        markerOptions.title(bean.getData().getName());
        markerOptions.snippet(bean.getData().getAddress().getStreet());
        aMap.addMarker(markerOptions);
    }

    //选择省份城市区域
    private void showCityChoice() {
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(getAssets().open("city.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            AddressPicker picker = new AddressPicker(this, data);
            picker.setHideCounty(true);
            picker.setSelectedItem("北京", "北京");
            picker.setOnAddressPickListener(this);
            picker.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //选择城市监听回调
    @Override
    public void onAddressPicked(Province province, City city, County county) {
        if (model == null) {
            model = new Model();
        }
        String param = "?city=" + city.getAreaName();
        model.getData(handler, UrlConstant.HTTP_URL_SEARCH_EXHIBITION, HandlerConstant.SEARCH_SUCCESS, param);
        startLoading("搜索展会中...");
    }
}
