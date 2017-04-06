package com.ex.administrator.zhanhui.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class InfoCategoryBean {
    private String successed;

    private String code;

    private String message;

    private List<Data> data;

    public InfoCategoryBean() {
    }

    public InfoCategoryBean(String successed, String code, String message, List<Data> data) {
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

    public class Data {
        private int id;

        private String name;

        private String codeNo;

        private BlogTerm blogTerm;

        private String description;

        private String layoutType;

        private int menuOrder;

        private String level;

        private String imageUrl;

        private int parentId;

        private String count;

        private boolean selChecked;

        public Data() {
        }

        public Data(int id, String name, String codeNo, BlogTerm blogTerm, String description, String layoutType, int menuOrder, String level, String imageUrl, int parentId, String count, boolean selChecked) {
            this.id = id;
            this.name = name;
            this.codeNo = codeNo;
            this.blogTerm = blogTerm;
            this.description = description;
            this.layoutType = layoutType;
            this.menuOrder = menuOrder;
            this.level = level;
            this.imageUrl = imageUrl;
            this.parentId = parentId;
            this.count = count;
            this.selChecked = selChecked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isSelChecked() {
            return selChecked;
        }

        public void setSelChecked(boolean selChecked) {
            this.selChecked = selChecked;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(int menuOrder) {
            this.menuOrder = menuOrder;
        }

        public String getLayoutType() {
            return layoutType;
        }

        public void setLayoutType(String layoutType) {
            this.layoutType = layoutType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BlogTerm getBlogTerm() {
            return blogTerm;
        }

        public void setBlogTerm(BlogTerm blogTerm) {
            this.blogTerm = blogTerm;
        }

        public String getCodeNo() {
            return codeNo;
        }

        public void setCodeNo(String codeNo) {
            this.codeNo = codeNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class BlogTerm {
        private int id;

        private String name;

        public BlogTerm() {
        }

        public BlogTerm(int id, String name) {
            this.id = id;
            this.name = name;
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
    }
}
