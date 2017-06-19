package com.zyrc.exhibit.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class TypeBean {
    private String successed;

    private String code;

    private String message;

    private List<Data> data;

    public TypeBean() {
    }

    public TypeBean(String successed, String code, String message, List<Data> data) {
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

        private String imageUrl;

        private String code;

        private int level;

        private int parentId;

        private boolean selChecked;

        private String levelName;

        public Data() {
        }

        public Data(int id, String name, String imageUrl, String code, int level, int parentId, boolean selChecked, String levelName) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.code = code;
            this.level = level;
            this.parentId = parentId;
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

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
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
    }

}
