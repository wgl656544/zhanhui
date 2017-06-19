package com.zyrc.exhibit.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class ApplyBean {
    private int totalPages;

    private int totalElements;

    private String successed;

    private String code;

    private String message;

    private List<Data> data;

    public ApplyBean() {
    }

    public ApplyBean(int totalPages, int totalElements, String successed, String code, String message, List<Data> data) {
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

    //Data
    public class Data {
        private int id;

        private int exhibId;

        private String typeTag;

        private String busiItem;

        private String description;

        private String username;

        private String mobile;

        private String email;

        private Corp corp;

        private String createdDate;

        private String userId;

        private int status;

        private boolean selChecked;

        public Data() {
        }

        public Data(int id, int exhibId, String typeTag, String busiItem, String description, String username, String mobile, String email, Corp corp, String createdDate, String userId, int status, boolean selChecked) {
            this.id = id;
            this.exhibId = exhibId;
            this.typeTag = typeTag;
            this.busiItem = busiItem;
            this.description = description;
            this.username = username;
            this.mobile = mobile;
            this.email = email;
            this.corp = corp;
            this.createdDate = createdDate;
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

        public int getExhibId() {
            return exhibId;
        }

        public void setExhibId(int exhibId) {
            this.exhibId = exhibId;
        }

        public String getTypeTag() {
            return typeTag;
        }

        public void setTypeTag(String typeTag) {
            this.typeTag = typeTag;
        }

        public String getBusiItem() {
            return busiItem;
        }

        public void setBusiItem(String busiItem) {
            this.busiItem = busiItem;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Corp getCorp() {
            return corp;
        }

        public void setCorp(Corp corp) {
            this.corp = corp;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
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

    public class Corp {
        private int id;

        private String name;

        private String description;

        private BusiType busiType;

        private String imageUrl;

        private String logo;

        private String code;

        private String codeImageUrl;

        private String email;

        private String tel;

        private Address address;

        private String content;

        private String statusIdent;

        private String statusPurch;

        private String statusExhib;

        private String statusService;

        private String createdDate;

        private String modified;

        private boolean selChecked;

        private String userId;

        private String imageUrlMid;

        private String imageUrlMin;

        public Corp() {
        }

        public Corp(int id, String name, String description, BusiType busiType, String imageUrl, String logo, String code, String codeImageUrl, String email, String tel, Address address, String content, String statusIdent, String statusPurch, String statusExhib, String statusService, String createdDate, String modified, boolean selChecked, String userId, String imageUrlMid, String imageUrlMin) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.busiType = busiType;
            this.imageUrl = imageUrl;
            this.logo = logo;
            this.code = code;
            this.codeImageUrl = codeImageUrl;
            this.email = email;
            this.tel = tel;
            this.address = address;
            this.content = content;
            this.statusIdent = statusIdent;
            this.statusPurch = statusPurch;
            this.statusExhib = statusExhib;
            this.statusService = statusService;
            this.createdDate = createdDate;
            this.modified = modified;
            this.selChecked = selChecked;
            this.userId = userId;
            this.imageUrlMid = imageUrlMid;
            this.imageUrlMin = imageUrlMin;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BusiType getBusiType() {
            return busiType;
        }

        public void setBusiType(BusiType busiType) {
            this.busiType = busiType;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeImageUrl() {
            return codeImageUrl;
        }

        public void setCodeImageUrl(String codeImageUrl) {
            this.codeImageUrl = codeImageUrl;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatusIdent() {
            return statusIdent;
        }

        public void setStatusIdent(String statusIdent) {
            this.statusIdent = statusIdent;
        }

        public String getStatusPurch() {
            return statusPurch;
        }

        public void setStatusPurch(String statusPurch) {
            this.statusPurch = statusPurch;
        }

        public String getStatusExhib() {
            return statusExhib;
        }

        public void setStatusExhib(String statusExhib) {
            this.statusExhib = statusExhib;
        }

        public String getStatusService() {
            return statusService;
        }

        public void setStatusService(String statusService) {
            this.statusService = statusService;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

    //busiType
    public class BusiType {
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

        public BusiType(int id, String name, String imageUrl, String code, String pageTag, int level, String menuOrder, int parentId, boolean selChecked, String levelName) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.code = code;
            this.pageTag = pageTag;
            this.level = level;
            this.menuOrder = menuOrder;
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
