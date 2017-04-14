package com.ex.administrator.zhanhui.view.homeTeamHeader;

import com.ex.administrator.zhanhui.model.filter.FilterEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class TeamFilterData {
    private List<FilterEntity> category;
    private List<FilterEntity> place;
    private List<FilterEntity> type;

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

    public List<FilterEntity> getType() {
        return type;
    }

    public void setType(List<FilterEntity> type) {
        this.type = type;
    }
}
