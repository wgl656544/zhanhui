package com.ex.administrator.zhanhui.view;


import com.ex.administrator.zhanhui.application.MyApplication;
import com.ex.administrator.zhanhui.entity.HotCityBean;
import com.ex.administrator.zhanhui.model.filter.FilterEntity;
import com.ex.administrator.zhanhui.model.filter.TravelingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17/3/7.
 */
public class ModelUtil {


    // 城市数据
    public static List<FilterEntity> getCityData() {
        HotCityBean hotCityBean = MyApplication.getHotCityBean();
        List<FilterEntity> list = new ArrayList<>();
        if (hotCityBean != null) {
            for (int i = 0; i < hotCityBean.getData().size(); i++) {
                list.add(new FilterEntity(hotCityBean.getData().get(i).getName()));
            }
        }
        return list;
    }

    // 筛选数据
    public static List<FilterEntity> getSiftData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("500人以下"));
        list.add(new FilterEntity("500-1000人"));
        list.add(new FilterEntity("1000-10000人"));
        list.add(new FilterEntity("10000人以上"));
        return list;
    }

    // 最近数据
    public static List<FilterEntity> getNearData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("0-1月"));
        list.add(new FilterEntity("0-3月"));
        list.add(new FilterEntity("0-6月"));
        list.add(new FilterEntity("0-12月"));
        return list;
    }

    // 团购筛选数据
    public static List<FilterEntity> getTeamType() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("0-200元"));
        list.add(new FilterEntity("201-500元"));
        list.add(new FilterEntity("501-1000元"));
        list.add(new FilterEntity("1001-5000元"));
        list.add(new FilterEntity("5000元以上"));
        return list;
    }


    // 暂无数据
    public static List<TravelingEntity> getNoDataEntity(int height) {
        List<TravelingEntity> list = new ArrayList<>();
        TravelingEntity entity = new TravelingEntity();
        entity.setNoData(true);
        entity.setHeight(height);
        list.add(entity);
        return list;
    }

}
