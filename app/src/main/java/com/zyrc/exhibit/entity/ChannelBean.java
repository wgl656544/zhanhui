package com.zyrc.exhibit.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class ChannelBean {
    private String successed;

    private String code;

    private String message;

    private List<Data> data ;

    public ChannelBean() {
    }

    public ChannelBean(String successed, String code, String message, List<Data> data) {
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

        private int exhibId;

        private String name;

        private String pageTag;

        private String layout;

        private String imageUrl;

        private int menuOrder;

        private int status;

        private String statusStr;

        public Data() {
        }

        public Data(int id, int exhibId, String name, String pageTag, String layout, String imageUrl, int menuOrder, int status, String statusStr) {
            this.id = id;
            this.exhibId = exhibId;
            this.name = name;
            this.pageTag = pageTag;
            this.layout = layout;
            this.imageUrl = imageUrl;
            this.menuOrder = menuOrder;
            this.status = status;
            this.statusStr = statusStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getExhibId() {
            return exhibId;
        }

        public void setExhibId(int exhibId) {
            this.exhibId = exhibId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPageTag() {
            return pageTag;
        }

        public void setPageTag(String pageTag) {
            this.pageTag = pageTag;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(int menuOrder) {
            this.menuOrder = menuOrder;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusStr() {
            return statusStr;
        }

        public void setStatusStr(String statusStr) {
            this.statusStr = statusStr;
        }

    }
}
