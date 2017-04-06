package com.ex.administrator.zhanhui.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class SearchTeamBean {
    private int totalPages;

    private int totalElements;

    private String successed;

    private String code;

    private String message;

    private List<Data> data;

    public SearchTeamBean() {
    }

    public SearchTeamBean(int totalPages, int totalElements, String successed, String code, String message, List<Data> data) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.successed = successed;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuccessed() {
        return successed;
    }

    public void setSuccessed(String successed) {
        this.successed = successed;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public class Data {
        private String name;

        private String description;

        private String entityName;

        private String imageUrl;

        private int entityId;

        private String linkUrl;

        private String startDate;

        private String endDate;

        private String city;

        private int price;

        private int oldPrice;

        private int hotStatus;

        public Data() {
        }

        public Data(String name, String description, String entityName, String imageUrl, int entityId, String linkUrl, String startDate, String endDate, String city, int price, int oldPrice, int hotStatus) {
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
            this.oldPrice = oldPrice;
            this.hotStatus = hotStatus;
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

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(int oldPrice) {
            this.oldPrice = oldPrice;
        }

        public int getHotStatus() {
            return hotStatus;
        }

        public void setHotStatus(int hotStatus) {
            this.hotStatus = hotStatus;
        }
    }
}
