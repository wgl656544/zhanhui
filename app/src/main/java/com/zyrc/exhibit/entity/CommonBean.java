package com.zyrc.exhibit.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class CommonBean implements Serializable{
    private int totalPages;
    private int totalElements;
    private String successed;
    private String code;
    private String message;
    private List<Data> data;

    public CommonBean() {
    }

    public CommonBean(int totalPages, int totalElements, String successed, String code, String message, List<Data> data) {
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

    public class Data implements Serializable,MultiItemEntity{
        public static final int HENG = 1;
        public static final int SHU = 2;
        private int itemType;
        private String name;
        private String description;
        private String entityName;
        private String imageUrl;
        private int    entityId;
        private String linkUrl;
        private String startDate;
        private String endDate;
        private String title;
        private String type;
        private String typeName;
        private String layout;
        private String city;
        private String address;
        private String timeBettwen;
        private String price;
        private String oldPrice;
        private String longitude;
        private String latitude;
        private String count;
        private String amount;
        private int    hotStatus;

        public Data() {
        }

        public Data(String name, String description, String entityName, String imageUrl, int entityId, String linkUrl, String startDate, String endDate, String title, String type, String typeName, String layout, String city, String address, String timeBettwen, String price, String oldPrice, String longitude, String latitude, String count, String amount, int hotStatus) {
            this.name = name;
            this.description = description;
            this.entityName = entityName;
            this.imageUrl = imageUrl;
            this.entityId = entityId;
            this.linkUrl = linkUrl;
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.type = type;
            this.typeName = typeName;
            this.layout = layout;
            this.city = city;
            this.address = address;
            this.timeBettwen = timeBettwen;
            this.price = price;
            this.oldPrice = oldPrice;
            this.longitude = longitude;
            this.latitude = latitude;
            this.count = count;
            this.amount = amount;
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

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTimeBettwen() {
            return timeBettwen;
        }

        public void setTimeBettwen(String timeBettwen) {
            this.timeBettwen = timeBettwen;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getHotStatus() {
            return hotStatus;
        }

        public void setHotStatus(int hotStatus) {
            this.hotStatus = hotStatus;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
