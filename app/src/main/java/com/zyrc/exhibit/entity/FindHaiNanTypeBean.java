package com.zyrc.exhibit.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class FindHaiNanTypeBean {
    private String successed;

    private String code;

    private String message;

    private List<Data> data ;

    public FindHaiNanTypeBean() {
    }

    public FindHaiNanTypeBean(String successed, String code, String message, List<Data> data) {
        this.successed = successed;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getSuccessed() {
        return successed;
    }

    public void setSuccessed(String successed) {
        this.successed = successed;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        private int id;

        private String name;

        private String codeNo;

        private String description;

        private String layoutType;

        private String menuOrder;

        private int level;

        private String imageUrl;

        private int parentId;

        private int count;

        private boolean selChecked;

        private String levelName;

        private int status;

        public Data() {
        }

        public Data(int id, String name, String codeNo, String description, String layoutType, String menuOrder, int level, String imageUrl, int parentId, int count, boolean selChecked, String levelName) {
            this.id = id;
            this.name = name;
            this.codeNo = codeNo;
            this.description = description;
            this.layoutType = layoutType;
            this.menuOrder = menuOrder;
            this.level = level;
            this.imageUrl = imageUrl;
            this.parentId = parentId;
            this.count = count;
            this.selChecked = selChecked;
            this.levelName = levelName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCodeNo() {
            return codeNo;
        }

        public void setCodeNo(String codeNo) {
            this.codeNo = codeNo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLayoutType() {
            return layoutType;
        }

        public void setLayoutType(String layoutType) {
            this.layoutType = layoutType;
        }

        public String getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(String menuOrder) {
            this.menuOrder = menuOrder;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isSelChecked() {
            return selChecked;
        }

        public void setSelChecked(boolean selChecked) {
            this.selChecked = selChecked;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

}
