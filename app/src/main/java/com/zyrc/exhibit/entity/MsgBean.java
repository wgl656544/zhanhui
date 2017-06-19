package com.zyrc.exhibit.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class MsgBean {
    private int totalPages;

    private int totalElements;

    private String successed;

    private String code;

    private String message;

    private List<Data> data ;

    public MsgBean() {
    }

    public MsgBean(int totalPages, int totalElements, String successed, String code, String message, List<Data> data) {
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

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
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

        private String createdDate;

        private String name;

        private String imageUrl;

        private String description;

        private String entityName;

        private String url;

        private String city;

        private String entityId;

        private int userId;

        private int status;

        private boolean selChecked;

        public Data() {
        }

        public Data(int id, String createdDate, String name, String imageUrl, String description, String entityName, String url, String city, String entityId, int userId, int status, boolean selChecked) {
            this.id = id;
            this.createdDate = createdDate;
            this.name = name;
            this.imageUrl = imageUrl;
            this.description = description;
            this.entityName = entityName;
            this.url = url;
            this.city = city;
            this.entityId = entityId;
            this.userId = userId;
            this.status = status;
            this.selChecked = selChecked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isSelChecked() {
            return selChecked;
        }

        public void setSelChecked(boolean selChecked) {
            this.selChecked = selChecked;
        }
    }
}
