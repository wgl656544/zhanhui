package com.zyrc.exhibit.view.homeInfoHeader;

import com.zyrc.exhibit.model.filter.FilterEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class InfoFilterData implements Serializable{
    private List<FilterEntity> category;
    private List<FilterEntity> place;
    private List<FilterEntity> date;

    public List<FilterEntity> getCategory() {
        return category;
    }

    public void setCategory(List<FilterEntity> category) {
        this.category = category;
    }

    public List<FilterEntity> getPlace() {
        return place;
    }

    public void setPlace(List<FilterEntity> place) {
        this.place = place;
    }

    public List<FilterEntity> getDate() {
        return date;
    }

    public void setDate(List<FilterEntity> date) {
        this.date = date;
    }
}
