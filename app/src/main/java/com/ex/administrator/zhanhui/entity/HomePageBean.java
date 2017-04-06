package com.ex.administrator.zhanhui.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class HomePageBean {
    private String successed;

    private String code;

    private String message;

    private List<Data> data;

    public HomePageBean() {
    }

    public HomePageBean(String successed, String code, String message, List<Data> data) {
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
        private String name;

        private String layoutType;

        private List<DataList> dataList;

        public Data() {
        }

        public Data(String name, String layoutType, List<DataList> dataList) {
            this.name = name;
            this.layoutType = layoutType;
            this.dataList = dataList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLayoutType() {
            return layoutType;
        }

        public void setLayoutType(String layoutType) {
            this.layoutType = layoutType;
        }

        public List<DataList> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataList> dataList) {
            this.dataList = dataList;
        }
    }

    public class DataList {
        private String name;

        private String description;

        private String entityName;

        private String imageUrl;

        private int entityId;

        private String linkUrl;

        private String startDate;

        private String endDate;

        private String city;

        private String price;

        private String hotStatus;

        public DataList() {
        }

        public DataList(String name, String description, String entityName, String imageUrl, int entityId, String linkUrl, String startDate, String endDate, String city, String price, String hotStatus) {
            this.name = name;
            this.description = description;
            this.entityName = entityName;
            this.imageUrl = imageUrl;
            this.entityId = entityId;
            this.linkUrl = linkUrl;
            this.startDate = startDate;
            this.endDate = endDate;
            this.city = city;
            this.price = price;
            this.hotStatus = hotStatus;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getHotStatus() {
            return hotStatus;
        }

        public void setHotStatus(String hotStatus) {
            this.hotStatus = hotStatus;
        }
    }
}
