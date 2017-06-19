package com.zyrc.exhibit.entity;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class UserPhotoBean {
    private String successed;

    private String code;

    private String message;

    private Data data;

    public UserPhotoBean() {
    }

    public UserPhotoBean(String successed, String code, String message, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int id;

        private String name;

        private String imageUrl;

        private String entityName;

        private String entityId;

        private String menuOrder;

        private String mineType;

        private String status;

        private int userId;

        private String createdDate;

        private boolean selChecked;

        public Data() {
        }

        public Data(int id, String name, String imageUrl, String entityName, String entityId, String menuOrder, String mineType, String status, int userId, String createdDate, boolean selChecked) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.entityName = entityName;
            this.entityId = entityId;
            this.menuOrder = menuOrder;
            this.mineType = mineType;
            this.status = status;
            this.userId = userId;
            this.createdDate = createdDate;
            this.selChecked = selChecked;
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

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(String menuOrder) {
            this.menuOrder = menuOrder;
        }

        public String getMineType() {
            return mineType;
        }

        public void setMineType(String mineType) {
            this.mineType = mineType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public boolean isSelChecked() {
            return selChecked;
        }

        public void setSelChecked(boolean selChecked) {
            this.selChecked = selChecked;
        }
    }
}
