package com.zyrc.exhibit.model.filter;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterEntity implements Serializable {

    private String key;
    private boolean isSelected;

    public FilterEntity() {
    }

    public FilterEntity(String key) {
        this.key = key;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
