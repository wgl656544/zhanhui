package com.ex.administrator.zhanhui.entity;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class DetailExBean {
    private String successed;
    private String code;
    private String message;
    private Data data;

    public DetailExBean() {
    }

    public DetailExBean(String successed, Data data, String message, String code) {
        this.successed = successed;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public String getSuccessed() {
        return successed;
    }

    public void setSuccessed(String successed) {
        this.successed = successed;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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

    //data
    public class Data {
        private int id;
        private Busitype busiType;
        private String corp;
        private String name;
        private String description;
        private String imageUrl;
        private String imageUrlWidth;
        private String content;
        private Address address;
        private String email;
        private String startDate;
        private String endDate;
        private String minuteOffer;
        private String minuteReal;
        private int hotStatus;
        private int menuOrder;
        private String layout;
        private String parentId;
        private Exhibposition exhibPosition;
        private String tel;
        private String telBusi;
        private String contactBusi;
        private String countOffer;
        private String countAttend;
        private String status;
        private String createdDate;
        private String modified;
        private boolean selChecked;

        public Data() {
        }

        public Data(int id, Busitype busiType, String corp, String name, String description, String imageUrl, String imageUrlWidth, String content, Address address, String email, String startDate, String endDate, String minuteOffer, String minuteReal, int hotStatus, int menuOrder, String layout, String parentId, Exhibposition exhibPosition, String tel, String telBusi, String contactBusi, String countOffer, String countAttend, String status, String createdDate, String modified, boolean selChecked) {
            this.id = id;
            this.busiType = busiType;
            this.corp = corp;
            this.name = name;
            this.description = description;
            this.imageUrl = imageUrl;
            this.imageUrlWidth = imageUrlWidth;
            this.content = content;
            this.address = address;
            this.email = email;
            this.startDate = startDate;
            this.endDate = endDate;
            this.minuteOffer = minuteOffer;
            this.minuteReal = minuteReal;
            this.hotStatus = hotStatus;
            this.menuOrder = menuOrder;
            this.layout = layout;
            this.parentId = parentId;
            this.exhibPosition = exhibPosition;
            this.tel = tel;
            this.telBusi = telBusi;
            this.contactBusi = contactBusi;
            this.countOffer = countOffer;
            this.countAttend = countAttend;
            this.status = status;
            this.createdDate = createdDate;
            this.modified = modified;
            this.selChecked = selChecked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Busitype getBusiType() {
            return busiType;
        }

        public void setBusiType(Busitype busiType) {
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

        public int getMenuOrder() {
            return menuOrder;
        }

        public void setMenuOrder(int menuOrder) {
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

        public Exhibposition getExhibPosition() {
            return exhibPosition;
        }

        public void setExhibPosition(Exhibposition exhibPosition) {
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

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
    }

    //Busitype
    public class Busitype {
        private int id;
        private String name;
        private String imageUrl;
        private String code;
        private int level;
        private int parentId;
        private boolean selChecked;
        private String levelName;

        public Busitype() {
        }

        public Busitype(int id, String name, String imageUrl, String code, int level, int parentId, boolean selChecked, String levelName) {
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

    //Exhibposition
    public class Exhibposition {
        private int id;
        private int exhibId;
        private String positionType;
        private String name;
        private String code;
        private String description;
        private String imageUrl;
        private int posTop;
        private int posLeft;
        private double longitude;
        private double latitude;
        private int status;
        private int parentId;
        private boolean selChecked;

        public Exhibposition() {
        }

        public Exhibposition(int id, int exhibId, String positionType, String name, String code, String description, String imageUrl, int posTop, int posLeft, double longitude, double latitude, int status, int parentId, boolean selChecked) {
            this.id = id;
            this.exhibId = exhibId;
            this.positionType = positionType;
            this.name = name;
            this.code = code;
            this.description = description;
            this.imageUrl = imageUrl;
            this.posTop = posTop;
            this.posLeft = posLeft;
            this.longitude = longitude;
            this.latitude = latitude;
            this.status = status;
            this.parentId = parentId;
            this.selChecked = selChecked;
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

        public String getPositionType() {
            return positionType;
        }

        public void setPositionType(String positionType) {
            this.positionType = positionType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public int getPosTop() {
            return posTop;
        }

        public void setPosTop(int posTop) {
            this.posTop = posTop;
        }

        public int getPosLeft() {
            return posLeft;
        }

        public void setPosLeft(int posLeft) {
            this.posLeft = posLeft;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
    }


    //Address
    public class Address {
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

        public Address(String country, String province, String city, String area, String street, String postCode, String contactUserName, String contactTel) {
            this.country = country;
            this.province = province;
            this.city = city;
            this.area = area;
            this.street = street;
            this.postCode = postCode;
            this.contactUserName = contactUserName;
            this.contactTel = contactTel;
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

}
