package com.zyrc.exhibit.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class DetailExBean implements Serializable{
    private String successed;

    private String code;

    private String message;

    private Data data;

    public DetailExBean() {
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
    public class Data implements Serializable{
        private int id;

        private BusiType busiType;

        private String corp;

        private String name;

        private String description;

        private String imageUrl;

        private String logo;

        private double longitude;

        private double latitude;

        private String imageUrlWidth;

        private String content;

        private Address address;

        private String email;

        private String startDate;

        private String endDate;

        private String minuteOffer;

        private String minuteReal;

        private int hotStatus;

        private String menuOrder;

        private String layout;

        private String parentId;

        private int statusMain;

        private String exhibPosition;

        private String tel;

        private String telBusi;

        private String contactBusi;

        private String countOffer;

        private String countAttend;

        private String countBook;

        private int status;

        private int countView;

        private String countWish;

        private String createdDate;

        private String modified;

        private boolean selChecked;

        private String statusStr;

        private String imageUrlMid;

        private String imageUrlMin;

        private String statusMainStr;

        private String hotStatusStr;

        public Data() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public BusiType getBusiType() {
            return busiType;
        }

        public void setBusiType(BusiType busiType) {
            this.busiType = busiType;
        }

        public String getCorp() {
            return corp;
        }

        public void setCorp(String corp) {
            this.corp = corp;
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

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getImageUrlWidth() {
            return imageUrlWidth;
        }

        public void setImageUrlWidth(String imageUrlWidth) {
            this.imageUrlWidth = imageUrlWidth;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getMinuteOffer() {
            return minuteOffer;
        }

        public void setMinuteOffer(String minuteOffer) {
            this.minuteOffer = minuteOffer;
        }

        public String getMinuteReal() {
            return minuteReal;
        }

        public void setMinuteReal(String minuteReal) {
            this.minuteReal = minuteReal;
        }

        public int getHotStatus() {
            return hotStatus;
        }

        public void setHotStatus(int hotStatus) {
            this.hotStatus = hotStatus;
        }

        public String getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(String menuOrder) {
            this.menuOrder = menuOrder;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getStatusMain() {
            return statusMain;
        }

        public void setStatusMain(int statusMain) {
            this.statusMain = statusMain;
        }

        public String getExhibPosition() {
            return exhibPosition;
        }

        public void setExhibPosition(String exhibPosition) {
            this.exhibPosition = exhibPosition;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTelBusi() {
            return telBusi;
        }

        public void setTelBusi(String telBusi) {
            this.telBusi = telBusi;
        }

        public String getContactBusi() {
            return contactBusi;
        }

        public void setContactBusi(String contactBusi) {
            this.contactBusi = contactBusi;
        }

        public String getCountOffer() {
            return countOffer;
        }

        public void setCountOffer(String countOffer) {
            this.countOffer = countOffer;
        }

        public String getCountAttend() {
            return countAttend;
        }

        public void setCountAttend(String countAttend) {
            this.countAttend = countAttend;
        }

        public String getCountBook() {
            return countBook;
        }

        public void setCountBook(String countBook) {
            this.countBook = countBook;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCountView() {
            return countView;
        }

        public void setCountView(int countView) {
            this.countView = countView;
        }

        public String getCountWish() {
            return countWish;
        }

        public void setCountWish(String countWish) {
            this.countWish = countWish;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public boolean isSelChecked() {
            return selChecked;
        }

        public void setSelChecked(boolean selChecked) {
            this.selChecked = selChecked;
        }

        public String getStatusStr() {
            return statusStr;
        }

        public void setStatusStr(String statusStr) {
            this.statusStr = statusStr;
        }

        public String getImageUrlMid() {
            return imageUrlMid;
        }

        public void setImageUrlMid(String imageUrlMid) {
            this.imageUrlMid = imageUrlMid;
        }

        public String getImageUrlMin() {
            return imageUrlMin;
        }

        public void setImageUrlMin(String imageUrlMin) {
            this.imageUrlMin = imageUrlMin;
        }

        public String getStatusMainStr() {
            return statusMainStr;
        }

        public void setStatusMainStr(String statusMainStr) {
            this.statusMainStr = statusMainStr;
        }

        public String getHotStatusStr() {
            return hotStatusStr;
        }

        public void setHotStatusStr(String hotStatusStr) {
            this.hotStatusStr = hotStatusStr;
        }
    }
    public class Address implements Serializable{
        private String country;

        private String province;

        private String city;

        private String area;

        private String street;

        private String postCode;

        private String contactUserName;

        private String contactTel;

        public Address() {
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getContactUserName() {
            return contactUserName;
        }

        public void setContactUserName(String contactUserName) {
            this.contactUserName = contactUserName;
        }

        public String getContactTel() {
            return contactTel;
        }

        public void setContactTel(String contactTel) {
            this.contactTel = contactTel;
        }
    }
    public class BusiType implements Serializable{
        private int id;

        private String name;

        private String imageUrl;

        private String code;

        private String pageTag;

        private int level;

        private String menuOrder;

        private int parentId;

        private boolean selChecked;

        private String levelName;

        public BusiType() {
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

        public String getPageTag() {
            return pageTag;
        }

        public void setPageTag(String pageTag) {
            this.pageTag = pageTag;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(String menuOrder) {
            this.menuOrder = menuOrder;
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
