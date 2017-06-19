package com.zyrc.exhibit.view.findEXheader;

import com.zyrc.exhibit.model.filter.FilterEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterData implements Serializable {

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

    public List<FilterEntity> getNear() {
        return near;
    }

    public void setNear(List<FilterEntity> near) {
        this.near = near;
    }

    public List<FilterEntity> getType() {
        return type;
    }

    public void setType(List<FilterEntity> type) {
        this.type = type;
    }

    public List<FilterEntity> getSift() {
        return sift;
    }

    public void setSift(List<FilterEntity> sift) {
        this.sift = sift;
    }
}
