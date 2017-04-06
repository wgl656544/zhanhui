package com.ex.administrator.zhanhui.view.homeFragmentBusiHeader;

import com.ex.administrator.zhanhui.model.filter.FilterEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class BusiFilterData {
    private List<FilterEntity> city;
    private List<FilterEntity> near;
    private List<FilterEntity> type;
    private List<FilterEntity> sift;

    public List<FilterEntity> getCity() {
        return city;
    }

    public void setCity(List<FilterEntity> city) {
        this.city = city;
    }

    public List<FilterEntity> getSift() {
        return sift;
    }

    public void setSift(List<FilterEntity> sift) {
        this.sift = sift;
    }

    public List<FilterEntity> getType() {
        return type;
    }

    public void setType(List<FilterEntity> type) {
        this.type = type;
    }

    public List<FilterEntity> getNear() {
        return near;
    }

    public void setNear(List<FilterEntity> near) {
        this.near = near;
    }
}
